package data;

import java.util.Arrays;

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
	
	public DoubleTimeSeries(double[] data, long[] timeStamps, int stream)
	{
		_data = data;
		_timeStamps = timeStamps;
		_stream = stream;
	}
	
	public double[] getTimeSeries() { return _data;}
	
	public long[] getTimeStamps() {return _timeStamps; }
	
	public long start(){return _timeStamps[0];}
	
	public long end(){ return _timeStamps[_timeStamps.length-1]; }
	
	public int getStream() { return _stream; }

	public int size() {
		return _data.length;
	}

	public DoubleTimeSeries cloneSubSection(int start, int stop) {	
		double[] subData = Arrays.copyOfRange(_data, start, stop);
		long[] subRange = Arrays.copyOfRange(_timeStamps, start, stop);
		return new DoubleTimeSeries(subData,  subRange, _stream);
	}
	
	public Double distance(DoubleTimeSeries other){
		double distance = Integer.MAX_VALUE;
		
		if(this.size() ==  other.size()){
			distance = 0;
			for(int i = 0 ; i < this.size(); i ++){
				double temp = this._data[i] - other._data[i];
				distance += (temp *temp);
			}
			//could do this to make it euclidean
			//distance = Math.sqrt(distance);
		}
		
		return distance;
	}
}

