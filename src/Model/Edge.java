package Model;

public class Edge{
	
	private Integer From;
	private Integer To;
	private double Gain;
	
	public Edge(Integer from, Integer to, double gain) {
		From = from;
		To = to;
		Gain = gain;
	}
	
	public Integer getFrom() {
		return From;
	}
	public void setFrom(Integer from) {
		From = from;
	}
	public Integer getTo() {
		return To;
	}
	public void setTo(Integer to) {
		To = to;
	}
	public double getGain() {
		return Gain;
	}
	
	public void setGain(double gain) {
		Gain = gain;
	}

}
