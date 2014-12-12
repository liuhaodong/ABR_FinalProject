package missingFeaturesAquisition;

import weka.classifiers.bayes.NaiveBayes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import dataUtility.MissingFeatureIndex;
import dataUtility.ReadInstances;

public class FeatureUtility {

	public int classLabelIndex = 0;

	public HashMap<String, Integer> classValueIndexMap;

	public static Instances readInstances(String path) throws IOException {
		BufferedReader instancesReader = new BufferedReader(
				new FileReader(path));
		Instances instances = new Instances(instancesReader);
		instances.deleteAttributeAt(15);
		instancesReader.close();
		return instances;
	}
	
	public HashMap<MissingFeatureIndex, ArrayList<Double>> calculateLG(
			Instances oracle, Instances train, Instances eval, double selectRate)
			throws Exception{

		oracle.setClassIndex(classLabelIndex);
		train.setClassIndex(classLabelIndex);
		eval.setClassIndex(classLabelIndex);

		classValueIndexMap = new HashMap<String, Integer>();

		HashMap<MissingFeatureIndex, ArrayList<Double>> resultMap = new HashMap<MissingFeatureIndex, ArrayList<Double>>();

		int tmpIndex = 0;
		Enumeration<?> classValueEnum = eval.attribute(classLabelIndex)
				.enumerateValues();
		while (classValueEnum.hasMoreElements()) {
			classValueIndexMap.put(classValueEnum.nextElement().toString(),
					tmpIndex);
			tmpIndex++;
		}
		
		int countMissTotal = 0;
		int countMissCal = 0;
		for (int i = 0; i < train.numInstances(); i++) {
			//System.out.println("instance: " + i);
			for (int j = 0; j < train.numAttributes(); j++) {
				
				Instance tmpInstance = train.instance(i);
				ArrayList<Double> sumLGList = new ArrayList<Double>();
				if (tmpInstance.isMissing(j)) {
					countMissTotal++;
					if(Math.random() > selectRate) continue;
					countMissCal++;
					// If a feature is missing, then calculate LG
					Attribute missAttribute = tmpInstance.attribute(j);
					Enumeration<?> missValues = missAttribute.enumerateValues();

					while (missValues.hasMoreElements()) {
						try {
							train.instance(i).setValue(j,
									missValues.nextElement().toString());
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("FeatureIndex: " + j
									+ "missValue: "
									+ missValues.nextElement().toString());
						}

						NaiveBayes nb = new NaiveBayes();
						nb.buildClassifier(train);
						double tmpLG = 0;
						double sumLG = 0;
						for (int k = 0; k < eval.numInstances(); k++) {
							double[] probabilityDistribution = nb
									.distributionForInstance(eval.instance(k));
							String instanceClassLabel = eval.instance(k)
									.stringValue(classLabelIndex);
							int labelIndex = classValueIndexMap
									.get(instanceClassLabel);
							double log = probabilityDistribution[labelIndex];
							tmpLG = -Math.log(log);
							sumLG = sumLG + tmpLG;
						}
						sumLGList.add(sumLG);
					}
					
					train.instance(i).setMissing(j);
					
					resultMap.put(new MissingFeatureIndex(i, j, train.instance(i).stringValue(classLabelIndex)), sumLGList);
				} else {
					continue;
				}
			}
		}
		System.out.println("Total"+ countMissTotal);
		System.out.println("MissCal"+ countMissCal);
		return resultMap;
	}
	
	public HashMap<MissingFeatureIndex, ArrayList<Double>> calculateLG(
			Instances oracle, Instances train, Instances eval)
			throws Exception {

		oracle.setClassIndex(classLabelIndex);
		train.setClassIndex(classLabelIndex);
		eval.setClassIndex(classLabelIndex);

		classValueIndexMap = new HashMap<String, Integer>();

		HashMap<MissingFeatureIndex, ArrayList<Double>> resultMap = new HashMap<MissingFeatureIndex, ArrayList<Double>>();

		int tmpIndex = 0;
		Enumeration<?> classValueEnum = eval.attribute(classLabelIndex)
				.enumerateValues();
		while (classValueEnum.hasMoreElements()) {
			classValueIndexMap.put(classValueEnum.nextElement().toString(),
					tmpIndex);
			tmpIndex++;
		}

		for (int i = 0; i < train.numInstances(); i++) {
			//System.out.println("instance: " + i);
			for (int j = 0; j < train.numAttributes(); j++) {
				Instance tmpInstance = train.instance(i);
				ArrayList<Double> sumLGList = new ArrayList<Double>();
				if (tmpInstance.isMissing(j)) {
					// If a feature is missing, then calculate LG
					Attribute missAttribute = tmpInstance.attribute(j);
					Enumeration<?> missValues = missAttribute.enumerateValues();

					while (missValues.hasMoreElements()) {
						try {
							train.instance(i).setValue(j,
									missValues.nextElement().toString());
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("FeatureIndex: " + j
									+ "missValue: "
									+ missValues.nextElement().toString());
						}

						NaiveBayes nb = new NaiveBayes();
						nb.buildClassifier(train);
						double tmpLG = 0;
						double sumLG = 0;
						for (int k = 0; k < eval.numInstances(); k++) {
							double[] probabilityDistribution = nb
									.distributionForInstance(eval.instance(k));
							String instanceClassLabel = eval.instance(k)
									.stringValue(classLabelIndex);
							int labelIndex = classValueIndexMap
									.get(instanceClassLabel);
							double log = probabilityDistribution[labelIndex];
							tmpLG = -Math.log(log);
							sumLG = sumLG + tmpLG;
						}
						sumLGList.add(sumLG);
					}
					
					train.instance(i).setMissing(j);
					
					resultMap.put(new MissingFeatureIndex(i, j, train.instance(i).stringValue(classLabelIndex)), sumLGList);
				} else {
					continue;
				}
			}
		}
		return resultMap;
	}

	public HashMap<MissingFeatureIndex, ArrayList<Double>> calculateLG(
			String oraclePath, String trainingPath, String evalPath)
			throws Exception {

		Instances oracle = ReadInstances.readInstances(oraclePath);
		Instances train = ReadInstances.readInstances(trainingPath);
		Instances eval = ReadInstances.readInstances(evalPath);
	
		return this.calculateLG(oracle, train, eval);
	}

	public static void main(String[] args) throws Exception {
		FeatureUtility test = new FeatureUtility();
		HashMap<MissingFeatureIndex, ArrayList<Double>> result = test
				.calculateLG("data/mushroom_train.arff",
						"data/mushroom_train_miss.arff",
						"data/mushroom_evaluation.arff");
		for (MissingFeatureIndex tmpIndex : result.keySet()) {
			System.out.println(tmpIndex.toString());
			System.out.println(result.get(tmpIndex));
		}
	}

}
