package com.whoeveryou.FlameWekaClassifiers;

import com.whoeveryou.FlameWekaCluster.WekaClusteringDemo;

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

		WekaClassifierDemo weka1 = new WekaClassifierDemo();
		MoaHoeffdingTree moa1 = new MoaHoeffdingTree();
		
		WekaIncrementalClassifier ap3 = new WekaIncrementalClassifier();
		WekaOptionTree ap4 = new WekaOptionTree();
		try {
//			String[] para1={"CLASSIFIER","weka.classifiers.trees.J48","-U","FILTER","weka.filters.unsupervised.instance.Randomize","DATASET","C:\\Program Files\\Weka-3-6\\data\\iris.arff"};
//			weka1.main(para1);
			String para2a = "C:\\Program Files\\Weka-3-6\\data\\iris.arff";
			WekaClusteringDemo weka2 = new WekaClusteringDemo(para2a);
			String[] para2b={para2a};
			weka2.main(para2b);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
