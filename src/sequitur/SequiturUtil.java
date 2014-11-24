package sequitur;

import java.util.ArrayList;
import java.util.Arrays;

import data.Sequence;
import data.SingleDimensionalMotif;
import data.Symbol;

public class SequiturUtil {
	
	public ArrayList<SingleDimensionalMotif> getSequiturMotifs(Sequence seq)
	{
		ArrayList<SingleDimensionalMotif> motifsFound =  new ArrayList<SingleDimensionalMotif>();

	    Rule.numRules = 0;
	    SequiturSymbol.theDigrams.clear();
	    
		Rule firstRule = new Rule();
		
		long[] locations = seq.getTimeStamps();
		
		System.out.print(seq.size() + "  - ");
		for(int i = 0 ; i < seq.size(); i ++){
			//load the sequence
			//nasty zeros break everything, i think they may be interpreted as null ?
			char charRep =(char) (seq.getSymbol(i).getSymbol() + 1); 
			System.out.print(seq.getSymbol(i).getSymbol());
			
			firstRule.last().insertAfter(new Terminal(charRep, i));
			firstRule.last().p.check();
		}
		
//		System.out.print(" motifs found -");
//		for(String s: firstRule.getAllStrings()){
//			System.out.println( s); //int chars dont show up .. not worth much 
//		}
		//get the rules
		ArrayList<SequiturMotif> motifs  = firstRule.getRuleList();
		System.out.println("motifs pulled out of Sequitur");
		
		
		//convert to thesingle variate motif representation
		for(SequiturMotif sm: motifs){
			ArrayList<Character> charMotif = sm.getChars();
			ArrayList<Symbol> symbols = new ArrayList<Symbol>();
			for(Character c: charMotif){
				symbols.add(new Symbol((int) c - 1));
			}
			int[] motifLocations = sm.getIndicies();
			
			for(int loc: motifLocations){
			//create a new motif for each entry (collapse these down later?) aaaahhhh!!!
				long[] motifPeriod = Arrays.copyOfRange(locations, loc , loc+symbols.size());
				Sequence newSeq = new Sequence(symbols, motifPeriod, seq.getStream());
				SingleDimensionalMotif newMotif = new SingleDimensionalMotif( newSeq, seq.getStream());
				motifsFound.add(newMotif);
			}
		}
		//System.out.println("motifs found");
		return motifsFound;
	}
	
	

}
