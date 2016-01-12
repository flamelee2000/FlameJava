package cn.edu.buct.ray;

import java.util.List;

public class PlumeChromosome {

	private double Q0;
	private double y0;
	private double z0;
	private double fitness;

	public PlumeChromosome(double Q0,double y0,double z0, List<Sensor> sensors, int stability, int urCondition, double u){
		this.Q0=Q0;
		this.y0=y0;
		this.z0=z0;
		this.fitness=fitnessFunc(sensors,stability,urCondition,u);
	}
	
	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public double getQ0() {
		return Q0;
	}

	public void setQ0(double q0) {
		Q0 = q0;
	}

	public double getY0() {
		return y0;
	}

	public void setY0(double y0) {
		this.y0 = y0;
	}

	public double getZ0() {
		return z0;
	}

	public void setZ0(double z0) {
		this.z0 = z0;
	}

	public double fitnessFunc(List<Sensor> sensors, int stability, int urCondition, double u){
		int sensorNum = sensors.size();
		PGPlumeModel pgPlumeModel = new PGPlumeModel(Q0,y0,z0,stability,urCondition,u);
		double[] densities = new double[sensorNum];
		double dist = 0;
		for (int i = 0; i < sensorNum; i++) {
			densities[i] = pgPlumeModel.getDensity(sensors.get(i).getxPos(),
					sensors.get(i).getyPos(), sensors.get(i).getzPos());
			dist += Math.pow(densities[i] - sensors.get(i).getValueMeasured(), 2);
		}
		return dist;
	}
}
