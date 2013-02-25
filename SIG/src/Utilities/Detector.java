package Utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Detector {
	
	public static String test_file_name = "D:\\SIG\\polys10.txt";
	public static String truth_file_name = "D:\\SIG\\points500.txt";
	
	public static HashMap<String, ArrayList<String>> truth;
	
	public static void main() throws IOException{
		BufferedReader truth_file = new BufferedReader(new FileReader(truth_file_name));
		String line;
		String ID = "";
		ArrayList <String> content = null;
		while ((line = truth_file.readLine()) != null){
			if (! ID.equals(line.split(":")[0])){
				truth.put(ID, content);
				content = new ArrayList<String>();
				content.add(line);
			}
			else{
				content.add(line);
			}
			
		}	
		truth_file.close();   
		
		BufferedReader test_file = new BufferedReader(new FileReader(test_file_name));
		while ((line = test_file.readLine()) != null){
			ID = line.split(":")[0];
			if (!truth.containsValue(ID) || !contains(truth.get(ID),line)){
				System.out.println(line);
			}
		}
		test_file.close();   
	}

	public static boolean contains(ArrayList<String> content, String line){
		if (content == null) return false;
		else{
			for (String s: content){
				if (s.equals(line)){
					return true;
				}
			}
			return false;
		}
	}
}
