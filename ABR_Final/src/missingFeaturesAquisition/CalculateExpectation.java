package missingFeaturesAquisition;

import java.util.ArrayList;
import java.util.HashMap;

import dataUtility.MissingFeatureIndex;
import dataUtility.instanceFeature;

public class CalculateExpectation {
	
	public HashMap<MissingFeatureIndex, Double> calculateFeatureExpectation(String oraclePath, String trainingPath, String evalPath) throws Exception{
		
		HashMap<instanceFeature, Double> featureValueProbabilityMap = (new FeatureProbability()).getFeatureProbility(trainingPath);
		HashMap<MissingFeatureIndex, ArrayList<Double>> featureLGMap = (new FeatureUtility()).calculateLG(oraclePath, trainingPath, evalPath);
		
		HashMap<MissingFeatureIndex, Double> featureExpectationMap = new HashMap<MissingFeatureIndex, Double>();
		
		return null;
	}

}
