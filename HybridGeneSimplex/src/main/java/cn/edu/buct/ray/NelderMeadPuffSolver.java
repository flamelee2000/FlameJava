package cn.edu.buct.ray;

public class NelderMeadPuffSolver {

	private double stopE;

	private double alfa;

	private double beta;

	private double delta;

	private double[][] densityMeasured;

	private double gama;

	private double[] h;

	private double Q0;
	
	private int stability;

	private double t;

	private double u;

	private double x0;

	private double y0;

	private double z0;

	private boolean flag;

	private int maxiIterationsNumber;

	public NelderMeadPuffSolver(double[][] densityMeasured, double Q0, double x0,
			double y0, double z0, int stability, double u, double t) {
		this.densityMeasured = densityMeasured;
		this.Q0 = Q0;
		this.x0 = x0;
		this.y0 = y0;
		this.z0 = z0;
		this.stability=stability;
		this.u = u;
		this.t = t;
	}

	private double[] calCentroid(double[][] vs1) {
		int len = vs1.length;
		double[] sum = { 0, 0, 0,0 };
		double[] avg = { 0, 0, 0,0 };
		for (int i = 0; i < len - 1; i++) {
			sum[0] += vs1[i][0];
			sum[1] += vs1[i][1];
			sum[2] += vs1[i][2];
			sum[3] += vs1[i][3];
		}
		avg[0] = sum[0] / (len - 1);
		avg[1] = sum[1] / (len - 1);
		avg[2] = sum[2] / (len - 1);
		avg[3] = sum[3] / (len - 1);
		return avg;
	}

	private double calculateDistance(PGPuffModel lm) {
		int sensorNum = densityMeasured.length;
		double[] densityCalculating = new double[sensorNum];
		double dist = 0;
		for (int i = 0; i < sensorNum; i++) {
			densityCalculating[i] = lm.getDensity(densityMeasured[i][0],
					densityMeasured[i][1], densityMeasured[i][2]);
			dist += Math.pow(densityCalculating[i] - densityMeasured[i][3], 2);
		}
		return dist;
	}

	private double[][] contract(double[][] vs, double[] centroid) {
		int len = vs.length;
		double[] xr = new double[4];
		double[] xc = new double[4];
		xr[0] = centroid[0] + alfa * (centroid[0] - vs[len - 1][0]);
		xr[1] = centroid[1] + alfa * (centroid[1] - vs[len - 1][1]);
		xr[2] = centroid[2] + alfa * (centroid[2] - vs[len - 1][2]);
		xr[3] = centroid[3] + alfa * (centroid[3] - vs[len - 1][3]);
		double fr = calculateDistance(new PGPuffModel(xr[0], xr[1], xr[2], xr[3], stability,
				u, t));
		double fs = calculateDistance(new PGPuffModel(vs[len - 2][0],
				vs[len - 2][1], vs[len - 2][2], vs[len - 2][3],stability, u, t));
		double fh = calculateDistance(new PGPuffModel(vs[len - 1][0],
				vs[len - 1][1], vs[len - 1][2], vs[len - 1][3],stability, u, t));
		double fc;
		if (fr >= fs) {
			if (fr < fh) {
				xc[0] = centroid[0] + beta * (xr[0] - centroid[0]);
				xc[1] = centroid[1] + beta * (xr[1] - centroid[1]);
				xc[2] = centroid[2] + beta * (xr[2] - centroid[2]);
				xc[3] = centroid[3] + beta * (xr[3] - centroid[3]);
				fc = calculateDistance(new PGPuffModel(xc[0], xc[1], xc[2],
						xc[3], stability, u, t));
				if (fc > fr)
					return vs;
			} else {
				xc[0] = centroid[0] + beta * (vs[len - 1][0] - centroid[0]);
				xc[1] = centroid[1] + beta * (vs[len - 1][1] - centroid[1]);
				xc[2] = centroid[2] + beta * (vs[len - 1][2] - centroid[2]);
				xc[3] = centroid[3] + beta * (vs[len - 1][3] - centroid[3]);
				fc = calculateDistance(new PGPuffModel(xc[0], xc[1], xc[2],
						xc[3],stability, u, t));
				if (fc >= fh)
					return vs;
			}
			flag = true;
			vs[len - 1][0] = xc[0];
			vs[len - 1][1] = xc[1];
			vs[len - 1][2] = xc[2];
			vs[len - 1][3] = xc[3];
		}
		return vs;
	}

