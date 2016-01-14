package cn.edu.buct.ray;

import java.util.List;

@SuppressWarnings("rawtypes")
public class MultiSourcePlumeChromosome implements Comparable {

	private double Q01;
	private double y01;
	private double z01;
	private double Q02;
	private double y02;
	private double z02;
	private double fitness;

	public String toStringWithName() {
		String showStr = "\rQ01 = " + Q01 + "\t";
		showStr += "y01 = " + y01 + "\t";
		showStr += "z01 = " + z01 + "\t";
		showStr += "\rQ02 = " + Q02 + "\t";
		showStr += "y02 = " + y02 + "\t";
		showStr += "z02 = " + z02 + "\t";
		showStr += "\rfitness = " + fitness + "\t";
		return showStr;
	}

	public MultiSourcePlumeChromosome(double Q01, double y01, double z01,
			double Q02, double y02, double z02, List<Sensor> sensors,
			int stability, int urCondition, double u) {
		this.Q01 = Q01;
		this.y01 = y01;
		this.z01 = z01;
		this.Q02 = Q02;
		this.y02 = y02;
		this.z02 = z02;
		this.fitness = fitnessFunc(sensors, stability, urCondition, u);
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(List<Sensor> sensors, int stability,
			int urCondition, double u) {
		this.fitness = fitnessFunc(sensors, stability, urCondition, u);
	}

	public double getQ02() {
		return Q02;
	}

	public void setQ02(double q02) {
		Q02 = q02;
	}

	public double getY02() {
		return y02;
	}

	public void setY02(double y02) {
		this.y02 = y02;
	}

	public double getZ02() {
		return z02;
	}

	public void setZ02(double z02) {
		this.z02 = z02;
	}

	public double getQ01() {
		return Q01;
	}

	public void setQ01(double q0) {
		Q01 = q0;
	}

	public double getY01() {
		return y01;
	}

	public void setY01(double y01) {
		this.y01 = y01;
	}

	public double getZ01() {
		return z01;
	}

	public void setZ01(double z01) {
		this.z01 = z01;
	}

	public double fitnessFunc(List<Sensor> sensors, int stability,
			int urCondition, double u) {
		int sensorNum = sensors.size();
		PGPlumeModel pgPlumeModel1 = new PGPlumeModel(Q01, y01, z01, stability,
				urCondition, u);
		PGPlumeModel pgPlumeModel2 = new PGPlumeModel(Q02, y02, z02, stability,
				urCondition, u);
		double[] densities = new double[sensorNum];
		double dist = 0;
		for (int i = 0; i < sensorNum; i++) {
			densities[i] = pgPlumeModel1.getDensity(sensors.get(i).getxPos(),
					sensors.get(i).getyPos(), sensors.get(i).getzPos())
					+ pgPlumeModel2.getDensity(sensors.get(i).getxPos(),
							sensors.get(i).getyPos(), sensors.get(i).getzPos());
			dist += Math.pow(densities[i] - sensors.get(i).getValueMeasured(),
					2);
		}
		return dist;
	}

	@Override
	public int compareTo(Object obj) {
		MultiSourcePlumeChromosome multiSourcePlumeChromosome = (MultiSourcePlumeChromosome) obj;
		int result;
		if (this.fitness > multiSourcePlumeChromosome.fitness)
			result = 1;
		else if (this.fitness < multiSourcePlumeChromosome.fitness)
			result = -1;
		else
			result = 0;
		return result;
	}
	
	
}
