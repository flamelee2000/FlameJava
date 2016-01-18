package cn.edu.buct.ray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NelderMeadPuffSolver {

	private double stopE;

	private double alfa;

	private double beta;

	private double delta;

	private List<Sensor> sensors;

	private double gama;

	private double[] h;

	private double Q0;

	private int stability;

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

	private double u;

	private double x0;

	private double y0;

	private double z0;

	private boolean flag;

	private int maxiIterationsNumber;

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

	public double getX0() {
		return x0;
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

	public NelderMeadPuffSolver(List<Sensor> sensors, double Q0, double x0,
			double y0, double z0, int stability, double u) {
		this.sensors = sensors;
		this.Q0 = Q0;
		this.x0 = x0;
		this.y0 = y0;
		this.z0 = z0;
		this.stability = stability;
		this.u = u;
	}

	private PuffChromosome calCentroid(List<PuffChromosome> puffChromosomes) {

		int len = puffChromosomes.size();
		double[] sum = { 0, 0, 0, 0 };
		for (int i = 0; i < len-1; i++) {
			sum[0] += puffChromosomes.get(i).getQ0();
			sum[1] += puffChromosomes.get(i).getX0();
			sum[2] += puffChromosomes.get(i).getY0();
			sum[3] += puffChromosomes.get(i).getZ0();
		}
		
		return new PuffChromosome(sum[0] / (len-1), sum[1] / (len-1), sum[2] / (len-1),
				sum[3] / (len-1), sensors, stability, u);
	}

	private List<PuffChromosome> contract(List<PuffChromosome> puffChromosomes,
			PuffChromosome centroid) {
		int len = puffChromosomes.size();

		double qr = centroid.getQ0() + alfa
				* (centroid.getQ0() - puffChromosomes.get(len - 1).getQ0());
		double xr = centroid.getX0() + alfa
				* (centroid.getX0() - puffChromosomes.get(len - 1).getX0());
		double yr = centroid.getY0() + alfa
				* (centroid.getY0() - puffChromosomes.get(len - 1).getY0());
		double zr = centroid.getZ0() + alfa
				* (centroid.getZ0() - puffChromosomes.get(len - 1).getZ0());

		PuffChromosome pr = new PuffChromosome(qr, xr, yr, zr, sensors,
				stability, u);
		double fr = pr.getFitness();
		double fs = puffChromosomes.get(len - 2).getFitness();
		double fh = puffChromosomes.get(len - 1).getFitness();
		double fc;
		double qc, xc, yc, zc;
		PuffChromosome pc;

		if (fr >= fs) {
			if (fr < fh) {
				qc = centroid.getQ0() + beta * (pr.getQ0() - centroid.getQ0());
				xc = centroid.getX0() + beta * (pr.getX0() - centroid.getX0());
				yc = centroid.getY0() + beta * (pr.getY0() - centroid.getY0());
				zc = centroid.getZ0() + beta * (pr.getZ0() - centroid.getZ0());
				pc = new PuffChromosome(qc, xc, yc, zc, sensors, stability, u);
				fc = pc.getFitness();
				if (fc > fr)
					return puffChromosomes;
			} else {
				qc = centroid.getQ0()
						+ beta
						* (puffChromosomes.get(len - 1).getQ0() - centroid
								.getQ0());
				xc = centroid.getX0()
						+ beta
						* (puffChromosomes.get(len - 1).getX0() - centroid
								.getX0());
				yc = centroid.getY0()
						+ beta
						* (puffChromosomes.get(len - 1).getY0() - centroid
								.getY0());
				zc = centroid.getZ0()
						+ beta
						* (puffChromosomes.get(len - 1).getZ0() - centroid
								.getZ0());
				pc = new PuffChromosome(qc, xc, yc, zc, sensors, stability, u);
				fc = pc.getFitness();
				if (fc >= fh)
					return puffChromosomes;
			}
			flag = true;
			puffChromosomes.set(len - 1, pc);
		}
		return puffChromosomes;
	}

	private List<PuffChromosome> expand(List<PuffChromosome> puffChromosomes,
			PuffChromosome centroid) {
		int len = puffChromosomes.size();

		double qr = centroid.getQ0() + alfa
				* (centroid.getQ0() - puffChromosomes.get(len - 1).getQ0());
		double xr = centroid.getX0() + alfa
				* (centroid.getX0() - puffChromosomes.get(len - 1).getX0());
		double yr = centroid.getY0() + alfa
				* (centroid.getY0() - puffChromosomes.get(len - 1).getY0());
		double zr = centroid.getZ0() + alfa
				* (centroid.getZ0() - puffChromosomes.get(len - 1).getZ0());

		double fl = puffChromosomes.get(0).getFitness();
		PuffChromosome pr = new PuffChromosome(qr, xr, yr, zr, sensors,
				stability, u);
		double fr = pr.getFitness();
		double qe, xe, ye, ze;
		PuffChromosome pe;

		if (fl > fr) {
			qe = centroid.getQ0() + gama * (qr - centroid.getQ0());
			xe = centroid.getX0() + gama * (xr - centroid.getX0());
			ye = centroid.getY0() + gama * (yr - centroid.getY0());
			ze = centroid.getZ0() + gama * (zr - centroid.getZ0());
			pe = new PuffChromosome(qe, xe, ye, ze, sensors, stability, u);
			double fe = pe.getFitness();

			if (fe < fr) {
				flag = true;
				puffChromosomes.set(len - 1, pe);
			}
		}

		return puffChromosomes;
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

	private List<PuffChromosome> initialSimplex() {

		List<PuffChromosome> puffChromosomes = new ArrayList<PuffChromosome>();
		puffChromosomes.add(new PuffChromosome(Q0, x0, y0, z0, sensors,
				stability, u));
		puffChromosomes.add(new PuffChromosome(Q0 + h[0], x0, y0, z0, sensors,
				stability, u));
		puffChromosomes.add(new PuffChromosome(Q0, x0 + h[1], y0, z0, sensors,
				stability, u));
		puffChromosomes.add(new PuffChromosome(Q0, x0, y0 + h[2], z0, sensors,
				stability, u));
		puffChromosomes.add(new PuffChromosome(Q0, x0, y0, z0 + h[3], sensors,
				stability, u));

		return puffChromosomes;
	}

	private List<PuffChromosome> reflect(List<PuffChromosome> puffChromosomes,
			PuffChromosome centroid) {
		int len = puffChromosomes.size();
		double q = centroid.getQ0() + alfa
				* (centroid.getQ0() - puffChromosomes.get(len - 1).getQ0());
		double x = centroid.getX0() + alfa
				* (centroid.getX0() - puffChromosomes.get(len - 1).getX0());
		double y = centroid.getY0() + alfa
				* (centroid.getY0() - puffChromosomes.get(len - 1).getY0());
		double z = centroid.getZ0() + alfa
				* (centroid.getZ0() - puffChromosomes.get(len - 1).getZ0());
		double fl = puffChromosomes.get(0).getFitness();
		PuffChromosome pr = new PuffChromosome(q, x, y, z, sensors, stability, u);
		double fr = pr.getFitness();
		double fs = puffChromosomes.get(len - 2).getFitness();
		if ((fl <= fr) && (fr < fs)) {
			flag = true;
			puffChromosomes.set(len - 1, pr);
		}
		return puffChromosomes;
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

	public void setX0(double x0) {
		this.x0 = x0;
	}

	public void setY0(double y0) {
		this.y0 = y0;
	}

	public void setZ0(double z0) {
		this.z0 = z0;
	}

	private List<PuffChromosome> shrink(List<PuffChromosome> puffChromosomes, PuffChromosome centroid) {
		int len = puffChromosomes.size();
		PuffChromosome puffChromosome;
		for (int i = 1; i < len; i++) {
			puffChromosome=new PuffChromosome(puffChromosomes.get(0).getQ0() + delta * (puffChromosomes.get(i).getQ0() - puffChromosomes.get(0).getQ0()),
					puffChromosomes.get(0).getX0() + delta * (puffChromosomes.get(i).getX0() - puffChromosomes.get(0).getX0()),
					puffChromosomes.get(0).getY0() + delta * (puffChromosomes.get(i).getY0() - puffChromosomes.get(0).getY0()),
					puffChromosomes.get(0).getZ0() + delta * (puffChromosomes.get(i).getZ0() - puffChromosomes.get(0).getZ0()),
					sensors, stability, u);
			puffChromosomes.set(i, puffChromosome);
		}
		return puffChromosomes;
	}

	@SuppressWarnings("unchecked")
	private PuffChromosome simplexTransformation(
			List<PuffChromosome> puffChromosomes) {

		PuffChromosome centroid;
		Collections.sort(puffChromosomes);
		centroid = calCentroid(puffChromosomes);

		int num = 0;
		while (!terminationTest(centroid) & num < maxiIterationsNumber) {
			puffChromosomes = transformation(puffChromosomes, centroid);
			Collections.sort(puffChromosomes);
			centroid = calCentroid(puffChromosomes);
			num++;
		}
		System.out.println("Number of loops: " + Integer.toString(num));

		return centroid;
	}

	public PuffChromosome NelderMeadSolve() {

		List<PuffChromosome> puffChromosomes = initialSimplex();
		return simplexTransformation(puffChromosomes);
	}

	private boolean terminationTest(PuffChromosome puffChromosome) {
		if (puffChromosome.getFitness() < stopE) {
			return true;
		} else {
			return false;
		}
	}

	private List<PuffChromosome> transformation(
			List<PuffChromosome> puffChromosomes, PuffChromosome centroid) {
		flag = false;
		puffChromosomes = reflect(puffChromosomes, centroid);
		if (flag) {
			return puffChromosomes;
		}
		puffChromosomes = expand(puffChromosomes, centroid);
		if (flag) {
			return puffChromosomes;
		}
		puffChromosomes = contract(puffChromosomes, centroid);
		if (flag) {
			return puffChromosomes;
		}
		puffChromosomes = shrink(puffChromosomes, centroid);
		return puffChromosomes;
	}

}
