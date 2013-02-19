package GML_Geometry;

import java.util.ArrayList;

class LinearRing{
	//linear ring contains a series of points
	private int length;
	public ArrayList <Point> pointArray;
	
	public int get_length(){
		length = this.pointArray.size();
		return length;
	}
	
	LinearRing(){
		pointArray = new ArrayList<Point>();
	}
	
	public void Add_Point(Point pt){
		this.pointArray.add(pt);
	}
	
	//for test, print out the points within
	@Override
	public String toString(){
		if (this.pointArray.size() == 0) {
			String msg = "This Linear Ring contains no Point !!";
			return msg;
		} 
		else{
			StringBuilder result = new StringBuilder();
			for (Point pt : pointArray){
				result.append(pt.toString() + ' ');
			}
			return result.toString();
		}
	}
	
}

