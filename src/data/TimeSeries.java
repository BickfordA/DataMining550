package data;

public class TimeSeries {
	private double[] _data;
	
	//Constructor
	public TimeSeries(double[] data)
	{
		_data = data;
	}
	
	public double[] getTimeSeries()
	{
		return _data;
	}
}

