package GML_Geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


//one moving polygon will contain a list of polygons with different shape
public class Moving_Polygon {
	private ArrayList <Polygon> poly_list;
	int ID;
	int n_Status;
	
	Moving_Polygon(int id){
		this.ID = id;
		this.poly_list = new ArrayList <Polygon>();
	}
	
	public void Add(Polygon p){
		this.poly_list.add(p);
	}
	
	//sort the polygon in the list in an ascending order based on time stamp
	public void Sort(){
		Collections.sort(this.poly_list, new PolygonComparable());
		this.n_Status = this.poly_list.size();
	}
	
	//check if the point is inside any polygon in the list of time sequence
	public String Predicate_Inside(Point pt){
		if (pt.Seq < this.poly_list.get(0).Seq){
			return "";
		}
		else{
			//find the latest polygon based on the time stamp of the point
			int index = 0;
			for (int i=0;i<this.poly_list.size()-1;i++){
				if (pt.Seq < this.poly_list.get(i).Seq){
					index = i -1;
					break;
				}	
			}
			Polygon poly = this.poly_list.get(index);
			if (pt.Inside(poly)){
				return pt.Print_Out() + poly.Print_Out();
			}
			else return "";
		}	
	}
	
	//check if the point is within any polygon in the list of time sequence
	public String Predicate_Within(Point pt, Double dist){
		if (pt.Seq < this.poly_list.get(0).Seq){
			return "";
		}
		else{
			//find the latest polygon based on the time stamp of the point
			int index = 0;
			for (int i=0;i<this.poly_list.size()-1;i++){
				if (pt.Seq < this.poly_list.get(i).Seq){
					index = i -1;
					break;
				}	
			}
			
			Polygon poly = this.poly_list.get(index);
			if (pt.Within(poly, dist)){
				return pt.Print_Out() + poly.Print_Out();
			}
			else return "";
		}	
	}
	
	@Override
	public String toString(){
		 if (this.poly_list.size() == 0) {
			 return "Empty polygon list !";
		 }
		 else{
			 StringBuilder result = new StringBuilder();
			 String NEW_LINE = System.getProperty("line.separator");
			 
			 for (Polygon p: poly_list){
				 result.append(p.Print_Out() + NEW_LINE);
			 }
			 return result.toString();
		 }
	}
}

//compare two polygons based on time stamp
class PolygonComparable implements Comparator<Polygon>{
 
    public int compare(Polygon p1, Polygon p2) {
        return (p1.Seq > p2.Seq ? 1 : (p1.Seq == p2.Seq ? 0 : -1));
    }   
}



