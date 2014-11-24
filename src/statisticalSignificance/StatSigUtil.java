package statisticalSignificance;

import java.util.ArrayList;
import java.util.HashMap;

import data.MultiDimensionalMotif;
import data.Sequence;
import data.Symbol;

public class StatSigUtil {
	
	static void assignStatisicalSignificance(ArrayList<MultiDimensionalMotif> motifsFound, Sequence[] sequenceData)
	{
		
		
	}
	
	//M1
	public double calculateBernoulliProbability(Sequence motif, Sequence inputSequence){
		double probability = 0; 
		
		HashMap<Integer, Integer> symbolCount = new HashMap<Integer, Integer>();
		
		//get a list of all of the unique symbols
		for(Symbol s: motif.getSequence()){
			symbolCount.put(s.getSymbol(), 0);
		}
		
		int symbol;
		//count the instances of each symbol
		for(Symbol s: inputSequence.getSequence()){
			symbol = s.getSymbol();
			if(symbolCount.containsKey(symbol)){
				int count = symbolCount.get(symbol) + 1;
				symbolCount.put(symbol,count);
			}
		}
		
		
		return probability;
	}

}
