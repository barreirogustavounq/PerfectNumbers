import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ThreadPool {
    int cantOfThreads;
    List<BigInteger> result;

    public ThreadPool(int cantOfThreads) {
        this.result = new ArrayList<>();
        this.cantOfThreads = cantOfThreads;
    }

    public String startWorkers(Buffer buffer, Barrier barrier, int activeThreads, List<BigInteger> listOfPerfects){
        int myThreads;
        if(activeThreads < cantOfThreads){ myThreads = activeThreads;} else{ myThreads = cantOfThreads;}
        this.startW(buffer, barrier, cantOfThreads, listOfPerfects);
        return "termine";
    }

    private void startW(Buffer buffer, Barrier barrier, int num, List<BigInteger> listOfPerfects){
        BigInteger count = BigInteger.valueOf(0);
        BigInteger n = new BigInteger("1");
        while(count.compareTo(BigInteger.valueOf(num)) < 0){
            PerfectWorker p1 =  new PerfectWorker(buffer, barrier){
                public void run(){
                    this.isPerfect(num, listOfPerfects);
                }
            };
            count = count.add(n);
            p1.start();
        }
    }
}
