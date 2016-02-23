package com.whoeveryou.FlameWekaClassifiers;

import java.io.File;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

/**
 * Hello world!
 *
 */
public class WekaJ48 {  
	   
    /** 
     * @param args 
     * @throws Exception  
     */  
    public static void main(String[] args) throws Exception {  
         Classifier m_classifier = new J48();  
            File inputFile = new File("C:\\Program Files\\Weka-3-6\\data\\cpu.with.vendor.arff");
            ArffLoader atf = new ArffLoader();   
            atf.setFile(inputFile);  
            Instances instancesTrain = atf.getDataSet();  
            inputFile = new File("C:\\Program Files\\Weka-3-6\\data\\cpu.with.vendor.arff");
            atf.setFile(inputFile);            
            Instances instancesTest = atf.getDataSet();
            instancesTest.setClassIndex(0);
            double sum = instancesTest.numInstances(),
            right = 0.0f;  
            instancesTrain.setClassIndex(0);  
             m_classifier.buildClassifier(instancesTrain); 
            for(int  i = 0;i<sum;i++)
            {  
                if(m_classifier.classifyInstance(instancesTest.instance(i))==instancesTest.instance(i).classValue())
                {  
                  right++;
                }  
            }  
            System.out.println("J48 classification precision:"+(right/sum));  
    }  
}  

