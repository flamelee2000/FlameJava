package cn.edu.buct.ray;

public class PGPuffModel {

	public PGPuffModel(double Q0, double x0, double y0, double z0,
			int stability, double u, double t) {
		this.Q0 = Q0;
		this.x0 = x0;
		this.y0 = y0;
		this.z0 = z0;
		this.stability = stability;
		this.u = u;
		this.t = t;
	}

	private int stability;
	private double Q0;
	private double t;
	private double u;
	private double x0;
	private double y0;
	private double z0;

	public int getStability() {
		return stability;
	}

	public void setStability(int stability) {
		this.stability = stability;
	}

	public double getQ0() {
		return Q0;
	}

	public double getT() {
		return t;
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

	public void setQ0(double q0) {
		Q0 = q0;
	}

	public void setT(double t) {
		this.t = t;
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

	public double getDensity(double x, double y, double z) {
		double r = Q0
				/ (Math.sqrt(2) * Math.pow(Math.PI, 1.5)
						* getDiffCoefficientX(x) * getDiffCoefficientY(x) * getDiffCoefficientZ(x));
		r = r
				* Math.exp(-0.5
						* ((Math.pow((x - x0 - u * t) / getDiffCoefficientX(x),
								2)
								+ Math.pow((y - y0) / getDiffCoefficientY(x), 2) + Math
									.pow((z - z0) / getDiffCoefficientZ(x), 2))));
		return r;
	}

	private double getDiffCoefficientX(double x) {
		double sigma = 0;
		switch (stability) {
		case 0:
			sigma = 0.18 * Math.pow(x, 0.92);
			break;
		case 1:
			sigma = 0.14 * Math.pow(x, 0.92);
			break;
		case 2:
			sigma = 0.1 * Math.pow(x, 0.92);
			break;
		case 3:
			sigma = 0.06 * Math.pow(x, 0.92);
			break;
		case 4:
			sigma = 0.04 * Math.pow(x, 0.92);
			break;
		case 5:
			sigma = 0.02 * Math.pow(x, 0.89);
			break;
		}
		return sigma;
	}

	private double getDiffCoefficientY(double y) {
		double sigma = 0;
		switch (stability) {
		case 0:
			sigma = 0.18 * Math.pow(y, 0.92);
			break;
		case 1:
			sigma = 0.14 * Math.pow(y, 0.92);
			break;
		case 2:
			sigma = 0.1 * Math.pow(y, 0.92);
			break;
		case 3:
			sigma = 0.06 * Math.pow(y, 0.92);
			break;
		case 4:
			sigma = 0.04 * Math.pow(y, 0.92);
			break;
		case 5:
			sigma = 0.02 * Math.pow(y, 0.89);
			break;
		}
		return sigma;
	}

	private double getDiffCoefficientZ(double z) {
		double sigma = 0;
		switch (stability) {
		case 0:
			sigma = 0.6 * Math.pow(z, 0.75);
			break;
		case 1:
			sigma = 0.53 * Math.pow(z, 0.73);
			break;
		case 2:
			sigma = 0.34 * Math.pow(z, 0.71);
			break;
		case 3:
			sigma = 0.15 * Math.pow(z, 0.7);
			break;
		case 4:
			sigma = 0.1 * Math.pow(z, 0.65);
			break;
		case 5:
			sigma = 0.05 * Math.pow(z, 0.61);
			break;
		}
		return sigma;
	}

}
