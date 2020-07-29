import java.math.BigInteger;


public class Buffer {
	private int size;
	public BigInteger[] data;
	private int begin = 0;
	private int end = 0;
	
	public Buffer(int n) {
		this.size = n;
		this.data = new BigInteger[size + 1];
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public synchronized void push ( BigInteger p) {
		while(this.isFull()) {
			try {
				//System.out.println("estoy lleno");
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		data[this.begin] = p;
		begin = next(this.begin);
		this.notifyAll();
		}
	
	public synchronized BigInteger pop() {
		while(this.isEmpty()) {
			try {
				System.out.println("estoy vacio");
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		BigInteger result = data[this.end];
		end = next(this.end);
		this.notifyAll();
		return result;
	}
	
	public boolean isEmpty() { 
		return this.begin == this.end; 
	}
	
	public boolean isFull() {
		return next(this.begin) == this.end;
	}
	
	private int next(int i) {

		return (i + 1) % (this.size + 1);
	}
}
