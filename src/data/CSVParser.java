package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import services.DataSetParameterService;

public class CSVParser implements Parser {

	private final String _fileName;

	/**
	 * Public constructor
	 */
	public CSVParser() {
		this(DataSetParameterService.getInstance());
	}

	public CSVParser(DataSetParameterService dataSetParameterService) {
		_fileName = dataSetParameterService.getFileName();
	}

	public TimeSeries[] loadDataSet() {
		ArrayList<String[]> rawData = importData(_fileName);
		TimeSeries[] timeSeriesData = splitIntoTimeSeries(rawData);
		timeSeriesData = normalizeData(timeSeriesData);
		return timeSeriesData;
	}

	public ArrayList<String[]> importData(String filePath) {
		BufferedReader buffReader = null;
		String line = "";
		ArrayList<String[]> readStreams = new ArrayList<String[]>();

		try {
			buffReader = new BufferedReader(new FileReader(filePath));
			while ((line = buffReader.readLine()) != null) {
				String[] stringTimeSlice = line.split(",");
				readStreams.add(stringTimeSlice);
			}
			buffReader.close();
		} catch (Exception e) {
			System.err.println("Error " + e.getMessage());
		}
		return readStreams;
	}

	public TimeSeries[] splitIntoTimeSeries(ArrayList<String[]> rawData) {
		TimeSeries[] _outData = new TimeSeries[rawData.get(0).length];
		for (int i = 0; i < rawData.get(0).length; i++) {
			double[] seriesData = new double[rawData.size()];
			for (int j = 0; j < rawData.size(); j++) {
				seriesData[j] = Double.parseDouble(rawData.get(j)[i]);
			}
			_outData[i] = new TimeSeries(seriesData);
		}
		return _outData;
	}

	/**
	 * normalizes all of the dimensions of the input dataset to the range 0 to 1
	 * 
	 * @param input
	 *            -unnormalized dataset
	 * @return - normalized dataset
	 */
	public TimeSeries[] normalizeData(TimeSeries[] input) {
		TimeSeries[] normalized = new TimeSeries[input.length];
		double high;
		double low;
		for (int i = 0; i < input[0].getTimeSeries().length; i++) { // for each
																	// dimension
																	// of the
																	// data
																	// points
			low = 0;
			high = 0;
			for (int j = 0; j < input.length; j++) { // find the min and max
														// values over all
														// datavectors
				// find min
				if (low > input[j].getTimeSeries()[i] || j == 0) {
					low = input[j].getTimeSeries()[i];
				}
				// find max
				if (high < input[j].getTimeSeries()[i] || j == 0) {
					high = input[j].getTimeSeries()[i];
				}
			}
			for (int j = 0; j < input.length; j++) { // Normalize that dimension
														// for all datavectors
				// normalize within range 0 to 1
				input[j].getTimeSeries()[i] = (input[j].getTimeSeries()[i] - low)
						/ (high - low);
			}

		}
		// copy over normalized data
		for (int i = 0; i < normalized.length; i++) {
			normalized[i] = input[i];
		}
		return normalized;
	}

}
