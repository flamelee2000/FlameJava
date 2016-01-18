package cn.edu.buct.ray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NelderMeadPlumeSolver {

	private double stopE;

	private double alfa;

	private double beta;

	private double delta;

	private List<Sensor> sensors;

	private double gama;

	private double[] h;

	private double Q0;

	private int stability;

	private double u;

	private int urCondition;

	private double y0;

	private double z0;

	private boolean flag;

	private int maxiIterationsNumber;
	
	public int getUrCondition() {
		return urCondition;
	}

	public void setUrCondition(int urCondition) {
		this.urCondition = urCondition;
	}
	
	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

	public double getDelta() {
		return delta;
	}

	public double getGama() {
		return gama;
	}

	public double[] getH() {
		return h;
	}

	public double getQ0() {
		return Q0;
	}

	public double getU() {
		return u;
	}

	public double getY0() {
		return y0;
	}

	public double getZ0() {
		return z0;
	}

	public double getAlfa() {
		return alfa;
	}

	public double getBeta() {
		return beta;
	}

	public NelderMeadPlumeSolver(List<Sensor> sensors, double Q0, 
			double y0, double z0, int stability, int urCondition, double u) {
		this.sensors = sensors;
		this.Q0 = Q0;
		this.y0 = y0;
		this.z0 = z0;
		this.stability = stability;
		this.urCondition=urCondition;
		this.u = u;
	}

	private PlumeChromosome calCentroid(List<PlumeChromosome> PlumeChromosomes) {

		int len = PlumeChromosomes.size();
		double[] sum = { 0, 0, 0 };
		for (int i = 0; i < len-1; i++) {
			sum[0] += PlumeChromosomes.get(i).getQ0();
			sum[1] += PlumeChromosomes.get(i).getY0();
			sum[2] += PlumeChromosomes.get(i).getZ0();
		}
		
		return new PlumeChromosome(sum[0] / (len-1), sum[1] / (len-1), sum[2] / (len-1),
				sensors, stability, urCondition, u);
	}

	private List<PlumeChromosome> contract(List<PlumeChromosome> PlumeChromosomes,
			PlumeChromosome centroid) {
		int len = PlumeChromosomes.size();

		double qr = centroid.getQ0() + alfa
				* (centroid.getQ0() - PlumeChromosomes.get(len - 1).getQ0());
		double yr = centroid.getY0() + alfa
				* (centroid.getY0() - PlumeChromosomes.get(len - 1).getY0());
		double zr = centroid.getZ0() + alfa
				* (centroid.getZ0() - PlumeChromosomes.get(len - 1).getZ0());

		PlumeChromosome pr = new PlumeChromosome(qr, yr, zr, sensors,
				stability, urCondition, u);
		double fr = pr.getFitness();
		double fs = PlumeChromosomes.get(len - 2).getFitness();
		double fh = PlumeChromosomes.get(len - 1).getFitness();
		double fc;
		double qc, yc, zc;
		PlumeChromosome pc;

		if (fr >= fs) {
			if (fr < fh) {
				qc = centroid.getQ0() + beta * (pr.getQ0() - centroid.getQ0());
				yc = centroid.getY0() + beta * (pr.getY0() - centroid.getY0());
				zc = centroid.getZ0() + beta * (pr.getZ0() - centroid.getZ0());
				pc = new PlumeChromosome(qc, yc, zc, sensors, stability, urCondition, u);
				fc = pc.getFitness();
				if (fc > fr)
					return PlumeChromosomes;
			} else {
				qc = centroid.getQ0()
						+ beta
						* (PlumeChromosomes.get(len - 1).getQ0() - centroid
								.getQ0());
				yc = centroid.getY0()
						+ beta
						* (PlumeChromosomes.get(len - 1).getY0() - centroid
								.getY0());
				zc = centroid.getZ0()
						+ beta
						* (PlumeChromosomes.get(len - 1).getZ0() - centroid
								.getZ0());
				pc = new PlumeChromosome(qc, yc, zc, sensors, stability, urCondition, u);
				fc = pc.getFitness();
				if (fc >= fh)
					return PlumeChromosomes;
			}
			flag = true;
			PlumeChromosomes.set(len - 1, pc);
		}
		return PlumeChromosomes;
	}

	private List<PlumeChromosome> expand(List<PlumeChromosome> PlumeChromosomes,
			PlumeChromosome centroid) {
		int len = PlumeChromosomes.size();

		double qr = centroid.getQ0() + alfa
				* (centroid.getQ0() - PlumeChromosomes.get(len - 1).getQ0());
		double yr = centroid.getY0() + alfa
				* (centroid.getY0() - PlumeChromosomes.get(len - 1).getY0());
		double zr = centroid.getZ0() + alfa
				* (centroid.getZ0() - PlumeChromosomes.get(len - 1).getZ0());
		double fl = PlumeChromosomes.get(0).getFitness();
		PlumeChromosome pr = new PlumeChromosome(qr, yr, zr, sensors,
				stability, urCondition, u);
		double fr = pr.getFitness();
		double qe, ye, ze;
		PlumeChromosome pe;

		if (fl > fr) {
			qe = centroid.getQ0() + gama * (qr - centroid.getQ0());
			ye = centroid.getY0() + gama * (yr - centroid.getY0());
			ze = centroid.getZ0() + gama * (zr - centroid.getZ0());
			pe = new PlumeChromosome(qe, ye, ze, sensors, stability, urCondition, u);
			double fe = pe.getFitness();

			if (fe < fr) {
				flag = true;
				PlumeChromosomes.set(len - 1, pe);
			}
		}

		return PlumeChromosomes;
	}

	public double getStopE() {
		return stopE;
	}

	public void setStopE(double stopE) {
		this.stopE = stopE;
	}

	public int getMaxiIterationsNumber() {
		return maxiIterationsNumber;
	}

	public void setMaxiIterationsNumber(int maxiIterationsNumber) {
		this.maxiIterationsNumber = maxiIterationsNumber;
	}

	public int getStability() {
		return stability;
	}

	public void setStability(int stability) {
		this.stability = stability;
	}

	private List<PlumeChromosome> initialSimplex() {

		List<PlumeChromosome> PlumeChromosomes = new ArrayList<PlumeChromosome>();
		PlumeChromosomes.add(new PlumeChromosome(Q0, y0, z0, sensors,
				stability, urCondition, u));
		PlumeChromosomes.add(new PlumeChromosome(Q0 + h[0], y0, z0, sensors,
				stability, urCondition, u));
		PlumeChromosomes.add(new PlumeChromosome(Q0, y0 + h[1], z0, sensors,
				stability, urCondition, u));
		PlumeChromosomes.add(new PlumeChromosome(Q0, y0, z0 + h[2], sensors,
				stability, urCondition, u));

		return PlumeChromosomes;
	}

	private List<PlumeChromosome> reflect(List<PlumeChromosome> PlumeChromosomes,
			PlumeChromosome centroid) {
		int len = PlumeChromosomes.size();
		double q = centroid.getQ0() + alfa
				* (centroid.getQ0() - PlumeChromosomes.get(len - 1).getQ0());
		double y = centroid.getY0() + alfa
				* (centroid.getY0() - PlumeChromosomes.get(len - 1).getY0());
		double z = centroid.getZ0() + alfa
				* (centroid.getZ0() - PlumeChromosomes.get(len - 1).getZ0());
		double fl = PlumeChromosomes.get(0).getFitness();
		PlumeChromosome pr = new PlumeChromosome(q, y, z, sensors, stability, urCondition, u);
		double fr = pr.getFitness();
		double fs = PlumeChromosomes.get(len - 2).getFitness();
		if ((fl <= fr) && (fr < fs)) {
			flag = true;
			PlumeChromosomes.set(len - 1, pr);
		}
		return PlumeChromosomes;
	}

	public void setAlfa(double alfa) {
		this.alfa = alfa;
	}

	public void setBeta(double beta) {
		this.beta = beta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	public void setGama(double gama) {
		this.gama = gama;
	}

	public void setH(double[] h) {
		this.h = h;
	}

	public void setQ0(double q0) {
		Q0 = q0;
	}

	public void setU(double u) {
		this.u = u;
	}

	public void setY0(double y0) {
		this.y0 = y0;
	}

	public void setZ0(double z0) {
		this.z0 = z0;
	}

	private List<PlumeChromosome> shrink(List<PlumeChromosome> PlumeChromosomes, PlumeChromosome centroid) {
		int len = PlumeChromosomes.size();
		PlumeChromosome PlumeChromosome;
		for (int i = 1; i < len; i++) {
			PlumeChromosome=new PlumeChromosome(PlumeChromosomes.get(0).getQ0() + delta * (PlumeChromosomes.get(i).getQ0() - PlumeChromosomes.get(0).getQ0()),
					PlumeChromosomes.get(0).getY0() + delta * (PlumeChromosomes.get(i).getY0() - PlumeChromosomes.get(0).getY0()),
					PlumeChromosomes.get(0).getZ0() + delta * (PlumeChromosomes.get(i).getZ0() - PlumeChromosomes.get(0).getZ0()),
					sensors, stability, urCondition, u);
			PlumeChromosomes.set(i, PlumeChromosome);
		}
		return PlumeChromosomes;
	}

	@SuppressWarnings("unchecked")
	private PlumeChromosome simplexTransformation(
			List<PlumeChromosome> PlumeChromosomes) {

		PlumeChromosome centroid;
		Collections.sort(PlumeChromosomes);
		centroid = calCentroid(PlumeChromosomes);

		int num = 0;
		while (!terminationTest(centroid) & num < maxiIterationsNumber) {
			PlumeChromosomes = transformation(PlumeChromosomes, centroid);
			Collections.sort(PlumeChromosomes);
			centroid = calCentroid(PlumeChromosomes);
			num++;
		}
		System.out.println("Number of loops: " + Integer.toString(num));

		return centroid;
	}

	public PlumeChromosome NelderMeadSolve() {

		List<PlumeChromosome> PlumeChromosomes = initialSimplex();
		return simplexTransformation(PlumeChromosomes);
	}

	private boolean terminationTest(PlumeChromosome PlumeChromosome) {
		if (PlumeChromosome.getFitness() < stopE) {
			return true;
		} else {
			return false;
		}
	}

	private List<PlumeChromosome> transformation(
			List<PlumeChromosome> PlumeChromosomes, PlumeChromosome centroid) {
		flag = false;
		PlumeChromosomes = reflect(PlumeChromosomes, centroid);
		if (flag) {
			return PlumeChromosomes;
		}
		PlumeChromosomes = expand(PlumeChromosomes, centroid);
		if (flag) {
			return PlumeChromosomes;
		}
		PlumeChromosomes = contract(PlumeChromosomes, centroid);
		if (flag) {
			return PlumeChromosomes;
		}
		PlumeChromosomes = shrink(PlumeChromosomes, centroid);
		return PlumeChromosomes;
	}
	
}
