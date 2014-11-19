package multiVariateMotif.Minnen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import services.TunableParameterService;
import multiVariateMotif.MotifMiner;
import data.DoubleTimeSeries;
import data.Sequence;
import discretization.Alphabet;
import discretization.Sax;
import factories.AlphabetFactory;

public class Minnen extends MotifMiner {

	private int _subSequenceLength; 
	private int _maxProjectionIterations;
	private int _initialProjectionLength;
	private int _dimensionRelevanceThreshold;
	
	private Random _randomNumberGenerator;
	
	
	public Minnen()
	{	
		Minnen(TunableParameterService.getInstance());
	}


	public void Minnen(TunableParameterService params)
	{
		long seed = params.randomNumberSeed();
		_randomNumberGenerator = new Random(seed);
		_subSequenceLength = params.getSubSequenceLength();
		_maxProjectionIterations = params.getMaxProjectionIterations();
		_initialProjectionLength = params.getInitialProjectionLength();
		_dimensionRelevanceThreshold = params.getDimensionalRelevanceThreshold();
	}
	
	
	
	
	public void mineDataForMotifs(DoubleTimeSeries[] dataset) 
	{
		
		DoubleTimeSeries[][] timeFrames = new DoubleTimeSeries[dataset.length][dataset[0].size() / _subSequenceLength];
		
		Sequence[][] timeSequences = new Sequence[timeFrames.length][timeFrames[0].length];
		
		//Collect all subsequences of length w from the time series (like time stamps )
		int idx = 0;
		for (int i = 0; i < dataset[0].size(); i += _subSequenceLength){
			for(int j = 0; j < dataset.length; j ++){
				timeFrames[j][idx] = dataset[j].cloneSubSection(i, i+ _subSequenceLength - 1);
			}
			idx++;
		}
		
		
		
		//Compute an estimate of the distribution between all of the non-trivial matches for each dimension
		
		
		//// (Pain in the Ass) Search for values alpha dn c (projection distance) which lead to a sparce collision matrix
		
		
		
		//compute the sax word length and aplhabet size for each dimension
		try{
			//divide the multi variate timeseries into time stamps (subsections)
			AlphabetFactory alphabetFactory = new AlphabetFactory();
			Alphabet alphabet = alphabetFactory.getAlphabet();
			Sax saxer = new Sax();
			
			for(int i = 0; i < timeSequences.length; i ++){
				for(int j = 0; j < timeSequences[i].length; j++){
					timeSequences[i][j] = saxer.dexcritizeTimeSeries(timeFrames[i][j], alphabet);
				}
			}
			
		}catch (ClassNotFoundException exeption){
			System.out.println(exeption.getMessage());
			return;
		}
		
		//Build the collision matrix using reandom projection over the SAX words
		
		CollisionMatrix collision = generateCollisionMatrix(timeSequences, _initialProjectionLength );
		
		
		
		//Enumerate the motifs based on the collision matrix
		
			// Find the best Collision matrix
		
			//Estimate the nieghborhood radius
		
			//Locate all other occurences of this motif 
		
			// Remove subsequences that would constitute trivaial matches with the occurences of this motif
		
		
		
	}
	
	public CollisionMatrix generateCollisionMatrix(Sequence[][] sequences, int startProjectionSize)
	{
		CollisionMatrix collisionMatrix = new CollisionMatrix(sequences[0].length);
		Boolean[] selectedDimensions = new Boolean[sequences[0][0].size()];
		
		//start with no projection and work up (by subtracting from this number ;) )
		int projSize = sequences[0][0].size();
		
		//TODO: need to take the min of the maX iterations and the number of dimension combinations
		for(int i =0 ; i <  _maxProjectionIterations; i++){
			selectedDimensions = makeRandomSelection(selectedDimensions, projSize);
		
			//project the selection
			projectTheSelection(collisionMatrix , sequences, selectedDimensions);
		}
	
		selectedDimensions = setToValue(selectedDimensions, false);
		
		return collisionMatrix;
	}
	
	public Boolean[] setToValue(Boolean[] vector, Boolean value)
	{
		for(int i = 0 ; i < vector.length; i ++)
		{
			vector[i] = value;
		}
		return vector;
	}
	
	public Boolean[] makeRandomSelection(Boolean[] choices, int selectionSize)
	{
		if(selectionSize >= choices.length){
			return setToValue(choices, true);
		}
		
		int selection;;
		for(int i = 0 ; i < selectionSize; i ++){
			selection = _randomNumberGenerator.nextInt();
			boolean notSet = true;
			while(notSet){
				int index = selection % choices.length;
				if(choices[index] == true){
					//continue to the next one
					selection ++;
					continue;
				}
				notSet = false;
				choices[index] = true;
			}			
		}
		
		return choices;
	}

	public void projectTheSelection(CollisionMatrix matrix, Sequence[][] data, Boolean[] selected)
	{
		for(int dimension = 0 ; dimension < data.length; dimension++){
			//For each dimension 
			
			//hash the entries
			HashMap<char[], ArrayList<Integer>> collisionMap = new HashMap<char[], ArrayList<Integer>>();
			//record the collisions
			
			//for each record
			Sequence[] currentDimension = data[dimension];
			for(int i = 0 ; i < currentDimension.length; i ++){
				char[] key = currentDimension[i].generateCharArray();
				
				if(collisionMap.containsKey(key)){
					ArrayList<Integer> mapping =  collisionMap.get(key);
					mapping.add(i);					
				}else{
					ArrayList<Integer> newColision =  new ArrayList<Integer>();
					newColision.add(i);
					collisionMap.put(key, newColision);
				}
				
			}			
			
			for(ArrayList<Integer> collisions: collisionMap.values()){
				if(collisions.size() > 1){
					//more than one sequence matched
					//record each pairwise collision
					for(int i = 0 ; i < collisions.size(); i++){
						for(int j = i ; j < collisions.size(); i++){
							//record all of the hit combinations
							matrix.incrementAt(i, j);
						}
					}
				}
			}
		}
	}
}
