package cn.edu.buct.ray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MultiSourceGAPlumeSolver {

	private double crossOverRate;
	private List<Sensor> sensors;
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

	public MultiSourceGAPlumeSolver(List<Sensor> sensors, int stability,
			int urCondition, double u) {
		this.sensors = sensors;
		this.stability = stability;
		this.urCondition = urCondition;
		this.u = u;
	}

	@SuppressWarnings("unchecked")
	private List<MultiSourcePlumeChromosome> crossover(
			List<MultiSourcePlumeChromosome> multiSourcePlumeChromosome) {
		int halfCrossNum = (int) Math
				.floor((sizePopulation * crossOverRate / 2));
		int[] randomIndex = randomCommon(0, sizePopulation - 1,
				halfCrossNum * 2);
		List<MultiSourcePlumeChromosome> plumeChromosomeAfterCrossOver = new ArrayList<MultiSourcePlumeChromosome>();
		MultiSourcePlumeChromosome plumeChromosome1,plumeChromosome2;
		for (int i = 0; i < halfCrossNum; i++) {
			double c = Math.random();
			double d = 1 - c;
			plumeChromosome1 = new MultiSourcePlumeChromosome(
					multiSourcePlumeChromosome.get(randomIndex[i]).getQ01()
							* c
							+ multiSourcePlumeChromosome.get(
									randomIndex[i + halfCrossNum]).getQ01() * d,
					multiSourcePlumeChromosome.get(randomIndex[i]).getY01()
							* d
							+ multiSourcePlumeChromosome.get(
									randomIndex[i + halfCrossNum]).getY01() * c,
					multiSourcePlumeChromosome.get(randomIndex[i]).getZ01()
							* d
							+ multiSourcePlumeChromosome.get(
									randomIndex[i + halfCrossNum]).getZ01() * c,
					multiSourcePlumeChromosome.get(randomIndex[i]).getQ02()
							* c
							+ multiSourcePlumeChromosome.get(
									randomIndex[i + halfCrossNum]).getQ02() * d,
					multiSourcePlumeChromosome.get(randomIndex[i]).getY02()
							* d
							+ multiSourcePlumeChromosome.get(
									randomIndex[i + halfCrossNum]).getY02() * c,
					multiSourcePlumeChromosome.get(randomIndex[i]).getZ02()
							* d
							+ multiSourcePlumeChromosome.get(
									randomIndex[i + halfCrossNum]).getZ02() * c,
					sensors, stability, urCondition, u);
			plumeChromosome2 = new MultiSourcePlumeChromosome(
					multiSourcePlumeChromosome.get(randomIndex[i]).getQ01()
							* d
							+ multiSourcePlumeChromosome.get(
									randomIndex[i + halfCrossNum]).getQ01() * c,
					multiSourcePlumeChromosome.get(randomIndex[i]).getY01()
							* c
							+ multiSourcePlumeChromosome.get(
									randomIndex[i + halfCrossNum]).getY01() * d,
					multiSourcePlumeChromosome.get(randomIndex[i]).getZ01()
							* c
							+ multiSourcePlumeChromosome.get(
									randomIndex[i + halfCrossNum]).getZ01() * d,
					multiSourcePlumeChromosome.get(randomIndex[i]).getQ02()
							* d
							+ multiSourcePlumeChromosome.get(
									randomIndex[i + halfCrossNum]).getQ02() * c,
					multiSourcePlumeChromosome.get(randomIndex[i]).getY02()
							* c
							+ multiSourcePlumeChromosome.get(
									randomIndex[i + halfCrossNum]).getY02() * d,
					multiSourcePlumeChromosome.get(randomIndex[i]).getZ02()
							* c
							+ multiSourcePlumeChromosome.get(
									randomIndex[i + halfCrossNum]).getZ02() * d,
					sensors, stability, urCondition, u);
			
			if (plumeChromosome1.compareTo(plumeChromosome2) > 0) {
				plumeChromosomeAfterCrossOver.add(plumeChromosome2);
			} else {
				plumeChromosomeAfterCrossOver.add(plumeChromosome1);
			}
		}

		int len = multiSourcePlumeChromosome.size();
		multiSourcePlumeChromosome = multiSourcePlumeChromosome.subList(0, len
				- halfCrossNum);
		multiSourcePlumeChromosome.addAll(plumeChromosomeAfterCrossOver);
		Collections.sort(multiSourcePlumeChromosome);
		return multiSourcePlumeChromosome;
	}

	private MultiSourcePlumeChromosome GAProcess(
			List<MultiSourcePlumeChromosome> multiSourcePlumeChromosome) {
		int generationNumber = 0;
		while (!terminationTest(multiSourcePlumeChromosome.get(0).getFitness())
				& generationNumber < generationBound) {
			multiSourcePlumeChromosome = crossover(multiSourcePlumeChromosome);
			multiSourcePlumeChromosome = mutation(multiSourcePlumeChromosome);
			generationNumber++;
		}
		System.out.println("Number of generations : "
				+ Integer.toString(generationNumber));

		return multiSourcePlumeChromosome.get(0);
	}

	public MultiSourcePlumeChromosome GASolve() {
		List<MultiSourcePlumeChromosome> multiSourcePlumeChromosome = initialGA();
		MultiSourcePlumeChromosome finalResult = GAProcess(multiSourcePlumeChromosome);
		return finalResult;
	}

	@SuppressWarnings("unchecked")
	private List<MultiSourcePlumeChromosome> initialGA() {
		if (sizePopulation < 10)
			sizePopulation = 10;
		List<MultiSourcePlumeChromosome> multiSourcePlumeChromosome = new ArrayList<MultiSourcePlumeChromosome>();
		for (int i = 0; i < sizePopulation; i++) {
			multiSourcePlumeChromosome.add(new MultiSourcePlumeChromosome(Math
					.random() * (maxQ0 - minQ0) + minQ0, Math.random()
					* (maxY0 - minY0) + minY0, Math.random() * (maxZ0 - minZ0)
					+ minZ0, Math.random() * (maxQ0 - minQ0) + minQ0, Math
					.random() * (maxY0 - minY0) + minY0, Math.random()
					* (maxZ0 - minZ0) + minZ0, sensors, stability, urCondition,
					u));
		}
		Collections.sort(multiSourcePlumeChromosome);
		return multiSourcePlumeChromosome;
	}

	@SuppressWarnings("unchecked")
	private List<MultiSourcePlumeChromosome> mutation(
			List<MultiSourcePlumeChromosome> multiSourcePlumeChromosome) {
		int mutationNum = (int) Math.floor((sizePopulation * mutationRate));
		int[] randomIndex = randomCommon(0, sizePopulation - 1, mutationNum);
		for (int i = 0; i < mutationNum; i++) {
			int j = new Random().nextInt(6);
			switch (j) {
			case 0:
				multiSourcePlumeChromosome.get(randomIndex[i]).setQ01(
						Math.random() * (maxQ0 - minQ0) + minQ0);
				break;
			case 1:
				multiSourcePlumeChromosome.get(randomIndex[i]).setY01(
						Math.random() * (maxY0 - minY0) + minY0);
				break;
			case 2:
				multiSourcePlumeChromosome.get(randomIndex[i]).setZ01(
						Math.random() * (maxZ0 - minZ0) + minZ0);
				break;
			case 3:
				multiSourcePlumeChromosome.get(randomIndex[i]).setQ02(
						Math.random() * (maxQ0 - minQ0) + minQ0);
				break;
			case 4:
				multiSourcePlumeChromosome.get(randomIndex[i]).setY02(
						Math.random() * (maxY0 - minY0) + minY0);
				break;
			case 5:
				multiSourcePlumeChromosome.get(randomIndex[i]).setZ02(
						Math.random() * (maxZ0 - minZ0) + minZ0);
				break;
			}
			multiSourcePlumeChromosome.get(randomIndex[i]).setFitness(sensors,
					stability, urCondition, u);
		}
		Collections.sort(multiSourcePlumeChromosome);
		return multiSourcePlumeChromosome;
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

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

	public int getUrCondition() {
		return urCondition;
	}

	public void setUrCondition(int urCondition) {
		this.urCondition = urCondition;
	}

	private boolean terminationTest(double bestF) {
		if (bestF < stopE) {
			return true;
		} else {
			return false;
		}
	}

}
