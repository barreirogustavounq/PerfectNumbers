import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PerfectWorker extends Thread {
	private Buffer buffer;
	private Barrier barrier;

	public PerfectWorker(Buffer buffer, Barrier barrier) {
		this.buffer = buffer;
		this.barrier = barrier;
	}

	public synchronized void isPerfect(int active, List<BigInteger> listOfPerfects) {
		BigInteger numberToProcess = buffer.pop();
		while (numberToProcess.compareTo(new BigInteger("0")) > -1) {
			System.out.print("analizando " + numberToProcess + " \n");
			/*
			if (this.isPerfectNum(numberToProcess)) {

				barrier.addResultOfThread(numberToProcess);
			}*/
			if(listOfPerfects.contains(numberToProcess)){
				System.out.print("Perfect   " + numberToProcess.toString() + "             ");
                barrier.addResultOfThread(numberToProcess);
            }
			numberToProcess = buffer.pop();
		}
		barrier.waitBarrier();
	}

	public boolean isPerfectNum(BigInteger num) {
		ArrayList<BigInteger> listOfDivisor = this.divisorsOf(num);
		BigInteger sum = new BigInteger("0");
		for (BigInteger r : listOfDivisor) {
			sum = sum.add(r);
		}
		return (num.compareTo(sum) == 0 );
	}

	public ArrayList<BigInteger> divisorsOf(BigInteger n) {
		ArrayList<BigInteger> divisors = new ArrayList<>();
		for (BigInteger i = new BigInteger("1"); i.compareTo(n) < 0; i =i.add(new BigInteger("1"))) {
			if (n.mod(i).compareTo(new BigInteger("0")) == 0 && n.compareTo(i) != 0) {
				divisors.add(i);
			}
		}
			return divisors;
	}
	//-----------------------------------------------







}
