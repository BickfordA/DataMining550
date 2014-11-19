package data;

import java.util.ArrayList;

public class Sequence {

	private int _stream;
	private ArrayList<Symbol> _sequence;
	private long[] _timeStamps;
	
	public Sequence( ArrayList<Symbol> newSequence, long[] timeStamps, int stream){
		_sequence = newSequence;
		_stream = stream;
		_timeStamps = timeStamps;
	}
	
	//getters
	public ArrayList<Symbol> getSequence() { return _sequence; }
	public long[] getTimeStamps(){ return _timeStamps; }
	public int getStream(){ return _stream; }
	
	public int size(){return _sequence.size();}
	public Symbol getSymbol(int i){ return _sequence.get(i);}
	
	public long start(){return _timeStamps[0]; }
	public long end() {return _timeStamps[_timeStamps.length -1]; }
	
	public char[] generateCharArray()
	{
		char[] charRepresentation = new char[_sequence.size()] ;
		
		for(int i = 0 ; i < _sequence.size(); i++){
			charRepresentation[i] = (char) _sequence.get(i).getSymbol();
		}
		
		return charRepresentation;
	}
}
