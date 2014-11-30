package multiVariateMotif.Balasubramanian;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
//			System.out.print(m.getMotifs().size() + " - hh ");
//			System.out.println("hit");
		}else{
//			System.out.println("miss");
			_motifCount.put(m, 1);
		}
		
	}
	
	public ArrayList<MultiDimensionalMotif> getMotifsWithCounts(){
		ArrayList<MultiDimensionalMotif> motifs = new ArrayList<MultiDimensionalMotif>();
		
		Iterator it = _motifCount.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry) it.next();
			MultiDimensionalMotif m = (MultiDimensionalMotif)pair.getKey();
			//System.out.print(m.getMotifs().size() + " - dd ");
			motifs.add((MultiDimensionalMotif)pair.getKey());
		}
		
		return motifs;
	}

}
