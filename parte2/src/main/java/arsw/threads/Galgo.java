package arsw.threads;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author rlopez
 * 
 */
public class Galgo extends Thread {
	private int paso;
	private Carril carril;
	RegistroLlegada regl;
	private boolean movimiento;

	public Galgo(Carril carril, String name, RegistroLlegada reg) {
		super(name);
		this.carril = carril;
		paso = 0;
		this.regl=reg;
	}

	public void corra() throws InterruptedException {
		movimiento=true;
		while (paso < carril.size()) {			
			Thread.sleep(100);
			carril.setPasoOn(paso++);
			carril.displayPasos(paso);
			synchronized (this){
				while(movimiento==false){
					this.wait();
				}

			}
			if (paso == carril.size()) {						
				carril.finish();
				int ubicacion;
				synchronized (regl) {
					ubicacion = regl.getUltimaPosicionAlcanzada();
					regl.setUltimaPosicionAlcanzada(ubicacion + 1);
					System.out.println("El galgo " + this.getName() + " llego en la posicion " + ubicacion);
				}
				if (ubicacion==1){
					regl.setGanador(this.getName());
				}
				
			}
		}
	}

	public synchronized void setmovimiento(boolean movimiento){
		this.movimiento=movimiento;
		if (movimiento== true){
			notifyAll();
		}
	}
	@Override
	public void run() {
		
		try {
			corra();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
