package emissionTests;

import emissioncalculator.*;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;

import java.math.RoundingMode;
import java.text.DecimalFormat;

//import org.junit.Test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class EmissionTests {
	private ArgumentParser parser;
	private static DecimalFormat df = new DecimalFormat("0.0");
	
	@Test
	public void testEmissionCalculationInKG() {
		parser = new ArgumentParser();
		parser.parse(new String[] {"--transportation-method", "medium-diesel-car", "--distance", "15", "--unit-of-distance", "km"});
		assertEquals("2.6", df.format(parser.calculateEmission()));
	}
	
	@Test
	public void checkIfDistanceInKMByDefault() {
		parser = new ArgumentParser();
		parser.parse(new String[] {"--distance", "1800.5","--transportation-method", "large-petrol-car"});
		assertFalse(parser.isDistanceInMeters());
	}
	
	
	
	@Test
	public void checkIfDistanceInMeters() {
		parser = new ArgumentParser();
		parser.parse(new String[] {"--transportation-method", "train", "--distance", "14500", "--unit-of-distance", "m"});
		assertTrue(parser.isDistanceInMeters());
	}

	
	@Test
	public void testEmissionCalculationInGrams() {
		parser = new ArgumentParser();
		parser.parse(new String[] {"--transportation-method", "train", "--distance", "14500", "--unit-of-distance", "m"});
		df.setRoundingMode(RoundingMode.UP);
		assertEquals("87.0", df.format(parser.calculateEmission()));
	}

	
	
	@Test
	public void testIsOutputInKG() {
		parser = new ArgumentParser();
		parser.parse(new String[] { "--transportation-method", "train", "--distance", "14500","--unit-of-distance", "m", "--output", "kg"});
		assertEquals("0.1", df.format(parser.calculateEmission()));
	}
}
