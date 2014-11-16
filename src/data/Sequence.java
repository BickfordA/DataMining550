package data;

import java.util.ArrayList;

public class Sequence {

	private int _stream;
	private ArrayList<Symbol> _sequence;
	private long[] _timeStamps;
	
	public Sequence( ArrayList<Symbol> newSequence){
		_sequence = newSequence;
	}
	
	//getters
	public ArrayList<Symbol> getSequence() { return _sequence; }
	public long[] getTimeStamps(){ return _timeStamps; }
	public int getStream(){ return _stream; }
}
