package multiVariateMotif.Balasubramanian;

import java.util.ArrayList;

import data.SingleDimensionalMotif;

public class MultivariateMotif {
	
	private ArrayList<SingleDimensionalMotif> _subMotifs;
	TemporalOrderingTuple _ordering;

	public MultivariateMotif( ArrayList<SingleDimensionalMotif> motifs, TemporalOrderingTuple ordering){
		_subMotifs = motifs;
		_ordering = ordering;
	}
	
	public ArrayList<SingleDimensionalMotif>getMotifs() {return _subMotifs;}
	public TemporalOrderingTuple getOrder() { return _ordering; } 
	
}
