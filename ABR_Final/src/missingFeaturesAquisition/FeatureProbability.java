package missingFeaturesAquisition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import dataUtility.InstanceFeature;
import dataUtility.ReadInstances;

public class FeatureProbability {

	public static Instances getInstances(String trainingFilePath)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(trainingFilePath));
		Instances resultInstances = new Instances(br);
		resultInstances.deleteAttributeAt(15);
		return resultInstances;
	}
	
	public  HashMap<InstanceFeature, Double> getFeatureProbility(
			Instances trainingInstances) throws Exception {
		HashMap<InstanceFeature, Double> resultFeatureProbilityMap = new HashMap<InstanceFeature, Double>();
		for (int i = 1; i < trainingInstances.numAttributes(); i++) {
			Instances tmp = new Instances(trainingInstances);
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
			
			
				

			// loop through all possible values and calculate probility
			
			int valueIndex = 0;
			while (values_0.hasMoreElements()) {
				
				String value_0 = values_0.nextElement().toString();
				Instance tmp_instance = new DenseInstance(2);
				tmp_instance.setValue(trainingInstances.attribute(0), value_0);
				tmp.add(tmp_instance);

				 double[] featureProbability = nb
				 .distributionForInstance(tmp.lastInstance());
				 
				 for (int j = 0; j < featureProbability.length; j++) {
					 InstanceFeature tmpInstanceFeature = new InstanceFeature(i,j, feature_1.value(j), value_0);
					 tmpInstanceFeature.featureValueIndex = j;
					 resultFeatureProbilityMap.put(tmpInstanceFeature, featureProbability[j]);
				}
				 
				 valueIndex++;
			}
		}
		return resultFeatureProbilityMap;
	}

	public HashMap<InstanceFeature, Double> getFeatureProbility(
			String trainingPath) throws Exception {
		Instances trainingInstances = ReadInstances.readInstances(trainingPath);
		return this.getFeatureProbility(trainingInstances);
	}

	public static void main(String[] args) throws Exception {
		HashMap<InstanceFeature, Double> result = (new FeatureProbability())
				.getFeatureProbility("data/mushroom_train.arff");
		
		for (InstanceFeature tmpFeature : result.keySet()) {
			System.out.println(tmpFeature.toString());
			System.out.println("probability: "+ result.get(tmpFeature));
		}
		
		System.out.println(result.get(new InstanceFeature(6800,13, "", "e")));
	}

}
