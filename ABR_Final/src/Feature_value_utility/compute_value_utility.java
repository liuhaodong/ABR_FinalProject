package Feature_value_utility;

import weka.classifiers.bayes.NaiveBayes;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import weka.classifiers.Evaluation;
import weka.core.Instances;

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
    
    BufferedReader evaluationReader = new BufferedReader(new FileReader("data/mushroom_evaluation.arff"));
    Instances evaluation = new Instances(evaluationReader);
    evaluation.setClassIndex(0);
    evaluationReader.close();
    
    int num_attribute_train = train.numAttributes();
    int num_instance_train = train.numInstances();
    
    int num_attribute_evaluation = evaluation.numAttributes();
    int num_instance_evaluation = evaluation.numInstances();
    
    
    //NaiveBayes nb = new NaiveBayes();
    //nb.buildClassifier(train);
    //Evaluation eval = new Evaluation(train);
    //eval.crossValidateModel(nb, train, 10, new Random(1));
    //eval.evaluateModel(nb, evaluation);
    //System.out.println(eval.toSummaryString());
    
    //System.out.println(train.instance(0).stringValue(1));
    train.instance(0).setValue(1, oracle.instance(0).value(1));
    //System.out.println(oracle.instance(0).stringValue(1));
    //System.out.println(train.instance(0).stringValue(1));
    
    NaiveBayes nb1 = new NaiveBayes();
    nb1.buildClassifier(train);
    //Evaluation eval1 = new Evaluation(train);
    //eval1.crossValidateModel(nb1, train, 10, new Random(1));
    //eval1.evaluateModel(nb1, evaluation);
    //System.out.println(eval1.toSummaryString());
    
    
    
  }

}
