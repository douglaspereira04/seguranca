import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		testPollardPMinus1(200, 4, b -> b.sqrt(),4);
		testPollardPMinus1(200, 8, b -> b.sqrt().sqrt(),4);
		testPollardPMinus1(200, 16, b -> b.sqrt().sqrt().sqrt(),4);
		

		Function<BigInteger, BigInteger> f = x -> x.pow(x.intValue()).add(BigInteger.valueOf(1));
		BigInteger n = BigInteger.valueOf(7171);
		BigInteger x1 = BigInteger.valueOf(1);
		BigInteger p = IntegerFactorizer.pollardRho(n, x1, f);
		System.out.println(p);
		
		p = IntegerFactorizer.wieners(BigInteger.valueOf(160523347), BigInteger.valueOf(60728973));
		System.out.println(p);
	}
	
	
	public static long testPollardPMinus1(int tests, int nSize, Function<BigInteger, BigInteger> bcalc, int threadAmount) throws InterruptedException {

		System.out.println("Pollard p-1: (tests = "+tests+", size =~ "+nSize*8+")");

		AtomicInteger randomHits = new AtomicInteger(0), strongHits = new AtomicInteger(0);
		long begin = System.nanoTime();
		Thread[] threads = new Thread[threadAmount];

		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(()->{
				BigInteger p;
				BigInteger n = null;
				BigInteger B = null;
				
				for (int j = 0; j < tests/threadAmount; j++) {
					n = IntegerFactorizer.pollardPMinusOneStrong(nSize, 8);
					B = bcalc.apply(n);
					p = IntegerFactorizer.pollardPMinusOne(n, B, 8);
					if(p != null) {
						strongHits.incrementAndGet();
					}
					n = IntegerFactorizer.randomN(nSize, nSize/2, 8);
					B = bcalc.apply(n);
					p = IntegerFactorizer.pollardPMinusOne(n, B, 8);
					if(p != null) {
						randomHits.incrementAndGet();
					}
				}
			});
			threads[i].start();
		}
		
		for (int i = 0; i < threads.length; i++) {
			threads[i].join();
		}
		long elapsedTime = System.nanoTime() - begin;
		System.out.println("\tElapsed time: "+elapsedTime+"ns");
		System.out.println("\tRandom n success ratio: "+randomHits.get()*100.0/tests+"%");
		System.out.println("\tStrong n success ratio: "+strongHits.get()*100.0/tests+"%");
		
		return elapsedTime;
	}
	
	public static long testWieners(int tests, int nSize) {
		System.out.println("Wieners: (tests = "+tests+", size =~ "+nSize*8+")");

		int randomHits = 0, weakHits = 0;
		
		long begin = System.nanoTime();
		for (int i = 0; i < tests; i++) {
			
		}
		long elapsedTime = System.nanoTime() - begin;
		System.out.println("\tElapsed time: "+elapsedTime+"ns");
		System.out.println("\tRandom n success ratio: "+randomHits*100.0/tests+"%");
		System.out.println("\tWeak n success ratio: "+weakHits*100.0/tests+"%");
		
		return elapsedTime;
		
		
	}
	
}
