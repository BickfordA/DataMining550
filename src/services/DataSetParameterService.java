package services;

public class DataSetParameterService 
{
	private static DataSetParameterService _instance;
	private final DataSetSourceEnum _dataSet;
	private final String _fileName;
	private final String _description;
	
	private DataSetParameterService(TunableParameterService parameterService) throws IllegalArgumentException
	{
		_dataSet = parameterService.getDataSet();
		
		switch(_dataSet)
		{
		case Buzz:
			_fileName = "";
			_description = "Social Media Buzz";
			break;
		case Electric:
			_fileName = "DataSets/TestSub.txt";
			_description = "Electric Consumption";
			break;
		case Flights:
			_fileName = "";
			_description = "Domestic Flights";
			break;
		case GasSensor:
			_fileName = "";
			_description = "Gas Sensor Readings";
			break;
		
		default:
			throw new IllegalArgumentException("No Information found for the selected data set");
		}
	}
	
	public static DataSetParameterService getInstance()
	{
		if(_instance == null){
			_instance = new DataSetParameterService(TunableParameterService.getInstance());
		}
		return _instance;
	}
	
	public String getFileName()
	{
		return _fileName;
	}
	
	public String getDescription()
	{
		return _description;
	}
	
	
}
