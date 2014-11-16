package sequitur;

import java.util.ArrayList;

public class SequiturMotif {
	private int[]  _indicies; //start position
	private ArrayList<Character> _motifChars;
	
	public SequiturMotif(int[] indicies, ArrayList<Character> motif) {
		_indicies = indicies;
		_motifChars = motif;
	}
	
	public ArrayList<Character> getChars(){
		return _motifChars;
	}
	
	public int[] getIndicies(){
		return _indicies;
	}
	
	
}
