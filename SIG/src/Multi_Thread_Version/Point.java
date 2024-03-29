package Multi_Thread_Version;

public class Point extends element{
	public double x,y;
	
	Point(double x,double y)
	{
		this.x=x;
		this.y=y;
	}
	
	Point (double x, double y, int id, int seq){
		this.x=x;
		this.y=y;
		this.ID = id;
		this.Seq = seq;
	}
	
	@Override 
	public String toString(){
		StringBuilder result = new StringBuilder();
		result.append(Double.toString(x)); 
		result.append(",");
		result.append(Double.toString(y));
		return result.toString();
	}
	
	public String Print_Out(){
		StringBuilder result = new StringBuilder();
		result.append(Integer.toString(ID)); 
		result.append(":");
		result.append(Integer.toString(Seq));
		return result.toString();
	}
	
	public boolean Equals(Point pt){
		if ((this.x == pt.x) && (this.y == pt.y)){
			return true;
		}
		else return false;
	}
	
	public double Distance(Point pt){
		return Math.sqrt(Math.pow((this.x - pt.x), 2) + Math.pow((this.y - pt.y), 2));
	}
		
	//check if the point is within a certain distance of the polygon
	public boolean Within(Polygon poly, Double dist){	
		if (this.Out_of_Bound(poly)){
			switch(this.Quadrant(poly.BB)){
				case 1: {
					if (this.Distance(new Point(poly.BB.left,poly.BB.up))>= dist) return false;
					else break; 
				}
				case 2:{
					if (this.y - poly.BB.up >= dist) return false;
					else break;
				}
				case 3:{
					if (this.Distance(new Point(poly.BB.right,poly.BB.up))>= dist) return false;
					else break;  
				}
				case 4:{
					if (this.x - poly.BB.right >= dist) return false;
					else break;
				}
				case 5:{
					if (this.Distance(new Point(poly.BB.right,poly.BB.bottom))>= dist) return false;
					else break;
				}
				case 6:{
					if (poly.BB.bottom - this.y >= dist) return false;
					else break;
				}
				case 7:{
					if (this.Distance(new Point(poly.BB.left,poly.BB.bottom))>= dist) return false;
					else break;  
				}
				case 8:{
					if (poly.BB.left - this.x >= dist) return false;
					else break;
				}
				default: System.out.println("something is wrong");
						 break;
			} 
		}

		//check all the points on the outer boundary
		for (Point pt: poly.OB.lr.pointArray){
			if (this.Distance(pt) < dist) {
				return true;
			}
			else continue;
		}
		
		//check all the points on the inner boundary
		if (poly.IBs != null){
			for (InnerBoundary IB: poly.IBs){
				for (Point pt: IB.lr.pointArray){
					if (this.Distance(pt) < dist) {
						return true;
					}
					else continue;
				}
			}
		} 
		
		//check if the point is inside the polygon
		if (this.ArcInside(poly)){
			return true;
		}

		return false;
	}
	
	//check if the point is outside the bounding box of the polygon
	public boolean Out_of_Bound(Polygon poly){
		return (this.x > poly.BB.right) || (this.x < poly.BB.left) || (this.y > poly.BB.up) || (this.y < poly.BB.bottom);	
	}
	
	//return the positional relationship between the point and the bounding box of polygon
	/*  1   2  3
	 *  8  BB  4
	 *  7   6  5
	 */ 
	public int Quadrant(BoundingBox BB){
		if (this.x <= BB.left && this.y >= BB.up) return 1;
		if (this.x > BB.left && this.x < BB.right && this.y >= BB.up) return 2;
		if (this.x >= BB.right && this.y >= BB.up) return 3;
		if (this.x >= BB.right && this.y < BB.up && this.y > BB.bottom) return 4;
		if (this.x >= BB.right && this.y <= BB.bottom) return 5;
		if (this.x > BB.left && this.x < BB.right && this.y <= BB.bottom) return 6;
		if (this.x <= BB.left && this.y <= BB.bottom) return 7;
		if (this.x < BB.left && this.y > BB.bottom && this.y < BB.up) return 8;
		return 0;
	}
	
