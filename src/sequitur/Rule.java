package sequitur;


/*
This class is part of a Java port of Craig Nevill-Manning's Sequitur algorithm.
Copyright (C) 1997 Eibe Frank

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/


import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.Hashtable;


public class Rule{

  // Guard symbol to mark beginning
  // and end of rule.

  public Guard theGuard;

  // Counter keeps track of how many
  // times the rule is used in the
  // grammar.

  public int count;

  // The total number of rules.

  public static int numRules = 0;

  // The rule's number.
  // Used for identification of
  // non-terminals.

  public int number;

  // Index used for printing.

  public int index;
  
  protected static Set<Integer> indexes = new TreeSet<Integer>();


  Rule(){
    number = numRules;
    numRules++;
    theGuard = new Guard(this);
    count = 0;
    index = 0;
    
    theRules.put(numRules, this);
  }

  public SequiturSymbol first(){
    return theGuard.n;
  }
  
  public SequiturSymbol last(){
    return theGuard.p;
  }

  public void addIndex(int position){
	  indexes.add(position);
  }
  
  //original get rule funtion
  public String getRules(){
    
    Vector rules = new Vector(numRules);
    Rule currentRule;
    Rule referedTo;
    SequiturSymbol sym;
    int index;
    int processedRules = 0;
    StringBuffer text = new StringBuffer();
    int charCounter = 0;

    text.append("Usage\tRule\n");
    rules.addElement(this);
    while (processedRules < rules.size()){
      currentRule = (Rule)rules.elementAt(processedRules);
      text.append(" ");
      text.append(currentRule.count);
      text.append("\tR");
      text.append(processedRules);
      text.append(" -> ");
      for (sym=currentRule.first();(!sym.isGuard());sym=sym.n){
	if (sym.isNonTerminal()){
	  referedTo = ((NonTerminal)sym).r;
	  if ((rules.size() > referedTo.index) &&
	      ((Rule)rules.elementAt(referedTo.index) ==
	       referedTo)){
	    index = referedTo.index;
	  }else{
	    index = rules.size();
	    referedTo.index = index;
	    rules.addElement(referedTo);
	  }
	  text.append('R');
	  text.append(index);
	}else{
	  if (sym.value == ' '){
	    text.append('_');
	  }else{
	    if (sym.value == '\n'){
	      text.append("\\n");
	    }else
	      text.append((char)sym.value);
	  }
	}
	text.append(' ');
      }
      text.append('\n');
      processedRules++;
    }
    return new String(text);
  }

	
	public ArrayList<SequiturMotif> getRuleList()
	{
		ArrayList<SequiturMotif> ruleArray = new ArrayList<SequiturMotif>();
		

	    Vector rules = new Vector(numRules);
	    Rule currentRule;
	    Rule referedTo;
	    SequiturSymbol sym;
	    int index;
	    int processedRules = 0;
	    StringBuffer text = new StringBuffer();
	    int charCounter = 0;

	    text.append("Usage\tRule\n");
	    rules.addElement(this);
	    while (processedRules < rules.size()){
	    	ArrayList<Character> charRule = getCharRepresentation();
	    	int[] indicies = getIndexes();
	    	ruleArray.add(new SequiturMotif( indicies, charRule));
	    	processedRules++;
	    }
		
		return ruleArray;
	}
	
	public int[] getIndexes() {
		int[] indexValues = new int[indexes.size()];
		
		int i = 0;
		for(int idx : indexes){
			indexValues[i] = idx;
			i++;
		}
		
		return indexValues;
	}
	
	public ArrayList<Character> getCharRepresentation()
	{
		ArrayList<Character> chars = new ArrayList<Character>();
		
		SequiturSymbol sym;
		Rule referedTo;
		for(sym = this.first(); (!sym.isGuard()); sym = sym.n){
			if(sym.isNonTerminal()){
				referedTo = ((NonTerminal)sym).r;
				chars.addAll(referedTo.getCharRepresentation());
			}else{
				chars.add((char) sym.value);
			}
		}
		return chars;
	}

}