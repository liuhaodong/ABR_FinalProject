package Feature_value_utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import dataUtility.instanceFeature;

public class FeatureProbability {

	public static Instances getInstances(String trainingFilePath)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(trainingFilePath));
		Instances resultInstances = new Instances(br);
		resultInstances.deleteAttributeAt(15);
		return resultInstances;
	}

	public HashMap<instanceFeature, Double> getFeatureProbility(
			String trainingPath) throws Exception {
		Instances trainingInstances = getInstances(trainingPath);
		HashMap<instanceFeature, Double> resultFeatureProbilityMap = new HashMap<instanceFeature, Double>();
		for (int i = 1; i < trainingInstances.numAttributes(); i++) {
			Instances tmp = new Instances(trainingInstances);
			System.out.println("feature: " + i);
			tmp.setClassIndex(0);
			// delete attributes
			int count = 1;
			boolean flag = false;
			while (count < trainingInstances.numAttributes()) {
				if (count == i) {
					count++;
					flag = true;
				} else {
					if (flag == false) {
						tmp.deleteAttributeAt(1);
						count++;
					} else {
						tmp.deleteAttributeAt(2);
						count++;
					}
				}

			}

			tmp.setClassIndex(1);

			NaiveBayes nb = new NaiveBayes();
			nb.buildClassifier(tmp);

			Attribute feature_0 = tmp.attribute(0);
			Enumeration<?> values_0 = feature_0.enumerateValues();
			
			Attribute feature_1 = tmp.attribute(1);
			Enumeration<?> values_1 = feature_1.enumerateValues();
			
			while(values_1.hasMoreElements())
				System.out.println(values_1.nextElement().toString());
			
				

			// loop through all possible values and calculate probility
			while (values_0.hasMoreElements()) {
				
				String value_0 = values_0.nextElement().toString();
				System.out.println("label: "+value_0 + "\n");
				Instance tmp_instance = new Instance(2);
				tmp_instance.setValue(trainingInstances.attribute(0), value_0);
				tmp.add(tmp_instance);

				 double[] featureProbability = nb
				 .distributionForInstance(tmp.lastInstance());
				 for(double d: featureProbability){
					 System.out.println(d);
				 }
			}
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		(new FeatureProbability())
				.getFeatureProbility("data/mushroom_train.arff");
	}

}
