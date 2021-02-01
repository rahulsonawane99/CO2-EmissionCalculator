package emissioncalculator;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option; 

public class ArgumentParser implements Emission {
	
	enum DistanceUnit {
		M,
		KM
	}
	
	enum EmissionUnit {
		G,
		KG
	}
	
	@Option(name="--d", aliases = { "--distance" }, usage="distance travelled in the vehicle.",
			required = true)
	private float distance;

	@Option(name="--u", aliases = { "--unit-of-distance" }, usage="distance measurement unit in km or m. Defaults to km.")
	private String unitOfDistance = "km";
	
	@Option(name="--o", aliases = { "--output" }, usage="Co2 emission measurement unit in g or kg. Defaults to g if less than 1000 else in kg.")
	private String output;

	@Option(name="--tm", aliases = { "--transportation-method" }, 
			usage="transportation mode used from the following options: "
			+ "\n1. small-diesel-car\n2. small-petrol-car\n3. small-plugin-hybrid-car\n4. small-electric-car\n5. "
			+ "medium-diesel-car\n6. medium-petrol-car\n7. medium-plugin-hybrid-car\n8. medium-electric-car\n9. "
			+ "large-diesel-car\n10. large-petrol-car\n11. large-plugin-hybrid-car\n12. large-electric-car\n13. "
			+ "bus\n14. "
			+ "train",
			required=true)
	
	private String transportationMethod;
	
	private static DecimalFormat df = new DecimalFormat("0.0");
	
	private static Map<String, Integer> TRANSPORTAIONS_METHODS_LIST = new HashMap<String, Integer>(); 
	
	static {
		TRANSPORTAIONS_METHODS_LIST.put("small-diesel-car", 142);
		TRANSPORTAIONS_METHODS_LIST.put("small-petrol-car", 154);
		TRANSPORTAIONS_METHODS_LIST.put("small-plugin-hybrid-car", 73);
		TRANSPORTAIONS_METHODS_LIST.put("small-electric-car", 50);
		
		TRANSPORTAIONS_METHODS_LIST.put("medium-diesel-car", 171);
		TRANSPORTAIONS_METHODS_LIST.put("medium-petrol-car", 192);
		TRANSPORTAIONS_METHODS_LIST.put("medium-plugin-hybrid-car", 110);
		TRANSPORTAIONS_METHODS_LIST.put("medium-electric-car", 58);
		
		TRANSPORTAIONS_METHODS_LIST.put("large-diesel-car", 209);
		TRANSPORTAIONS_METHODS_LIST.put("large-petrol-car", 282);
		TRANSPORTAIONS_METHODS_LIST.put("large-plugin-hybrid-car", 126);
		TRANSPORTAIONS_METHODS_LIST.put("large-electric-car", 73);
		
		TRANSPORTAIONS_METHODS_LIST.put("bus", 27);
		TRANSPORTAIONS_METHODS_LIST.put("train", 6);
	}
	
	
	public void parse(String [] args) {
		CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
            System.out.println("Your trip caused " + getFinalEmission() + " CO2-equivalent");
        } catch( CmdLineException e ) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            return;
        }
        
	}
	
	public int getTransportationMethod() {
		return TRANSPORTAIONS_METHODS_LIST.get(transportationMethod);
	}
	
	public String getFinalEmission() {
		float emissionGenerated = calculateEmission();
		return df.format(emissionGenerated) + getOutput();
	}
	
	public float calculateEmission() {		
		df.setRoundingMode(RoundingMode.UP);
		float emission = getTransportationMethod() * checkAndConvertDistance();
		if(!isEmissionUnitInKGrams()) {
			emission = convertGramsToKG(emission);
		}
		return emission;
	}
	
	public float checkAndConvertDistance() {
		if(isDistanceInMeters()) return distance/1000;
		return distance;
	}
	
	public String getUnitOfDistance() {
		return unitOfDistance;
	}
	
	public float getDistance() {
		return distance;
	}
	
	public String getOutput() {
		return output.toLowerCase();
	}
	
	public float convertGramsToKG(float emission) {
		setOuput(EmissionUnit.KG.toString());
		return emission/1000;
	}
	
	
	private void setOuput(String value) {
		output = value.toLowerCase();
	}
	
	
	public Boolean isEmissionUnitInKGrams() {
		if(output == null) {
			output = unitOfDistance.equals("m") ? "g" : "kg";
		}
		return output.toLowerCase().equals(EmissionUnit.G.toString().toLowerCase());
	}
	
	public Boolean isDistanceInMeters() {
		return getUnitOfDistance().equals(DistanceUnit.M.toString().toLowerCase());
	}
	
}
