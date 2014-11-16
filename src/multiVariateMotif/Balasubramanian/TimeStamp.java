package multiVariateMotif.Balasubramanian;

import java.util.ArrayList;

import sequitur.SequiturUtil;
import data.Sequence;
import data.SingleDimensionalMotif;
import data.DoubleTimeSeries;

public class TimeStamp {
	//store the all of the series for each of the time stamps here
	private Sequence[] _subSeries;
	
	public TimeStamp(Sequence[] series){
		_subSeries = series;
	}
	
	public ArrayList<SingleDimensionalMotif> getSubMotifs(){
		SequiturUtil sq = new SequiturUtil();
		for(Sequence currentSequence: _subSeries){
			ArrayList<SingleDimensionalMotif>  foundMotifs =  sq.getSequiturMotifs(currentSequence);
			for(SingleDimensionalMotif s: foundMotifs){
				s.
			}
		}
		
		return foundMotifs;
	}
}
