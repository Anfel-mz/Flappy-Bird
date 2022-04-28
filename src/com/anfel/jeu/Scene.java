package com.anfel.jeu;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.anfel.objet.Tuyau;
import com.anfel.personnage.FlappyBird;

public class Scene extends JPanel{

	////////////////////////////////variables //////////////////////////////////////////
	private ImageIcon icoBandeFond; 
	private Image imgBandeFond;
	
	public Tuyau tuyauHaut1;
	public Tuyau tuyauBas1;
	public Tuyau tuyauHaut2;
	public Tuyau tuyauBas2;
	public Tuyau tuyauHaut3;
	public Tuyau tuyauBas3;
	
	public FlappyBird flappyBird;
	
	private final int LARGEUR_BANDE_FOND = 140;
	private final int DISTANCE_TUYAUX = 250;
	private final int ECART_TUYAUX = 120;
	
	private int xFond;
	private int xTuyaux;
	
	private Random random;
	private int r1 = new Random().nextInt((320 - 150) + 1) + 100;
	private int r2 = new Random().nextInt((320 - 150) + 1) + 100;
	private int r3 = new Random().nextInt((320 - 150) + 1) + 100;
	
	//constructeur 
	public Scene() {
		
		super(); //because it inherits the JPanel Class we can remove it nothing will change cause we are not using it
		this.icoBandeFond = new ImageIcon(getClass().getResource("/images/bandeFondEcran.png"));
		this.imgBandeFond = this.icoBandeFond.getImage();
				
		this.xFond = 0;
		this.xTuyaux = 100; 
	
		
		
		this.tuyauHaut1 = new Tuyau(this.xTuyaux, -r1, "/images/tuyauHaut.png");
		this.tuyauBas1 = new Tuyau (this.xTuyaux, tuyauHaut1.getY() + tuyauHaut1.getHauteur() + ECART_TUYAUX, "/images/tuyauBas.png");
		this.tuyauHaut2 = new Tuyau(this.xTuyaux +this.DISTANCE_TUYAUX , -r2, "/images/tuyauHaut.png");
		this.tuyauBas2 = new Tuyau (this.xTuyaux +this.DISTANCE_TUYAUX, tuyauHaut2.getY() + tuyauHaut2.getHauteur() + ECART_TUYAUX, "/images/tuyauBas.png");
		this.tuyauHaut3 = new Tuyau(this.xTuyaux +this.DISTANCE_TUYAUX * 2,- r3, "/images/tuyauHaut.png");
		this.tuyauBas3 = new Tuyau (this.xTuyaux +this.DISTANCE_TUYAUX * 2,tuyauHaut3.getY() + tuyauHaut3.getHauteur() + ECART_TUYAUX, "/images/tuyauBas.png");
		
		this.flappyBird = new FlappyBird(100, 150, "/images/oiseau1.png");
		
		random = new Random();
		
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.addKeyListener(new Clavier());
		
		Thread chronoEcran = new Thread(new Chrono ());
		chronoEcran.start();
	}
	
	
	//methodes 
	
	private void moveTuyau(Tuyau tuyauHaut, Tuyau tuyauBas, Tuyau t3) {
		tuyauHaut.setX(tuyauHaut.getX() - 1);
		tuyauBas.setX(tuyauHaut.getX());
		
		if(tuyauHaut.getX() == - this.xTuyaux) {
			tuyauHaut.setX (t3.getX() + DISTANCE_TUYAUX); 
			tuyauHaut.setY(-xTuyaux - 10 * random.nextInt(18));
			tuyauBas.setY(tuyauHaut.getY() + tuyauHaut.getHauteur() + ECART_TUYAUX);
		}
		
	}
	
	//fonction d'aide pour le paintComponent
	private void deplacempenTuyau(Graphics g) {
		
		//tuyau 1
		moveTuyau(this.tuyauHaut1, this.tuyauBas1, this.tuyauHaut3);
		
		g.drawImage(this.tuyauHaut1.getImgTuyau(), this.tuyauHaut1.getX(), this.tuyauHaut1.getY(), null);
		g.drawImage(this.tuyauBas1.getImgTuyau(), this.tuyauBas1.getX(), this.tuyauBas1.getY(), null);
		
		//tuyau 2
		moveTuyau(this.tuyauHaut2, this.tuyauBas2, this.tuyauHaut1);
		
		g.drawImage(this.tuyauHaut2.getImgTuyau(), this.tuyauHaut2.getX(), this.tuyauHaut2.getY(), null);
		g.drawImage(this.tuyauBas2.getImgTuyau(), this.tuyauBas2.getX(), this.tuyauBas2.getY(), null);
		
		//tuyau 3
		moveTuyau(this.tuyauHaut3, this.tuyauBas3, this.tuyauHaut2);
		
		g.drawImage(this.tuyauHaut3.getImgTuyau(), this.tuyauHaut3.getX(), this.tuyauHaut3.getY(), null);
		g.drawImage(this.tuyauBas3.getImgTuyau(), this.tuyauBas3.getX(), this.tuyauBas3.getY(), null);
	}
	
	//fonction d'aide pour le paintComponent
	private void deplacementFond(Graphics g) {
		if(this.xFond == -this.LARGEUR_BANDE_FOND) this.xFond = 0;
		g.drawImage(this.imgBandeFond, this.xFond, 0, null);
		g.drawImage(this.imgBandeFond, this.LARGEUR_BANDE_FOND + this.xFond, 0, null);
		g.drawImage(this.imgBandeFond,2* this.LARGEUR_BANDE_FOND + this.xFond, 0, null);
	
	}
	
	public void paintComponent(Graphics g) {
	   
		this.deplacementFond(g);
		this.deplacempenTuyau(g);
		this.flappyBird.setY(this.flappyBird.getY()+1);
		g.drawImage(this.flappyBird.getImgOiseau(), this.flappyBird.getX(), this.flappyBird.getY(), null);
	}
	
	
	
	//Getters & Setters 
	public int getXFond() {
		return this.xFond;
	}
	
	public void decrXFond() {
		this.xFond --;
	}
}