	private double[][] expand(double[][] vs, double[] centroid) {
		int len = vs.length;
		double[] xr = new double[4];
		double[] xe = new double[4];
		xr[0] = centroid[0] + alfa * (centroid[0] - vs[len - 1][0]);
		xr[1] = centroid[1] + alfa * (centroid[1] - vs[len - 1][1]);
		xr[2] = centroid[2] + alfa * (centroid[2] - vs[len - 1][2]);
		xr[3] = centroid[3] + alfa * (centroid[3] - vs[len - 1][3]);
		double fl = calculateDistance(new PGPuffModel(vs[0][0], vs[0][1],
				vs[0][2], vs[0][3], stability, u, t));
		double fr = calculateDistance(new PGPuffModel(xr[0], xr[1], xr[2], xr[3], stability,
				u, t));

		if (fl > fr) {
			xe[0] = centroid[0] + gama * (xr[0] - centroid[0]);
			xe[1] = centroid[1] + gama * (xr[1] - centroid[1]);
			xe[2] = centroid[2] + gama * (xr[2] - centroid[2]);
			xe[3] = centroid[3] + gama * (xr[3] - centroid[3]);
			double fe = calculateDistance(new PGPuffModel(xe[0], xe[1], xe[2],
					xe[3],stability, u, t));
			if (fe < fr) {
				flag = true;
				vs[len - 1][0] = xe[0];
				vs[len - 1][1] = xe[1];
				vs[len - 1][2] = xe[2];
				vs[len - 1][3] = xe[3];
			}
		}

		return vs;
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


	public double getAlfa() {
		return alfa;
	}

	public double getBeta() {
		return beta;
	}

	public double getDelta() {
		return delta;
	}

	public double[][] getDensityMeasured() {
		return densityMeasured;
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

	private double[][] initialSimplex() {
		double[][] vertisSet = { { Q0, x0, y0, z0 }, { Q0, x0, y0, z0 },
				{ Q0, x0, y0, z0 }, { Q0, x0, y0, z0 } , { Q0, x0, y0, z0 }};
		vertisSet[1][0] = Q0 + h[0];
		vertisSet[2][1] = x0 + h[1];
		vertisSet[3][2] = y0 + h[2];
		vertisSet[4][3] = z0 + h[3];
		return vertisSet;
	}

	private double[][] ordering(double[][] vertisSet) {

		int len = vertisSet.length;
		double[] temp;
		double[] dist = new double[len];
		for (int i = 0; i < len; i++) {
			PGPuffModel lm = new PGPuffModel(vertisSet[i][0],
					vertisSet[i][1], vertisSet[i][2], vertisSet[i][3],stability, u, t);
			dist[i] = calculateDistance(lm);
		}
		// sorting
		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j <= len - 1; j++) {
				if (dist[i] > dist[j]) {
					temp = vertisSet[i];
					vertisSet[i] = vertisSet[j];
					vertisSet[j] = temp;
				}
			}
		}

		return vertisSet;
	}

