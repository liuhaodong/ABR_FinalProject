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

public class FeatureProbility {
	
	public static Instances getInstances(String trainingFilePath) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(trainingFilePath));
		Instances resultInstances = new Instances(br);
		
		return resultInstances;
	}

	public HashMap<instanceFeature, Double> getFeatureProbility(String trainingPath) throws Exception{
		Instances trainingInstances = getInstances(trainingPath);
		HashMap<instanceFeature, Double> resultFeatureProbilityMap = new HashMap<>();
		for(int i = 1; i< trainingInstances.numAttributes(); i++){
			Instances tmp = trainingInstances;
			
			//delete attributes
			for(int j = 1; j < trainingInstances.numAttributes()&&j!=i; j++)
				tmp.deleteAttributeAt(j);
			
			tmp.setClassIndex(1);
			Attribute feature_0 = tmp.attribute(0);
			
			NaiveBayes nb = new NaiveBayes();
			nb.buildClassifier(tmp);
			
			
			Enumeration values_0 = feature_0.enumerateValues();
			Attribute feature_1 = tmp.attribute(0);
			Enumeration values_1 = feature_0.enumerateValues();
			
			
			//loop through all possible values and calculate probility
			while(values_0.hasMoreElements()){
				 String value_0 = values_0.nextElement().toString();
				while(values_1.hasMoreElements()){
					String value_1 = values_1.nextElement().toString();
					Instance tmp_instance = new Instance(2);
					tmp_instance.setValue(trainingInstances.attribute(0), value_0);
					tmp_instance.setValue(trainingInstances.attribute(1), value_1);
					double[] featureProbility = nb.distributionForInstance(tmp_instance);
				}
			}
			
			
			Evaluation eval = new Evaluation(tmp);
			eval.crossValidateModel(nb, tmp, 10, new Random(1));

		}
		return null;
	}

}
