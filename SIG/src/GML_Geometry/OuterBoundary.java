package GML_Geometry;

public class OuterBoundary{
	//outer boundary contains one linear ring
	public LinearRing lr;
	OuterBoundary(LinearRing lring){
		this.lr = lring;
	}
	
	@Override
	public String toString(){
		StringBuilder result = new StringBuilder();
		String NEW_LINE = System.getProperty("line.separator");
		result.append("Outer_Boundary" + NEW_LINE);
		result.append(lr.toString());
		return result.toString();
	}
}

