package services;

import factories.AlphabetEnum;
import factories.MotifMiningAlgorithmEnum;


public class TunableParameterService {
	/**
	 * This class is a central location to change all of the tunable parameters for the project
	 * This class is passed in through dependency injection to all classes that need it
	 */
    private static TunableParameterService _instance;
    
	//Declare all of the parameters here
    DataSetSourceEnum _dataSet;
    MotifMiningAlgorithmEnum _algorithm;
    AlphabetEnum _saxAlphabet;
    
    int _iSaxBaseCardinality;
    int _iSaxWordLength;
	
    
    //Minnen variables
    private final int _minnenSequenceLength;
    private final int _maxProjectionIterations;
    private final int _intitialProjectionLength;
    private final int _dimensionalRelevanceThreshold;
    private final int _randomNumberSeed;
    private final int _randomDistributiuonSampleSize;
    
    //Balasubramanian variables
    private  int _timeStampLength;
    
	
	private TunableParameterService()
	{
		//set the values here 
		_dataSet = DataSetSourceEnum.Electric;
		_saxAlphabet = AlphabetEnum.NormalAlphabet;
		
		_algorithm = MotifMiningAlgorithmEnum.Balasubramanian;
		
		
		_iSaxWordLength = 1;
		_iSaxBaseCardinality = 4;
		
		//minnen init
		_minnenSequenceLength = 10;
		_maxProjectionIterations = 1000;
		_intitialProjectionLength = 100;
		_dimensionalRelevanceThreshold = 1 ;
		_randomNumberSeed = 10;
		_randomDistributiuonSampleSize = 20;
		
		//Balla....
		_timeStampLength = 10;
	}
	
	//getters for the values 
	
    // Gets the singleton instance of this class
    public static TunableParameterService getInstance()
    {
        // Lazy load the instance
        if (_instance == null)
            _instance = new TunableParameterService();

        return _instance;
    }

	public DataSetSourceEnum getDataSet() {
		return _dataSet;
	}

	public MotifMiningAlgorithmEnum getMotifAlgorithm() {
		return _algorithm;
	}

	public int getSaxBaseCardinality() {
		return _iSaxBaseCardinality;
	}

	public int getSaxWordLength() {
		return _iSaxWordLength;
	}

	public AlphabetEnum getAlphabet() {
		return _saxAlphabet;
	}

	public int getSubSequenceLength() {
		return _minnenSequenceLength;
	}

	public int getMaxProjectionIterations() {
		return _maxProjectionIterations;
	}

	public int getInitialProjectionLength() {
		return _intitialProjectionLength;
	}

	public int getDimensionalRelevanceThreshold() {
		return _dimensionalRelevanceThreshold;
	}

	public long randomNumberSeed() {
		return _randomNumberSeed;
	}

	public int getRandomDistributionSampleSize() {
		return _randomDistributiuonSampleSize;
	}

	public int getTimeStampLength() {
		return _timeStampLength;
	}

}
