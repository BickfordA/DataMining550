package services;


public class TunableParameterService {
	/**
	 * This class is a central location to change all of the tunable parameters for the project
	 * This class is passed in through dependency injection to all classes that need it
	 */
    private static TunableParameterService _instance;
    
	//Declare all of the parameters here
	
	//private final .....
	
	private TunableParameterService()
	{
		//set the values here 
		
		
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

}
