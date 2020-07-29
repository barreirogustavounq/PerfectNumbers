import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PerfectsNumbersCalculator {
    public List<BigInteger> getListOfPerfects() {
        return listOfPerfects;
    }

    public BigInteger getTWO() {
        return TWO;
    }

    public BigInteger getTHREE() {
        return THREE;
    }

    private List<BigInteger> listOfPerfects = new ArrayList<>();
    BigInteger TWO = new BigInteger("2");
    BigInteger THREE = new BigInteger("3");

    public void calculatePerfects(int cantOfPerfects) {
        int p = 2;

        while (this.listOfPerfects.size() < cantOfPerfects ) {
            if (isPrime(p)) {
                BigInteger mersenne = TWO.pow(p).subtract(BigInteger.ONE);

                if (isPrime(mersenne)) {
                    this.listOfPerfects.add(TWO.pow(p - 1).multiply(mersenne));
                }
            }
            System.out.println(listOfPerfects);

            p += (p == 2) ? 1 : 2;
        }
    }

    private boolean isPrime(BigInteger number) {
        if (number.mod(TWO).equals(BigInteger.ZERO)) {
            return number.equals(TWO);
        }

        for (BigInteger i = THREE; number.compareTo(i.multiply(i)) >= 0; i = i.add(TWO)) {
            if (number.mod(i).equals(BigInteger.ZERO)) {
                return false;
            }
        }

        return true;
    }

    private boolean isPrime(int number) {
        if (number % 2 == 0) {
            return number == 2;
        }

        for (int i = 3; number >= i * i; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }
}
