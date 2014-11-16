package multiVariateMotif.Balasubramanian;

import java.util.ArrayList;

import data.SingleDimensionalMotif;
import data.TimeSeries;

public class TimeStamp {
	//store the all of the series for each of the time stamps here
	private TimeSeries[] _subSeries;
	
	public TimeStamp(TimeSeries[] series){
		_subSeries = series;
	}
	
	public ArrayList<SingleDimensionalMotif> getSubMotifs(){
		ArrayList<SingleDimensionalMotif>  foundMotifs = new ArrayList<SingleDimensionalMotif>(0);
		
		
		//return the set of all the single variate motifs found accross all of the dimension
		
		//make the motif's TimeSeries relative to the motif time stamp
		
		
		return foundMotifs;
	}
}
