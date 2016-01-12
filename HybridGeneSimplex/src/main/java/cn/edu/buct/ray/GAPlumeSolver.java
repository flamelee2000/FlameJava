package cn.edu.buct.ray;

import java.util.Random;

public class GAPlumeSolver {

	private double crossOverRate;
	private double[][] densityMeasured;
	private int generationBound;
	private double maxQ0;
	private double maxY0;
	private double maxZ0;
	private double minQ0;
	private double minY0;
	private double mutationRate;
	private int sizePopulation;
	private double stopE;
	private int stability;
	private int urCondition;
	private double u;

	private static int[] randomCommon(int min, int max, int n) {
		if (n > (max - min + 1) || max < min) {
			return null;
		}
		int[] result = new int[n];
		int count = 0;
		while (count < n) {
			int num = (int) (Math.random() * (max - min)) + min;
			boolean flag = true;
			for (int j = 0; j < n; j++) {
				if (num == result[j]) {
					flag = false;
					break;
				}
			}
			if (flag) {
				result[count] = num;
				count++;
			}
		}
		return result;
	}

	public GAPlumeSolver(double[][] densityMeasured, int stability,
			int urCondition, double u) {
		this.densityMeasured = densityMeasured;
		this.stability = stability;
		this.urCondition = urCondition;
		this.u = u;
	}

	private double calculateDistance(PGPlumeModel lm) {
		int sensorNum = densityMeasured.length;
		double[] densityCalculating = new double[sensorNum];
		double dist = 0;
		for (int i = 0; i < sensorNum; i++) {
			densityCalculating[i] = lm.getDensity(densityMeasured[i][0],
					densityMeasured[i][1], densityMeasured[i][2]);
			dist += Math.pow(densityCalculating[i] - densityMeasured[i][3], 2);
		}
		return dist;
	}

	private double[][] crossover(double[][] chromosomes) {
		int halfCrossNum = (int) Math
				.floor((sizePopulation * crossOverRate / 2));
		int[] randomIndex = randomCommon(0, sizePopulation - 1,
				halfCrossNum * 2);
		double[][] crossResult = new double[halfCrossNum][4];
		for (int i = 0; i < halfCrossNum; i++) {
			crossResult[i][0] = (chromosomes[randomIndex[i]][0] + chromosomes[randomIndex[i
					+ halfCrossNum]][0]) / 2;
			crossResult[i][1] = (chromosomes[randomIndex[i]][1] + chromosomes[randomIndex[i
					+ halfCrossNum]][1]) / 2;
			crossResult[i][2] = (chromosomes[randomIndex[i]][2] + chromosomes[randomIndex[i
					+ halfCrossNum]][2]) / 2;
			crossResult[i][3] = calculateDistance(new PGPlumeModel(
					crossResult[i][0], crossResult[i][1], crossResult[i][2],
					stability, urCondition, u));
		}
		int len = chromosomes.length;
		for (int i = 0; i < halfCrossNum; i++) {
			chromosomes[len - 1 - i] = crossResult[i];
		}
		return ordering(chromosomes);
	}

	private double[] GAProcess(double[][] chromosomes) {
		int generationNumber = 0;
		while (!terminationTest(chromosomes[0][3])
				& generationNumber < generationBound) {
			chromosomes = crossover(chromosomes);
			chromosomes = mutation(chromosomes);
			chromosomes = ordering(chromosomes);
			generationNumber++;
		}
		System.out.println("Number of generations : "
				+ Integer.toString(generationNumber));

		double[] bestIndividual = chromosomes[0];
		return bestIndividual;
	}

	public double[] GASolve() {
		double[][] chromosomes = initialGA();
		double[] finalResult = GAProcess(chromosomes);
		return finalResult;
	}

	public double getCrossOverRate() {
		return crossOverRate;
	}

	public int getGenerationBound() {
		return generationBound;
	}

	public double getMaxQ0() {
		return maxQ0;
	}

	public double getMaxY0() {
		return maxY0;
	}

	public double getMinQ0() {
		return minQ0;
	}

	public double getMinY0() {
		return minY0;
	}

