package com.anfel.jeu;

public class Chrono implements Runnable{

	private final int PAUSE = 5; //régler la vitesse du jeu
	
	@Override
	public void run() {
		while (true) {
			main.scene.decrXFond();
			main.scene.repaint(); //repaint appelle la methode paintComponent
			try { 
				Thread.sleep(this.PAUSE);
			} catch (InterruptedException e) { }
		}
		
	}

	
	
}
