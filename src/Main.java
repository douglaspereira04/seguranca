import java.math.BigInteger;

public class Main {

	public static void main(String[] args) {
		
		BigInteger v = new BigInteger("1403");
		BigInteger B = v.sqrt();
		
		BigInteger p = IntegerFactorizer.pollardPMinusOne(v, B, 8);
		System.out.println(p);
	}

}
