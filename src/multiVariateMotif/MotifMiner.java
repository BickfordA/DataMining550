package multiVariateMotif;

import java.util.List;

import data.*;

public abstract class MotifMiner 
{
	protected List<MultiDimensionalMotif> _motifs;
	
	public abstract void mineDataForMotifs(DoubleTimeSeries[] dataset);
	
	
}
