package GML_Geometry;

import java.util.ArrayList;

class Polygon extends element {
	
	//polygon contains one outer boundary and zero or more inner boundaries
	OuterBoundary OB;
	ArrayList <InnerBoundary> IBs;
	
	//Bounding box
	BoundingBox BB;
	
	public Polygon(int id, int seq){
		this.ID = id;
		this.Seq = seq;
	}
	
	public void Set_Out_Boundary(OuterBoundary outBound){
		this.OB = outBound;
		
		//initialize and calculate the bounding box
		this.BB = new BoundingBox(this.OB);
	}
	
	public void Add_Inner_Boundary(InnerBoundary inBound){
		if (this.IBs == null){
			IBs = new ArrayList <InnerBoundary>();
		} 
		else{
			IBs.add(inBound);
		}
	}
	
	@Override
	public String toString(){
		if (this.OB == null && this.IBs == null){
			String msg = "Polygon has no content !!";
			return msg;
		}
		else{
			 StringBuilder result = new StringBuilder();
			 String NEW_LINE = System.getProperty("line.separator");
			 result.append("Polygon" + NEW_LINE);
			 result.append(this.ID + ":" + this.Seq + NEW_LINE);
			 result.append(OB.toString()+ NEW_LINE);
			
			 if (this.IBs != null){
				for (InnerBoundary IB: IBs){
					result.append(IB.toString() + NEW_LINE);
				}
			 }
			 return result.toString();
		}
	}
	
	public String Print_Out(){
		StringBuilder result = new StringBuilder();
		result.append(Integer.toString(ID)); 
		result.append(":");
		result.append(Integer.toString(Seq));
		return result.toString();
	}
}

//Bounding box of the polygon
class BoundingBox {
	public double left;
	public double right;
	public double bottom;
	public double up;
	
	public BoundingBox(OuterBoundary OB){
		int i = 0;
		for (Point pt: OB.lr.pointArray){
			if (i == 0){
				left = pt.x;
				right = pt.x;
				up = pt.y;
				bottom = pt.y;
				i = 1;
			} 
			else{
				left   = Math.min(left, pt.x);
				right  = Math.max(right, pt.x);
				up	   = Math.max(up, pt.y);
				bottom = Math.min(bottom, pt.y);
			}
		}
	}
}