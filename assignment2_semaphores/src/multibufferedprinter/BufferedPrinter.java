package multibufferedprinter;
import java.util.concurrent.*;


import java.util.concurrent.Semaphore;

public class BufferedPrinter {
	
	String source;
	int currentrecordNo;
	
	public static void main ( String[] args ) {
		
		/*
		 * Definition of semaphores, two per buffer.
		 */
		Semaphore isfullBuf1 = new Semaphore(0);
		Semaphore isfullBuf2 = new Semaphore(0);
		
		Semaphore isemptyBuf1 = new Semaphore(1);
		Semaphore isemptyBuf2 = new Semaphore(1);
		
		int numrecords = 4; //to easily set the number of records to be printed
		
		/* 
		 * Set the source file names. Must be done manually.
		 * Note: files must be in the root eclipse directory.
		 */

		System.out.println("Running...");
		String[] sourcename = new String[numrecords]; 
		sourcename[0] = new String("The quick brown fox.txt");
		sourcename[1] = new String("The slow pink fox.txt");
		sourcename[2] = new String("The sly silver fox.txt");
		sourcename[3] = new String("Description.txt");

		StringBuilder Buffer1 = new StringBuilder();
		StringBuilder Buffer2 = new StringBuilder();
		
		/*
		 * Create Threads
		 */
		
		SrcLoader A = new SrcLoader(sourcename, Buffer1, isfullBuf1, 
									isemptyBuf1, numrecords);
		
		BufCopier B = new BufCopier(Buffer1, Buffer2, 
									isfullBuf1, isemptyBuf1, 
									isfullBuf2, isemptyBuf2, 
									numrecords);
		
		BufPrinter C = new BufPrinter(Buffer2, 
									  isfullBuf2, isemptyBuf2, 
									  numrecords);
		
		
		Thread ThreadA = new Thread(A,"Thread A");
		Thread ThreadB = new Thread(B,"Thread B");
		Thread ThreadC = new Thread(C,"Thread C");
		
		/*
		 * Run Threads
		 */
		ThreadA.start();
		ThreadB.start();
		ThreadC.start();
		
	}
	
	public String nextRecord(){
		return source;	
	}
	
}

