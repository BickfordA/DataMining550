package multiVariateMotif.Minnen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.math3.distribution.NormalDistribution;

import services.MyRandom;
import services.TunableParameterService;
import multiVariateMotif.MotifMiner;
import data.DoubleTimeSeries;
import data.MultiDimensionalMotif;
import data.Sequence;
import data.SingleDimensionalMotif;
import discretization.Alphabet;
import discretization.Sax;
import factories.AlphabetFactory;

public class Minnen extends MotifMiner {

	private int _subSequenceLength;
	private int _maxProjectionIterations;
	private int _initialProjectionLength;
	private int _dimensionRelevanceThreshold;

	private MyRandom _randomNumberGenerator;

	// for the calculation of the distribution of distances
	private int _distributionSampleSize;

	public Minnen() {
		Minnen(TunableParameterService.getInstance());
	}

	public void Minnen(TunableParameterService params) {
		long seed = params.randomNumberSeed();
		_randomNumberGenerator = new MyRandom(seed);
		_subSequenceLength = params.getSubSequenceLength();
		_maxProjectionIterations = params.getMaxProjectionIterations();
		_initialProjectionLength = params.getInitialProjectionLength();
		_dimensionRelevanceThreshold = params
				.getDimensionalRelevanceThreshold();
		_distributionSampleSize = params.getRandomDistributionSampleSize();
	}

	public void mineDataForMotifs(DoubleTimeSeries[] dataset) 
	{
		
		DoubleTimeSeries[][] timeFrames = new DoubleTimeSeries[dataset.length][dataset[0].size() / _subSequenceLength];
		
		Sequence[][] timeSequences = new Sequence[timeFrames.length][timeFrames[0].length];
		System.out.println("splitting");
		//Collect all subsequences of length w from the time series (like time stamps )
		int idx = 0;
		for (int i = 0; i < dataset[0].size()-_subSequenceLength; i += _subSequenceLength){
			for(int j = 0; j < dataset.length; j ++){
				timeFrames[j][idx] = dataset[j].cloneSubSection(i, i+ _subSequenceLength - 1);
			}
			idx++;
		}
		
		
		
		//Compute an estimate of the distribution between all of the non-trivial matches for each dimension
		System.out.println("building distributiuons");
		NormalDistribution dimensinalDistributions[]  =  getDistributionByRandomSampling(timeFrames);
	
		
		//// (Pain in the Ass) Search for values alpha dn c (projection distance) which lead to a sparse collision matrix
		// the authors set these initially to the c to the full length
		// and alpha to 3 and grow these based on the density of the collision matrix (heuristics....... :-(
		System.out.println("descritizing");
		
		//compute the sax word length and aplhabet size for each dimension
		try{
			//divide the multi variate timeseries into time stamps (subsections)
			AlphabetFactory alphabetFactory = new AlphabetFactory();
			Alphabet alphabet = alphabetFactory.getAlphabet();
			Sax saxer = new Sax();
			
			for(int i = 0; i < timeSequences.length; i ++){
				for(int j = 0; j < timeSequences[i].length; j++){
//					System.out.println(".");
					timeSequences[i][j] = saxer.dexcritizeTimeSeries(timeFrames[i][j], alphabet);
				}
			}
			System.out.println("descritized");
		}catch (ClassNotFoundException exeption){
			System.out.println(exeption.getMessage());
			return;
		}
		
		//Build the collision matrix using reandom projection over the SAX words
		System.out.println("build collision matrix");
		CollisionMatrix collision = generateCollisionMatrix(timeSequences, _initialProjectionLength );
		System.out.println("get max collisions");
		ArrayList<CollisionPair> collisionsOfMaxSize = collision.getMaxCollisions(); 
		System.out.println("number of colision of max size: " + collisionsOfMaxSize.size());
		
		//Enumerate the motifs based on the collision matrix
		ArrayList<Double> distancePerDim = new ArrayList<Double>();
		
		ArrayList<MultiDimensionalMotif> mdfs = new ArrayList<MultiDimensionalMotif>();
		
		for(CollisionPair pair: collisionsOfMaxSize){
			ArrayList<SingleDimensionalMotif> motifList = new ArrayList<SingleDimensionalMotif>();

			// Find the best Collision matrix
			//find the pair with the lowest distance
			int idxA = pair.getA();
			int idxB = pair.getB();
			double[] distDim  = new double[timeFrames.length];
			int lowestIdx = -1;
			double lowestDist = Integer.MAX_VALUE;
			for(int i = 0 ; i < distDim.length; i++){
				distDim[i] = timeFrames[i][idxA].distance(timeFrames[i][idxB]);
				if(distDim[i] < lowestDist){
					lowestIdx = i;
					lowestDist = distDim[i];
				}
			}
		
			// determine the relevant dimensions
			ArrayList<Integer> relevantDimensions = new ArrayList<Integer>();
			ArrayList<Double> dimensionalRelevance = new ArrayList<Double>();
			for(int i = 0 ; i < distDim.length; i ++){
				double relevance = dimensinalDistributions[i].cumulativeProbability(distDim[i]);
				if( relevance < _dimensionRelevanceThreshold){
					relevantDimensions.add(i);
					dimensionalRelevance.add(relevance);
					Sequence dimensionSequence = timeSequences[i][idxA];//A or B ?
					motifList.add(new SingleDimensionalMotif(dimensionSequence, i ));
				}
			}
			double d = 0;
			for(int i = 0; i < relevantDimensions.size();i++){
				d += ( distDim[relevantDimensions.get(i)] * dimensionalRelevance.get(i) / dimensionalRelevance.get(i));
			}
			distancePerDim.add(d);
			
			mdfs.add(new MultiDimensionalMotif(motifList));
			
		}
		
		//select the n ones with the smallest dist per rel dim.
//		int smallestIdx;
//		double smallest = Double.MAX_VALUE;
//		for(int i = 0 ; i < distancePerDim.size(); i++){
//			if(distancePerDim.get(i) < smallest){
//				smallest = distancePerDim.get(i);
//			}
//		}
		
		//fuck it ! just get them all
		ArrayList<Integer> selection = new ArrayList<Integer>();
		//could select the top n eventually
		for(int i = 0 ; i < distancePerDim.size(); i ++){
			selection.add(i);
		}
		
		
		
		
		//Estimate the neighborhood radius ((?))
		
		
		//Locate all other occurrences of this motif
		

		
		
		// Remove subsequences that would constitute trivial matches with the occurrences of this motif
		//(i.e subsequences of this motif ?) 
		System.out.println("Number of Motifs FOudn: " + mdfs.size());
		_motifs = mdfs;
		
	}
	