	private double[][] reflect(double[][] vs, double[] centroid) {
		int len = vs.length;
		double[] xr = new double[4];
		xr[0] = centroid[0] + alfa * (centroid[0] - vs[len - 1][0]);
		xr[1] = centroid[1] + alfa * (centroid[1] - vs[len - 1][1]);
		xr[2] = centroid[2] + alfa * (centroid[2] - vs[len - 1][2]);
		xr[3] = centroid[3] + alfa * (centroid[3] - vs[len - 1][3]);
		double fl = calculateDistance(new PGPuffModel(vs[0][0], vs[0][1],
				vs[0][2], vs[0][3], stability,u, t));
		double fr = calculateDistance(new PGPuffModel(xr[0], xr[1], xr[2], xr[3],stability,
				u, t));
		double fs = calculateDistance(new PGPuffModel(vs[len - 2][0],
				vs[len - 2][1], vs[len - 2][2], vs[len - 2][3], stability,u, t));
		if ((fl <= fr) && (fr < fs)) {
			flag = true;
			vs[len - 1][0] = xr[0];
			vs[len - 1][1] = xr[1];
			vs[len - 1][2] = xr[2];
			vs[len - 1][3] = xr[3];
		}
		return vs;
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

	public void setDensityMeasured(double[][] densityMeasured) {
		this.densityMeasured = densityMeasured;
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

	private double[][] shrink(double[][] vs, double[] centroid) {
		int len = vs.length;
		for (int i = 1; i < len; i++) {
			vs[i][0] = vs[0][0] + delta * (vs[i][0] - vs[0][0]);
			vs[i][1] = vs[0][1] + delta * (vs[i][1] - vs[0][1]);
			vs[i][2] = vs[0][2] + delta * (vs[i][2] - vs[0][2]);
			vs[i][3] = vs[0][3] + delta * (vs[i][3] - vs[0][3]);
		}
		return vs;
	}

	private double[] simplexTransformation(double[][] vertisSet) {

		double[][] vs = vertisSet;
		double[] centroid;

		vs = ordering(vertisSet);
		centroid = calCentroid(vs);
		int num = 0;
		while (!terminationTest(centroid) & num< maxiIterationsNumber) {
			vs = transformation(vs, centroid);
			vs = ordering(vs);
			centroid = calCentroid(vs);
			num++;
		}
		System.out.println("Number of loops: "+Integer.toString(num));
		
		double[] result=new double[5];
		result[0]=centroid[0];
		result[1]=centroid[1];
		result[2]=centroid[2];
		result[3]=centroid[3];
		result[4]=calculateDistance(new PGPuffModel(result[0],
				result[1], result[2], result[3],stability, u, t));
		return result;
	}

	public double[] NelderMeadSolve() {

		double[][] vertisSet = initialSimplex();
		return simplexTransformation(vertisSet);
	}

	private boolean terminationTest(double[] centroid) {
		PGPuffModel lmc = new PGPuffModel(centroid[0], centroid[1],
				centroid[2], centroid[3],stability, u, t);
		double distc = calculateDistance(lmc);
		if (distc < stopE) {
			return true;
		} else {
			return false;
		}
	}
	
/*	private boolean terminationTest(double[][] vs, double[] centroid) {
		int len = vs.length;
		PGPuffModel lmc = new PGPuffModel(centroid[0], centroid[1],
				centroid[2], centroid[3], u, t);
		double distc = calculateDistance(lmc);
		double sum = 0;
		for (int i = 0; i < len; i++) {
			PGPuffModel lm = new PGPuffModel(vs[i][0], vs[i][1], vs[i][2],
					vs[i][3], stability, u, t);
			sum += Math.pow(calculateDistance(lm) - distc, 2);
		}
		sum /= len;
		sum = Math.sqrt(sum);
		if (sum < stopE) {
			return true;
		} else {
			return false;
		}
	}*/

	private double[][] transformation(double[][] vs, double[] centroid) {
		flag = false;
		vs = reflect(vs, centroid);
		if (flag) {
			return vs;
		}
		vs = expand(vs, centroid);
		if (flag) {
			return vs;
		}
		vs = contract(vs, centroid);
		if (flag) {
			return vs;
		}
		vs = shrink(vs, centroid);
		return vs;
	}

	
}
