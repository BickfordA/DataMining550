package multiVariateMotif.Balasubramanian;

import data.SingleDimensionalMotif;
import data.DoubleTimeSeries;

public class TemporalOrdering {
		private long _streamA;
		private long _orderA; 
		private long _streamB;
		private long _orderB;
		private TemporalRelationEnum _relationship;

	public TemporalOrdering(long streamA, long orderA, long streamB, long orderB, TemporalRelationEnum relationShip)
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
		long startA = seriesA.start();
		long endA = seriesA.end();
		
		long startB = seriesB.start();
		long endB = seriesB.end();
		
			
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
	public long getStreamA() {return _streamA; }
	public long getStreamB() {return _streamB; }
	public long getOrderA() {return _orderA; }
	public long getOrderB() {return _orderB; }
	public TemporalRelationEnum getRelation() { return _relationship;}
	
	public boolean equals(Object obj){
		if(obj instanceof TemporalOrdering) {
			TemporalOrdering other = ((TemporalOrdering) obj);
			boolean equal = true;
			
			equal &= (_streamA == other._streamA);
			equal &= (_streamB == other._streamB);
			equal &= (_orderA == other._orderA);
			equal &= (_orderB == other._orderB);
			//System.out.println("temporal ordering mismatch");
			return equal;
		}
		return false;
	}
}
