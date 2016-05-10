package eu.tankernn.periodictable;

public class Solution {
	
	private Substance substance;
	private double volume;
	private double concentration;
	
	public Solution(Substance substance) {
		this.substance = substance;
	}
	
	public void setVolume(double volume) {
		System.out.println("v=" + volume);
		this.volume = volume;
		this.concentration = calcConcentration();
	}
	
	public void setConcentration(double concentration) {
		System.out.println("c=" + concentration);
		this.concentration = concentration;
		this.volume = calcVolume();
	}
	
	public Substance getSubstance() {
		return substance;
	}

	public double getVolume() {
		return volume;
	}

	public double getConcentration() {
		return concentration;
	}

	private double calcVolume() {
		double vol = substance.getQuantityOfSubstance() / concentration;
		System.out.println("c=" + substance.getQuantityOfSubstance() + "/" + concentration + "=" + vol + "dm3");
		return vol;
	}

	private double calcConcentration() {
		double con = substance.getQuantityOfSubstance() / volume;
		System.out.println("c=" + substance.getQuantityOfSubstance() + "/" + volume + "=" + con + "mol/dm3");
		return con;
	}
	
	public static Solution blend(Solution[] solutions) {
		Solution blend = null;
		
		for (Solution solution : solutions) {
			if (blend == null) {
				blend = solution;
			} else {
				blend.substance.setQuantityOfSubstance(blend.substance.getQuantityOfSubstance() + solution.substance.getQuantityOfSubstance());
				
				blend.volume += solution.volume;
			}
		}
		
		return blend;
	}
	
}
