package edu.eci.arsw.primefinder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.Timer;

public class PrimeFinderThread extends Thread {

	private int a,b;
	private List<Integer> primes=new LinkedList<Integer>();
	private boolean suspender;
	private AtomicInteger count;

	public PrimeFinderThread(int a, int b, AtomicInteger count) {
		super();
		this.a = a;
		this.b = b;
		this.suspender = false;
		this.count = count;
	}

	public void run() {

		for (int i = a; i <= b; i++) {
			if (isPrime(i)) {
				primes.add(i);
				System.out.println(i);
				count.incrementAndGet();
			}
			synchronized (this) {
				while (suspender) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}


	boolean isPrime(int n) {
		if (n%2==0) return false;
		for(int i=3;i*i<=n;i+=2) {
			if(n%i==0)
				return false;
		}
		return true;
	}

	public List<Integer> getPrimes() {
		return primes;
	}

	synchronized void suspenderhilo(){
		suspender=true;
	}

	synchronized void renaudarhilo(){
		suspender=false;
		notify();
	}
}
