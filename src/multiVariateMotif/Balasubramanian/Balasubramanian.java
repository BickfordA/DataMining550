package multiVariateMotif.Balasubramanian;

import java.util.ArrayList;

import multiVariateMotif.MotifMiner;
import data.SingleDimensionalMotif;
import data.TimeSeries;

public class Balasubramanian extends MotifMiner{

	public void mineDataForMotifs(TimeSeries[] dataset) {
		//tunable parameters
		// - neighborhood distance
		
		//divide the multi variate timeseries into time stamps (subsections)
		

		
	}
	
	public void generatMotifBags(int neighborhoodDistance, TimeStamp[] timeStamps)
	{
		for(int i = 0;  i < timeStamps.length; i += neighborhoodDistance ){
			TimeStamp[] currentNeighborHood = new TimeStamp[neighborhoodDistance];
			
			//pull out the neighborhood 
			for(int j = i; j < ( i + neighborhoodDistance); j ++){
				currentNeighborHood[j-i] = timeStamps[j];
			}
			
			ArrayList<SingleDimensionalMotif> currentNeighborSet = new ArrayList<SingleDimensionalMotif>();
			//get the motifs for each time stamp
			for(TimeStamp s: currentNeighborHood){
				ArrayList<SingleDimensionalMotif> foundMotifs = s.getSubMotifs();
				//add the unique ones to the list found in this "bag"
				for(SingleDimensionalMotif m : foundMotifs){
					if(!currentNeighborSet.contains(m)){
						currentNeighborSet.add(m);
					}
					
				}
			}
			
			
			
		}
	}

}
