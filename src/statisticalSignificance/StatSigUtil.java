package statisticalSignificance;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.math3.util.MathUtils;

import data.MultiDimensionalMotif;
import data.Sequence;
import data.SingleDimensionalMotif;
import data.Symbol;

public class StatSigUtil {
	
	public static void assignStatisicalSignificance(ArrayList<MultiDimensionalMotif> motifsFound, Sequence[] sequenceData)
	{
		ArrayList< HashMap< Integer, Integer> >  dimensionalSymbolCount = new ArrayList< HashMap< Integer, Integer> >();
		for(Sequence dimension: sequenceData){
			HashMap< Integer , Integer> symbolCount = countChars(dimension);
			dimensionalSymbolCount.add(symbolCount);
		}
		
		for(MultiDimensionalMotif m: motifsFound){
			double significance = 1;
			
			System.out.println("sdm length: " + m.getMotifs().size());
			
			for(SingleDimensionalMotif sdm : m.getMotifs()){
				//compute the probability
				double subSignificance = 0;
				int possibleWordCount = sequenceData[sdm.getStream()].size() ;
				int motifCount = motifCount(sdm.getTimeSeries(), sequenceData[sdm.getStream()]);
				
				double prob = calculateBernoulliProbability(dimensionalSymbolCount.get(sdm.getStream()), sdm, possibleWordCount);
				
				long permutations = permutaionCount(possibleWordCount, sdm.getTimeSeries().size());
				//System.out.println("perm: " + permutations);
				//System.out.println("prob: " + prob);
				//System.out.println("count: " + motifCount);
				boolean err = false;
				for(int i = 0; i < motifCount; i ++){
					double d2 = Math.pow(1 - prob, possibleWordCount - sdm.getTimeSeries().size());
					if(d2 == 0){
						d2 = Double.MIN_VALUE;
					}
					subSignificance +=   Math.pow(prob, i) * permutations *  d2 ;
					if(subSignificance <= 0  && ! err){
						err = true;
						System.out.println("perm " + permutations + " pow " + Math.pow(prob, i)  + " sec pow " +Math.pow(1 - prob, possibleWordCount - sdm.getTimeSeries().size()));
					}
				}
				//System.out.println("sub sig:"+ subSignificance + " ");
				significance *= subSignificance;
			}
			m.setStatSig(significance);
			System.out.println("       sts:"+significance);
		}
	}
	
	
	//M1 get the counts for all of the different symbols
	private static HashMap<Integer, Integer> countChars( Sequence inputSequence){
		
		HashMap<Integer, Integer> symbolCount = new HashMap<Integer, Integer>();
		
		
		int symbol;
		//count the instances of each symbol
		for(Symbol s: inputSequence.getSequence()){
			symbol = s.getSymbol();
			if(symbolCount.containsKey(symbol)){
				int count = symbolCount.get(symbol) + 1;
				symbolCount.put(symbol,count);
			}else{
				symbolCount.put(symbol, 0);
			}
		}
		
		for(Integer i: symbolCount.keySet()){
			System.out.print("symbol : " + i +" ");
		}
		System.out.println("donez");
		
		return symbolCount;
	}
	
	private static double calculateBernoulliProbability(HashMap< Integer, Integer> symbolCounts, SingleDimensionalMotif motif, int totalCount )
	{
		double prob = 1; 
		
		//multiply the number of words together. 
		for(Symbol s: motif.getTimeSeries().getSequence()){
			int v = s.getSymbol();
//			System.out.println(" v: "+ v + " count "+ symbolCounts.get(v));
			double sub = (double) symbolCounts.get(v) / totalCount;
			prob = sub * prob;
			if(prob > 1  || prob < 0 ){// this should never happen 
				System.out.println("prob result: " + prob + "sub" + sub + " total count: " + totalCount +" sub count: " + symbolCounts.get(v) );
			}
//			System.out.println("prob result: " + prob + "sub" + sub);
		}
		
//		System.out.println("prob result: " + (prob)+ "total: "+ totalCount);
		
		return prob;
	}
	
	private static int motifCount(Sequence motif, Sequence fullSeries)
	{
		int count = 0; 
		
		for(int i = 0; i < fullSeries.size() - motif.size(); i ++){
			boolean match = true;;
			for(int j = 0 ; j < motif.size(); j ++){
				match &= (motif.getSequence().get(j).getSymbol() == fullSeries.getSequence().get(i + j).getSymbol());
			}
			if (match){
				count ++;
			}	
		}
		
		return count;
	}

	private static long permutaionCount(int number, int chosen)
	{
		long returnVal = 1;
		long numberSub = number;
		for(int i = 0 ; i < chosen; i ++){
			returnVal *= numberSub;
			numberSub --;
		}
		long chosenFactorial = 1;
		for(int i = 1; i <= chosen; i ++ ){
			chosenFactorial *= i; 
		}
		
		return returnVal / chosenFactorial;
	}
}
