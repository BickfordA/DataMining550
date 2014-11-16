package data;

public class DoubleTimeSeries {
	private int _stream; //which variate this stream belongs to
	private double[] _data; // the actual data (may want to create another class with char instead of doubles)
	private long[] _timeStamps; // the time stamps that go with the data
	
	//Constructor
	public DoubleTimeSeries(double[] data, int stream)
	{
		_data = data;
		_timeStamps = new long[data.length];
		for( int i = 0; i < _timeStamps.length; i ++){
			_timeStamps[i] = (long) i;
		}
	}
	
	public double[] getTimeSeries() { return _data;}
	
	public long[] getTimeStamps() {return _timeStamps; }
	
	public long start(){return _timeStamps[0];}
	
	public long end(){ return _timeStamps[_timeStamps.length-1]; }
	
	public int getStream() { return _stream; }
}

