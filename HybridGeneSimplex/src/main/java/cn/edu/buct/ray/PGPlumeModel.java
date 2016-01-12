package cn.edu.buct.ray;

public class PGPlumeModel {
	
	private int stability;
	private int uiCondition;
	private double Q0;
	private double y0;
	private double z0;
	private double u;

	public PGPlumeModel(double Q0, double y0, double z0, int stability, int urCondition, double u) {
		this.Q0 = Q0;
		this.y0 = y0;
		this.z0 = z0;
		this.u = u;
		this.stability=stability;
	}

	public double getU() {
		return u;
	}

	public void setU(double u) {
		this.u = u;
	}

	public int getStability() {
		return stability;
	}

	public void setStability(int stability) {
		this.stability = stability;
	}


	public int getUiCondition() {
		return uiCondition;
	}

	public void setUiCondition(int uiCondition) {
		this.uiCondition = uiCondition;
	}
	
	public double getQ0() {
		return Q0;
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

	public void setY0(double y0) {
		this.y0 = y0;
	}

	public void setZ0(double z0) {
		this.z0 = z0;
	}

	public double getDensity(double x, double y, double z) {
		double r = Q0
				/ (u * Math.PI
						* getDiffCoefficientY(x) * getDiffCoefficientZ(x));
		r = r
				* Math.exp(-0.5
						* (Math.pow((y - y0) / getDiffCoefficientY(x), 2) + Math
									.pow((z - z0) / getDiffCoefficientZ(x), 2)));
		return r;
	}


	private double getDiffCoefficientY(double y) {
		double sigma = 0;
		if (uiCondition == 0) {
			switch (stability) {
			case 0:
				sigma = 0.22 * y * Math.pow(1 + 0.0001 * y, -0.5);
				break;
			case 1:
				sigma = 0.16 * y * Math.pow(1 + 0.0001 * y, -0.5);
				break;
			case 2:
				sigma = 0.11 * y * Math.pow(1 + 0.0001 * y, -0.5);
				break;
			case 3:
				sigma = 0.08 * y * Math.pow(1 + 0.0001 * y, -0.5);
				break;
			case 4:
				sigma = 0.06 * y * Math.pow(1 + 0.0001 * y, -0.5);
				break;
			case 5:
				sigma = 0.04 * y * Math.pow(1 + 0.0001 * y, -0.5);
				break;
			}
		} else {
			switch (stability) {
			case 0:
				sigma = 0.32 * y * Math.pow(1 + 0.0004 * y, -0.5);
				break;
			case 1:
				sigma = 0.32 * y * Math.pow(1 + 0.0004 * y, -0.5);
				break;
			case 2:
				sigma = 0.22 * y * Math.pow(1 + 0.0004 * y, -0.5);
				break;
			case 3:
				sigma = 0.16 * y * Math.pow(1 + 0.0004 * y, -0.5);
				break;
			case 4:
				sigma = 0.11 * y * Math.pow(1 + 0.0004 * y, -0.5);
				break;
			case 5:
				sigma = 0.11 * y * Math.pow(1 + 0.0004 * y, -0.5);
				break;
			}
		}
		return sigma;
	}

	private double getDiffCoefficientZ(double z) {
		double sigma = 0;
		if (uiCondition == 0) {
			switch (stability) {
			case 0:
				sigma = 0.2 * z;
				break;
			case 1:
				sigma = 0.12 * z;
				break;
			case 2:
				sigma = 0.08 * z * Math.pow(1 + 0.0002 * z, -0.5);
				break;
			case 3:
				sigma = 0.06 * z * Math.pow(1 + 0.0015 * z, -0.5);
				break;
			case 4:
				sigma = 0.03 * z * Math.pow(1 + 0.0003* z, -1);
				break;
			case 5:
				sigma = 0.016 * z * Math.pow(1 + 0.0003 * z, -1);
				break;
			}
		} else {
			switch (stability) {
			case 0:
				sigma = 0.24 * z * Math.pow(1 + 0.0001 * z, 0.5);
				break;
			case 1:
				sigma = 0.24 * z * Math.pow(1 + 0.0004 * z, 0.5);
				break;
			case 2:
				sigma = 0.2 * z;
				break;
			case 3:
				sigma = 0.14 * z * Math.pow(1 + 0.0003 * z, -0.5);
				break;
			case 4:
				sigma = 0.08 * z * Math.pow(1 + 0.0015 * z, -0.5);
				break;
			case 5:
				sigma = 0.08 * z * Math.pow(1 + 0.0015 * z, -0.5);
				break;
			}
		}
		return sigma;
	}

}
