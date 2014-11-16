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

	public DoubleTimeSeries[] loadDataSet() {
		ArrayList<String[]> rawData = importData(_fileName);
		DoubleTimeSeries[] timeSeriesData = splitIntoTimeSeries(rawData);
		System.out.println("Data set imported ");
		timeSeriesData = computeZScore(timeSeriesData);
		System.out.println("Data set z normalized ");
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

	public DoubleTimeSeries[] splitIntoTimeSeries(ArrayList<String[]> rawData) {
		DoubleTimeSeries[] _outData = new DoubleTimeSeries[rawData.get(0).length];
		for (int i = 0; i < rawData.get(0).length; i++) {
			double[] seriesData = new double[rawData.size()];
			for (int j = 0; j < rawData.size(); j++) {
				seriesData[j] = Double.parseDouble(rawData.get(j)[i]);
			}
			_outData[i] = new DoubleTimeSeries(seriesData, i);
		}
		return _outData;
	}


	public DoubleTimeSeries[] computeZScore(DoubleTimeSeries[] input) {
		// z-score 
		//
		// z-score = (x - mean ) / (standard deviation)
		//
		
		DoubleTimeSeries[] normalized = new DoubleTimeSeries[input.length];
		

		for (int i = 0; i < input.length; i++) {
			double[] values = input[i].getTimeSeries();
			double mean = 0;
			double stdev = 0;
			//get the mean 
			for(int j = 0 ; j < values.length; j++){
				mean += values[j];
			}
			mean = mean / values.length;
		
			//get the standard deviation
			for(int j = 0 ; j < values.length; j++){
				double v = values[j] - mean;
				stdev +=  v * v ;
			}
			stdev = stdev / values.length;
			stdev = Math.sqrt(stdev);
			
			//calculate the z-score
			for(int j = 0 ; j < values.length; j++){
				values[j] = (values[j]- mean) / stdev;
			}
			
			normalized[i] = new DoubleTimeSeries(values, i);
		}

		return normalized;
	}

}
