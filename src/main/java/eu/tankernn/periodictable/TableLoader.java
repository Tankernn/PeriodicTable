package eu.tankernn.periodictable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class TableLoader {
	private static Map<String, Element> table = new HashMap<String, Element>();
	private static int roundValue;
	
	static Map<String, Element> loadData(int roundValue) throws FileNotFoundException {
		TableLoader.roundValue = roundValue;
		
		JSONObject obj = new JSONObject(readFile("/periodicTable.json"));
		
		for (String s: JSONObject.getNames(obj)) {
			JSONArray arr = obj.getJSONArray(s);
			if (s.equals("table"))
				for (int j = 0; j < arr.length(); j++) {
					loadElementsFromJSON(arr.getJSONObject(j).getJSONArray("elements"));
				}
			else
				loadElementsFromJSON(arr);
		}
		return table;
	}
	
	private static void loadElementsFromJSON(JSONArray elementarr) {
		for (int i = 0; i < elementarr.length(); i++) {
			JSONObject element = elementarr.getJSONObject(i);
			
			table.put(element.getString("small"), new Element(element.getInt("number"), element.getString("name"), element.getString("small"), round(element.getDouble("molar"), roundValue), getElectrons(element.getJSONArray("electrons"))));
		}
	}
	
	private static int[] getElectrons(JSONArray jsonArr) {
		int[] electrons = new int[jsonArr.length()];
		
		for (int i = 0; i < jsonArr.length(); i++) {
			electrons[i] = jsonArr.getInt(i);
		}
		
		return electrons;
	}
	
	private static String readFile(String filename) {
		URL url = Class.class.getResource(filename);
		
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			result = sb.toString();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
