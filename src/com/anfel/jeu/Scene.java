package com.anfel.jeu;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.anfel.objet.Tuyau;
import com.anfel.personnage.FlappyBird;

public class Scene extends JPanel{

	////////////////////////////////variables //////////////////////////////////////////
	//image du fond 
	private ImageIcon icoBandeFond; 
	private Image imgBandeFond;
	
	//les trois tuyaux
	public Tuyau tuyauHaut1;
	public Tuyau tuyauBas1;
	public Tuyau tuyauHaut2;
	public Tuyau tuyauBas2;
	public Tuyau tuyauHaut3;
	public Tuyau tuyauBas3;
	
	//le flappy bird
	public FlappyBird flappyBird;
	
	private int score;
	private Font police;
	
	//useful consts
	private final int LARGEUR_BANDE_FOND = 140;
	private final int DISTANCE_TUYAUX = 250;
	private final int ECART_TUYAUX = 120;
	
	//useful vars
	private int xFond;
	private int xTuyaux;
	private boolean finDuJeu = false;
	
	//random values for random postions of les tuyaux
	private Random random;
	private int r1 = new Random().nextInt((300 - 150) + 1) + 100;
	private int r2 = new Random().nextInt((300 - 150) + 1) + 100;
	private int r3 = new Random().nextInt((300 - 150) + 1) + 100;
	
	//constructeur 
	public Scene() {
		
		super(); //because it inherits the JPanel Class we can remove it nothing will change cause we are not using it
		//the image
		this.icoBandeFond = new ImageIcon(getClass().getResource("/images/bandeFondEcran.png"));
		this.imgBandeFond = this.icoBandeFond.getImage();
		
		//starting values 
		this.xFond = 0;
		this.xTuyaux = 300; 
		this.finDuJeu = false;
	
		
		this.score = 0;
		this.police = new Font("Arial", Font.PLAIN, 40);
		
		//initialisation des tuyaux
		this.tuyauHaut1 = new Tuyau(this.xTuyaux, -r1, "/images/tuyauHaut.png");
		this.tuyauBas1 = new Tuyau (this.xTuyaux, tuyauHaut1.getY() + tuyauHaut1.getHauteur() + ECART_TUYAUX, "/images/tuyauBas.png");
		this.tuyauHaut2 = new Tuyau(this.xTuyaux +this.DISTANCE_TUYAUX , -r2, "/images/tuyauHaut.png");
		this.tuyauBas2 = new Tuyau (this.xTuyaux +this.DISTANCE_TUYAUX, tuyauHaut2.getY() + tuyauHaut2.getHauteur() + ECART_TUYAUX, "/images/tuyauBas.png");
		this.tuyauHaut3 = new Tuyau(this.xTuyaux +this.DISTANCE_TUYAUX * 2,- r3, "/images/tuyauHaut.png");
		this.tuyauBas3 = new Tuyau (this.xTuyaux +this.DISTANCE_TUYAUX * 2,tuyauHaut3.getY() + tuyauHaut3.getHauteur() + ECART_TUYAUX, "/images/tuyauBas.png");
		
		//initialisation du personnage
		this.flappyBird = new FlappyBird(100, 150, "/images/oiseau1.png");
		
		//variable randome pour changer les Y des tuyaux (we used it in moveTuyau)
		random = new Random();
		
		//setting the focus on the input of the keyboard 
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.addKeyListener(new Clavier());
	
		
		//ajout d'un thread pour dynamiser le programe /le fond
		Thread chronoEcran = new Thread(new Chrono ());
		chronoEcran.start();
	}
	
	
	//methodes 
	
	//moveTuyau c'est pour aider à deplacer définir les cordonnées des tuyaux et les changer à chaque fois 
	private void moveTuyau(Tuyau tuyauHaut, Tuyau tuyauBas, Tuyau t3) {
		tuyauHaut.setX(tuyauHaut.getX() - 1);
		tuyauBas.setX(tuyauHaut.getX());
		
		if(tuyauHaut.getX() == - this.xTuyaux) {
			tuyauHaut.setX (t3.getX() + DISTANCE_TUYAUX); 
			tuyauHaut.setY(-(xTuyaux-150) - 8 * random.nextInt(15));
			tuyauBas.setY(tuyauHaut.getY() + tuyauHaut.getHauteur() + ECART_TUYAUX);
		}
		
	}
	
