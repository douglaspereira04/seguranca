import java.math.BigInteger;
import java.util.function.Function;

public class Main {

	public static void main(String[] args) {
		
		BigInteger n = BigInteger.valueOf(1403);
		BigInteger B = n.sqrt();

		BigInteger p = IntegerFactorizer.pollardPMinusOne(n, B, 8);
		System.out.println(p);
		

		Function<BigInteger, BigInteger> f = x -> x.pow(x.intValue()).add(BigInteger.valueOf(1));
		n = BigInteger.valueOf(7171);
		BigInteger x1 = BigInteger.valueOf(1);
		p = IntegerFactorizer.pollardRho(n, x1, f);
		System.out.println(p);
	}

}
