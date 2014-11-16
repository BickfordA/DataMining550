package multiVariateMotif.Balasubramanian;

import java.util.ArrayList;
import java.util.HashMap;

public class MotifOrderIndex {

	private HashMap<TemporalOrderingTuple, ArrayList<Integer>> _MOIndex;
	
	public MotifOrderIndex()
	{
		_MOIndex = new HashMap<TemporalOrderingTuple, ArrayList<Integer>>();
	}

	public void append(TemporalOrderingTuple tuple, int timeStamp)
	{
		if(_MOIndex.containsKey(tuple)){
			ArrayList<Integer> stampSet = _MOIndex.get(tuple);
			stampSet.add(timeStamp);
			_MOIndex.put(tuple, stampSet);
		}else{
			ArrayList<Integer> stampSet = new ArrayList<Integer>();
			stampSet.add(timeStamp);
			_MOIndex.put(tuple, stampSet);
		}
		
	}
	
}
