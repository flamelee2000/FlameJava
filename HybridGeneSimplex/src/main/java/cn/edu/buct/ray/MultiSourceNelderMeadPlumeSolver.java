package cn.edu.buct.ray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.edu.buct.ray.GAPlumeSolver.FitnessComparator;

public class MultiSourceNelderMeadPlumeSolver {

	private double stopE;

	private double alfa;

	private double beta;

	private double delta;

	private List<Sensor> sensors;

	private double gama;

	private double[] h;

	private double Q01;

	private double y01;

	private double z01;

	private double Q02;

	private double y02;

	private double z02;

	private int stability;

	private double u;

	private int urCondition;

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

	public double getQ01() {
		return Q01;
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

	public double getU() {
		return u;
	}

	public double getY01() {
		return y01;
	}

	public double getZ01() {
		return z01;
	}

	public double getAlfa() {
		return alfa;
	}

	public double getBeta() {
		return beta;
	}

	public MultiSourceNelderMeadPlumeSolver(List<Sensor> sensors, double Q01,
			double y01, double z01, double Q02, double y02, double z02,
			int stability, int urCondition, double u) {
		this.sensors = sensors;
		this.Q01 = Q01;
		this.y01 = y01;
		this.z01 = z01;
		this.Q02 = Q02;
		this.y02 = y02;
		this.z02 = z02;
		this.stability = stability;
		this.urCondition = urCondition;
		this.u = u;
	}

	private MultiSourcePlumeChromosome calCentroid(
			List<MultiSourcePlumeChromosome> MultiSourcePlumeChromosomes) {

		int len = MultiSourcePlumeChromosomes.size();
		double[] sum = { 0, 0, 0,0,0,0 };
		for (int i = 0; i < len - 1; i++) {
			sum[0] += MultiSourcePlumeChromosomes.get(i).getQ01();
			sum[1] += MultiSourcePlumeChromosomes.get(i).getY01();
			sum[2] += MultiSourcePlumeChromosomes.get(i).getZ01();
			sum[3] += MultiSourcePlumeChromosomes.get(i).getQ02();
			sum[4] += MultiSourcePlumeChromosomes.get(i).getY02();
			sum[5] += MultiSourcePlumeChromosomes.get(i).getZ02();
		}

		return new MultiSourcePlumeChromosome(sum[0] / (len - 1), sum[1]
				/ (len - 1), sum[2] / (len - 1), sum[3] / (len - 1), sum[4]
				/ (len - 1), sum[5] / (len - 1), sensors, stability,
				urCondition, u);
	}

	private List<MultiSourcePlumeChromosome> contract(
			List<MultiSourcePlumeChromosome> MultiSourcePlumeChromosomes,
			MultiSourcePlumeChromosome centroid) {
		int len = MultiSourcePlumeChromosomes.size();

		double qr1 = centroid.getQ01()
				+ alfa
				* (centroid.getQ01() - MultiSourcePlumeChromosomes.get(len - 1)
						.getQ01());
		double yr1 = centroid.getY01()
				+ alfa
				* (centroid.getY01() - MultiSourcePlumeChromosomes.get(len - 1)
						.getY01());
		double zr1 = centroid.getZ01()
				+ alfa
				* (centroid.getZ01() - MultiSourcePlumeChromosomes.get(len - 1)
						.getZ01());
		double qr2 = centroid.getQ02()
				+ alfa
				* (centroid.getQ02() - MultiSourcePlumeChromosomes.get(len - 1)
						.getQ02());
		double yr2 = centroid.getY02()
				+ alfa
				* (centroid.getY02() - MultiSourcePlumeChromosomes.get(len - 1)
						.getY02());
		double zr2 = centroid.getZ02()
				+ alfa
				* (centroid.getZ02() - MultiSourcePlumeChromosomes.get(len - 1)
						.getZ02());

		MultiSourcePlumeChromosome pr = new MultiSourcePlumeChromosome(qr1,
				yr1, zr1, qr2, yr2, zr2, sensors, stability, urCondition, u);
		double fr = pr.getFitness();
		double fs = MultiSourcePlumeChromosomes.get(len - 2).getFitness();
		double fh = MultiSourcePlumeChromosomes.get(len - 1).getFitness();
		double fc;
		double qc1, yc1, zc1, qc2, yc2, zc2;
		MultiSourcePlumeChromosome pc;

		if (fr >= fs) {
			if (fr < fh) {
				qc1 = centroid.getQ01() + beta
						* (pr.getQ01() - centroid.getQ01());
				yc1 = centroid.getY01() + beta
						* (pr.getY01() - centroid.getY01());
				zc1 = centroid.getZ01() + beta
						* (pr.getZ01() - centroid.getZ01());
				qc2 = centroid.getQ02() + beta
						* (pr.getQ02() - centroid.getQ02());
				yc2 = centroid.getY02() + beta
						* (pr.getY02() - centroid.getY02());
				zc2 = centroid.getZ02() + beta
						* (pr.getZ02() - centroid.getZ02());
				pc = new MultiSourcePlumeChromosome(qc1, yc1, zc1, qc2, yc2,
						zc2, sensors, stability, urCondition, u);
				fc = pc.getFitness();
				if (fc > fr)
					return MultiSourcePlumeChromosomes;
			} else {
				qc1 = centroid.getQ01()
						+ beta
						* (MultiSourcePlumeChromosomes.get(len - 1).getQ01() - centroid
								.getQ01());
				yc1 = centroid.getY01()
						+ beta
						* (MultiSourcePlumeChromosomes.get(len - 1).getY01() - centroid
								.getY01());
				zc1 = centroid.getZ01()
						+ beta
						* (MultiSourcePlumeChromosomes.get(len - 1).getZ01() - centroid
								.getZ01());
				qc2 = centroid.getQ02()
						+ beta
						* (MultiSourcePlumeChromosomes.get(len - 1).getQ02() - centroid
								.getQ02());
				yc2 = centroid.getY02()
						+ beta
						* (MultiSourcePlumeChromosomes.get(len - 1).getY02() - centroid
								.getY02());
				zc2 = centroid.getZ02()
						+ beta
						* (MultiSourcePlumeChromosomes.get(len - 1).getZ02() - centroid
								.getZ02());
				pc = new MultiSourcePlumeChromosome(qc1, yc1, zc1, qc2, yc2,
						zc2, sensors, stability, urCondition, u);
				fc = pc.getFitness();
				if (fc >= fh)
					return MultiSourcePlumeChromosomes;
			}
			flag = true;
			MultiSourcePlumeChromosomes.set(len - 1, pc);
		}
		return MultiSourcePlumeChromosomes;
	}

	private List<MultiSourcePlumeChromosome> expand(
			List<MultiSourcePlumeChromosome> MultiSourcePlumeChromosomes,
			MultiSourcePlumeChromosome centroid) {
		int len = MultiSourcePlumeChromosomes.size();

		double qr1 = centroid.getQ01()
				+ alfa
				* (centroid.getQ01() - MultiSourcePlumeChromosomes.get(len - 1)
						.getQ01());
		double yr1 = centroid.getY01()
				+ alfa
				* (centroid.getY01() - MultiSourcePlumeChromosomes.get(len - 1)
						.getY01());
		double zr1 = centroid.getZ01()
				+ alfa
				* (centroid.getZ01() - MultiSourcePlumeChromosomes.get(len - 1)
						.getZ01());
		double qr2 = centroid.getQ02()
				+ alfa
				* (centroid.getQ02() - MultiSourcePlumeChromosomes.get(len - 1)
						.getQ02());
		double yr2 = centroid.getY02()
				+ alfa
				* (centroid.getY02() - MultiSourcePlumeChromosomes.get(len - 1)
						.getY02());
		double zr2 = centroid.getZ02()
				+ alfa
				* (centroid.getZ02() - MultiSourcePlumeChromosomes.get(len - 1)
						.getZ02());
		double fl = MultiSourcePlumeChromosomes.get(0).getFitness();
		MultiSourcePlumeChromosome pr = new MultiSourcePlumeChromosome(qr1,
				yr1, zr1, qr2, yr2, zr2, sensors, stability, urCondition, u);
		double fr = pr.getFitness();
		double qe1, ye1, ze1, qe2, ye2, ze2;
		MultiSourcePlumeChromosome pe;

		if (fl > fr) {
			qe1 = centroid.getQ01() + gama * (qr1 - centroid.getQ01());
			ye1 = centroid.getY01() + gama * (yr1 - centroid.getY01());
			ze1 = centroid.getZ01() + gama * (zr1 - centroid.getZ01());
			qe2 = centroid.getQ02() + gama * (qr2 - centroid.getQ02());
			ye2 = centroid.getY02() + gama * (yr2 - centroid.getY02());
			ze2 = centroid.getZ02() + gama * (zr2 - centroid.getZ02());
			pe = new MultiSourcePlumeChromosome(qe1, ye1, ze1, qe2, ye2, ze2,
					sensors, stability, urCondition, u);
			double fe = pe.getFitness();

			if (fe < fr) {
				flag = true;
				MultiSourcePlumeChromosomes.set(len - 1, pe);
			}
		}

		return MultiSourcePlumeChromosomes;
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

	private List<MultiSourcePlumeChromosome> initialSimplex() {

		List<MultiSourcePlumeChromosome> MultiSourcePlumeChromosomes = new ArrayList<MultiSourcePlumeChromosome>();
		MultiSourcePlumeChromosomes.add(new MultiSourcePlumeChromosome(Q01,
				y01, z01, Q02, y02, z02, sensors, stability, urCondition, u));
		MultiSourcePlumeChromosomes.add(new MultiSourcePlumeChromosome(Q01
				+ h[0], y01, z01, Q02, y02, z02, sensors, stability,
				urCondition, u));
		MultiSourcePlumeChromosomes
				.add(new MultiSourcePlumeChromosome(Q01, y01 + h[1], z01, Q02,
						y02, z02, sensors, stability, urCondition, u));
		MultiSourcePlumeChromosomes.add(new MultiSourcePlumeChromosome(Q01,
				y01, z01 + h[2], Q02, y02, z02, sensors, stability,
				urCondition, u));
		MultiSourcePlumeChromosomes.add(new MultiSourcePlumeChromosome(Q01,
				y01, z01, Q02 + h[3], y02, z02, sensors, stability,
				urCondition, u));
		MultiSourcePlumeChromosomes.add(new MultiSourcePlumeChromosome(Q01,
				y01, z01, Q02, y02 + h[4], z02, sensors, stability,
				urCondition, u));
		MultiSourcePlumeChromosomes.add(new MultiSourcePlumeChromosome(Q01,
				y01, z01, Q02, y02, z02 + h[5], sensors, stability,
				urCondition, u));

		return MultiSourcePlumeChromosomes;
	}

	private List<MultiSourcePlumeChromosome> reflect(
			List<MultiSourcePlumeChromosome> MultiSourcePlumeChromosomes,
			MultiSourcePlumeChromosome centroid) {
		int len = MultiSourcePlumeChromosomes.size();
		double q1 = centroid.getQ01()
				+ alfa
				* (centroid.getQ01() - MultiSourcePlumeChromosomes.get(len - 1)
						.getQ01());
		double y1 = centroid.getY01()
				+ alfa
				* (centroid.getY01() - MultiSourcePlumeChromosomes.get(len - 1)
						.getY01());
		double z1 = centroid.getZ01()
				+ alfa
				* (centroid.getZ01() - MultiSourcePlumeChromosomes.get(len - 1)
						.getZ01());
		double q2 = centroid.getQ02()
				+ alfa
				* (centroid.getQ02() - MultiSourcePlumeChromosomes.get(len - 1)
						.getQ02());
		double y2 = centroid.getY02()
				+ alfa
				* (centroid.getY02() - MultiSourcePlumeChromosomes.get(len - 1)
						.getY02());
		double z2 = centroid.getZ02()
				+ alfa
				* (centroid.getZ02() - MultiSourcePlumeChromosomes.get(len - 1)
						.getZ02());
		double fl = MultiSourcePlumeChromosomes.get(0).getFitness();
		MultiSourcePlumeChromosome pr = new MultiSourcePlumeChromosome(q1, y1,
				z1, q2, y2, z2, sensors, stability, urCondition, u);
		double fr = pr.getFitness();
		double fs = MultiSourcePlumeChromosomes.get(len - 2).getFitness();
		if ((fl <= fr) && (fr < fs)) {
			flag = true;
			MultiSourcePlumeChromosomes.set(len - 1, pr);
		}
		return MultiSourcePlumeChromosomes;
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

	public void setQ01(double Q01) {
		this.Q01 = Q01;
	}

	public void setU(double u) {
		this.u = u;
	}

	public void setY01(double y01) {
		this.y01 = y01;
	}

	public void setZ01(double z01) {
		this.z01 = z01;
	}

	private List<MultiSourcePlumeChromosome> shrink(
			List<MultiSourcePlumeChromosome> MultiSourcePlumeChromosomes,
			MultiSourcePlumeChromosome centroid) {
		int len = MultiSourcePlumeChromosomes.size();
		MultiSourcePlumeChromosome MultiSourcePlumeChromosome;
		for (int i = 1; i < len; i++) {
			MultiSourcePlumeChromosome = new MultiSourcePlumeChromosome(
					MultiSourcePlumeChromosomes.get(0).getQ01()
							+ delta
							* (MultiSourcePlumeChromosomes.get(i).getQ01() - MultiSourcePlumeChromosomes
									.get(0).getQ01()),
					MultiSourcePlumeChromosomes.get(0).getY01()
							+ delta
							* (MultiSourcePlumeChromosomes.get(i).getY01() - MultiSourcePlumeChromosomes
									.get(0).getY01()),
					MultiSourcePlumeChromosomes.get(0).getZ01()
							+ delta
							* (MultiSourcePlumeChromosomes.get(i).getZ01() - MultiSourcePlumeChromosomes
									.get(0).getZ01()),
					MultiSourcePlumeChromosomes.get(0).getQ02()
							+ delta
							* (MultiSourcePlumeChromosomes.get(i).getQ02() - MultiSourcePlumeChromosomes
									.get(0).getQ02()),
					MultiSourcePlumeChromosomes.get(0).getY02()
							+ delta
							* (MultiSourcePlumeChromosomes.get(i).getY02() - MultiSourcePlumeChromosomes
									.get(0).getY02()),
					MultiSourcePlumeChromosomes.get(0).getZ02()
							+ delta
							* (MultiSourcePlumeChromosomes.get(i).getZ02() - MultiSourcePlumeChromosomes
									.get(0).getZ02()), sensors, stability,
					urCondition, u);
			MultiSourcePlumeChromosomes.set(i, MultiSourcePlumeChromosome);
		}
		return MultiSourcePlumeChromosomes;
	}

	@SuppressWarnings("unchecked")
	private MultiSourcePlumeChromosome simplexTransformation(
			List<MultiSourcePlumeChromosome> MultiSourcePlumeChromosomes) {

		MultiSourcePlumeChromosome centroid;
		Collections.sort(MultiSourcePlumeChromosomes, new FitnessComparator());
		centroid = calCentroid(MultiSourcePlumeChromosomes);

		int num = 0;
		while (!terminationTest(centroid) & num < maxiIterationsNumber) {
			MultiSourcePlumeChromosomes = transformation(
					MultiSourcePlumeChromosomes, centroid);
			Collections.sort(MultiSourcePlumeChromosomes,
					new FitnessComparator());
			centroid = calCentroid(MultiSourcePlumeChromosomes);
			num++;
		}
		System.out.println("Number of loops: " + Integer.toString(num));

		return centroid;
	}

	public MultiSourcePlumeChromosome NelderMeadSolve() {

		List<MultiSourcePlumeChromosome> MultiSourcePlumeChromosomes = initialSimplex();
		return simplexTransformation(MultiSourcePlumeChromosomes);
	}

	private boolean terminationTest(
			MultiSourcePlumeChromosome MultiSourcePlumeChromosome) {
		if (MultiSourcePlumeChromosome.getFitness() < stopE) {
			return true;
		} else {
			return false;
		}
	}

	private List<MultiSourcePlumeChromosome> transformation(
			List<MultiSourcePlumeChromosome> MultiSourcePlumeChromosomes,
			MultiSourcePlumeChromosome centroid) {
		flag = false;
		MultiSourcePlumeChromosomes = reflect(MultiSourcePlumeChromosomes,
				centroid);
		if (flag) {
			return MultiSourcePlumeChromosomes;
		}
		MultiSourcePlumeChromosomes = expand(MultiSourcePlumeChromosomes,
				centroid);
		if (flag) {
			return MultiSourcePlumeChromosomes;
		}
		MultiSourcePlumeChromosomes = contract(MultiSourcePlumeChromosomes,
				centroid);
		if (flag) {
			return MultiSourcePlumeChromosomes;
		}
		MultiSourcePlumeChromosomes = shrink(MultiSourcePlumeChromosomes,
				centroid);
		return MultiSourcePlumeChromosomes;
	}
	
	@SuppressWarnings("rawtypes")
	static class FitnessComparator implements Comparator {
		public int compare(Object object1, Object object2) {
			MultiSourcePlumeChromosome p1 = (MultiSourcePlumeChromosome) object1;
			MultiSourcePlumeChromosome p2 = (MultiSourcePlumeChromosome) object2;
			return p1.compareTo(p2);
		}
	}

}
