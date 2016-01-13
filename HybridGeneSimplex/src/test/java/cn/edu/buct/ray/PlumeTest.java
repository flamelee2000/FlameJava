package cn.edu.buct.ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class PlumeTest {
	
	@Test
	public void testPlume() {
		
		List<Sensor> sensors = new ArrayList<Sensor>();
		sensors.add(new Sensor(240.0, 5.0, 0.0, 0.10914448867083733));
		sensors.add(new Sensor(242.0, 10.0, 0.0, 0.17338405713633676));
		sensors.add(new Sensor(244.0, 20.0, 0.0, 0.18133154729660994));
		sensors.add(new Sensor(246.0, 23.0, 0.0, 0.15408299345442455));
		sensors.add(new Sensor(248.0, 24.0, 0.0, 0.14759862270063084));
		sensors.add(new Sensor(250.0, 25.0, 0.0, 0.1401537537248513));
		sensors.add(new Sensor(252.0, 24.0, 0.0, 0.16149016612474731));
		sensors.add(new Sensor(254.0, 20.0, 0.0, 0.22266455658743103));
		sensors.add(new Sensor(256.0, 22.0, 0.0, 0.20559229553296893));
		sensors.add(new Sensor(258.0, 10.0, 0.0, 0.2397976029403484));
		PlumeChromosome finalPlumeChromosome = new PlumeChromosome(2700, 8, 5, sensors, 5,0,2);

		long startTime = System.currentTimeMillis();
		System.out.println("Performing genetic algorithm ...");
		GAPlumeSolver gs = new GAPlumeSolver(sensors, 5, 0, 2);
		gs.setMinQ0(20.0);
		gs.setMaxQ0(8000.0);
		gs.setMinY0(2.0);
		gs.setMaxY0(50.0);
		gs.setMinZ0(0);
		gs.setMaxZ0(20.0);
		gs.setStopE(1E-6);
		gs.setCrossOverRate(0.9);
		gs.setMutationRate(0.05);
		gs.setSizePopulation(300);
		gs.setGenerationBound(200);
		finalPlumeChromosome = gs.GASolve();
		System.out.println("Result: " + finalPlumeChromosome.toString());
//		System.out.println("Applying Nelder Mead simplex method ...");
//		NelderMeadPlumeSolver nms = new NelderMeadPlumeSolver(densityMeasured0,
//				result[0], result[1], result[2], 5, 0, 2);
//		double[] h1 = { 90, 3, 2 };
//		nms.setH(h1);
//		nms.setAlfa(1);
//		nms.setBeta(0.5);
//		nms.setGama(2);
//		nms.setDelta(0.5);
//		nms.setStopE(1E-9);
//		nms.setMaxiIterationsNumber(9999);
//		result = nms.NelderMeadSolve();
//		System.out.println("Result: " + Arrays.toString(result));
		long timeConsumed = System.currentTimeMillis() - startTime;
		System.out.println("time consumed:" + Long.toString(timeConsumed, 10)
				+ " (ms)");
	}
	
	@Ignore
	@Test
	public void generatePlumeTestData() {
		double[][] densityMeasured0 = {
				{ 240.0, 5.0, 0.0, 0.10914448867083733 },
				{ 242.0, 10.0, 0.0, 0.17338405713633676 },
				{ 244.0, 20.0, 0.0, 0.18133154729660994 },
				{ 246.0, 23.0, 0.0, 0.15408299345442455 },
				{ 248.0, 24.0, 0.0, 0.14759862270063084 },
				{ 250.0, 25.0, 0.0, 0.1401537537248513 },
				{ 252.0, 24.0, 0.0, 0.16149016612474731 },
				{ 254.0, 20.0, 0.0, 0.22266455658743103 },
				{ 256.0, 22.0, 0.0, 0.20559229553296893 },
				{ 258.0, 10.0, 0.0, 0.2397976029403484 } };
		PGPlumeModel lm2 = new PGPlumeModel(2000, 15, 10, 5, 0, 2);
		for (int i = 0; i < densityMeasured0.length; i++) {
			densityMeasured0[i][3] = lm2.getDensity(densityMeasured0[i][0],
					densityMeasured0[i][1], densityMeasured0[i][2]);
		}
		System.out.print(Arrays.deepToString(densityMeasured0));
	}

}
