package hack.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class Utility {
	
	private Utility()
	{
	}
	
	public static Double generateRandomNumberBetween(Double minVal, Double maxVal)
	{
		return round(ThreadLocalRandom.current().nextDouble(minVal, maxVal),2);
	}
	
	public static double round(double value, int places) 
	{
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

	public static Integer generateRandomNumberBetween(Integer minVal, Integer maxVal) 
	{
		return ThreadLocalRandom.current().nextInt(minVal, maxVal);
	}
}
