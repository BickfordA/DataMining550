package multiVariateMotif.Balasubramanian;

import java.util.ArrayList;

import org.apache.commons.math3.util.MultidimensionalCounter;

import data.MultiDimensionalMotif;
import data.SingleDimensionalMotif;

public class MultivariateMotifBalasubra extends MultiDimensionalMotif{
	
	TemporalOrderingTuple _ordering;

	public MultivariateMotifBalasubra( ArrayList<SingleDimensionalMotif> motifs, TemporalOrderingTuple ordering){
		super(motifs);
		_ordering = ordering;
	}
	
	public TemporalOrderingTuple getOrder() { return _ordering; } 
	
	public int hashCode() {
		
		return 13;
	}
	
	public boolean equals(Object obj){
		
		boolean match = false;
		if(obj instanceof MultivariateMotifBalasubra ){
			MultivariateMotifBalasubra other = (MultivariateMotifBalasubra) obj;
			match = true; 
			match &= other._ordering.equals(this._ordering);
//			if(match == true) System.out.print("match os true");
			match &= other._subMotifs.equals( this._subMotifs );
					
		}
		
		return match;
	}
}
