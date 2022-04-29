package com.anfel.jeu;

public class Chrono implements Runnable{

	private final int PAUSE = 5; //régler la vitesse du jeu
	
	@Override
	public void run() {
		while (!main.scene.getFinDuJeu()) {
			main.scene.decrXFond(); //to move le fond
			main.scene.repaint(); //repaint appelle la methode paintComponent donc on re dessine tous avec des nouveaux coordonnées 
			try { 
				Thread.sleep(this.PAUSE); //to slow down the loop process
			} catch (InterruptedException e) { }
		}
		
	}

	
	
}
