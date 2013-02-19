package GML_Geometry;

import java.io.*;
import java.util.TreeMap;

/*
 * this is the entry point
 */

public class Test_GML {
	
	public static String poly_file_name = "D:\\SIG\\polys10.txt";
	public static String pt_file_name = "D:\\SIG\\points500.txt";
	public static String out_file_name = "D:\\SIG\\output_10_500.txt";
	public static String cmdln = "INSIDE";
	public static String NEW_LINE = System.getProperty("line.separator");
	
	public static TreeMap<Integer, String> results;
	
	public static void main(String[] args) {
		
		if (! Validate_cmdln(cmdln)){
			System.out.println("Illigal predicate string !!");
			return;
		}
		
		Polygon_File PF = new Polygon_File(cmdln);
		GML_Polygon_Reader poly_gr = new GML_Polygon_Reader(poly_file_name);

		results = new TreeMap<Integer,String>();
		
		try{
			//build index for polygons 
			poly_gr.Build_Polygon_Index(PF);
			
			//testing of the polygon_file and GML_polygon_Reader
			//System.out.println(PF.toString());
			
			BufferedReader pt_file = new BufferedReader(new FileReader(pt_file_name));
			String line;
			String msg;
			while ((line = pt_file.readLine()) != null){
				
				//testing of the point and GML_Point_Parser
				//msg = GML_Point_Parser.Parse_Point(line).Print_Out();
				//System.out.println(msg);
				
				Point pt = GML_Point_Parser.Parse_Point(line);
				msg = PF.Process_One_Pt(pt);
				
				//for out put
				if (! msg.equals("")){
					if (results.containsKey(pt.ID)) {
						StringBuilder result = new StringBuilder();			
						result.append(results.get(pt.ID));
						result.append(msg);
						results.put(pt.ID, result.toString());						
					}
					else {
						StringBuilder result = new StringBuilder();			
						result.append(msg);
						results.put(pt.ID, result.toString());	
					}
				}
				
			}	
			pt_file.close();   
			
			//write the result to the file			
			BufferedWriter out_file = new BufferedWriter(new FileWriter(out_file_name));
			for (String result:results.values()){
				out_file.write(result);
			}
			
			out_file.close();
		
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
