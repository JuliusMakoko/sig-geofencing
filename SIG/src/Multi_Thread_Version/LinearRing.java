package Multi_Thread_Version;

import java.util.ArrayList;

public class LinearRing{
	//linear ring contains a series of points
	private int length;
	public ArrayList <Point> pointArray;
	
	public int get_length(){
		//length = this.pointArray.size();
		return length;
	}
	
	LinearRing(){
		pointArray = new ArrayList<Point>();
		this.length = 0;
	}
	
	public void Add_Point(Point pt){
		this.pointArray.add(pt);
		this.length ++;
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
			String NEW_LINE = System.getProperty("line.separator");
			for (Point pt : pointArray){
				result.append(pt.toString() + NEW_LINE);
			}
			return result.toString();
		}
	}
	
}

