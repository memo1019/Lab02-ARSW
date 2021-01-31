package edu.eci.arsw.primefinder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
	private static LinkedList<PrimeFinderThread> threads = new LinkedList<>();

	public static void pararHilos(LinkedList<PrimeFinderThread> hilos){
		for (PrimeFinderThread hilo: threads){
			hilo.suspenderhilo();
		}
	}

	public static void reanudarHilos(LinkedList<PrimeFinderThread> hilos){
		for (PrimeFinderThread hilo: threads){
			hilo.renaudarhilo();
		}
	}

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