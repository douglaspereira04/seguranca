import java.math.BigInteger;
import java.util.List;
import java.util.function.Function;

public class IntegerFactorizer {

	/**
	 * Pollard P-1
	 * @param n
	 * @param B
	 * @param primeCertaintyFactor
	 * @return prime factor or null when fails
	 */
	public static BigInteger pollardPMinusOne(BigInteger n, BigInteger B, int primeCertaintyFactor){
		BigInteger a = BigInteger.TWO;
		BigInteger j = BigInteger.TWO;
		BigInteger d = null;
	
		while(j.compareTo(B) < 0){
			if(j.isProbablePrime(primeCertaintyFactor)) {
				a = a.modPow(j, n);
			}
			j = j.add(BigInteger.ONE);
		}
		d = a.subtract(BigInteger.ONE).gcd(n);
		
		if (d.compareTo(BigInteger.ONE) > 0 && d.compareTo(n) < 0)	{
			return d;
		} else {
			return null;
		}
			
	}
	
	/**
	 * Pollard P-1 given j prime numbers list
	 * @param n
	 * @param B
	 * @param primeCertaintyFactor
	 * @return prime factor or null when fails
	 */
	public static BigInteger pollardPMinusOne(BigInteger n, List<BigInteger> primeList){
		BigInteger a = BigInteger.TWO;
		BigInteger d = null;
	
		for (BigInteger j : primeList) {
			a = a.modPow(j, n);
		}
		d = a.subtract(BigInteger.ONE).gcd(n);
		
		if (d.compareTo(BigInteger.ONE) > 0 && d.compareTo(n) < 0)	{
			return d;
		} else {
			return null;
		}
			
	}
	
	public static BigInteger pollardRho(BigInteger n, BigInteger x1,  Function<BigInteger, BigInteger> f){
		BigInteger x = x1;
		BigInteger xl = f.apply(x).mod(n);
		BigInteger p = x.subtract(xl).gcd(n);
	
		while(p.compareTo(BigInteger.ONE) == 0){
			x = f.apply(x).mod(n);
			xl = f.apply(xl).mod(n);
			xl = f.apply(xl).mod(n);
			p = x.subtract(xl).gcd(n);
		}
		
		if (p.compareTo(n) == 0)	{
			return null;
		} else {
			return p;
		}
			
	}
}