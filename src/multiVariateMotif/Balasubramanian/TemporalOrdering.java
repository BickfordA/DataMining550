package multiVariateMotif.Balasubramanian;

import data.SingleDimensionalMotif;
import data.TimeSeries;

public class TemporalOrdering {
		private int _streamA;
		private int _orderA; 
		private int _streamB;
		private int _orderB;
		private TemporalRelationEnum _relationship;

	public TemporalOrdering(int streamA, int orderA, int streamB, int orderB, TemporalRelationEnum relationShip)
	{
		_streamA = streamA;
		_streamB = streamB;
		
		_orderA = orderA;
		_orderB = orderB;
		
		_relationship = relationShip;
	}
	
	public TemporalOrdering(SingleDimensionalMotif seriesA, SingleDimensionalMotif seriesB)
	{
		//check for each of the thirteen cases
		int startA = seriesA.start();
		int endA = seriesA.end();
		
		int startB = seriesB.start();
		int endB = seriesB.end();
		
			
		//equal
		if(startA == startB && endA == endB){
			_relationship = TemporalRelationEnum.Equal;
		}
		//meets
		else if(startA == (endB+1) || startB == (endA + 1)){
			_relationship = TemporalRelationEnum.Meets;
		}
		//overlaps
		else if((startA <= endB && startA > startB && endA > endB) || (startB <= endB && startB > startA && endB > endA)){
			_relationship = TemporalRelationEnum.Overlaps;
		}
		//during
		else if((startA > startB && endA < endB) || (startB > startA && endB < endA)){
			_relationship = TemporalRelationEnum.During;
		}
		//starts
		else if(startA == startB){
			_relationship = TemporalRelationEnum.Starts;
		}
		//finishes
		else if(endA == endB){
			_relationship = TemporalRelationEnum.Finishes; 
		}
		//before
		else if(endA < startB || endB < startA){
			_relationship = TemporalRelationEnum.Before;
		}
		
		_streamA = seriesA.getStream();
		_streamB = seriesB.getStream();
	}
	
	// getters
	public int getStreamA() {return _streamA; }
	public int getStreamB() {return _streamB; }
	public int getOrderA() {return _orderA; }
	public int getOrderB() {return _orderB; }
	public TemporalRelationEnum getRelation() { return _relationship;}
	
	public boolean equals(Object obj){
		if(obj instanceof TemporalOrdering) {
			TemporalOrdering other = ((TemporalOrdering) obj);
			boolean equal = true;
			
			equal &= (_streamA == other._streamA);
			equal &= (_streamB == other._streamB);
			equal &= (_orderA == other._orderA);
			equal &= (_orderB == other._orderB);
			
			return equal;
		}
		return false;
	}
}