	//fonction d'aide pour le paintComponent déplacement des objets 
	private void deplacempenTuyau(Graphics g) {
		
		//tuyau 1
		moveTuyau(this.tuyauHaut1, this.tuyauBas1, this.tuyauHaut3);//update les coordonnées 
		
		g.drawImage(this.tuyauHaut1.getImgTuyau(), this.tuyauHaut1.getX(), this.tuyauHaut1.getY(), null); //draw them 
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
	
	
	private void deplacementFond(Graphics g) {
		//mettre à jour la variable xfond
		if(this.xFond == -this.LARGEUR_BANDE_FOND) this.xFond = 0;
		//drawing three images so they can move fluidly
		g.drawImage(this.imgBandeFond, this.xFond, 0, null);
		g.drawImage(this.imgBandeFond, this.LARGEUR_BANDE_FOND + this.xFond, 0, null);
		g.drawImage(this.imgBandeFond,2* this.LARGEUR_BANDE_FOND + this.xFond, 0, null);
	
	}
	
	private void deplacementFlappy(Graphics g) {
		this.flappyBird.setY(this.flappyBird.getY()+1); // on fait tomber le flappy bird 
		g.drawImage(this.flappyBird.getImgOiseau(), this.flappyBird.getX(), this.flappyBird.getY(), null);
	}
	
	
	
	private boolean collisionFlappy() {
		boolean finDuJeu = false;
		//proche tuyau 1 
		if(this.flappyBird.getX() + this.flappyBird.getLargeur() > this.tuyauBas1.getX() - 20 && this.flappyBird.getX() - 20 < this.tuyauBas1.getLargeur() + this.tuyauBas1.getX()) {
			finDuJeu = this.flappyBird.collision(tuyauBas1) || this.flappyBird.collision(tuyauHaut1);
		}else {
			//proche tuyau 2
			if( this.flappyBird.getX() + this.flappyBird.getLargeur() > this.tuyauBas2.getX() - 20 && this.flappyBird.getX() - 20 < this.tuyauBas2.getLargeur() + this.tuyauBas2.getX()) {
				finDuJeu = this.flappyBird.collision(tuyauBas2) || this.flappyBird.collision(tuyauHaut2);
			}else {
				//proche tuyau 3
				if( this.flappyBird.getX() + this.flappyBird.getLargeur() > this.tuyauBas3.getX() - 20 && this.flappyBird.getX() - 20 < this.tuyauBas3.getLargeur() + this.tuyauBas3.getX()) {
					finDuJeu = this.flappyBird.collision(tuyauBas3) || this.flappyBird.collision(tuyauHaut3);
					} else {
						//contacte avec le plafond ou le sol
						if(this.flappyBird.getY() <0 || this.flappyBird.getY() + this.flappyBird.getHauteur() > 355) {
							finDuJeu = true;
						}
					}
			}
			}
		
		return finDuJeu;
		}
		
		
	private void score() {
		
		 if(this.tuyauBas1.getX() +this.tuyauBas1.getHauteur() == 95 ||this.tuyauBas2.getX() +this.tuyauBas2.getHauteur() == 95||this.tuyauBas3.getX() +this.tuyauBas3.getHauteur() == 95) {
			 Audio.playSound("/Audio/sonnerie.wav");//les fichiers mp3 ne marche pas
			 if(score == 0) score++;
			 this.score ++; 
		 }
	}
		

	
	public void paintComponent(Graphics g) {
		
		this.deplacementFond(g);
		this.deplacempenTuyau(g);
		this.score();
		g.setFont(police);
		g.drawString("" + score, 140, 50);     
		this.finDuJeu = this.collisionFlappy(); // a chaque raffrichement de l'ecran on vérifie la collision
		this.deplacementFlappy(g);
		if(this.finDuJeu) {
			g.drawString("Fin du jeu", 60, 200);
			Audio.playSound("/Audio/choc.wav");
		}
	}
	
	
	
	//Getters & Setters 
	public int getXFond() {
		return this.xFond;
	}
	
	public boolean getFinDuJeu() {
		return this.finDuJeu;
	}
	
	public void setFinDuJeu(boolean b) {
		this.finDuJeu = b;
	}
	
	//we need this for class Chrono
	public void decrXFond() {
		this.xFond --;
	}
}
