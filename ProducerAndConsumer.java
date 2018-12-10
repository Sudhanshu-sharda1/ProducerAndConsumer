import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class ProducerAndConsumer {
	//buffer to store the max capacity
	
	int bufferSize=0;
	
	//Stores actual elements
	Queue<Integer> buffer=new LinkedList<>();
	
	
	 void producer() throws InterruptedException {
		
		 int numberOfItemProduced=0;
		 
		
		while(numberOfItemProduced<10)
		{
			//Synchronized block to synchronize producer and consumer
			synchronized (this) {
				//If Buffer is full then block the Producer thread
				if(buffer.size()==bufferSize )
					{
						wait();
					}
				
				numberOfItemProduced++;
				
				System.out.println("Producer produced Item number "+numberOfItemProduced);
				//Add item to buffer
				buffer.add(numberOfItemProduced);
				
				//wake the consumer thread
				notify();
				Thread.sleep(1000);
			}
			
		}
	
	}	
	void consumer() throws InterruptedException {
		 int numberOfItemconsumed=0;
		 int count=0;
			while(count<10)
			{
				//Synchronized block to synchronize producer and consumer 
				synchronized (this) {
					//if there is no item in buffer, block the consumer thread
					if(buffer.size()==0)
						wait();
					//Remove the item by consumer thread
					numberOfItemconsumed=buffer.remove();
					System.out.println("Consumer consumed Item number "+numberOfItemconsumed);
					count++;
					//awaken the producer thread
					notify();				
					Thread.sleep(500);				
				}	
			}
	}
	public static void main(String[] args) throws InterruptedException {	
 		Scanner sc=new Scanner(System.in);		
 		System.out.println("Enter the Size of Buffer:");
 		int BufferSize=sc.nextInt();		
 		ProducerAndConsumer obj=new ProducerAndConsumer();		
		obj.bufferSize=BufferSize;		
		//creating the producer thread
 		Thread producerThread=new Thread(new Runnable()
 				{
					@Override
					public void run() {
						try {
							obj.producer();
						} catch (InterruptedException e) {
							
							e.printStackTrace();
						}
					}
 				});
				
		//creating the consumer thread
 		Thread ConsumerThread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					obj.consumer();
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
		});
		//Starts the producer and consumer threads
 		producerThread.start();
 		ConsumerThread.start();
 		
 		producerThread.join();
 		ConsumerThread.join();
 		
 		sc.close();
 			
	}

}
