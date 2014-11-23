import multiVariateMotif.MotifMiner;
import data.CSVParser;
import data.Parser;
import data.DoubleTimeSeries;
import discretization.Alphabet;
import factories.AlphabetFactory;
import factories.MotifMiningFactory;
import services.TunableParameterService;

public class Main {

	public static void main(String args[]) throws ClassNotFoundException {
		final TunableParameterService parameterService = TunableParameterService
				.getInstance();

		Parser csvParser = new CSVParser();
		DoubleTimeSeries[] ts = csvParser.loadDataSet();
		System.out.println("data loaded");
		MotifMiningFactory mineFactory = new MotifMiningFactory();
		MotifMiner currentMiner = mineFactory.getMotifMiner();
		System.out.println("created mining algorithm");
		currentMiner.mineDataForMotifs(ts);
	}
}
