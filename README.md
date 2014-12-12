ABR_FinalProject
================
This is the implementation of ABR Final Project. 

Use RunExperiment.java to run the experiment. Modify training, oracle and evaluation dataset path in the main function and select different feature selection stratigy, eg: Active Learner, Simplifed Active Learner, Random Learner and Sequential Learner. 

Brief Description. 

dataProcessing/FeatureRemoval: Remove features to create a training set with missing feature values.
dataUtility/InstanceFeature: Helper class for storing instance feature. 
dataUtility/MissingFeatureIndex: Helper class for storing missing feature index. 
dataUtility/ReadInstances: Helper class to read instance files. 
evaluation/NBCV: Naive Bayes Cross Validation. 
evaluation/NBEvaluate: Naive Bayes Evaluator. 
evaluation/RunEvaluation: Main Class. Use this to run experiment. 
missingFeatureAquisition/CalculateExpectation: Calculate missing feature expectation. 
missingFeatureAquisition/FeatureProbability: Calculate probability of feature values. 
missingFeatureAquisition/FeatureUtility: Calculate feature utility (Log Gain). 

