package GML_Geometry;

class Point extends element{
	double x,y;
	
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
	
	//check if the point is inside the polygon
	public boolean Inside(Polygon poly){
		
		int count_intersection = 0;
		int n_intersection = 0;
		
		if (this.Out_of_Bound(poly)) {
			return false;
		}
		else{
			Ray r = new Ray(this.x, this.y);
			//check all the segments of the outter boundary
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
	
	//check if the point is within a certain distance of the polygon
	public boolean Within(Polygon poly, Double dist){
		
		//check all the points on the outter boundary
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
		else return false;
	}
	
	//check if the point is outside the bounding box of the polygon
	public boolean Out_of_Bound(Polygon poly){
		return (this.x > poly.BB.right) || (this.x < poly.BB.left) || (this.y > poly.BB.up) || (this.y < poly.BB.bottom);	
	}
	
	// redefine a function to check if the point is inside a polygon
	public boolean ArcInside(Polygon poly){
		
		int wn1 = 0;
		int wn2 = 0;
		int wn;
		int i,j;
		double tempvalue;
		
	    if (this.Out_of_Bound(poly)){
			return false;
	    }
	    else {
			
			// check all the segments of the outboundary
            for (i = 0; i< poly.OB.lr.pointArray.size()-1;i++){
				
				Point st_pt = poly.OB.lr.pointArray.get(i);
				Point end_pt = poly.OB.lr.pointArray.get(i + 1);
				
				Segment seg = new Segment(st_pt,end_pt);
				
				if (seg.Point_on_Segment(this) == true) return true; // it means the point is in the segment
				else {
					
					tempvalue = (end_pt.x-st_pt.x)*(st_pt.y-this.y)-(end_pt.y-st_pt.y)*(st_pt.x-this.x);
					if (this.y >= st_pt.y && this.y < end_pt.y)
					{ // above the segment
						if (tempvalue >0) ++wn1;
				    }
				    else if (this.y < st_pt.y && this.y >= end_pt.y)
				    {// below the segment
						if (tempvalue <0) --wn1;
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
            				
            				tempvalue = (end_pt.x-st_pt.x)*(st_pt.y-this.y)-(end_pt.y-st_pt.y)*(st_pt.x-this.x);
            				if (this.y >= st_pt.y && this.y < end_pt.y){ // above the segment
            					if (tempvalue >0) ++wn2;
            				}
            				else if (this.y < st_pt.y && this.y >= end_pt.y){// below the segment
            					if (tempvalue <0) --wn2;
            				}
            			}
            		}
            	}	
            }
            		 
            wn = wn1 + wn2;
            if (wn == 0) return false;
            else return true;
            		
		}
	}
}