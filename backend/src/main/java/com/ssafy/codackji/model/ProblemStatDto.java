package com.ssafy.codackji.model;

public class ProblemStatDto {

	private double rank1;
	private double rank2;
	private double rank3;
	private double accuracy;
	
	
	public double getRank1() {
		return rank1;
	}
	public void setRank1(double rank1) {
		this.rank1 = rank1;
	}
	public double getRank2() {
		return rank2;
	}
	public void setRank2(double rank2) {
		this.rank2 = rank2;
	}
	public double getRank3() {
		return rank3;
	}
	public void setRank3(double rank3) {
		this.rank3 = rank3;
	}
	public double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	@Override
	public String toString() {
		return "ProblemStatDto [rank1=" + rank1 + ", rank2=" + rank2 + ", rank3=" + rank3 + ", accuracy=" + accuracy
				+ "]";
	}
	
}
