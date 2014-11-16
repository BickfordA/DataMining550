package sequitur;

import java.util.ArrayList;
import java.util.Arrays;

import data.Sequence;
import data.SingleDimensionalMotif;
import data.Symbol;

public class SequiturUtil {
	
	private char[] _charAlphabet = { 'a', 'b', 'c', 'd',  'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 
			'p', 'q','r','s','t','u','v','w','x','y','z'};
	
	
	public ArrayList<SingleDimensionalMotif> getSequiturMotifs(Sequence seq)
	{
		ArrayList<SingleDimensionalMotif> motifsFound =  new ArrayList<SingleDimensionalMotif>();
		
		Rule firstRule = new Rule();
		
		long[] locations = seq.getTimeStamps();
		
		for(int i = 0 ; i < seq.size(); i ++){
			//load the sequence
			char charRep = (char) seq.getSymbol(i).getSymbol(); 
			firstRule.last().insertAfter(new Terminal(charRep, i));
			firstRule.last().p.check();
		}
		
		//get the rules
		ArrayList<SequiturMotif> motifs  = firstRule.getRuleList();
		
		
		
		//convert to thesingle variate motif representation
		for(SequiturMotif sm: motifs){
			ArrayList<Character> charMotif = sm.getChars();
			ArrayList<Symbol> symbols;
			for(Character c: charMotif){
				symbols.add(new Symbol((int) c));
			}
			long[] motifLocation = Arrays.copyOf(locations, sm.)
			Sequence newSeq = new Sequence(symbols);
			SingleDimensionalMotif newMotif = new SingleDimensionalMotif( newSeq, seq.getStream())
		}
		
		return motifsFound;
	}
	
	

}
