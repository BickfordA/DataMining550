package multiVariateMotif;

import java.util.ArrayList;

import data.*;

public abstract class MotifMiner 
{
	protected ArrayList<MultiDimensionalMotif> _motifs;
	protected Sequence[] _sequenceData;
	
	public abstract void mineDataForMotifs(DoubleTimeSeries[] dataset);
	
	public ArrayList<MultiDimensionalMotif> getMotifs() { return _motifs ; }
	public Sequence[] getSequenceData() { return _sequenceData; }
	
}
