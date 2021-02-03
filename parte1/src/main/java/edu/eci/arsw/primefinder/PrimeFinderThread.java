package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Esta clase es un hilo que realiza la búsqueda de números primos de un intervalo dado.
 */
public class PrimeFinderThread extends Thread {

	private int a,b;
	private List<Integer> primes=new LinkedList<Integer>();
	private boolean suspender;
	private AtomicInteger count;

	/**
	 * Constructor del hilo
	 * @param a Límite inferior del intervalo
	 * @param b Límite superior del intervalo
	 * @param count Cuenta de los primos encontrados
	 */
	public PrimeFinderThread(int a, int b, AtomicInteger count) {
		super();
		this.a = a;
		this.b = b;
		this.suspender = false;
		this.count = count;
	}

	/**
	 * Método para correr el hilo, tiene el ciclo que realizará la búsqueda de cada uno de los
	 * números primos en el intervalo que se tiene
	 */
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

	/**
	 * Método que verifica si un número es primo
	 * @param n Número que se va a verificar
	 * @return true si es un número primo
	 */
	boolean isPrime(int n) {
		if (n%2==0) return false;
		for(int i=3;i*i<=n;i+=2) {
			if(n%i==0)
				return false;
		}
		return true;
	}

	/**
	 * Método que retorna los primos que se tienen hasta el momento de invocar este método
	 * @return Lista de primos
	 */
	public List<Integer> getPrimes() {
		return primes;
	}

	/**
	 * Método que suspende el hilo
	 */
	synchronized void suspenderHilo(){
		suspender=true;
	}

	/**
	 * Método que reanuda el hilo
	 */
	synchronized void renaudarHilo(){
		suspender=false;
		notify();
	}
}
