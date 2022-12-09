import java.math.BigInteger;
import java.util.List;
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
		
		List<BigInteger> continuedFractionExpansion = RSAAttack.continuedFractionExpansion(BigInteger.valueOf(160523347),BigInteger.valueOf(60728973));
		System.out.print("[");
		for (BigInteger v : continuedFractionExpansion) {
			System.out.print(","+v);
		}
		System.out.println("]");
		
		p = RSAAttack.wienersAttack(BigInteger.valueOf(160523347), BigInteger.valueOf(60728973));
		System.out.println(p);
	}
}