	public double getMutationRate() {
		return mutationRate;
	}

	public int getSizePopulation() {
		return sizePopulation;
	}

	public int getStability() {
		return stability;
	}

	public void setStability(int stability) {
		this.stability = stability;
	}

	public double getStopE() {
		return stopE;
	}

	private double[][] initialGA() {
		if (sizePopulation < 10)
			sizePopulation = 10;
		double[][] chromosomes = new double[sizePopulation][4];
		for (int i = 0; i < sizePopulation; i++) {
			chromosomes[i][0] = Math.random() * (maxQ0 - minQ0) + minQ0;
			chromosomes[i][1] = Math.random() * (maxY0 - minY0) + minY0;
			chromosomes[i][2] = Math.random() * (maxZ0 - minZ0) + minZ0;
			chromosomes[i][3] = calculateDistance(new PGPlumeModel(
					chromosomes[i][0], chromosomes[i][1], chromosomes[i][2],
					stability, urCondition, u));
		}
		return ordering(chromosomes);
	}

	private double[][] mutation(double[][] chromosomes) {
		int mutationNum = (int) Math.floor((sizePopulation * mutationRate));
		int[] randomIndex = randomCommon(0, sizePopulation - 1, mutationNum);
		double[][] mutationResult = new double[mutationNum][4];
		for (int i = 0; i < mutationNum; i++) {
			mutationResult[i] = chromosomes[randomIndex[i]];
			int j = new Random().nextInt(3);
			switch (j) {
			case 0:
				mutationResult[i][0] = Math.random() * (maxQ0 - minQ0) + minQ0;
				break;
			case 1:
				mutationResult[i][1] = Math.random() * (maxY0 - minY0) + minY0;
				break;
			case 2:
				mutationResult[i][2] = Math.random() * (maxZ0 - minZ0) + minZ0;
				break;
			}
			mutationResult[i][3] = calculateDistance(new PGPlumeModel(
					mutationResult[i][0], mutationResult[i][1],
					mutationResult[i][2], stability, urCondition, u));
		}
		int len = chromosomes.length;
		for (int i = 0; i < mutationNum; i++) {
			chromosomes[len - 1 - i] = mutationResult[i];
		}
		return ordering(chromosomes);
	}

	private double[][] ordering(double[][] chromosomes) {
		int len = chromosomes.length;
		double[] temp;
		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j <= len - 1; j++) {
				if (chromosomes[i][3] > chromosomes[j][3]) {
					temp = chromosomes[i];
					chromosomes[i] = chromosomes[j];
					chromosomes[j] = temp;
				}
			}
		}
		return chromosomes;
	}

	public void setCrossOverRate(double crossOverRate) {
		this.crossOverRate = crossOverRate;
	}

	public void setGenerationBound(int generationBound) {
		this.generationBound = generationBound;
	}

	public void setMaxQ0(double maxQ0) {
		this.maxQ0 = maxQ0;
	}

	public void setMaxY0(double maxY0) {
		this.maxY0 = maxY0;
	}

	public void setMinQ0(double minQ0) {
		this.minQ0 = minQ0;
	}

	public void setMinY0(double minY0) {
		this.minY0 = minY0;
	}

	public void setMutationRate(double mutationRate) {
		this.mutationRate = mutationRate;
	}

	public void setSizePopulation(int sizePopulation) {
		this.sizePopulation = sizePopulation;
	}

	public int getUrCondition() {
		return urCondition;
	}

	public void setUrCondition(int urCondition) {
		this.urCondition = urCondition;
	}

	public void setStopE(double stopE) {
		this.stopE = stopE;
	}

	public double getMaxZ0() {
		return maxZ0;
	}

	public void setMaxZ0(double maxZ0) {
		this.maxZ0 = maxZ0;
	}

	public double getMinZ0() {
		return minZ0;
	}

	public void setMinZ0(double minZ0) {
		this.minZ0 = minZ0;
	}

	private double minZ0;

	private boolean terminationTest(double bestF) {
		if (bestF < stopE) {
			return true;
		} else {
			return false;
		}
	}

}
