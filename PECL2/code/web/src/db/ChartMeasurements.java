package db;

import java.util.ArrayList;

public class ChartMeasurements {
	private int sensorType;
	private ArrayList<Integer> minValues;
	private ArrayList<Integer> maxValues;
	private ArrayList<Integer> avgValues;
	private ArrayList<String> labels;

	// constructors
	public ChartMeasurements() {
		this.setSensorType(0);
		this.minValues = new ArrayList<Integer>();
		this.maxValues = new ArrayList<Integer>();
		this.avgValues = new ArrayList<Integer>();
		this.labels = new ArrayList<String>();
	}

	public ChartMeasurements(int sensorType, ArrayList<Integer> minValues, ArrayList<Integer> maxValues,
			ArrayList<Integer> avgValues, ArrayList<String> labels) {
		this.setSensorType(sensorType);
		this.minValues = minValues;
		this.maxValues = maxValues;
		this.avgValues = avgValues;
		this.labels = labels;
	}

	public ArrayList<Integer> getMinValues() {
		return minValues;
	}

	public void setMinValues(ArrayList<Integer> minValues) {
		this.minValues = minValues;
	}

	public ArrayList<Integer> getMaxValues() {
		return maxValues;
	}

	public void setMaxValues(ArrayList<Integer> maxValues) {
		this.maxValues = maxValues;
	}

	public ArrayList<Integer> getAvgValues() {
		return avgValues;
	}

	public void setAvgValues(ArrayList<Integer> avgValues) {
		this.avgValues = avgValues;
	}

	public ArrayList<String> getLabels() {
		return labels;
	}

	public void setLabels(ArrayList<String> labels) {
		this.labels = labels;
	}

	public int getSensorType() {
		return sensorType;
	}

	public void setSensorType(int sensorType) {
		this.sensorType = sensorType;
	}

}
