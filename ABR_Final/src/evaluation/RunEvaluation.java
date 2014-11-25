package evaluation;

import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import weka.classifiers.Evaluation;
import weka.core.Instances;
import missingFeaturesAquisition.CalculateExpectation;
import missingFeaturesAquisition.CalculateExpectation.ValueComparator;
import dataUtility.MissingFeatureIndex;
import dataUtility.ReadInstances;

public class RunEvaluation {

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
		
		Instances oracleInstances = ReadInstances.readInstances("data/mushroom_train.arff");
		Instances trainInstances = ReadInstances.readInstances("data/mushroom_train.arff");
		
		Evaluation trainEval = NBCV.eval(trainInstances);
		System.out.println(trainEval.toSummaryString());
		int batchNum = 50;
		
		for (Entry<MissingFeatureIndex, Double> tmpEntry : sortedMap.entrySet()) {
			if (batchNum == 0) {
				break;
			}else {
				batchNum--;
			}
			int tmpInstanceIndex = tmpEntry.getKey().instanceIndex;
			int tmpFeatureIndex = tmpEntry.getKey().featureIndex;
			trainInstances.instance(tmpInstanceIndex).setValue(trainInstances.attribute(tmpFeatureIndex), oracleInstances.instance(tmpInstanceIndex).stringValue(oracleInstances.attribute(tmpFeatureIndex)));
		}
		
		Evaluation trainEval2 = NBCV.eval(trainInstances);
		System.out.println(trainEval2.toSummaryString());

	}

}
