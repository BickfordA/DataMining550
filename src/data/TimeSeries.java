package data;

public class TimeSeries {
	private int _stream; //which variate this stream belongs to
	private double[] _data; // the actual data (may want to create another class with char instead of doubles)
	private int[] _timeStamps; // the time stamps that go with the data
	
	//Constructor
	public TimeSeries(double[] data, int stream)
	{
		_data = data;
		_timeStamps = new int[data.length];
		for( int i = 0; i < _timeStamps.length; i ++){
			_timeStamps[i] = i;
		}
	}
	
	public double[] getTimeSeries()
	{
		return _data;
	}
	
	public int start(){
		return _timeStamps[0];
	}
	
	public int end(){
		return _timeStamps[_timeStamps.length-1];
	}
}

