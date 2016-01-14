package cn.edu.buct.ray;

import java.util.List;

@SuppressWarnings("rawtypes")
public class PlumeChromosome  implements Comparable{

	private double Q0;
	private double y0;
	private double z0;
	private double fitness;

	public String toStringWithName(){
		String showStr = "Q0 = " + Q0 + "\t";
		showStr += "y0 = " + y0 + "\t";
		showStr += "z0 = " + z0 + "\t";
		showStr += "fitness = " + fitness + "\t";
		return showStr;
	}
	
	public PlumeChromosome(double Q0,double y0,double z0, List<Sensor> sensors, int stability, int urCondition, double u){
		this.Q0=Q0;
		this.y0=y0;
		this.z0=z0;
		this.fitness=fitnessFunc(sensors,stability,urCondition,u);
	}
	
	public double getFitness() {
		return fitness;
	}

	public void setFitness(List<Sensor> sensors, int stability, int urCondition, double u) {
		this.fitness = fitnessFunc(sensors,stability,urCondition,u);
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

	@Override
	public int compareTo(Object obj) {
		PlumeChromosome plumeChromosome = (PlumeChromosome) obj;
		int result;
		if(this.fitness > plumeChromosome.fitness)
		    result = 1;
		 else if(this.fitness < plumeChromosome.fitness)
		    result = -1;
		 else
		    result = 0; 
		return result;
	}
}
