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
	
	//private final .....
	
	private TunableParameterService()
	{
		//set the values here 
		_dataSet = DataSetSourceEnum.Electric;
		_saxAlphabet = AlphabetEnum.NormalAlphabet;
		
		_algorithm = MotifMiningAlgorithmEnum.Balasubramanian;
		
		
		
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
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxProjectionIterations() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getInitialProjectionLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getDimensionalRelevanceThreshold() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long randomNumberSeed() {
		// TODO Auto-generated method stub
		return 0;
	}

}
