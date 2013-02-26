package Utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Detector {
	
	public static String truth_file_name = "D:\\SIG\\output_10_500.txt";
	public static String test_file_name = "D:\\SIG\\truth_10_500.txt";
	
	public static HashMap<String, ArrayList<String>> truth;
	
	public static void main(String[] args){

		String line;
		String ID = "";
		ArrayList <String> content = null;
		truth = new HashMap<String, ArrayList<String>>();
		try{
			BufferedReader truth_file = new BufferedReader(new FileReader(truth_file_name));
			while ((line = truth_file.readLine()) != null){
				if (! ID.equals(line.split(":")[0])){
					if (content != null) {
						truth.put(ID, content);
					}
					ID = line.split(":")[0];
					content = new ArrayList<String>();
					content.add(line.trim());
				}
				else{
					content.add(line.trim());
				}

			}	
			truth_file.close();   

			int count = 0;
			BufferedReader test_file = new BufferedReader(new FileReader(test_file_name));
			while ((line = test_file.readLine()) != null){
				ID = line.split(":")[0];
				if (!truth.containsKey(ID) || !contains(truth.get(ID),line.trim())){
					System.out.println(line);
					count ++;
				}
			}
			test_file.close();
			System.out.println("differents: " + count);
		}
		catch( Exception ex ){
			System.out.println(ex.toString());
		}		
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
