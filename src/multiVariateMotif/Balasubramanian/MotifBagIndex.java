package multiVariateMotif.Balasubramanian;

import java.util.ArrayList;
import java.util.HashMap;

public class MotifBagIndex {

	private HashMap<MotifBag, ArrayList<Integer>> _MBIndex;
	
	public MotifBagIndex()
	{
		_MBIndex = new HashMap<MotifBag, ArrayList<Integer>>();
	}
	
	public void append(MotifBag bag, int timeStamp){
		if(_MBIndex.containsKey(bag) ){
			ArrayList<Integer> stampSet = _MBIndex.get(bag);
			stampSet.add(timeStamp);
			_MBIndex.put(bag, stampSet);
		}else{
			ArrayList<Integer> newStampSet = new ArrayList<Integer>();
			newStampSet.add(timeStamp);
			_MBIndex.put(bag, newStampSet);
		}
	}
}
