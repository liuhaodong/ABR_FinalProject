package hello;

import weka.classifiers.bayes.NaiveBayes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Enumeration;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.AttributeStats;
import weka.core.Instances;

public class my_nb {
  public static void main(String[] args) throws Exception {

    BufferedReader bReader = new BufferedReader(new FileReader("data/mushroom_train_miss.arff"));
    Instances train = new Instances(bReader);
    train.setClassIndex(0);
    bReader.close();

    BufferedReader cReader = new BufferedReader(new FileReader("data/mushroom_train.arff"));
    Instances oracle = new Instances(cReader);
    oracle.setClassIndex(0);
    cReader.close();

    NaiveBayes nb = new NaiveBayes();
    nb.buildClassifier(train);
    Evaluation eval = new Evaluation(train);
    eval.crossValidateModel(nb, train, 10, new Random(1));
    // System.out.println(eval.toSummaryString());
    double[] a;
    a = nb.distributionForInstance(train.instance(0));
    System.out.println(a[0] + a[1]);

    Attribute k = train.attribute(1);
    Enumeration enu = k.enumerateValues();
    while (enu.hasMoreElements() == true) {
      System.out.println(enu.nextElement().toString());
    }
    train.instance(0).setValue(1, oracle.instance(0).toString(1));
    System.out.println(train.instance(0).toString(1));
  }

}
