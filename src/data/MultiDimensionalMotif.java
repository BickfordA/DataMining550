package data;

import java.util.ArrayList;

public class MultiDimensionalMotif {
	
	protected ArrayList<SingleDimensionalMotif> _subMotifs;
	protected int _count;
	
	
	public void setCount(int count){ _count = count;}
	public int getCount(){ return _count;}
	public ArrayList<SingleDimensionalMotif>getMotifs() {return _subMotifs;}
}
