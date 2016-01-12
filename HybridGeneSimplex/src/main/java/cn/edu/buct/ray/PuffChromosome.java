package cn.edu.buct.ray;

import java.util.List;

@SuppressWarnings("rawtypes")
public class PuffChromosome implements Comparable{
	
	private double Q0;
	private double x0;
	private double y0;
	private double z0;
	private double fitness;
	
	public String toString(){
		String showStr = "Q0 = " + Q0 + "\t";
		showStr += "x0 = " + x0 + "\t";
		showStr += "y0 = " + y0 + "\t";
		showStr += "z0 = " + z0 + "\t";
		showStr += "fitness = " + fitness + "\t";
		return showStr;
	}

	public PuffChromosome(double Q0,double x0, double y0,double z0, List<Sensor> sensors, int stability, double u){
		this.Q0=Q0;
		this.x0=x0;
		this.y0=y0;
		this.z0=z0;
		this.fitness=fitnessFunc(sensors,stability,u);
	}
	public double getX0() {
		return x0;
	}

	public void setX0(double x0) {
		this.x0 = x0;
	}
	
	public double getFitness() {
		return fitness;
	}

	public void setFitness(List<Sensor> sensors, int stability, double u) {
		this.fitness = fitnessFunc(sensors,stability,u);
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

	public double fitnessFunc(List<Sensor> sensors, int stability, double u){
		int sensorNum = sensors.size();
		double[] densities = new double[sensorNum];
		double dist = 0;
		for (int i = 0; i < sensorNum; i++) {
			PGPuffModel pgPuffModel = new PGPuffModel(Q0,x0,y0,z0,stability,u,sensors.get(i).getT());
			densities[i] = pgPuffModel.getDensity(sensors.get(i).getxPos(),
					sensors.get(i).getyPos(), sensors.get(i).getzPos());
			dist += Math.pow(densities[i] - sensors.get(i).getValueMeasured(), 2);
		}
		return dist;
	}
	
	@Override
	public int compareTo(Object obj) {
		PuffChromosome puffChromosome = (PuffChromosome) obj;
		int result;
		if(this.fitness > puffChromosome.fitness)
		    result = 1;
		 else if(this.fitness < puffChromosome.fitness)
		    result = -1;
		 else
		    result = 0; 
		return result;
	}
	
}
