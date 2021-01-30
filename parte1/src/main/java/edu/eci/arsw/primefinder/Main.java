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
		Long inicio = System.currentTimeMillis()/1000;
		int n=1;
		int average = 30000000/n;
		int cont = 0;
		AtomicInteger count = new AtomicInteger();

		for (int i = 0; i < n; i++){
			threads.add(new PrimeFinderThread(cont,cont+average, count));
			cont = cont + average;
		}

		Timer timer = new Timer (5000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pararHilos(threads);
			}
		});
		timer.start();
		for (PrimeFinderThread hilo: threads){
			hilo.start();
		}
		Scanner waitForKeypress = new Scanner(System.in);
		System.out.print("Presiona la tecla Enter para continuar"+count);
		waitForKeypress.nextLine();
		reanudarHilos(threads);
		timer.stop();
	}
}