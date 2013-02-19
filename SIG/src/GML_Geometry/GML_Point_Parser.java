package GML_Geometry;

class GML_Point_Parser {
	
	public static Point Parse_Point(String line){
		
		int start, end, pos;
		String head_str, gml_str;
		String[] temp_str_array;
			
		pos = line.indexOf('<');
		head_str = line.substring(0,pos);
		temp_str_array = head_str.split(":");
		gml_str = line.substring(pos);
		start = gml_str.indexOf("<gml:coordinates");
		end = gml_str.indexOf("</gml:coordinates>");
		String tmp_str = gml_str.substring(start,end); 
		
		start = tmp_str.indexOf("<");
		end = tmp_str.indexOf(">");
		
		String Coord = tmp_str.substring(end + 1);
		
		String [] xy_array = Coord.split(",");
		
		Point pt = new Point(Double.parseDouble(xy_array[0]), Double.parseDouble(xy_array[1]),
				Integer.parseInt(temp_str_array[1]),Integer.parseInt(temp_str_array[2]));
		return pt;
	}
}
