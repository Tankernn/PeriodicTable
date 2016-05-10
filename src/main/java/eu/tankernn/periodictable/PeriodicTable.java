package eu.tankernn.periodictable;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class PeriodicTable {
	private static Map<String, Element> table = new HashMap<String, Element>();
	
	static Scanner in = new Scanner(System.in);
	static boolean interrupt = false;
	
	public static void main(String[] args) {
		System.out.println("How many decimals should be used for molarMass values?");
		try {
			table = TableLoader.loadData(in.nextInt());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		System.out.println("Enter a command, alternatively the short name, full name or atomic number of any element.");
		
		while (!interrupt) {
			try {
				System.out.print(">");
				executeCommand(in.nextLine());
			} catch (Exception ex) {
				System.out.println("There was an exception: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
		in.close();
	}
	
	static void executeCommand(String command) {
		switch (command) {
		case "molarMass":
			System.out.println(new Substance(in.nextLine()).getMolarMass());
			break;
		case "info":
			System.out.println(new Substance(in.nextLine()).getComponentListing());
			break;
		case "concentration":
			System.out.println("Enter a chemical term for any substance, eg. 'NaCl': ");
			Substance term = new Substance(in.nextLine());
			
			System.out.println("How many grams of " + term.getSmallName() + "?");
			term.setMass(in.nextDouble());
			Solution so = new Solution(term);
			
			System.out.println("What volume of water? (dm3)");
			so.setVolume(in.nextDouble());
			
			System.out.println(so.getConcentration());
			break;
		case "help":
			System.out.println("concentration: calculates concentration of a solutiuon." + "\n"
							+ "molarMass: calculates molar mass of given substance." + "\n"
							+ "exit: exits the program.");
			break;
		case "exit":
			interrupt = true;
			break;
		default:
			try {
				System.out.println(getElement(command));
			} catch (NullPointerException ex) {
				System.out.println("No such command or element. Type 'help' for a list of commands.");
			}
			break;
		}
	}
	
	static Element getElement(String query) throws NullPointerException {
		try {
			if (table.get(query) != null)
				return table.get(query);
		} catch (NullPointerException ex) {
			System.out.println("No element with short name " + query);
		}
		
		Collection<Element> elements = table.values();
		
		Optional<Element> element = elements.stream().filter(e -> e.getFullName().equals(query)).findFirst();
		
		try {
			int i = Integer.parseInt(query);
			element = elements.stream().filter(e -> e.getAtomicNumber() == i).findFirst();
		} catch (NumberFormatException ex) {
		}
		
		if (element.isPresent())
			return element.get();
		else
			throw new NullPointerException("The element " + query + " was not found.");
	}
}
