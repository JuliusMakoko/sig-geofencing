package Multi_Thread_Version;

import java.util.ArrayList;

public class Polygon extends element {
	
	//polygon contains one outer boundary and zero or more inner boundaries
	public OuterBoundary OB;
	public ArrayList <InnerBoundary> IBs;
	
	//Bounding box
	public BoundingBox BB;
	
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
			IBs.add(inBound);
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
			 //result.append("Polygon" + NEW_LINE);
			 //result.append(this.ID + ":" + this.Seq + NEW_LINE);
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

