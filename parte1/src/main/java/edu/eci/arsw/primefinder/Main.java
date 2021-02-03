package edu.eci.arsw.primefinder;

import java.util.Scanner;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Clase principal que ejecuta la aplicación para buscar los números primos que hay
 * entre 1 y 30000000
 */
public class Main {
	private static LinkedList<PrimeFinderThread> threads = new LinkedList<>();

	/**
	 * Método que suspende la ejecución de todos los hilos que se tienen
	 * @param hilos Lista con todos los hilos creados para resolver el problema
	 */
	public static void pararHilos(LinkedList<PrimeFinderThread> hilos){
		for (PrimeFinderThread hilo: threads){
			hilo.suspenderHilo();
		}
	}

	/**
	 * Método que reanuda la ejecución de todos los hilos que se tienen
	 * @param hilos Lista con todos los hilos creados para resolver el problema
	 */
	public static void reanudarHilos(LinkedList<PrimeFinderThread> hilos){
		for (PrimeFinderThread hilo: threads){
			hilo.renaudarHilo();
		}
	}

	/**
	 * Método que crea y ejecuta todos los hilos para resolver el problema
	 * @param args
	 */
	public static void main(String[] args) {
		Long inicio = System.currentTimeMillis();
		int n=3;
		int average = 30000000/n;
		int cont = 0;
		AtomicInteger count = new AtomicInteger();
		for (int i = 0; i < n; i++){
			threads.add(new PrimeFinderThread(cont,cont+average, count));
			cont = cont + average;
		}
		for (PrimeFinderThread hilo: threads){
			hilo.start();
		}
		while(true){
			if(System.currentTimeMillis()-inicio==5000){
				pararHilos(threads);
				System.out.print("Primos encontrados después de 5 segundos: "+count);
				break;
			}
		}
		Scanner waitForKeypress = new Scanner(System.in);
		waitForKeypress.nextLine();
		reanudarHilos(threads);
		for (PrimeFinderThread hilo: threads){
			try {
				hilo.join();
			} catch (InterruptedException interruptedException) {
				interruptedException.printStackTrace();
			}
		}
		System.out.print("Primos encontrados en total: "+count);
	}

}