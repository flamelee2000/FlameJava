package cn.edu.buct.ray;

public class Sensor {
	
	private double xPos;
	private double yPos;
	private double zPos;
	private double t;
	private double valueMeasured;
	
	public Sensor(double xPos,double yPos,double zPos,double t, double valueMeasured){
		this.xPos=xPos;
		this.yPos=yPos;
		this.zPos=zPos;
		this.t=t;
		this.valueMeasured=valueMeasured;
	}
	
	public Sensor(double xPos,double yPos,double zPos,double valueMeasured){
		this.xPos=xPos;
		this.yPos=yPos;
		this.zPos=zPos;
		this.valueMeasured=valueMeasured;
	}
	
	public double getxPos() {
		return xPos;
	}
	public void setxPos(double xPos) {
		this.xPos = xPos;
	}
	public double getyPos() {
		return yPos;
	}
	public void setyPos(double yPos) {
		this.yPos = yPos;
	}
	public double getzPos() {
		return zPos;
	}
	public void setzPos(double zPos) {
		this.zPos = zPos;
	}
	public double getT() {
		return t;
	}
	public void setT(double t) {
		this.t = t;
	}
	public double getValueMeasured() {
		return valueMeasured;
	}
	public void setValueMeasured(double valueMeasured) {
		this.valueMeasured = valueMeasured;
	}

}
