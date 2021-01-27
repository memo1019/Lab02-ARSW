package edu.eci.arsw.primefinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Main {
	public static void main(String[] args) {
		Long inicio = System.currentTimeMillis()/1000;
		int n=3;
		LinkedList<PrimeFinderThread> threads = new LinkedList<>();

		int average = 30000000/n;
		int cont = 0;

		for (int i = 0; i < n; i++){
			threads.add(new PrimeFinderThread(cont,cont+average));
			cont = cont + average;
		}

		for (PrimeFinderThread hilo: threads){
			hilo.start();
			hilo.suspenderhilo();
			System.out.println("Hilo suspendido");
			hilo.renaudarhilo();
			System.out.println("Hilo reanudado");
		}
	}
}