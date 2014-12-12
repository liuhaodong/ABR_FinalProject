ABR_FinalProject
================
This is the implementation of ABR Final Project. \br

Use RunExperiment.java to run the experiment. Modify training, oracle and evaluation dataset path in the main function and select different feature selection stratigy, eg: Active Learner, Simplifed Active Learner, Random Learner and Sequential Learner. \br

Brief Description. \br

dataProcessing/FeatureRemoval: Remove features to create a training set with missing feature values.\br
dataUtility/InstanceFeature: Helper class for storing instance feature. \br
dataUtility/MissingFeatureIndex: Helper class for storing missing feature index. \br
dataUtility/ReadInstances: Helper class to read instance files. \br
evaluation/NBCV: Naive Bayes Cross Validation. \br
evaluation/NBEvaluate: Naive Bayes Evaluator. \br
evaluation/RunEvaluation: Main Class. Use this to run experiment. \br
missingFeatureAquisition/CalculateExpectation: Calculate missing feature expectation. \br
missingFeatureAquisition/FeatureProbability: Calculate probability of feature values. \br
missingFeatureAquisition/FeatureUtility: Calculate feature utility (Log Gain). \br

