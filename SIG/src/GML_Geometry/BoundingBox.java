package GML_Geometry;

//Bounding box of the polygon
public class BoundingBox {
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
