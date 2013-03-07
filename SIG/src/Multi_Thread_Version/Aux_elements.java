package Multi_Thread_Version;

//Auxiliary geometry elements for the point inside polygon operation

//horizontal ray 
class Ray{
	double start;
	double height;
	Point source_pt;
	
	//a ray starts from a source point and goes horizontally to the right 
	public Ray(double st, double h){
		this.start = st;
		this.height = h;
		
		source_pt = new Point(st,h);
	}
	
	public boolean Point_on_Ray(Point pt){
		return (pt.y == this.height) && (pt.x >= this.start);
	}
	
	public boolean Point_Above_Ray(Point pt){
		return pt.y > this.height;
	}
	
	public boolean Point_Below_Ray(Point pt){
		return pt.y < this.height;
	}
	
	//calculate the number of intersections of the ray originated from the point and the segment
	//return -1 if the starting point of the ray is on the segment
	public int Segment_Intersection(Segment seg){
		//starting point of the ray is on the segment (point on polygon)
		if (seg.Point_on_Segment(this.source_pt)){
			return -1;
		}
		
		//starting point of the ray goes through at least one of the point of the segment.
	     //if none the point is below the ray, count 0
	     if (this.Point_on_Ray(seg.start_pt) && this.Point_on_Ray(seg.end_pt)){
	    	 return 0;
	     }
	     
	     if (this.Point_on_Ray(seg.start_pt) && this.Point_Above_Ray(seg.end_pt)){
	    	 return 0;
	     }
	     
	     if (this.Point_on_Ray(seg.end_pt) && this.Point_Above_Ray(seg.start_pt)){
	    	 return 0;
	     }

	     //if one of the point is below the ray, count 1
	     if (this.Point_on_Ray(seg.start_pt) && this.Point_Below_Ray(seg.end_pt)){
	    	 return 1;
	     }
	     
	     if (this.Point_on_Ray(seg.end_pt) && this.Point_Below_Ray(seg.start_pt)){
	    	 return 1;
	     }

	     //ray does not go through vertex of the segment
	     //segment is vertical (90 degree)
	     if (seg.start_pt.x == seg.end_pt.x){
	    	 //ray and segment have one intersection
	    	 if ((seg.start_pt.x > this.start) &&
	    			 ((seg.start_pt.y - this.height)*(seg.end_pt.y - this.height) < 0)){
	    		 return 1;
	    	 }
	    	 //no intersection
	    	 else{
	    		 return 0;
	    	 }
	     }
	     
	     //segment is horizontal, ray goes through the segment has been considered before
	     else if (seg.start_pt.y == seg.end_pt.y){
	    	 return 0;
	     }
	     
	     //find the intersection of the line defined by the segment and the line defined by the ray
	     //see if the intersection is on the segment and on the ray
	     else{
	    	  double inter_x = (this.source_pt.y - seg.start_pt.y)*(seg.end_pt.x - seg.start_pt.x)/(seg.end_pt.y - seg.start_pt.y) + seg.start_pt.x;
	    	  double inter_y = this.height;
	    	  Point intersection = new Point(inter_x,inter_y);
	    	  
	    	  if (seg.Point_on_Segment(intersection) && (intersection.x > this.start)) {
	    		  return 1;
	    	  }
	    	  else return 0;
	     }
	}
}

//segment of the polygon
class Segment{
	
	Point start_pt;
	Point end_pt;
	private double length;
	
	//the length of the segment
	public double get_length(){
		return this.length;
	}
	
	//initialize the segment and calculate the length
	public Segment(Point st_pt, Point e_pt){
		this.start_pt = st_pt;
		this.end_pt = e_pt;
		this.length = start_pt.Distance(end_pt);
	}
	
	//check if the point is on the segment
	public boolean Point_on_Segment(Point pt){
		Segment sub_seg_1, sub_seg_2;
		sub_seg_1 = new Segment(this.start_pt,pt);
		sub_seg_2 = new Segment(pt,this.end_pt);
		
		if (this.get_length() == sub_seg_1.get_length() + sub_seg_2.get_length()){
			return true;
		}
		else {
			return false;
		}
	}
}