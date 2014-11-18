package Feature_value_utility;

import weka.classifiers.bayes.NaiveBayes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Enumeration;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.util.Enumeration;

public class compute_value_utility {
  public static void main(String[] args) throws Exception {
    BufferedReader oracleReader = new BufferedReader(new FileReader("data/mushroom_train.arff"));
    Instances oracle = new Instances(oracleReader);
    oracle.setClassIndex(0);
    oracleReader.close();

    BufferedReader trainReader = new BufferedReader(new FileReader("data/mushroom_train_miss.arff"));
    Instances train = new Instances(trainReader);
    train.setClassIndex(0);
    trainReader.close();

    BufferedReader evaluationReader = new BufferedReader(new FileReader(
            "data/mushroom_evaluation.arff"));
    Instances evaluation = new Instances(evaluationReader);
    evaluation.setClassIndex(0);
    evaluationReader.close();

    Attribute temp_attr = train.attribute(1);
    Enumeration enu = temp_attr.enumerateValues();
    while (enu.hasMoreElements() == true) {
      train.instance(0).setValue(1, enu.nextElement().toString());

      NaiveBayes nb1 = new NaiveBayes();
      nb1.buildClassifier(train);
      Evaluation eval1 = new Evaluation(train);
      // eval1.crossValidateModel(nb1, train, 10, new Random(1));
      double sum_LG = 0;
      for (int i = 0; i < evaluation.numInstances(); i++) {
        double[] a;
        a = nb1.distributionForInstance(evaluation.instance(i));
        eval1.evaluateModelOnce(nb1, evaluation.instance(i));
        double isCorrect = eval1.correct();
        double log;

        if (isCorrect == 1) {
          if (a[0] >= a[1]) {
            log = a[0];
          } else {
            log = a[1];
          }
        } else {
          if (a[0] >= a[1]) {
            log = a[1];
          } else {
            log = a[0];
          }
        }

        double LG;
        LG = -Math.log(log);
        sum_LG = sum_LG + LG;
      }
      System.out.println(sum_LG);
    }// end for
  }// end while
}
