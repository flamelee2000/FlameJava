package cn.edu.buct.ray;

public class Sensor {
	
	private double xPos;
	private double yPos;
	private double zPos;
	private double t=-1;
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
	
	public String toStringWithName(){
		String showStr = "xPos = " + xPos + "\t";
		showStr += "yPos = " + yPos + "\t";
		showStr += "zPos = " + zPos + "\t";
		if (t>0) {showStr += "t = " + t + "\t";}
		showStr += "valueMeasured = " + valueMeasured + "\t";
		return showStr;
	}
	
	public String toString(){
		String showStr = "(" + xPos ;
		showStr += ", " + yPos ;
		showStr += ", " + zPos;
		if (t>0) {showStr += ", " + t;}
		showStr += ", " + valueMeasured + ")";
		return showStr;
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
