package evaluation;

import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class NBCV {
	public static Evaluation eval(Instances train) throws Exception{
		NaiveBayes nb = new NaiveBayes();
		nb.buildClassifier(train);
		Evaluation eval = new Evaluation(train);
		eval.crossValidateModel(nb, train, 10, new Random(1));
		return eval;
	}

}
