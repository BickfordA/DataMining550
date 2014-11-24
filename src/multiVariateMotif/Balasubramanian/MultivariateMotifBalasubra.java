package multiVariateMotif.Balasubramanian;

import java.util.ArrayList;

import org.apache.commons.math3.util.MultidimensionalCounter;

import data.MultiDimensionalMotif;
import data.SingleDimensionalMotif;

public class MultivariateMotifBalasubra extends MultiDimensionalMotif{
	
	TemporalOrderingTuple _ordering;

	public MultivariateMotifBalasubra( ArrayList<SingleDimensionalMotif> motifs, TemporalOrderingTuple ordering){
		_subMotifs = motifs;
		_ordering = ordering;
	}
	
	public TemporalOrderingTuple getOrder() { return _ordering; } 
	
}
