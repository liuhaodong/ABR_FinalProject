package evaluation;

import java.io.IOException;
import java.util.ArrayList;
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

	public static ArrayList<MissingFeatureIndex> getQueryFeatures(
			String trainPath, double selectPercentage) throws IOException {
		Instances trainInstances = ReadInstances.readInstances(trainPath);
		ArrayList<MissingFeatureIndex> resultList = new ArrayList<MissingFeatureIndex>();
		for (int i = 0; i < trainInstances.numInstances(); i++) {
			for (int j = 0; j < trainInstances.numAttributes(); j++) {
				if (trainInstances.instance(i).isMissing(j)) {
					double rand = Math.random();
					if (rand < selectPercentage) {
						resultList.add(new MissingFeatureIndex(i, j));
					}
				}
			}
		}
		return resultList;
	}

	public static ArrayList<MissingFeatureIndex> getQueryFeatures(
			String trainPath, int queryNum) throws IOException {
		Instances trainInstances = ReadInstances.readInstances(trainPath);
		ArrayList<MissingFeatureIndex> resultList = new ArrayList<MissingFeatureIndex>();

		int count = 0;

		for (int i = 0; i < trainInstances.numInstances() && count < queryNum; i++) {
			for (int j = 0; j < trainInstances.numAttributes(); j++) {
				if (trainInstances.instance(i).isMissing(j)) {
					resultList.add(new MissingFeatureIndex(i, j));
					count++;
				}
			}
		}

		return resultList;
	}

	public static void evaluateRandomLearner(String oraclePath,
			String trainPath, String evaluationPath) throws Exception {
		Instances trainInstances = ReadInstances.readInstances(trainPath);
		Instances oracleInstances = ReadInstances.readInstances(oraclePath);
		Instances evaluationInstances = ReadInstances
				.readInstances(evaluationPath);

		ArrayList<MissingFeatureIndex> featuresToQuery = getQueryFeatures(
				trainPath, 1);

		Evaluation trainEval = NBEvaluate.eval(trainInstances,
				evaluationInstances);
		System.out.println(trainEval.toSummaryString());

		for (MissingFeatureIndex tmpFeatureIndex : featuresToQuery) {
			trainInstances.instance(tmpFeatureIndex.instanceIndex).setValue(
					tmpFeatureIndex.featureIndex,
					oracleInstances.instance(tmpFeatureIndex.instanceIndex)
							.stringValue(tmpFeatureIndex.featureIndex));
		}

		Evaluation trainEval2 = NBEvaluate.eval(trainInstances,
				evaluationInstances);
		System.out.println(trainEval2.toSummaryString());
	}

	public static void evaluateSequentialLearner(String oraclePath,
			String trainPath, String evaluationPath) throws Exception {
		Instances trainInstances = ReadInstances.readInstances(trainPath);
		Instances oracleInstances = ReadInstances.readInstances(oraclePath);
		Instances evaluationInstances = ReadInstances
				.readInstances(evaluationPath);

		ArrayList<MissingFeatureIndex> featuresToQuery = getQueryFeatures(
				trainPath, 100);

		Evaluation trainEval = NBEvaluate.eval(trainInstances,
				evaluationInstances);
		System.out.println(trainEval.toSummaryString());

		for (MissingFeatureIndex tmpFeatureIndex : featuresToQuery) {
			trainInstances.instance(tmpFeatureIndex.instanceIndex).setValue(
					tmpFeatureIndex.featureIndex,
					oracleInstances.instance(tmpFeatureIndex.instanceIndex)
							.stringValue(tmpFeatureIndex.featureIndex));
		}

		Evaluation trainEval2 = NBEvaluate.eval(trainInstances,
				evaluationInstances);
		System.out.println(trainEval2.toSummaryString());
	}

	public static void evaluateActiveLeaner(Instances oracleInstances,
			Instances trainInstances, Instances evalInstances) throws Exception {

		CalculateExpectation test = new CalculateExpectation();

		HashMap<MissingFeatureIndex, Double> resultMap = test
				.calculateFeatureExpectation(oracleInstances, trainInstances,
						evalInstances);
		ValueComparator comparator = new ValueComparator(resultMap);
		TreeMap<MissingFeatureIndex, Double> sortedMap = new TreeMap<MissingFeatureIndex, Double>(
				comparator);

		sortedMap.putAll(resultMap);

		Evaluation trainEval = NBEvaluate.eval(trainInstances, evalInstances);
		System.out.println(trainEval.toSummaryString());
		int batchNum = 50;


		for (Entry<MissingFeatureIndex, Double> tmpEntry : sortedMap.entrySet()) {
			if (batchNum == 0) {
				break;
			} else {
				batchNum--;
			}
			int tmpInstanceIndex = tmpEntry.getKey().instanceIndex;
			int tmpFeatureIndex = tmpEntry.getKey().featureIndex;
		//	System.out.println("instance quieried id: " + tmpInstanceIndex
		//			+ " feature id: " + tmpFeatureIndex);
			trainInstances.instance(tmpInstanceIndex).setValue(
					tmpFeatureIndex,
					oracleInstances.instance(tmpInstanceIndex).stringValue(
							tmpFeatureIndex));
		}

		Evaluation trainEval2 = NBEvaluate.eval(trainInstances, evalInstances);
		System.out.println(trainEval2.toSummaryString());

	}

	public static void evaluateActiveLeaner(String oraclePath,
			String trainPath, String evaluationPath) throws Exception {

		Instances oracleInstances = ReadInstances.readInstances(oraclePath);
		Instances trainInstances = ReadInstances.readInstances(trainPath);
		Instances evalInstances = ReadInstances.readInstances(evaluationPath);

		evaluateActiveLeaner(oracleInstances, trainInstances, evalInstances);

	}
	
	public static void evaluateSimplifiedActiveLeaner(Instances oracleInstances,
			Instances trainInstances, Instances evalInstances, double selectRate) throws Exception{

		CalculateExpectation test = new CalculateExpectation();

		HashMap<MissingFeatureIndex, Double> resultMap = test
				.calculateSimplifiedFeatureExpectation(oracleInstances, trainInstances,
						evalInstances, selectRate);
		ValueComparator comparator = new ValueComparator(resultMap);
		TreeMap<MissingFeatureIndex, Double> sortedMap = new TreeMap<MissingFeatureIndex, Double>(
				comparator);

		sortedMap.putAll(resultMap);

		Evaluation trainEval = NBEvaluate.eval(trainInstances, evalInstances);
		System.out.println(trainEval.toSummaryString());
		int batchNum = 100;


		for (Entry<MissingFeatureIndex, Double> tmpEntry : sortedMap.entrySet()) {
			if (batchNum == 0) {
				break;
			} else {
				batchNum--;
			}
			int tmpInstanceIndex = tmpEntry.getKey().instanceIndex;
			int tmpFeatureIndex = tmpEntry.getKey().featureIndex;
		//	System.out.println("instance quieried id: " + tmpInstanceIndex
		//			+ " feature id: " + tmpFeatureIndex);
			trainInstances.instance(tmpInstanceIndex).setValue(
					tmpFeatureIndex,
					oracleInstances.instance(tmpInstanceIndex).stringValue(
							tmpFeatureIndex));
		}

		Evaluation trainEval2 = NBEvaluate.eval(trainInstances, evalInstances);
		System.out.println(trainEval2.toSummaryString());

	}

	public static void evaluateSimplifiedActiveLeaner(String oraclePath,
			String trainPath, String evaluationPath, double selectRate) throws Exception {
		Instances oracleInstances = ReadInstances.readInstances(oraclePath);
		Instances trainInstances = ReadInstances.readInstances(trainPath);
		Instances evalInstances = ReadInstances.readInstances(evaluationPath);

		evaluateSimplifiedActiveLeaner(oracleInstances, trainInstances, evalInstances, selectRate);
	}

	public static void main(String[] args) throws Exception {

		Instances oracleInstances = ReadInstances
				.readInstances("data/mushroom_train.arff");
		Instances trainInstances = ReadInstances
				.readInstances("data/mushroom_train_miss.arff");
		Instances evalInstances = ReadInstances
				.readInstances("data/mushroom_evaluation.arff");


		evaluateSimplifiedActiveLeaner(oracleInstances, trainInstances, evalInstances,1);

		evaluateSimplifiedActiveLeaner(oracleInstances, trainInstances, evalInstances,1);

	}

}
