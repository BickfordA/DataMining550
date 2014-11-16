package multiVariateMotif.Balasubramanian;

import java.util.ArrayList;

import data.SingleDimensionalMotif;

public class MotifBag {
	
	private ArrayList<SingleDimensionalMotif> _motifs;
	
	public MotifBag( ArrayList<SingleDimensionalMotif> motifs)
	{
		_motifs = motifs;
	}
	
	public boolean equals( Object obj ){
		boolean equal = false;
		if(obj instanceof MotifBag){
			MotifBag other = (MotifBag) obj;
			
			equal = true;
			for(SingleDimensionalMotif m : _motifs){
				equal &= other._motifs.contains(m);
			}
			
		}
		return equal;
	}
	
	public int hashCode(Object obj)
	{
		int retVal = 1;
		for(SingleDimensionalMotif m : _motifs){
			retVal *= m.hashCode();
		}
		return retVal;
	}

}
