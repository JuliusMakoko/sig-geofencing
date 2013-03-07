package Multi_Thread_Version;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.TreeMap;

public class Output {
	private TreeMap<Integer, String> results;
	private String out_file_name;
	
	public Output(String file_name){
		results = new TreeMap<Integer,String>();
		this.out_file_name = file_name;
	}
	
	public synchronized void Add(int id, String value){
		if (results.containsKey(id)) {
			StringBuilder result = new StringBuilder();			
			result.append(results.get(id));
			result.append(value);
			results.put(id, result.toString());						
		}
		else {
			StringBuilder result = new StringBuilder();			
			result.append(value);
			results.put(id, result.toString());	
		}
	}
	
	public void write_to_file(){
		try{
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
	
}
