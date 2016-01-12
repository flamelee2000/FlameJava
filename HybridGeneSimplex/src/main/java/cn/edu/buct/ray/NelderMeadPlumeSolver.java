package cn.edu.buct.ray;

public class NelderMeadPlumeSolver {

	private double stopE;

	private double alfa;

	private double beta;

	private double delta;

	private double[][] densityMeasured;

	private double gama;

	private double[] h;

	private double Q0;
	
	private int stability;

	private int urCondition;

	private double u;

	private double y0;

	private double z0;

	private boolean flag;

	private int maxiIterationsNumber;

	public NelderMeadPlumeSolver(double[][] densityMeasured, double Q0, 
			double y0, double z0, int stability, int urCondition, double u) {
		this.densityMeasured = densityMeasured;
		this.Q0 = Q0;
		this.y0 = y0;
		this.z0 = z0;
		this.stability=stability;
		this.urCondition=urCondition;
		this.u = u;
	}

	private double[] calCentroid(double[][] vs1) {
		int len = vs1.length;
		double[] sum = { 0, 0, 0};
		double[] avg = { 0, 0, 0};
		for (int i = 0; i < len - 1; i++) {
			sum[0] += vs1[i][0];
			sum[1] += vs1[i][1];
			sum[2] += vs1[i][2];
		}
		avg[0] = sum[0] / (len - 1);
		avg[1] = sum[1] / (len - 1);
		avg[2] = sum[2] / (len - 1);
		return avg;
	}

	private double calculateDistance(PGPlumeModel lm) {
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
		double[] xr = new double[3];
		double[] xc = new double[3];
		xr[0] = centroid[0] + alfa * (centroid[0] - vs[len - 1][0]);
		xr[1] = centroid[1] + alfa * (centroid[1] - vs[len - 1][1]);
		xr[2] = centroid[2] + alfa * (centroid[2] - vs[len - 1][2]);
		double fr = calculateDistance(new PGPlumeModel(xr[0], xr[1], xr[2], stability, urCondition, u));
		double fs = calculateDistance(new PGPlumeModel(vs[len - 2][0],
				vs[len - 2][1], vs[len - 2][2],stability, urCondition, u));
		double fh = calculateDistance(new PGPlumeModel(vs[len - 1][0],
				vs[len - 1][1], vs[len - 1][2],stability, urCondition, u));
		double fc;
		if (fr >= fs) {
			if (fr < fh) {
				xc[0] = centroid[0] + beta * (xr[0] - centroid[0]);
				xc[1] = centroid[1] + beta * (xr[1] - centroid[1]);
				xc[2] = centroid[2] + beta * (xr[2] - centroid[2]);
				fc = calculateDistance(new PGPlumeModel(xc[0], xc[1], xc[2],
						stability, urCondition, u));
				if (fc > fr)
					return vs;
			} else {
				xc[0] = centroid[0] + beta * (vs[len - 1][0] - centroid[0]);
				xc[1] = centroid[1] + beta * (vs[len - 1][1] - centroid[1]);
				xc[2] = centroid[2] + beta * (vs[len - 1][2] - centroid[2]);
				fc = calculateDistance(new PGPlumeModel(xc[0], xc[1], xc[2],
						stability, urCondition, u));
				if (fc >= fh)
					return vs;
			}
			flag = true;
			vs[len - 1][0] = xc[0];
			vs[len - 1][1] = xc[1];
			vs[len - 1][2] = xc[2];
		}
		return vs;
	}

	private double[][] expand(double[][] vs, double[] centroid) {
		int len = vs.length;
		double[] xr = new double[3];
		double[] xe = new double[3];
		xr[0] = centroid[0] + alfa * (centroid[0] - vs[len - 1][0]);
		xr[1] = centroid[1] + alfa * (centroid[1] - vs[len - 1][1]);
		xr[2] = centroid[2] + alfa * (centroid[2] - vs[len - 1][2]);
		double fl = calculateDistance(new PGPlumeModel(vs[0][0], vs[0][1],
				vs[0][2], stability, urCondition, u));
		double fr = calculateDistance(new PGPlumeModel(xr[0], xr[1], xr[2], stability, urCondition, u));

		if (fl > fr) {
			xe[0] = centroid[0] + gama * (xr[0] - centroid[0]);
			xe[1] = centroid[1] + gama * (xr[1] - centroid[1]);
			xe[2] = centroid[2] + gama * (xr[2] - centroid[2]);
			double fe = calculateDistance(new PGPlumeModel(xe[0], xe[1], xe[2],
					stability, urCondition, u));
			if (fe < fr) {
				flag = true;
				vs[len - 1][0] = xe[0];
				vs[len - 1][1] = xe[1];
				vs[len - 1][2] = xe[2];
			}
		}

		return vs;
	}
	
	public int getUrCondition() {
		return urCondition;
	}

	public void setUrCondition(int urCondition) {
		this.urCondition = urCondition;
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

	public double getU() {
		return u;
	}

	public double getY0() {
		return y0;
	}

	public double getZ0() {
		return z0;
	}

	private double[][] initialSimplex() {
		double[][] vertisSet = { { Q0, y0, z0 },  { Q0, y0, z0 }, 
				 { Q0, y0, z0 },  { Q0, y0, z0 }};
		vertisSet[1][0] = Q0 + h[0];
		vertisSet[2][1] = y0 + h[1];
		vertisSet[3][2] = z0 + h[2];
		return vertisSet;
	}

	private double[][] ordering(double[][] vertisSet) {

		int len = vertisSet.length;
		double[] temp;
		double[] dist = new double[len];
		for (int i = 0; i < len; i++) {
			PGPlumeModel lm = new PGPlumeModel(vertisSet[i][0],
					vertisSet[i][1], vertisSet[i][2],stability, urCondition, u);
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
		double[] xr = new double[3];
		xr[0] = centroid[0] + alfa * (centroid[0] - vs[len - 1][0]);
		xr[1] = centroid[1] + alfa * (centroid[1] - vs[len - 1][1]);
		xr[2] = centroid[2] + alfa * (centroid[2] - vs[len - 1][2]);
		double fl = calculateDistance(new PGPlumeModel(vs[0][0], vs[0][1],
				vs[0][2], stability, urCondition, u));
		double fr = calculateDistance(new PGPlumeModel(xr[0], xr[1], xr[2],stability, urCondition, u));
		double fs = calculateDistance(new PGPlumeModel(vs[len - 2][0],
				vs[len - 2][1], vs[len - 2][2],  stability, urCondition, u));
		if ((fl <= fr) && (fr < fs)) {
			flag = true;
			vs[len - 1][0] = xr[0];
			vs[len - 1][1] = xr[1];
			vs[len - 1][2] = xr[2];
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

	public void setU(double u) {
		this.u = u;
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
		
		double[] result=new double[4];
		result[0]=centroid[0];
		result[1]=centroid[1];
		result[2]=centroid[2];
		result[3]=calculateDistance(new PGPlumeModel(result[0],
				result[1], result[2], stability, urCondition, u));
		return result;
	}

	public double[] NelderMeadSolve() {

		double[][] vertisSet = initialSimplex();
		return simplexTransformation(vertisSet);
	}

	private boolean terminationTest(double[] centroid) {
		PGPlumeModel lmc = new PGPlumeModel(centroid[0], centroid[1],
				centroid[2], stability, urCondition, u);
		double distc = calculateDistance(lmc);
		if (distc < stopE) {
			return true;
		} else {
			return false;
		}
	}

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
