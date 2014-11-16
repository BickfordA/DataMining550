package multiVariateMotif.Balasubramanian;

import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Single;

import multiVariateMotif.MotifMiner;
import data.SingleDimensionalMotif;
import data.DoubleTimeSeries;

public class Balasubramanian extends MotifMiner{

	public void mineDataForMotifs(DoubleTimeSeries[] dataset) {
		//tunable parameters
		// - neighborhood distance
		
		//divide the multi variate timeseries into time stamps (subsections)
		

		
	}
	
	public void generatMotifBagsAndOrderings(int neighborhoodDistance, TimeStamp[] timeStamps)
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
			
			//generate the powerset for the currentNeighborSet
			ArrayList<ArrayList<SingleDimensionalMotif>> neighborhoodMotifPowerSet = generateMotifBagPowerSet(currentNeighborSet);
			
			ArrayList<ArrayList<SingleDimensionalMotif>>  motifBag = new ArrayList<ArrayList<SingleDimensionalMotif>>();
			ArrayList<ArrayList<TemporalOrdering> > motifOrdering = new ArrayList<ArrayList<TemporalOrdering> >();
			
			for(ArrayList<SingleDimensionalMotif> set: neighborhoodMotifPowerSet){
				//make sure that the motifs don't overlap (in the same single variate time stream)
				if(noOverLappingMotifs(set)){
					motifBag.add(set);
					ArrayList<TemporalOrdering> tempOrdering = getTemporalOrderings(set);
					
					if(!motifOrdering.contains(tempOrdering)){
							motifOrdering.add(tempOrdering);
					}
				}
			}
		}
	}
	
	public ArrayList<ArrayList<SingleDimensionalMotif> > generateMotifBagPowerSet( ArrayList<SingleDimensionalMotif> motifs)
	{
		ArrayList<ArrayList<SingleDimensionalMotif> > powerSet = new ArrayList<ArrayList<SingleDimensionalMotif> >();
		
		long powerSetSize = (long) Math.pow(2, motifs.size());
		
		//don't include the empty set ~ (not really power set) start at 1
		for(int i = 1 ; i < powerSetSize; i ++ ){
			ArrayList<SingleDimensionalMotif> set = new ArrayList<SingleDimensionalMotif>();
			//each i'th bit corresponds to a item in the original set
			for(int j = 0 ; j < motifs.size(); j++ ){
				//check if the item should be included in the set
				if((powerSetSize & (1L << j)) != 0){
					//this bit is set and it is in the powerset
					set.add(motifs.get(j));
				}
			}
		}
		return powerSet;
	}
	
	public boolean noOverLappingMotifs(ArrayList<SingleDimensionalMotif> motifSet)
	{
		boolean overlapping = false;
		//do a pair wise comparison
		for(int i = 0; i < motifSet.size(); i++  ){
			//check this item against all items greater than it( less than items have already been check when they
			//checked their greater than items
			for(int j = i; j < motifSet.size(); j ++){
				if(j == i){
					continue; // don't compare against itself
				}
				if(motifSet.get(i).overLappingMotif(motifSet.get(j))){
					overlapping = true;
					break;
				}
			}
			if(overlapping){
				break;
			}
		}
		return !overlapping;
		
	}
	
	public ArrayList<TemporalOrdering> getTemporalOrderings(ArrayList<SingleDimensionalMotif> motifSet)
	{
		ArrayList<TemporalOrdering> temporalOrderings = new ArrayList<TemporalOrdering>();
		
		//Temporal orderings are symmetric, whoo hoo!
		for(int i = 0 ; i < motifSet.size(); i ++){
			for(int j = i; j < motifSet.size(); j++){
				if(i == j){
					//don't need to compare against itself
					continue;
				}
				TemporalOrdering newRelation = new TemporalOrdering(motifSet.get(i), motifSet.get(j));
				temporalOrderings.add(newRelation);
			}
		}
		return temporalOrderings;
	}
	
}
