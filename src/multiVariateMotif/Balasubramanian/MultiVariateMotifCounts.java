package multiVariateMotif.Balasubramanian;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import data.MultiDimensionalMotif;

public class MultiVariateMotifCounts{
	
	private HashMap<MultivariateMotifBalasubra, Integer> _motifCount;
	
	public MultiVariateMotifCounts(){
		_motifCount = new HashMap<MultivariateMotifBalasubra, Integer>();
	}
	
	public void append( MultivariateMotifBalasubra m ){
		if(_motifCount.containsKey(m)){
			int count = _motifCount.get(m);
			count ++;
			_motifCount.put(m, count);
		}else{
			_motifCount.put(m, 1);
		}
		
	}
	
	public List<MultiDimensionalMotif> getMotifsWithCounts(){
		ArrayList<MultiDimensionalMotif> motifs = new ArrayList<MultiDimensionalMotif>();
		
		motifs.addAll( _motifCount.keySet());
		
		for(MultiDimensionalMotif m: motifs){
			m.setCount(_motifCount.get(m));
		}
		
		return motifs;
	}

}
