package com.whoeveryou.FlameWekaClassifiers;

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
			String[] para1={"CLASSIFIER","weka.classifiers.trees.J48","-U","FILTER","weka.filters.unsupervised.instance.Randomize","DATASET","C:\\Program Files\\Weka-3-6\\data\\iris.arff"};
			WekaClassifierDemo.main(para1);

//			String[] para2={"C:\\Program Files\\Weka-3-6\\data\\iris.arff"};
//			WekaClusteringDemo.main(para2);
			
//			String[] para3 = {"C:\\Program Files\\Weka-3-6\\data\\iris.arff"};
//			WekaClassesToClusters.main(para3);
	
//			String[] para4 = {"C:\\Program Files\\Weka-3-6\\data\\iris.arff"};
//			WekaAttributeSelectionTest.main(para4);
			
//			WekaM5PExample weka5 = new WekaM5PExample();
//			weka5.main(null);
			
//			String[] para6={"weka.classifiers.functions.SMO","-K","\"weka.classifiers.functions.supportVector.RBFKernel\""};
//			WekaOptionsToCode.main(para6);
			
//			String[] para7 = {"option1","option2"};
//			WekaOptionTree.main(para7);
			
//			String[] para8 = {"C:\\Program Files\\Weka-3-6\\data\\iris.arff"};
//			WekaIncrementalClassifier.main(para8);
			
//			String[] para9 = {"C:\\Program Files\\Weka-3-6\\data\\iris.arff"};
//			WekaIncrementalClusterer.main(para9);
			
//			String[] para10 = {"C:\\Program Files\\Weka-3-6\\data\\cpu.arff"};
//			WekaArff2Database.main(para10);
			
//			MoaHoeffdingTree moa1 = new MoaHoeffdingTree();
//			moa1.main(null);
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
