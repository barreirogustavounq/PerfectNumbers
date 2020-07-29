import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Barrier {
	private int maxThreads;
	private List<BigInteger> resulTotal;
	private int waiting = 0;
	private int finished = 0;

    public int getFinished() {
        return finished;
    }

    public Barrier(int maxThreads) {
		this.maxThreads = maxThreads;
		this.resulTotal = new ArrayList<>();
		
	}
	public  void addResultOfThread(BigInteger num){
			this.resulTotal.add(num);
	}
	public List<BigInteger> getResulTotal(){
		return this.resulTotal;
	}
	
	public synchronized void waitBarrier() {
		this.waiting++;

		while(this.getWaiting() < this.getMaxThreads()) {
        	try {
                System.out.println("me colgue esperando que el ultimo termine!");
                this.wait();
            } catch (InterruptedException e) {
            	e.printStackTrace();
			}
    }
            this.finished++;
            System.out.println("Salí");
		    this.notify();
}

	public int getMaxThreads() {
		return maxThreads;
	}

	public void setMaxThreads(int maxThreads) {
		this.maxThreads = maxThreads;
	}

	public int getWaiting() {
		return waiting;
	}

	public void setWaiting(int waiting) {
		this.waiting = waiting;
	}



}
 