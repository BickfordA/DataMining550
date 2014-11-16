package discretization;

import java.util.ArrayList;

import data.DoubleTimeSeries;
import data.Sequence;
import data.Symbol;
import services.TunableParameterService;

public class Sax {
	
	private int _baseCardinality;
	private int _wordLength;
	
	public Sax(){
		Sax(TunableParameterService.getInstance());
	}

	public void Sax(TunableParameterService paramService){
		_baseCardinality = paramService.getSaxBaseCardinality();
		_wordLength = paramService.getSaxWordLength();
	}
	
	public Sequence dexcritizeTimeSeries(DoubleTimeSeries data, Alphabet alphabet)
	{
		DoubleTimeSeries paa = PiecewiseAggregateApproximation(data, _wordLength);
		
		double[] values = paa.getTimeSeries();
		long[] tstamps = paa.getTimeStamps();

		
		ArrayList<Symbol> saxSequence = new ArrayList<Symbol>();		
		double[] cuts = alphabet.getCuts(_baseCardinality);
		
		
		for(double value: values){
			int rep = 0;
			while(value < cuts[rep] && rep < cuts.length){
				rep ++;
			}
			Symbol repSymbol = new Symbol(rep);
			saxSequence.add(repSymbol);
		}
		return new Sequence(saxSequence, tstamps, data.getStream() );
	}
	
	public DoubleTimeSeries PiecewiseAggregateApproximation(DoubleTimeSeries timeSeries, int paaSize)
	{
		double[] tsValues  = timeSeries.getTimeSeries();
		long[] timeStamps  = timeSeries.getTimeStamps();
		
		int reducedSize = (int) (tsValues.length / paaSize);
		
		double[] aggregateValues = new double[reducedSize];
		
		int locationInOriginal = 0;
		for(int i = 0; i < aggregateValues.length; i ++ ){
			//the approximation is equal to mean value over that time range
			aggregateValues[i] = 0;
			for(int j = i*paaSize; j < (i+1)*paaSize; j ++){
				aggregateValues[i] += tsValues[j];
			}
			aggregateValues[i] = aggregateValues[i] / i;
		}
		
		DoubleTimeSeries retVal  = new DoubleTimeSeries(tsValues, timeSeries.getStream());
		return retVal;
	}
	
	
	
	
	

}
