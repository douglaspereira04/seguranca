import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.security.SecureRandom;

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
	

	/**
	 * Wiener's Algorithm
	 * @param n
	 * @param b
	 * @return one factor p or failure
	 */
	public static BigInteger wieners(BigInteger n, BigInteger b) {
		BigInteger r;
		
		BigInteger qj;
		BigInteger nll = n;
		BigInteger bl = b;
		
		r = bl.mod(nll);
		qj = bl.subtract(r).divide(nll);
		bl = nll;
		nll = r;
		
		BigInteger c0 = BigInteger.ONE;
		BigInteger c1 = qj;
		BigInteger c2;
		BigInteger d0 = BigInteger.ZERO;
		BigInteger d1 = BigInteger.ONE;
		BigInteger d2;
		
		BigInteger nl;
		
		BigInteger p = BigInteger.ZERO;
		BigInteger q = BigInteger.ZERO;

		while(!nll.equals(BigInteger.ZERO)) {
			r = bl.mod(nll);
			qj = bl.subtract(r).divide(nll);
			bl = nll;
			nll = r;
			
			c2 = (qj.multiply(c1)).add(c0);
			d2 = (qj.multiply(d1)).add(d0);
			nl = d2.multiply(b).subtract(BigInteger.ONE).divide(c2);

			c0 = c1;
			d0 = d1;
			c1 = c2;
			d1 = d2;
			
			BigInteger bbaskhara = nl.subtract(n).subtract(BigInteger.ONE);
			BigInteger d = (bbaskhara.pow(2)).subtract(BigInteger.valueOf(4).multiply(n));
			if(d.compareTo(BigInteger.ZERO)>0) {
				p = bbaskhara.negate().add(d.sqrt()).divide(BigInteger.TWO);
				q = bbaskhara.negate().subtract(d.sqrt()).divide(BigInteger.TWO);
			}
			
			if(	p.multiply(q).compareTo(n) == 0) {
				return p;
			}
			
		}
		return null;
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
	 * Gera n resitente a fatoracao pollard p-1
	 * @param size em bytes
	 * @param primalityCertainty
	 * @return
	 */
	public static BigInteger pollardPMinusOneStrong(int size, int primalityCertainty) {
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