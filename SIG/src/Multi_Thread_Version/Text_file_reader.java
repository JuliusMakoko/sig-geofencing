package Multi_Thread_Version;

import java.io.BufferedReader;
import java.io.FileReader;

public class Text_file_reader {
	private static BufferedReader in_file;

	public static void open_file(String file_name){
		try{
			in_file = new BufferedReader(new FileReader(file_name));
		}
		catch( Exception ex ){
			System.out.println(ex.toString());
		}	
	}
	
	public static void close_file(){
		try{
			in_file.close();
		}
		catch( Exception ex ){
			System.out.println(ex.toString());
		}	
	}
	
	public static synchronized String get_new_line(){
		String line = "";
		try{
			 line = in_file.readLine();
			 if (line == null) line = "";
		}
		catch( Exception ex ){
			System.out.println(ex.toString());
		}	
		return line;
	}
}
