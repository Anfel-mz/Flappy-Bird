package com.anfel.jeu;

import java.util.function.Function;

import javax.swing.JFrame;


public class main {
	
	
	public static JFrame window;
	public static Scene scene;
	
	public static void main(String[] args) {
	
		//window settings 
		window = new JFrame ("Flappy Bird");
		scene = new Scene();
		
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //fermer la window
		window.setSize(300, 425);
		window.setLocationRelativeTo(null);//centrer la window
		window.setResizable(false);//on ne pas redimenssionner la window 
		window.setAlwaysOnTop(true);
		
		window.setContentPane(scene);
		window.setVisible(true);//setvisible tjr en dernier 
		
		
		
		
	}
	
	

	
	
		
	
}
