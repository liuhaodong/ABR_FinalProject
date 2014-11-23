package missingFeaturesAquisition;

import java.util.ArrayList;
import java.util.HashMap;

import dataUtility.MissingFeatureIndex;
import dataUtility.InstanceFeature;

public class CalculateExpectation {

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
				System.out.println(tmpMissIndex.toString());
				expectation += tmpFeatureLGList.get(i)
						* featureValueProbabilityMap.get(new InstanceFeature(
								tmpMissIndex.featureIndex,
								i, "",
								tmpMissIndex.instanceLabel));
			}
			featureExpectationMap.put(tmpMissIndex, expectation);
		}
		return featureExpectationMap;
	}

	public static void main(String[] args) throws Exception {
		CalculateExpectation test = new CalculateExpectation();

		HashMap<MissingFeatureIndex, Double> resultMap = test
				.calculateFeatureExpectation("data/mushroom_train.arff",
						"data/mushroom_train_miss.arff",
						"data/mushroom_evaluation.arff");
		
		for (MissingFeatureIndex tmpFeatureIndex : resultMap.keySet()) {
			System.out.println(tmpFeatureIndex.toString()+ " Expectation: " + resultMap.get(tmpFeatureIndex));
		}
	}

}
