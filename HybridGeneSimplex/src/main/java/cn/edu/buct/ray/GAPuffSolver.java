package cn.edu.buct.ray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class GAPuffSolver {

	private double crossOverRate;
	private List<Sensor> sensors;
	private int generationBound;
	private double maxQ0;
	private double maxX0;
	private double maxY0;
	private double maxZ0;
	private double minQ0;
	private double minX0;
	private double minY0;
	private double mutationRate;
	private int sizePopulation;
	private double stopE;
	private int stability;
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

	public double getMaxX0() {
		return maxX0;
	}

	public double getMaxY0() {
		return maxY0;
	}

	public double getMinQ0() {
		return minQ0;
	}

	public double getMinX0() {
		return minX0;
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

	public GAPuffSolver(List<Sensor> sensors, int stability, double u) {
		this.sensors = sensors;
		this.stability = stability;
		this.u = u;
	}

	@SuppressWarnings("unchecked")
	private List<PuffChromosome> crossover(List<PuffChromosome> puffChromosomes) {
		int halfCrossNum = (int) Math
				.floor((sizePopulation * crossOverRate / 2));
		int[] randomIndex = randomCommon(0, sizePopulation - 1,
				halfCrossNum * 2);
		List<PuffChromosome> puffChromosomeAfterCrossOver = new ArrayList<PuffChromosome>();
		PuffChromosome puffChromosome;
		for (int i = 0; i < halfCrossNum; i++) {
			double c=Math.random();
			double d=1-c;
			puffChromosome=new PuffChromosome(puffChromosomes.get(randomIndex[i]).getQ0()*c + puffChromosomes
							.get(randomIndex[i + halfCrossNum]).getQ0()*d, puffChromosomes.get(randomIndex[i]).getX0()*c + puffChromosomes
									.get(randomIndex[i + halfCrossNum]).getX0()*d,puffChromosomes.get(randomIndex[i]).getY0()*c+ puffChromosomes
											.get(randomIndex[i + halfCrossNum]).getY0()*d,puffChromosomes.get(randomIndex[i]).getZ0()*c + puffChromosomes
													.get(randomIndex[i + halfCrossNum]).getZ0()*d,sensors, stability, u);
			puffChromosomeAfterCrossOver.add(puffChromosome);
		}

		int len = puffChromosomes.size();
		puffChromosomes = puffChromosomes.subList(0, len - halfCrossNum);
		puffChromosomes.addAll(puffChromosomeAfterCrossOver);
		Collections.sort(puffChromosomes, new FitnessComparator());
		return puffChromosomes;
	}

	private PuffChromosome GAProcess(List<PuffChromosome> puffChromosomes) {
		int generationNumber = 0;
		while (!terminationTest(puffChromosomes.get(0).getFitness()) & generationNumber < generationBound) {
			puffChromosomes = crossover(puffChromosomes);
			puffChromosomes = mutation(puffChromosomes);
			generationNumber++;
		}
		System.out.println("Number of generations : "
				+ Integer.toString(generationNumber));

		return puffChromosomes.get(0);
	}

	public PuffChromosome GASolve() {
		List<PuffChromosome> puffChromosomes = initialGA();
		PuffChromosome finalResult = GAProcess(puffChromosomes);
		return finalResult;
	}

	@SuppressWarnings("unchecked")
	private List<PuffChromosome> initialGA() {
		if (sizePopulation < 10)
			sizePopulation = 10;
		List<PuffChromosome> puffChromosomes = new ArrayList<PuffChromosome>();
		for (int i = 0; i < sizePopulation; i++) {
			puffChromosomes
					.add(new PuffChromosome(Math.random() * (maxQ0 - minQ0)
							+ minQ0, Math.random() * (maxX0 - minX0) + minX0,
							Math.random() * (maxY0 - minY0) + minY0, Math
									.random() * (maxZ0 - minZ0) + minZ0,
							sensors, stability, u));
		}
		Collections.sort(puffChromosomes, new FitnessComparator());
		return puffChromosomes;
	}

	@SuppressWarnings("unchecked")
	private List<PuffChromosome> mutation(List<PuffChromosome> puffChromosomes) {
		int mutationNum = (int) Math.floor((sizePopulation * mutationRate));
		int[] randomIndex = randomCommon(0, sizePopulation - 1, mutationNum);
		for (int i = 0; i < mutationNum; i++) {
			int j = new Random().nextInt(4);
			switch (j) {
			case 0:
				puffChromosomes.get(randomIndex[i]).setQ0(
						Math.random() * (maxQ0 - minQ0) + minQ0);
				break;
			case 1:
				puffChromosomes.get(randomIndex[i]).setX0(
						Math.random() * (maxX0 - minX0) + minX0);
				break;
			case 2:
				puffChromosomes.get(randomIndex[i]).setY0(
						Math.random() * (maxY0 - minY0) + minY0);
				break;
			case 3:
				puffChromosomes.get(randomIndex[i]).setZ0(
						Math.random() * (maxZ0 - minZ0) + minZ0);
				break;
			}
			puffChromosomes.get(randomIndex[i]).setFitness(sensors, stability,
					u);
		}
		Collections.sort(puffChromosomes, new FitnessComparator());
		return puffChromosomes;
	}

	@SuppressWarnings("rawtypes")
	static class FitnessComparator implements Comparator {
		public int compare(Object object1, Object object2) {
			PuffChromosome p1 = (PuffChromosome) object1;
			PuffChromosome p2 = (PuffChromosome) object2;
			return p1.compareTo(p2);
		}
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

	public void setMaxX0(double maxX0) {
		this.maxX0 = maxX0;
	}

	public void setMaxY0(double maxY0) {
		this.maxY0 = maxY0;
	}

	public void setMinQ0(double minQ0) {
		this.minQ0 = minQ0;
	}

	public void setMinX0(double minX0) {
		this.minX0 = minX0;
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

	private boolean terminationTest(double bestF) {
		if (bestF < stopE) {
			return true;
		} else {
			return false;
		}
	}

}
