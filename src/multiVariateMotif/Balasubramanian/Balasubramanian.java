package multiVariateMotif.Balasubramanian;

import java.util.ArrayList;
import java.util.Arrays;

import services.TunableParameterService;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Single;

import multiVariateMotif.MotifMiner;
import data.Sequence;
import data.SingleDimensionalMotif;
import data.DoubleTimeSeries;
import discretization.Alphabet;
import discretization.Sax;
import factories.AlphabetFactory;

public class Balasubramanian extends MotifMiner{

	private int _timeStampLength;
	
	
	public Balasubramanian(){
		Balasubramanian(TunableParameterService.getInstance());
	}

	public void Balasubramanian(TunableParameterService params){
		_timeStampLength = params.getTimeStampLength();
	}
	
	public void mineDataForMotifs(DoubleTimeSeries[] dataset){
		//tunable parameters
		// - neighborhood distance
		
		TimeStamp[] timeStamps = new TimeStamp[dataset[0].size()/_timeStampLength ];
		try{
			//divide the multi variate timeseries into time stamps (subsections)
			AlphabetFactory alphabetFactory = new AlphabetFactory();
			Alphabet alphabet = alphabetFactory.getAlphabet();
			
			Sax saxer = new Sax();
			
			int count = 0;
			for(int i = 0; i < dataset[0].size() - _timeStampLength; i += _timeStampLength){
				
				Sequence subSequence[] = new Sequence[dataset.length];
				for(int j = 0; j < dataset.length; j ++){
					System.out.println("j: "+ j + " - "+dataset[j].getTimeSeries().length + " i stop " +( i +_timeStampLength) + " start  " + i);
					double[] ogTimeseries = dataset[j].getTimeSeries();
					double[] subTimeseries = Arrays.copyOfRange(dataset[j].getTimeSeries(),i, i +_timeStampLength);
					long[] subTstamps = Arrays.copyOfRange(dataset[j].getTimeStamps(), i, i +_timeStampLength);
					DoubleTimeSeries subTs = new DoubleTimeSeries(subTimeseries, subTstamps,  j);
					subSequence[j] = saxer.dexcritizeTimeSeries( subTs,  alphabet);
				}
				timeStamps[count] = new TimeStamp(subSequence);
				count ++;
			}

		}catch (ClassNotFoundException exeption){
			System.out.println(exeption.getMessage());
			return;
		}
		System.out.println("looking for motif bags");
		_motifs = generatMotifBagsAndOrderings(10 , timeStamps).getMotifsWithCounts();
		System.out.println("Finished Looking");
		
	}
	
	public MultiVariateMotifCounts generatMotifBagsAndOrderings(int neighborhoodDistance, TimeStamp[] timeStamps)
	{
		MultiVariateMotifCounts motifs = new MultiVariateMotifCounts();
		System.out.println("Time stamp count: "+ timeStamps.length);
		for(int i = 0;  i < timeStamps.length-neighborhoodDistance; i += neighborhoodDistance ){
			System.out.println("\n\nTime stamp: "+ i);
			TimeStamp[] currentNeighborHood = new TimeStamp[neighborhoodDistance];
			
			//pull out the neighborhood 
			for(int j = i; j < ( i + neighborhoodDistance); j ++){
				currentNeighborHood[j-i] = timeStamps[j];
			}
			
			ArrayList<SingleDimensionalMotif> currentNeighborSet = new ArrayList<SingleDimensionalMotif>();
			System.out.println("Neighborhood size: " + currentNeighborHood.length);
			//get the motifs for each time stamp
			for(TimeStamp s: currentNeighborHood){
				ArrayList<SingleDimensionalMotif> foundMotifs = s.getSubMotifs();
				//add the unique ones to the list found in this "bag"
				System.out.println("Found :" + foundMotifs.size()+ " motifs");
				for(SingleDimensionalMotif m : foundMotifs){
					if(!currentNeighborSet.contains(m)){
						currentNeighborSet.add(m);
					}
				}
			}
			
			System.out.println("finished finding motifs");
			//generate the powerset for the currentNeighborSet
			ArrayList<ArrayList<SingleDimensionalMotif>> neighborhoodMotifPowerSet = generateMotifBagPowerSet(currentNeighborSet);
			
//			ArrayList<ArrayList<SingleDimensionalMotif>>  motifBag = new ArrayList<ArrayList<SingleDimensionalMotif>>();
//			ArrayList<ArrayList<TemporalOrdering> > motifOrdering = new ArrayList<ArrayList<TemporalOrdering> >();
			System.out.println("Powerset size: " + neighborhoodMotifPowerSet.size() + "\n constructing canidates");
			int count = 0;
			for(ArrayList<SingleDimensionalMotif> set: neighborhoodMotifPowerSet){
				//make sure that the motifs don't overlap (in the same single variate time stream)
				if(noOverLappingMotifs(set)){
					//System.out.println("set size: " + set.size());
					TemporalOrderingTuple to = new TemporalOrderingTuple(getTemporalOrderings(set));
					MultivariateMotifBalasubra foundMotif = new MultivariateMotifBalasubra(set, to);
					
					motifs.append(foundMotif);
					count ++;
					if(count % 100 == 0){
						System.out.print(".");
					}
//					motifBag.add(set);
//					ArrayList<TemporalOrdering> tempOrdering = getTemporalOrderings(set);
//					
//					if(!motifOrdering.contains(tempOrdering)){
//							motifOrdering.add(tempOrdering);
//					}
				}
			}
			System.gc();
		}
		
		return motifs;
	}
	
	public ArrayList<ArrayList<SingleDimensionalMotif> > generateMotifBagPowerSet( ArrayList<SingleDimensionalMotif> motifs)
	{
		System.out.println("finding powerset");
		ArrayList<ArrayList<SingleDimensionalMotif> > powerSet = new ArrayList<ArrayList<SingleDimensionalMotif> >();
		
		long powerSetSize = (long) Math.pow(2, motifs.size());
		System.out.println("motif count "+motifs.size());
		System.out.println("finding powerset, size: " + powerSetSize);
		//don't include the empty set ~ (not really power set) start at 1
		for(int i = 1 ; i < powerSetSize; i ++ ){
			if( i % (powerSetSize/ 4) == 0){
				System.out.print( i / (powerSetSize/ 4) * 25  + " percent, " );
			}
			ArrayList<SingleDimensionalMotif> set = new ArrayList<SingleDimensionalMotif>();
			//each i'th bit corresponds to a item in the original set
			for(int j = 0 ; j < motifs.size(); j++ ){
				//check if the item should be included in the set
//				System.out.print(","+(1L << j)+ " " + (i & (1L << j)) );
				if((i & (1L << j)) != 0){
					//this bit is set and it is in the powerset
					set.add(motifs.get(j));
//					System.out.println("add");
				}
			}
			powerSet.add(set);
		}
		System.out.println("100 percent");
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