	private ArrayList<Integer> findOccurencesOfMotif(NormalDistribution dist, DoubleTimeSeries[][] subsequences, int motif)
	{
		ArrayList<Integer> matchingIndecies = new ArrayList<Integer>();
		for(int i = 0; i < subsequences[0].length; i ++){
			//count all matches within a cumulatuve probability accross all of the dimensions;
			
		}
		return matchingIndecies;
	}

	private NormalDistribution[] getDistributionByRandomSampling(
			DoubleTimeSeries[][] timeFrames) {
		// create a distribytion for each dimension
		NormalDistribution[] retVal = new NormalDistribution[timeFrames.length];
		for (int i = 0; i < timeFrames.length; i++) {
			int sampleSize = _distributionSampleSize;
			boolean maxSamples = false;
			double stdev = 0;
			while(stdev == 0){
				double mean = 0;
				double[] distances = new double[sampleSize];
				for (int j = 0; j < sampleSize; j++) {
					distances[j] = findRandomDistance(timeFrames[i]);
					mean += distances[j];
				}
	
				// find std deviation
				for (int j = 0; j < sampleSize; j++) {
					double v = distances[j] - mean;
					stdev += v * v;
				}
				stdev = stdev / sampleSize;
				stdev = Math.sqrt(stdev);
				
				if(maxSamples){
					stdev = Double.MIN_VALUE;
				}
				
				if(stdev == 0 && !maxSamples){
					sampleSize = sampleSize * 2;
					if( sampleSize > _distributionSampleSize){
						sampleSize = _distributionSampleSize;
						maxSamples = true;
					}
					continue;
				}
				
				retVal[i] = new NormalDistribution(mean, stdev);
			}
		}
		return retVal;

	}

	private double findRandomDistance(DoubleTimeSeries sequences[]) {
		int idxA = _randomNumberGenerator.nextNonNegative() % sequences.length;
		int idxB = _randomNumberGenerator.nextNonNegative() % sequences.length;
		while (idxB == idxA) {
			idxB = _randomNumberGenerator.nextNonNegative() % sequences.length;
		}
		return sequences[idxA].distance(sequences[idxB]);
	}

