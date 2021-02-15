package io.openvidu.js.java.model;

public class ProblemStatDto {

	private double rank1; //rank1 정답수 / 전체rank1문제수
	private double rank2; //rank2 정답수 / 전체rank2문제수
	private double rank3; //rank3 정답수 / 전체rank3문제수
	private double accuracy; //전체 정답수 / 전체 문제수
	
	
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
