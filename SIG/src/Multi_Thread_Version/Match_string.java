package Multi_Thread_Version;

public class Match_string {
	
	public int matchstring(String string1, String string2){
		
		int i;
		for (i=0; i<string1.length(); i++){
			
			if (string1.charAt(i)!= string2.charAt(i)){
				break;
			}
		}
		
		return i;
	}
}