	public CollisionMatrix generateCollisionMatrix(Sequence[][] sequences,
			int startProjectionSize) {
		CollisionMatrix collisionMatrix = new CollisionMatrix(
				sequences[0].length);
		
		Boolean[] selectedDimensions = new Boolean[sequences[0][0].size()];

		// start with no projection and work up (by subtracting from this number
		// ;) )
		int projSize = sequences[0][0].size();

		int maxCollisionSize = 0;
		while(maxCollisionSize < 4  && projSize > 0){
			// TODO: need to take the min of the maX iterations and the number of
			// dimension combinations
			for (int i = 0; i < _maxProjectionIterations; i++) {
				selectedDimensions = setToValue(selectedDimensions, false);
				selectedDimensions = makeRandomSelection(selectedDimensions,
						projSize);
	
				// project the selection
				projectTheSelection(collisionMatrix, sequences, selectedDimensions);
			}
			maxCollisionSize = collisionMatrix.getMaxCollisionNumber(); 
			projSize = projSize - 1;
			System.out.print(projSize);
			selectedDimensions = setToValue(selectedDimensions, false);
		}
		return collisionMatrix;
	}

	/**
	 * 
	 * @param vector
	 * @param value
	 * @return
	 */
	public Boolean[] setToValue(Boolean[] vector, Boolean value) {
		for (int i = 0; i < vector.length; i++) {
			vector[i] = value;
		}
		return vector;
	}

	/**
	 * 
	 * @param choices
	 * @param selectionSize
	 * @return
	 */
	public Boolean[] makeRandomSelection(Boolean[] choices, int selectionSize) {
		if (selectionSize >= choices.length) {
			return setToValue(choices, true);
		}
		int selection;
		for (int i = 0; i < selectionSize; i++) {
			selection = _randomNumberGenerator.nextInt();
			if(selection < 0 ){
				selection = selection * -1;
			}
			boolean notSet = true;
			while (notSet) {
				int index = selection % choices.length;
//				System.out.println("Selection: " +selection + " choises length " + choices.length + " index: " + index );
				if (choices[index] == true) {
					// continue to the next one
					selection++;
					continue;
				}
				notSet = false;
				choices[index] = true;
			}
		}

		return choices;
	}

	/**
	 * 
	 * @param matrix
	 * @param data
	 * @param selected
	 */
	public void projectTheSelection(CollisionMatrix matrix, Sequence[][] data,
			Boolean[] selected) {
		
		int selectedSize = 0;
		for(boolean s: selected){
			if(s) selectedSize ++;
		}
		for (int dimension = 0; dimension < data.length; dimension++) {
			// For each dimension

			// hash the entries
			HashMap<ArrayList<Character>, ArrayList<Integer>> collisionMap = new HashMap<ArrayList<Character>, ArrayList<Integer>>();
			// record the collisions

			// for each record
			Sequence[] currentDimension = data[dimension];
			for (int i = 0; i < currentDimension.length; i++) {
				char[] seqChars = currentDimension[i].generateCharArray();
				
				ArrayList<Character> key = new ArrayList<Character>();
				
				for(int j = 0;j < selected.length; j ++)
				{
					if(selected[j]){
						key.add(seqChars[j]);
					}
				}
//				System.out.println("selected Size "+ selectedSize);
//				for(char c: key){
//					int inc =((int ) c +1);
//					System.out.print(inc+ " ");
//					
//				}
//				System.out.println("");

				if (collisionMap.containsKey(key)) {
					ArrayList<Integer> mapping = collisionMap.get(key);
					mapping.add(i);
//					System.out.println("hit");
				} else {
					ArrayList<Integer> newColision = new ArrayList<Integer>();
					newColision.add(i);
					collisionMap.put(key, newColision);
//					System.out.println("miss");
				}

			}

			for (ArrayList<Integer> collisions : collisionMap.values()) {
				if (collisions.size() > 1) {
					// more than one sequence matched
					// record each pairwise collision
					for (int i = 0; i < collisions.size(); i++) {
						for (int j = i; j < collisions.size(); j++) {
							// record all of the hit combinations
							matrix.incrementAt(i, j);
						}
					}
				}
			}
		}
	}
}
