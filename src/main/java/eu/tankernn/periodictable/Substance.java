package eu.tankernn.periodictable;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Substance {
	private double mass;
	private double molarMass;
    private final String smallName;
    private double quantityOfSubstance;
    
    Map<Substance, Integer> components = new LinkedHashMap<Substance, Integer>();
    
	Substance(String elements) {
		smallName = elements;
		
		String smallName = "";
		Substance lastSubstance = null;
		
		for (int i = 0; i < elements.length(); i++) {
			Character c = elements.charAt(i);
			
			if (Character.isDigit(c)) {
				int counter;
				for (counter = i; counter < elements.length(); counter++) {
					if (!Character.isDigit(elements.charAt(counter)))
						break;
				}
				int multiplier = Integer.parseInt(elements.substring(i, counter));
				
				if (lastSubstance != null)
					components.put(lastSubstance, multiplier);
				
				lastSubstance = null;
				smallName = "";
				continue;
			} else if (c.equals('(')) {
				if (lastSubstance != null)
					components.put(lastSubstance, 1);
				
				int counter;
				for (counter = i; counter < elements.length(); counter++)
					if (elements.charAt(counter) == ')')
						break;
				
				lastSubstance = new Substance(elements.substring(i + 1, counter));
				i = counter;
			} else if (c.equals(')')) {
				//Nothing
			} else if (Character.isLowerCase(c)) {
				smallName += c;
				try {
					lastSubstance = PeriodicTable.getElement(smallName);
				} catch (NullPointerException ex) {
					lastSubstance = null;
				}
			} else if (Character.isUpperCase(c)) {
				if (lastSubstance != null)
					components.put(lastSubstance, 1);
				
				smallName = c.toString();
				try {
					lastSubstance = PeriodicTable.getElement(smallName);
				} catch (NullPointerException ex) {
					lastSubstance = null;
				}
			}
		}
		
		if (lastSubstance != null)
			components.put(lastSubstance, 1);
		
		molarMass = calcMolarMass();
	}
	
	Substance(Map<Substance, Integer> components) {
		this.components = components;
		this.molarMass = getMolarMass();
		
		String smallName = "";
		Iterator<Entry<Substance, Integer>> it = components.entrySet().iterator();
	    while (it.hasNext()) {
	    	Entry<Substance, Integer> entry = it.next();
	    	smallName += entry.getKey().smallName;
	    	if (entry.getValue() > 1)
	    		smallName += entry.getValue();
	    }
	    
	    this.smallName = smallName;
	}
	
	Substance(String smallName, double molarMass) {
		this.smallName = smallName;
		this.molarMass = molarMass;
	}
	
	public void setMass(double mass) {
		this.mass = mass;
		this.quantityOfSubstance = calcQuantityOfSubstance();
	}
	
	private double calcMass() {
		return quantityOfSubstance * molarMass;
	}
	
	private double calcMolarMass() {
		double molarMass = 0;
		
		Iterator<Entry<Substance, Integer>> it = components.entrySet().iterator();
	    while (it.hasNext()) {
	    	Entry<Substance, Integer> entry = it.next();
	    	
	    	molarMass += entry.getKey().getMolarMass() * entry.getValue();
	    }
		
		System.out.println("M(" + this.smallName + ")=" + molarMass + "g/mol");
		return molarMass;
	}
	
	public double getMolarMass() {
        return molarMass;
    }
	
	void setQuantityOfSubstance(double quantityOfSubstance) {
		this.quantityOfSubstance = quantityOfSubstance;
		this.mass = calcMass();
	}
	
	private double calcQuantityOfSubstance() {
		System.out.println("n=" + mass / molarMass + "mol");
		return mass / molarMass;
	}
	
	double getQuantityOfSubstance() {
		return quantityOfSubstance;
	}
	
	public String getSmallName() {
		return smallName;
	}
	
    public String getComponentListing() {
    	String componentString = "";
    	
    	Iterator<Entry<Substance, Integer>> it = components.entrySet().iterator();
	    while (it.hasNext()) {
	    	Entry<Substance, Integer> entry = it.next();
	    	
	    	componentString += entry.getValue() + " atom(s) of: \n" + entry.getKey().toString() + "\n";
	    }
	    
	    return componentString;
    }
}
