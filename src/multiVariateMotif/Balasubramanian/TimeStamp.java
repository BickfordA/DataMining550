package multiVariateMotif.Balasubramanian;

import java.util.ArrayList;

import com.sun.org.apache.xpath.internal.FoundIndex;

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
	
	public ArrayList<SingleDimensionalMotif> getSubMotifs()
	{
		ArrayList<SingleDimensionalMotif> foundMotifs = new ArrayList<SingleDimensionalMotif>();
		
		for(Sequence currentSequence: _subSeries){
			SequiturUtil sq = new SequiturUtil();
			ArrayList<SingleDimensionalMotif>  seriesMotifs =  sq.getSequiturMotifs(currentSequence);
			foundMotifs.addAll(seriesMotifs);
		}
		System.gc();
		
		return foundMotifs;
	}
}
