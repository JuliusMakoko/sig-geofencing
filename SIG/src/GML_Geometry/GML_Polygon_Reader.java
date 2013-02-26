package GML_Geometry;

import java.io.*;

public class GML_Polygon_Reader {
	String input_file_name;
	Polygon_File PF;
	
	public GML_Polygon_Reader(String file_name){
		this.input_file_name = file_name;
	}
	
	public GML_Polygon_Reader(){
		
	}
	
	public void Build_Polygon_Index(Polygon_File PF) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(input_file_name));;
		try{
			
			String line;
			while ((line = in.readLine()) != null){
				PF.Add(Parse_Polygon(line));
			}
			
			//sort the polygon list of each moving polygon 
			PF.Arrange();
		}
		catch( Exception ex ){
			System.out.println(ex.toString());
		}
		finally{
			in.close();
		}
			
	}
	
	public Polygon Parse_Polygon(String line){
		int start, end, pos;
		String head_str, gml_str;
		String[] temp_str_array, coord_array;
		
			
		pos = line.indexOf('<');
		head_str = line.substring(0,pos);
		temp_str_array = head_str.split(":");
		gml_str = line.substring(pos);
		
		Polygon poly = new Polygon(Integer.parseInt(temp_str_array[1]),Integer.parseInt(temp_str_array[2]));

		start = gml_str.indexOf("<");
		end  =  gml_str.indexOf(">");

		String tag = gml_str.substring(start,end + 1);
		StringBuilder tag_name = new StringBuilder(); 
		tag_name.append(tag.substring(tag.indexOf("<"), tag.indexOf(" ")));
		tag_name.append(">");
		tag_name.insert(1, "/");

		//remove the tag of polygon
		gml_str = gml_str.substring(end + 1, gml_str.indexOf(tag_name.toString()));

		//now the string contains the outer boundary and some inner boundaries
		start = gml_str.indexOf("<");
		end  =  gml_str.indexOf(">");
		tag = gml_str.substring(start,end + 1);

		//get the coordinates of the points of the outer boundary
		if (tag.contains("outerBoundaryIs")){
			tag_name = new StringBuilder().append(tag);
			tag_name.insert(1, "/");

			//remove the tags of the outerBoundary
			String tag_value = gml_str.substring(end + 1, gml_str.indexOf(tag_name.toString()));
			gml_str = gml_str.substring(gml_str.indexOf(tag_name.toString()) + tag_name.length());

			start = tag_value.indexOf("<gml:coordinates");
			end   = tag_value.indexOf("</gml:coordinates>");
			String tmp_str = tag_value.substring(start,end); 
			String Coord = tmp_str.substring(tmp_str.indexOf(">") + 1);
			coord_array = Coord.split(" ");

			//retrieve the coordinates of each point
			String [] xy_array;
			LinearRing lr_OB = new LinearRing();
			for (String xy: coord_array){
				xy_array = xy.split(",");
				Point pt = new Point(Double.parseDouble(xy_array[0]),Double.parseDouble(xy_array[1]));
				lr_OB.Add_Point(pt);
			}

			OuterBoundary OB = new OuterBoundary(lr_OB);
			poly.Set_Out_Boundary(OB);

		}

		//get the coordinates of the points of the inner boundary
		while (gml_str.equals("") == false){
			start = gml_str.indexOf("<");
			end  =  gml_str.indexOf(">");
			tag = gml_str.substring(start,end + 1);

			if (tag.contains("innerBoundaryIs")){
				tag_name = new StringBuilder().append(tag);
				tag_name.insert(1, "/");

				//remove the tags of the outerBoundary
				String tag_value = gml_str.substring(end + 1, gml_str.indexOf(tag_name.toString()));
				//int i1 = gml_str.indexOf(tag_name.toString());
				//int i2 = tag_name.length();
				gml_str = gml_str.substring(gml_str.indexOf(tag_name.toString()) + tag_name.length());
				
				start = tag_value.indexOf("<gml:coordinates");
				end   = tag_value.indexOf("</gml:coordinates>");
				String tmp_str = tag_value.substring(start,end); 
				String Coord = tmp_str.substring(tmp_str.indexOf(">") + 1);
								
				coord_array = Coord.split(" ");

				LinearRing lr_IB = new LinearRing();
				String [] xy_array;
				for (String xy: coord_array){
					xy_array = xy.split(",");
					Point pt = new Point(Double.parseDouble(xy_array[0]),Double.parseDouble(xy_array[1]));
					lr_IB.Add_Point(pt);
				}

				InnerBoundary IB = new InnerBoundary(lr_IB);
				poly.Add_Inner_Boundary(IB);
			}
		}
		return poly;
	}
	
}
