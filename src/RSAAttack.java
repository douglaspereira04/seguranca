import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
public class RSAAttack {

	/**
	 * Wiener's Algorithm
	 * @param n
	 * @param b
	 * @return one factor p or failure
	 */
	public static BigInteger wienersAttack(BigInteger n, BigInteger b) {
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
	
}
