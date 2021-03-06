 package sequitur;

/*
This class is part of a Java port of Craig Nevill-Manning's Sequitur algorithm.
Copyright (C) 1997, 2012 Eibe Frank

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

import java.util.Hashtable;

public abstract class SequiturSymbol{

  static final int numTerminals = 100000;

  static final int prime = 2265539;
  static Hashtable theDigrams = new Hashtable(SequiturSymbol.prime);
  
  protected int originalPosition;
  
  public int value;
  SequiturSymbol p,n;
  
  /**
   * Links two symbols together, removing any old
   * digram from the hash table.
   */
  
  public static void join(SequiturSymbol left, SequiturSymbol right){

    if (left.n != null)  {
      left.deleteDigram();

      // Bug fix (21.8.2012): included two if statements, adapted from
      // sequitur_simple.cc, to deal with triples
      
      if ((right.p != null) && (right.n != null) &&
          right.value == right.p.value &&
          right.value == right.n.value) {
        theDigrams.put(right, right);
      }
      
      if ((left.p != null) && (left.n != null) &&
          left.value == left.n.value &&
          left.value == left.p.value) {
        theDigrams.put(left.p, left.p);
      }
    }

    left.n = right;
    right.p = left;
  }

  /**
   * Abstract method: cleans up for symbol deletion.
   */

  public abstract void cleanUp();

  /**
   * Inserts a symbol after this one.
   */

  public void insertAfter(SequiturSymbol toInsert){
    join(toInsert,n);
    join(this,toInsert);
  }

  /**
   * Removes the digram from the hash table.
   * Overwritten in sub class guard.
   */

  public void deleteDigram(){
    
    SequiturSymbol dummy;

    if (n.isGuard())
      return;
    dummy = (SequiturSymbol)theDigrams.get(this);

    // Only delete digram if its exactly
    // the stored one.

    if (dummy == this)
      theDigrams.remove(this);
  }

  /**
   * Returns true if this is the guard symbol.
   * Overwritten in subclass guard.
   */

  public boolean isGuard(){
    return false;
  }

  /**
   * Returns true if this is a non-terminal.
   * Overwritten in subclass nonTerminal.
   */

  public boolean isNonTerminal(){
    return false;
  }

  /**
   * Checks a new digram. If it appears
   * elsewhere, deals with it by calling
   * match(), otherwise inserts it into the
   * hash table.
   * Overwritten in subclass guard.
   */

  public boolean check(){


    if (n.isGuard()){
    	//i am the rule
    	return false;
    }
    
    if (!theDigrams.containsKey(this)){
      theDigrams.put(this,this);
      return false;
    }

    SequiturSymbol found = (SequiturSymbol)theDigrams.get(this);
    
    if (found.n != this)
      match(this,found);
    
    return true;
  }

  /**
   * Replace a digram with a non-terminal.
   */

  public void substitute(Rule r){
    
	r.addIndex(this.originalPosition);
	  
	this.cleanUp();
    this.n.cleanUp();
    
    NonTerminal nt = new NonTerminal(r);
    nt.originalPosition = this.originalPosition;
    this.p.insertAfter(nt);
    if (!p.check())
      p.n.check();
  }

  /**
   * Deal with a matching digram.
   */
  
  public void match(SequiturSymbol newD,SequiturSymbol matching){
    
    Rule rule;
    SequiturSymbol first,second;
    
    if (matching.p.isGuard() && 
        matching.n.n.isGuard()){
      
      // reuse an existing rule
      
      rule = ((Guard)matching.p).r;
      newD.substitute(rule);
    }else{
      
      // create a new rule
      
      rule = new Rule();
      
      try{
        first = (SequiturSymbol)newD.clone();
        second = (SequiturSymbol)newD.n.clone();
        rule.theGuard.n = first;
        first.p = rule.theGuard;
        first.n = second;
        second.p = first;
        second.n = rule.theGuard;
        rule.theGuard.p = second;

        matching.substitute(rule);
        newD.substitute(rule);

        // Bug fix (21.8.2012): moved the following line
        // to occur after substitutions (see sequitur_simple.cc)
        
        theDigrams.put(first,first);
      }catch (CloneNotSupportedException c){
        c.printStackTrace();
      }
    }
    
    // Check for an underused rule.
    
    if (rule.first().isNonTerminal() && (((NonTerminal)rule.first()).r.count == 1))
      ((NonTerminal)rule.first()).expand();
  }
  
  /**
   * Produce the hashcode for a digram.
   */

  public int hashCode(){
    
    long code;

    // Values in linear combination with two
    // prime numbers.

    code = ((21599*(long)value)+(20507*(long)n.value));
    code = code%(long)prime;
    return (int)code;
  }

  /**
   * Test if two digrams are equal.
   * WARNING: don't use to compare two symbols.
   */

  public boolean equals(Object obj){
    return ((value == ((SequiturSymbol)obj).value) &&
            (n.value == ((SequiturSymbol)obj).n.value));
  }
  
  public String toString() {
	    return "SAXSymbol [value=" + value + ", p=" + p + ", n=" + n + "]";
  }
}