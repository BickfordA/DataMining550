package multiVariateMotif;

import java.util.List;

import data.*;

public abstract class MotifMiner 
{
	protected List<MulitDimensionalMotif> _motifs;
	
	public abstract void mineDataForMotifs(TimeSeries[] dataset);
	
	
}
