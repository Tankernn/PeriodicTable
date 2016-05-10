package eu.tankernn.periodictable;

import org.json.*;

public class Element extends Substance {
	private final int atomicNumber;
    private final String fullName;
    private final int[] electrons;
    
    Element(int atomicNumber, String fullName, String smallName, double atomicMass, int[] electrons) {
        super(smallName, atomicMass);
    	this.atomicNumber = atomicNumber;
        this.fullName = fullName;
        this.electrons = electrons;
    }

    public int getAtomicNumber() {
        return atomicNumber;
    }

    public String getFullName() {
        return fullName;
    }
	
	public String toString() {
		return "Full name: " + this.fullName + "\n"
				+ "Atomic number: " + this.atomicNumber + "\n"
				+ "Short name: " + this.getSmallName() + "\n"
				+ "Atomic mass: " + this.getMolarMass() + "\n"
				+ "Electrons: " + new JSONArray(electrons).toString();
	}
}
