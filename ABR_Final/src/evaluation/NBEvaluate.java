package evaluation;

import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class NBEvaluate {
	public static Evaluation eval(Instances train, Instances evaluation)
			throws Exception {
		 //Classifier cls = new weka.classifiers.trees.J48();
		Classifier cls = new weka.classifiers.bayes.NaiveBayes();
		cls.buildClassifier(train);
		Evaluation eval = new Evaluation(train);
		eval.evaluateModel(cls, evaluation);
		return eval;
	}

}
