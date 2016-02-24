package com.whoeveryou.FlameWekaClassifiers;

import com.whoeveryou.FlameWekaCluster.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */

	public void testApp() {

		try {
//			WekaClassifierDemo weka1 = new WekaClassifierDemo();
//			String[] para1={"CLASSIFIER","weka.classifiers.trees.J48","-U","FILTER","weka.filters.unsupervised.instance.Randomize","DATASET","C:\\Program Files\\Weka-3-6\\data\\iris.arff"};
//			weka1.main(para1);

//			String para2a = "C:\\Program Files\\Weka-3-6\\data\\iris.arff";
//			WekaClusteringDemo weka2 = new WekaClusteringDemo(para2a);
//			String[] para2b={para2a};
//			weka2.main(para2b);
			
//			String[] para3 = {"C:\\Program Files\\Weka-3-6\\data\\iris.arff"};
//			WekaClassesToClusters weka3 = new WekaClassesToClusters();
//			weka3.main(para3);
	
//			String[] para4 = {"C:\\Program Files\\Weka-3-6\\data\\iris.arff"};
//			WekaAttributeSelectionTest weka4 = new WekaAttributeSelectionTest();
//			weka4.main(para4);
			
//			// Failed
//			WekaM5PExample weka5 = new WekaM5PExample();
//			weka5.main(null);
			
//			// Failed
//			WekaOptionsToCode weka6 = new WekaOptionsToCode();
//			String[] para6={"weka.classifiers.functions.SMO","-K","\"weka.classifiers.functions.supportVector.RBFKernel\"",">","d:\\OptionsTest.java"};
//			weka6.main(para6);
			
//			// Failed
//			WekaOptionTree.main(null);
			
//			String[] para8 = {"C:\\Program Files\\Weka-3-6\\data\\iris.arff"};
//			WekaIncrementalClassifier weka8 = new WekaIncrementalClassifier();
//			weka8.main(para8);
			
			
//			String[] para9 = {"C:\\Program Files\\Weka-3-6\\data\\iris.arff"};
//			WekaIncrementalClusterer weka9 = new WekaIncrementalClusterer();
//			weka9.main(para9);
			
			MoaHoeffdingTree moa1 = new MoaHoeffdingTree();
			moa1.main(null);
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