	// redefine a function to check if the point is inside a polygon
	public boolean ArcInside(Polygon poly){
		
		int wn = 0;;
		int i,j;
		double tempvalue;
		
	    if (this.Out_of_Bound(poly)){
			return false;
	    }
	    else {
			
			// check all the segments of the out boundary
            for (i = 0; i< poly.OB.lr.pointArray.size()-1;i++){
				
				Point st_pt = poly.OB.lr.pointArray.get(i);
				Point end_pt = poly.OB.lr.pointArray.get(i + 1);
				
				Segment seg = new Segment(st_pt,end_pt);
				
				if (seg.Point_on_Segment(this) == true) return true; // it means the point is in the segment
				else {
					
					tempvalue = (end_pt.x-st_pt.x)*(this.y-st_pt.y)-(end_pt.y-st_pt.y)*(this.x-st_pt.x);
					if (this.y >= st_pt.y && this.y < end_pt.y)
					{ // above the segment
						if (tempvalue >0) wn = wn + 1;
				    }
				    else if (this.y < st_pt.y && this.y >= end_pt.y)
				    {// below the segment
						if (tempvalue <0) wn = wn - 1;
				    }
				}
			}
            
            /// check all the segments of all the inner boundaries	
            if (poly.IBs != null){
            		
            	for (InnerBoundary IB: poly.IBs){
            		for (j=0; j<IB.lr.pointArray.size()-1; j++){
            				
            			Point st_pt = IB.lr.pointArray.get(j);
            			Point end_pt = IB.lr.pointArray.get(j + 1);
            			Segment seg = new Segment(st_pt,end_pt);
            				
            			if (seg.Point_on_Segment(this) == true) return true;  // it means the point is in the segment
            			else {
            				
            				tempvalue = (end_pt.x-st_pt.x)*(this.y-st_pt.y)-(end_pt.y-st_pt.y)*(this.x-st_pt.x);
            				if (this.y >= st_pt.y && this.y < end_pt.y){ // above the segment
            					if (tempvalue >0) wn = wn + 1;
            				}
            				else if (this.y < st_pt.y && this.y >= end_pt.y){// below the segment
            					if (tempvalue <0) wn = wn - 1;
            				}
            			}
            		}
            	}	
            }
            		 
            if (wn == 0) return false;
            else return true;
            		
		}
	}
	
	//check if the point is inside the polygon
	public boolean Inside(Polygon poly){
		
		int count_intersection = 0;
		int n_intersection = 0;
		
		if (this.Out_of_Bound(poly)) {
			return false;
		}
		else{
			Ray r = new Ray(this.x, this.y);
			//check all the segments of the outer boundary
			for (int i = 0; i< poly.OB.lr.pointArray.size()-1;i++){
				
				Point st_pt = poly.OB.lr.pointArray.get(i);
				Point end_pt = poly.OB.lr.pointArray.get(i + 1);
				
				Segment seg = new Segment(st_pt,end_pt);
				n_intersection = r.Segment_Intersection(seg);
				
				//point on the segment
				if (n_intersection == -1){
					return true;
				}
				else
				{
					count_intersection += n_intersection;
				}
			}
			
			//check all the segments of the inner boundary
			if (poly.IBs != null){
				for (InnerBoundary IB: poly.IBs){
					for (int j = 0; j < IB.lr.pointArray.size()-1; j++){
						Point st_pt = IB.lr.pointArray.get(j);
						Point end_pt = IB.lr.pointArray.get(j + 1);
						
						Segment seg = new Segment(st_pt,end_pt);
						n_intersection = r.Segment_Intersection(seg);
						
						//point on the segment
						if (n_intersection == -1){ 
							return true;
						}
						else
						{
							count_intersection += n_intersection;
						}
					}
				}
			} 
		}
		
		
		if (count_intersection % 2 == 0 ){
			return false;
		}
		else return true;
	}
}