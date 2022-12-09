import java.math.BigInteger;
import java.util.List;
import java.util.function.Function;

public class Main {

	public static void main(String[] args) {
		
		BigInteger n = BigInteger.valueOf(1403);
		BigInteger B = n.sqrt();


		BigInteger p;
		int randomHits = 0, strongHits = 0;
		int testes = 200;
		for (int i = 0; i < testes; i++) {
			n = IntegerFactorizer.pollardPMinusOneStrong(4, 8);
			System.out.println(n);
			p = IntegerFactorizer.pollardPMinusOne(n, B, 8);
			System.out.println(p);
			if(p != null) {
				strongHits++;
			}
			n = IntegerFactorizer.randomN(4, 8);
			System.out.println(n);
			p = IntegerFactorizer.pollardPMinusOne(n, B, 8);
			System.out.println(p);
			if(p != null) {
				randomHits++;
			}
		}
		System.out.println("random: "+randomHits*100.0/testes+"%");
		System.out.println("strong: "+strongHits*100.0/testes+"%");
		
		

		Function<BigInteger, BigInteger> f = x -> x.pow(x.intValue()).add(BigInteger.valueOf(1));
		n = BigInteger.valueOf(7171);
		BigInteger x1 = BigInteger.valueOf(1);
		p = IntegerFactorizer.pollardRho(n, x1, f);
		System.out.println(p);
		
		p = IntegerFactorizer.wieners(BigInteger.valueOf(160523347), BigInteger.valueOf(60728973));
		System.out.println(p);
	}
}
