package GML_Geometry;

import java.util.HashMap;

public class Polygon_File {
	private HashMap<Integer, Moving_Polygon> Polygons;
	private Predicate option;
	
	public int n_Polygons;
	public int n_MPs;
	public static String NEW_LINE = System.getProperty("line.separator");
	
	Polygon_File(String cmdln){
		Polygons = new HashMap<Integer, Moving_Polygon>();
		this.option = new Predicate(cmdln);
		n_Polygons = 0;
		n_MPs = 0;
	}
	
	public void Add(Polygon p){
		if (this.Polygons.containsKey(p.ID)){
			Polygons.get(p.ID).Add(p);
		}
		else{
			Moving_Polygon MP = new Moving_Polygon(p.ID);
			MP.Add(p);
			Polygons.put(p.ID, MP);
		}
	}
	
	@Override
	public String toString(){
		 StringBuilder result = new StringBuilder();
		 String NEW_LINE = System.getProperty("line.separator");
		 result.append("Number of Polygons: " + this.n_Polygons + NEW_LINE);
		 result.append("Number of Moving Polygons: " + this.n_MPs + NEW_LINE);
		 result.append(NEW_LINE);
		 for (Moving_Polygon mp:this.Polygons.values()){
			 result.append(mp.toString() + NEW_LINE);
		 }
		 return result.toString();
	}
	
	
	//sort each moving polygon in the map based on time stamp
	public void Arrange(){
		for (Moving_Polygon MP: Polygons.values()){
			MP.Sort();
			n_MPs ++;
			n_Polygons += MP.n_Status;
		}
	}
	
	public String Process_One_Pt(Point pt){	
		StringBuilder result = new StringBuilder();
		String msg = "";
		switch(option.p){
			case INSIDE:
				for (Moving_Polygon MP: Polygons.values()) {
					msg = MP.Predicate_Inside(pt);
					if (! msg.equals("")) result.append(msg + NEW_LINE);
				}
				break;
			
			case WITHIN:
				for (Moving_Polygon MP: Polygons.values()) {
					msg = MP.Predicate_Within(pt, option.Dist);
					if (! msg.equals("")) result.append(msg + NEW_LINE);
				}
				break;
				
			default:
				break;
		}
		return result.toString();
	}
}
	

enum Pred{ INSIDE,WITHIN }


class Predicate {
	Pred p;
	Double Dist;
	
	Predicate(String cmdln){
		if (cmdln.equals("INSIDE")){
			p = Pred.INSIDE;
		}
		else{
			p = Pred.WITHIN;
			Dist = Double.parseDouble(cmdln.substring(cmdln.indexOf(" ")+1));
		}
	}
}
