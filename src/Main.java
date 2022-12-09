import java.math.BigInteger;
import java.util.function.Function;

public class Main {

	public static void main(String[] args) {

		testPollardPMinus1(200, 4, b -> b.sqrt().sqrt());
		testPollardPMinus1(200, 8, b -> b.sqrt().sqrt());
		testPollardPMinus1(200, 16, b -> b.sqrt().sqrt().sqrt());
		

		Function<BigInteger, BigInteger> f = x -> x.pow(x.intValue()).add(BigInteger.valueOf(1));
		BigInteger n = BigInteger.valueOf(7171);
		BigInteger x1 = BigInteger.valueOf(1);
		BigInteger p = IntegerFactorizer.pollardRho(n, x1, f);
		System.out.println(p);
		
		p = IntegerFactorizer.wieners(BigInteger.valueOf(160523347), BigInteger.valueOf(60728973));
		System.out.println(p);
	}
	
	
	public static long testPollardPMinus1(int tests, int nSize, Function<BigInteger, BigInteger> bcalc) {

		System.out.println("Pollard p-1: (tests = "+tests+", size =~ "+nSize*8+")");
		BigInteger n = null;
		BigInteger B = null;

		BigInteger p;
		int randomHits = 0, strongHits = 0;
		long begin = System.nanoTime();
		for (int i = 0; i < tests; i++) {
			n = IntegerFactorizer.pollardPMinusOneStrong(nSize, 8);
			B = bcalc.apply(n);
			p = IntegerFactorizer.pollardPMinusOne(n, B, 8);
			if(p != null) {
				strongHits++;
			}
			n = IntegerFactorizer.randomN(nSize, nSize/2, 8);
			B = bcalc.apply(n);
			p = IntegerFactorizer.pollardPMinusOne(n, B, 8);
			if(p != null) {
				randomHits++;
			}
		}
		long elapsedTime = System.nanoTime() - begin;
		System.out.println("\tElapsed time: "+elapsedTime+"ns");
		System.out.println("\tRandom n success ratio: "+randomHits*100.0/tests+"%");
		System.out.println("\tStrong n success ratio: "+strongHits*100.0/tests+"%");
		
		return elapsedTime;
	}
	
}
