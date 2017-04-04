package hack.util;

import java.util.concurrent.ThreadLocalRandom;

public class Utility {
	
	private Utility()
	{
	}
	
	public static Double generateRandomNumberBetween(Double minVal, Double maxVal)
	{
		return round(ThreadLocalRandom.current().nextDouble(minVal, maxVal),2);
	}
	
	/*public static double round(double value, int places) 
	{
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}*/
	public static double roundUpDecimalVal(Double val)
	{
		double number = val;
		number = Math.round(number * 100);
		number = number/100;
		return number;
	}
	
	public static double round (double value, int precision) 
	{
	    int scale = (int) Math.pow(10, precision);
	    return (double) Math.round(value * scale) / scale;
	}
	
	public static Integer generateRandomNumberBetween(Integer minVal, Integer maxVal) 
	{
		return ThreadLocalRandom.current().nextInt(minVal, maxVal);
	}
}
