package multiVariateMotif.Balasubramanian;

import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

public class TemporalOrderingTuple {
	
	ArrayList<TemporalOrdering> _tuple;
	
	public void TemporalOrdering(ArrayList<TemporalOrdering> tuple)
	{
		_tuple = tuple;
	}
	
	public ArrayList<TemporalOrdering> getTuple()
	{
		return _tuple;
	}
	
	public int size(){
		return _tuple.size();
	}
	
	public boolean equals(Object obj)
	{
		boolean equal = false;
		if(obj instanceof TemporalOrderingTuple){
			equal = true;
			
			TemporalOrderingTuple other = (TemporalOrderingTuple) obj;
			
			equal &= (other.size() == size());
			for(TemporalOrdering o : _tuple){
				equal &= other._tuple.contains(o);
			}
			
		}
		return equal;
	}
	
	public int hashCode(Object obj)
	{
		int retVal = 1;
		for(TemporalOrdering o: _tuple){
			retVal *= o.hashCode();
		}
		return retVal;
	}
}
