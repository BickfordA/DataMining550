import multiVariateMotif.MotifMiner;
import data.CSVParser;
import data.Parser;
import data.TimeSeries;
import factories.MotifMiningFactory;
import services.TunableParameterService;


public class Main {

	public static void main(String args[]) throws ClassNotFoundException
	{
		final TunableParameterService parameterService = TunableParameterService.getInstance();
		
		Parser csvParser = new CSVParser();
		TimeSeries[] ts = csvParser.loadDataSet();
		
		MotifMiningFactory mineFactory = new MotifMiningFactory();
		MotifMiner currentMiner = mineFactory.getMotifMiner();
		
		currentMiner.mineDataForMotifs(ts);
	}
}

