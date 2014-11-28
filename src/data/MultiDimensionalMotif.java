package data;

import java.util.ArrayList;

public class MultiDimensionalMotif {
	
	protected ArrayList<SingleDimensionalMotif> _subMotifs;
	protected int _count;
	protected double _statisticalSignificance;
	
	
	public void setCount(int count){ _count = count;}
	public int getCount(){ return _count;}
	public ArrayList<SingleDimensionalMotif>getMotifs() {return _subMotifs;}
	
	public void setStatSig(double value){ _statisticalSignificance = value;}
	public double getStatSig(){ return _statisticalSignificance;}
}
