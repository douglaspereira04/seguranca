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
		List<BigInteger> continuedFractionExpansion = continuedFractionExpansion(b,n);
		
		BigInteger c0 = BigInteger.ONE;
		BigInteger c1 = continuedFractionExpansion.get(0);
		BigInteger c2;
		BigInteger d0 = BigInteger.ZERO;
		BigInteger d1 = BigInteger.ONE;
		BigInteger d2;
		
		BigInteger nl;
		
		
		for (int j = 1; j < continuedFractionExpansion.size(); j++) {
			c2 = (continuedFractionExpansion.get(j).multiply(c1)).add(c0);
			d2 = (continuedFractionExpansion.get(j).multiply(d1)).add(d0);
			nl = d2.multiply(b).subtract(BigInteger.ONE).divide(c2);

			c0 = c1;
			d0 = d1;
			c1 = c2;
			d1 = d2;
			
			BigInteger[] roots = new BigInteger[2];
			
			roots(n, nl, roots);
			BigInteger p = roots[0];
			BigInteger q = roots[1];
			
			if(	p.multiply(q).compareTo(n) == 0) {
				return p;
			}
		}
		return null;
	}
	
	private static void roots(BigInteger n, BigInteger nl , BigInteger[] roots) {
		BigInteger a = BigInteger.ONE;
		BigInteger b = nl.subtract(n).subtract(BigInteger.ONE);
		BigInteger c = n;
		BigInteger p = BigInteger.ZERO;
		BigInteger q = BigInteger.ZERO;
		
		BigInteger d = (b.pow(2)).subtract(BigInteger.valueOf(4).multiply(a).multiply(c));
		if(d.compareTo(BigInteger.ZERO)>0) {
			p = b.negate().add(d.sqrt()).divide(BigInteger.TWO.multiply(a));
			q = b.negate().subtract(d.sqrt()).divide(BigInteger.TWO.multiply(a));
		}
		
		roots[0] = p;
		roots[1] = q;
	}
	
	public static List<BigInteger> continuedFractionExpansion(BigInteger a, BigInteger b){
		ArrayList<BigInteger> l = new ArrayList<BigInteger>();
		BigInteger r;
		
		while(!b.equals(BigInteger.ZERO)) {
			r = a.mod(b);
			l.add(a.subtract(r).divide(b));
			a = b;
			b = r;
		}
		
		return l;
		
	}
	
}
