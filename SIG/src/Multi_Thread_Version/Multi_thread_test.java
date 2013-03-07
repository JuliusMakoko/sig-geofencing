package Multi_Thread_Version;

import java.text.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/*
 * this is the entry point
 */

public class Multi_thread_test {
	
	public static String poly_file_name = "D:\\SIG\\TrainingDataSet\\poly15.txt";
	public static String pt_file_name =   "D:\\SIG\\TrainingDataSet\\points1000.txt";
	public static String out_file_name = "D:\\SIG\\TestingResult\\within_15_1000.txt";
	public static String cmdln = "WITHIN 1000";
	public static String NEW_LINE = System.getProperty("line.separator");
	
	public static Polygon_File PF;
	public static Output out;
	private static ExecutorService exec = Executors.newCachedThreadPool();
	
	static class PointChecker implements Runnable{
		public void run(){
			while(true){
				String line = Text_file_reader.get_new_line();
				if (line == "") break;
				else{
						Point pt = GML_Point_Parser.Parse_Point(line);
						String msg = PF.Process_One_Pt(pt);
						//add for output
						if (! msg.equals("")) out.Add(pt.ID,msg);
					}
			}
		}
	}
	
	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis();
	
		if (! Validate_cmdln(cmdln)){
			System.out.println("Illigal predicate string !!");
			return;
		}
		
		
		try{
			//initialize output
			out = new Output(out_file_name);
			
			//build index for polygons
			PF = new Polygon_File(cmdln);
			GML_Polygon_Reader poly_gr = new GML_Polygon_Reader(poly_file_name);
			poly_gr.Build_Polygon_Index(PF);
			
			//open point file
			Text_file_reader.open_file(pt_file_name);
			
			//Initialize 4 other threads
			for (int i = 0;i < 4;i++)
				exec.execute(new PointChecker());
			
			//no other thread accepted
			exec.shutdown();
			
			//let the main thread wait till all the threads terminates
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			
			//close point file
			Text_file_reader.close_file();   
			
			//write output to disk
			out.write_to_file();
			
			//for the execution time
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			
			NumberFormat formatter = new DecimalFormat("#0.00000");
			System.out.println("Execution time is " + formatter.format(totalTime / 1000d) + " seconds");
			
			//for the total memory
			NumberFormat format = NumberFormat.getInstance();
			long used  = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			StringBuilder mem = new StringBuilder();	
			mem.append("Memory used: " + format.format(used / 1024 / 1024) + "M");
			System.out.println(mem.toString());
		
		}
		catch( Exception ex ){
			System.out.println(ex.toString());
		}		
	}
	
	public static boolean Validate_cmdln(String cmdln){
		if (cmdln.equals("INSIDE")){
			return true;
		}
		else{
			String [] tmp_str_array = cmdln.split(" ");
			if (tmp_str_array.length > 2){
				return false;
			}
			else{
				if (! tmp_str_array[0].equals("WITHIN")){
					return false;
				}
				else{
					 double dist = Double.parseDouble(tmp_str_array[1]);
					 if (dist < 0){
						 return false;
					 }
				}
				return true;
			}
		}
	}
}
