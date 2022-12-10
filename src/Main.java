import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		Function<BigInteger, BigInteger> f = x -> x.pow(2).add(BigInteger.valueOf(1));

		multiTest(500, 4,  b -> b.sqrt(), f, 4);
		multiTest(500, 8,  b -> b.sqrt().sqrt(), f, 4);
		
		testPollardPMinus1(200, 4, b -> b.sqrt(),4);
		testPollardPMinus1(200, 8, b -> b.sqrt().sqrt(),4);
		testPollardPMinus1(200, 16, b -> b.sqrt().sqrt().sqrt(),4);
		
		testWieners(1000, 16, 4);
		
	}
	
	
	/**
	 * Test pollard p-1 algorithm with strong n and random n
	 * @param tests
	 * @param nSize
	 * @param bcalc
	 * @param threadAmount
	 * @return
	 * @throws InterruptedException
	 */
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
					n = IntegerFactorizer.pollardPMinusOneStrongN(nSize, 8);
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

	
	
	/**
	 * Test pollard p-1 algorithm with strong n and random n
	 * @param tests
	 * @param nSize
	 * @param bcalc
	 * @param threadAmount
	 * @return
	 * @throws InterruptedException
	 */
	public static long multiTest(int tests, int nSize, Function<BigInteger, BigInteger> bcalc, Function<BigInteger, BigInteger> f, int threadAmount) throws InterruptedException {

		System.out.println("Test Multiplo: (tests = "+tests+", size =~ "+nSize*8+")");

		AtomicInteger rhoHits = new AtomicInteger(0), pm1Hits = new AtomicInteger(0), dixonhits = new AtomicInteger(0);
		long begin = System.nanoTime();
		Thread[] threads = new Thread[threadAmount];

		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(()->{
				BigInteger p;
				BigInteger n = null;
				BigInteger B = null;
				BigInteger x1 = BigInteger.valueOf(1);
				
				for (int j = 0; j < tests/threadAmount; j++) {
					n = IntegerFactorizer.randomN(nSize, nSize/2, 8);
					
					p = IntegerFactorizer.pollardRho(n, x1, f);
					if(p != null) {
						rhoHits.incrementAndGet();
					}
					
					B = bcalc.apply(n);
					p = IntegerFactorizer.pollardPMinusOne(n, B, 8);
					if(p != null) {
						pm1Hits.incrementAndGet();
					}

					p = IntegerFactorizer.dixon(n, 8);
					if(p != null) {
						dixonhits.incrementAndGet();
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
		System.out.println("\tRho n success ratio: "+rhoHits.get()*100.0/tests+"%");
		System.out.println("\tP-1 n success ratio: "+pm1Hits.get()*100.0/tests+"%");
		System.out.println("\tDixon n success ratio: "+dixonhits.get()*100.0/tests+"%");

		
		return elapsedTime;
	}
	

	
	
	/**
	 * Tests wieners algorithm with vulnerable and non vulnarable parameters 
	 * @param tests
	 * @param nSize
	 * @param threadAmount
	 * @return
	 * @throws InterruptedException
	 */
	public static long testWieners(int tests, int nSize, int threadAmount) throws InterruptedException {
		System.out.println("Wieners: (tests = "+tests+", size =~ "+nSize*8+")");
		
		AtomicInteger randomHits = new AtomicInteger(0), weakHits = new AtomicInteger(0);
		long begin = System.nanoTime();
		Thread[] threads = new Thread[threadAmount];

		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(()->{
				BigInteger[] parameters = null;
				BigInteger p = null, q = null, n = null, a = null, b = null;
				
				for (int j = 0; j < tests/threadAmount; j++) {
					
					parameters = WienersAttack.wienersWeakRSAParameters(8, 8);
					p = parameters[0];
					q = parameters[1];
					n = parameters[2];
					a = parameters[3];
					b = parameters[4];
					
					BigInteger pl;

					pl = WienersAttack.wieners(n,b);
					if(pl != null && (pl.compareTo(p) == 0 || pl.compareTo(q) == 0) ) {
						weakHits.incrementAndGet();
					}
					
					parameters = WienersAttack.randomRSAParameters(8, 8);
					p = parameters[0];
					q = parameters[1];
					n = parameters[2];
					a = parameters[3];
					b = parameters[4];

					pl = WienersAttack.wieners(n,b);
					if(pl != null && (pl.compareTo(p) == 0 || pl.compareTo(q) == 0) ) {
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
		System.out.println("\tWeak n success ratio: "+weakHits.get()*100.0/tests+"%");
		
		
		return elapsedTime;
		
		
	}
	
}
