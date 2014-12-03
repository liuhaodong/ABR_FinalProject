package missingFeaturesAquisition;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import weka.core.Instances;
import dataUtility.MissingFeatureIndex;
import dataUtility.InstanceFeature;

public class CalculateExpectation {
	
	public HashMap<MissingFeatureIndex, Double> calculateFeatureExpectation(
			Instances oracleInstances, Instances trainingInstances, Instances evalInstances)
			throws Exception {

		HashMap<InstanceFeature, Double> featureValueProbabilityMap = (new FeatureProbability())
				.getFeatureProbility(trainingInstances);
		
		
		System.out.println("Finished Calculating Feature Probability. Start Calculating Log Gain");
		
		HashMap<MissingFeatureIndex, ArrayList<Double>> featureLGMap = (new FeatureUtility())
				.calculateLG(oracleInstances, trainingInstances, evalInstances);
		
		
		System.out.println("Finished Calculating Log Gain. Start Calculating Feature Expectation." );

		HashMap<MissingFeatureIndex, Double> featureExpectationMap = new HashMap<MissingFeatureIndex, Double>();

		for (MissingFeatureIndex tmpMissIndex : featureLGMap.keySet()) {
			ArrayList<Double> tmpFeatureLGList = featureLGMap.get(tmpMissIndex);
			double expectation = 0;
			for (int i = 0; i < tmpFeatureLGList.size(); i++) {
				expectation += tmpFeatureLGList.get(i)
						* featureValueProbabilityMap.get(new InstanceFeature(
								tmpMissIndex.featureIndex, i, "",
								tmpMissIndex.instanceLabel));
			}
			featureExpectationMap.put(tmpMissIndex, expectation);
		}
		
		
		
		System.out.println("Finished Calculating Feature Expectation.");
		
		return featureExpectationMap;
	}

	public HashMap<MissingFeatureIndex, Double> calculateFeatureExpectation(
			String oraclePath, String trainingPath, String evalPath)
			throws Exception {

		HashMap<InstanceFeature, Double> featureValueProbabilityMap = (new FeatureProbability())
				.getFeatureProbility(trainingPath);
		HashMap<MissingFeatureIndex, ArrayList<Double>> featureLGMap = (new FeatureUtility())
				.calculateLG(oraclePath, trainingPath, evalPath);

		HashMap<MissingFeatureIndex, Double> featureExpectationMap = new HashMap<MissingFeatureIndex, Double>();

		for (MissingFeatureIndex tmpMissIndex : featureLGMap.keySet()) {
			ArrayList<Double> tmpFeatureLGList = featureLGMap.get(tmpMissIndex);
			double expectation = 0;
			for (int i = 0; i < tmpFeatureLGList.size(); i++) {
				expectation += tmpFeatureLGList.get(i)
						* featureValueProbabilityMap.get(new InstanceFeature(
								tmpMissIndex.featureIndex, i, "",
								tmpMissIndex.instanceLabel));
			}
			featureExpectationMap.put(tmpMissIndex, expectation);
		}
		return featureExpectationMap;
	}

	public static class ValueComparator implements
			Comparator<MissingFeatureIndex> {

		Map<MissingFeatureIndex, Double> base;

		public ValueComparator(Map<MissingFeatureIndex, Double> base) {
			this.base = base;
		}

		@Override
		public int compare(MissingFeatureIndex arg0, MissingFeatureIndex arg1) {
			if (base.get(arg0).doubleValue() < base.get(arg1).doubleValue()) {
				return -1;
			} else {
				return 1;
			}
		}

	}

	public static void main(String[] args) throws Exception {
		CalculateExpectation test = new CalculateExpectation();

		HashMap<MissingFeatureIndex, Double> resultMap = test
				.calculateFeatureExpectation("data/mushroom_train.arff",
						"data/mushroom_train_miss.arff",
						"data/mushroom_evaluation.arff");
		ValueComparator comparator = new ValueComparator(resultMap);
		TreeMap<MissingFeatureIndex, Double> sortedMap = new TreeMap<MissingFeatureIndex, Double>(
				comparator);

		sortedMap.putAll(resultMap);

		// System.out.println("sorted: " + sortedMap);

		for (Entry<MissingFeatureIndex, Double> tmpEntry : sortedMap.entrySet()) {
			System.out.println(tmpEntry.getKey().toString() + " Expectation: "
					+ tmpEntry.getValue());
		}
	}

}
