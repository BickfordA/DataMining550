import data.CSVParser;
import data.Parser;
import data.TimeSeries;
import services.TunableParameterService;


public class Main {

	public static void main(String args[]) throws ClassNotFoundException
	{
		final TunableParameterService parameterService = TunableParameterService.getInstance();
		
		Parser csvParser = new CSVParser();
		TimeSeries[] ts = csvParser.loadDataSet();
		
		
		
	}
}

