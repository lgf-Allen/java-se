/**
 * 
 */
package com.allen.collection.queue;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Alllen
 *
 */
public class CustomerQueue {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		addTest();
		delTest();
	}

	/**compare method :add(E e) and offer(E e)
	 * Inserts the specified element into this queue.
	 */
	private static void addTest() {
		try {
			
			Queue queueAdd = new LinkedBlockingQueue<String>(2);
			queueAdd.add("lisi");
			queueAdd.add("zhangsan");
			queueAdd.add("wangwu");//throws java.lang.IllegalStateException
		}catch(Exception e) {
			System.out.println("Method add(E e) if this queue no space is currently available throws "+e);
		}
		
		Queue<String> queueOffer = new LinkedBlockingQueue<String>(2);
		queueOffer.offer("lisi");
		queueOffer.offer("zhangsan");
		Boolean status = queueOffer.offer("wangwu");//return false
		System.out.println("Method offer(E e) if this queue no space is currently available return status is "+status);
	}
	
	/**
	 * Compare method :poll and remove
	 * Retrieves and removes the head of this queue.
	 */
	private static void delTest() {
		try{
			Queue queueRemove = new LinkedBlockingQueue<>();
			queueRemove.remove();//java.util.NoSuchElementException
		}catch(Exception e) {
			System.out.println("Method remove if this queue is empty throws "+e);
		}
		Queue queuePoll = new LinkedBlockingQueue<>();
		Object object = queuePoll.poll();//return null
		System.out.println("Method poll if this queue is empty return "+ object);
		
	}
	
	/**
	 * Compare method : element and peek
	 * Retrieves, but does not remove, the head of this queue. 
	 */
	private static void retrieveTest() {
		try {
			Queue queueElement = new LinkedBlockingQueue<>();
			queueElement.element();// throws java.util.NoSuchElementException
		}catch (Exception e) {
			System.out.println("Method Element if this queue is empty throws "+e);
		}
		Queue queuePeek = new LinkedBlockingQueue<>();
		Object object = queuePeek.peek();//return null
		System.out.println("Method peek if this queue is empty return "+ object);
	}
	
	
	
}
