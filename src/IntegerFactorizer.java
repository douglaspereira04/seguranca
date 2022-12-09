import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class IntegerFactorizer {
	public static final Random rand = new Random();

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
	

	
	public static BigInteger randomN(int size, int psize, int primalityCertainty) {
		byte[] pbytes = new byte[psize];
		byte[] qbytes = new byte[size-psize];
		BigInteger p = null, q = null;

		do {
			rand.nextBytes(pbytes);
			p = new BigInteger(pbytes);
		} while (p.compareTo(BigInteger.ONE) <= 0 || !p.isProbablePrime(primalityCertainty) );
		
		do {
			rand.nextBytes(qbytes);
			q = new BigInteger(qbytes);
		} while (q.compareTo(BigInteger.ONE) <= 0 || !q.isProbablePrime(primalityCertainty) );
		
		
		return p.multiply(q);
	}


	/**
	 * Generates n resistant to pollard p-1 factorization 
	 * @param size bytes
	 * @param primalityCertainty
	 * @return
	 */
	public static BigInteger pollardPMinusOneStrongN(int size, int primalityCertainty) {
		byte[] bytes = new byte[size/2];
		BigInteger p1 = null, p = null;
		BigInteger q1 = null, q = null;

		do {
			rand.nextBytes(bytes);
			p1 = new BigInteger(bytes);
			p = p1.multiply(BigInteger.TWO).add(BigInteger.ONE);
			
		} while (p.compareTo(BigInteger.ONE) <= 0 || !(p1.isProbablePrime(primalityCertainty) && p.isProbablePrime(primalityCertainty)) );
		
		do {
			rand.nextBytes(bytes);
			q1 = new BigInteger(bytes);
			q = q1.multiply(BigInteger.TWO).add(BigInteger.ONE);
			
		} while (q.compareTo(BigInteger.ONE) <= 0 || !(q1.isProbablePrime(primalityCertainty) && q.isProbablePrime(primalityCertainty)) );
		
		
		return p.multiply(q);
	}
	

}