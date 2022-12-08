import java.math.BigInteger;

public class IntegerFactorizer {
	protected static final BigInteger one = new BigInteger("1");
	protected static final BigInteger two = new BigInteger("2");

	/**
	 * Pollard P-1
	 * @param n
	 * @param B
	 * @param primeCertaintyFactor
	 * @return prime factor or null when fails
	 */
	public static BigInteger pollardPMinusOne(BigInteger n, BigInteger B, int primeCertaintyFactor){
		BigInteger a = two;
		BigInteger j = two;
		BigInteger d = null;
	
		while(j.compareTo(B) < 0){
			if(j.isProbablePrime(primeCertaintyFactor)) {
				a = a.modPow(j, n);
			}
			j = j.add(one);
		}
		d = a.subtract(one).gcd(n);
		
		if (d.compareTo(one) > 0 && d.compareTo(n) < 0)	{
			return d;
		} else {
			return null;
		}
			
	}
}