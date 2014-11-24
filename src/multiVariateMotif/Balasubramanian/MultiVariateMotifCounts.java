package multiVariateMotif.Balasubramanian;

import java.util.HashMap;

public class MultiVariateMotifCounts{
	
	private HashMap<MultivariateMotif, Integer> _motifCount;
	
	public MultiVariateMotifCounts(){
		_motifCount = new HashMap<MultivariateMotif, Integer>();
	}
	
	public void append( MultivariateMotif m ){
		if(_motifCount.containsKey(m)){
			int count = _motifCount.get(m);
			count ++;
			_motifCount.put(m, count);
		}else{
			_motifCount.put(m, 1);
		}
		
	}

}
