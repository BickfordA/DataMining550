import java.util.Arrays;

import multiVariateMotif.MotifMiner;
import multiVariateMotif.Balasubramanian.TimeStamp;
import data.CSVParser;
import data.Parser;
import data.DoubleTimeSeries;
import data.Sequence;
import discretization.Alphabet;
import discretization.Sax;
import factories.AlphabetFactory;
import factories.MotifMiningFactory;
import services.TunableParameterService;
import statisticalSignificance.StatSigUtil;

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
		
		System.out.println("Convert time sereies to symbols");
		Sequence convertedTs[] = new Sequence[ts.length];
		try{
			//divide the multi variate timeseries into time stamps (subsections)
			AlphabetFactory alphabetFactory = new AlphabetFactory();
			Alphabet alphabet = alphabetFactory.getAlphabet();
			
			Sax saxer = new Sax();
			
			int count = 0;

			for(int j = 0; j < ts.length; j ++){
				convertedTs[j] = saxer.dexcritizeTimeSeries( ts[j],  alphabet);
			}

		}catch (ClassNotFoundException exeption){
			System.out.println(exeption.getMessage());
			return;
		}

		System.out.println("Converted time sereies to symbols");
		StatSigUtil.assignStatisicalSignificance(currentMiner.getMotifs(),convertedTs );
		System.out.println("finished");
	}
}
