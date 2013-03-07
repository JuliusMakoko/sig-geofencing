package Multi_Thread_Version;

public class InnerBoundary{
	//inner boundary contains one linear ring
	public LinearRing lr;
	InnerBoundary(LinearRing lring){
		this.lr = lring;
	}
	
	@Override
	public String toString(){
		StringBuilder result = new StringBuilder();
		String NEW_LINE = System.getProperty("line.separator");
		result.append("Inner_Boundary" + NEW_LINE);
		result.append(lr.toString() + NEW_LINE);
		return result.toString();
	}
}