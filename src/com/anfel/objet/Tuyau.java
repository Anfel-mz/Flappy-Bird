package com.anfel.objet;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Tuyau {

	//VARIABLES 
	private int largeur;
	private int hauteur;
	private int x; 
	private int y; 
	private String strImage;
	private ImageIcon icoTuyau;
	private Image imgTuyau;
	
	
	//CONSTRUCTEUR
	public Tuyau (int x, int y, String strImage) {
		this.largeur = 50;
		this.hauteur = 300;
		this.x = x;
		this.y = y;
		this.strImage = strImage;
		
		this.icoTuyau = new ImageIcon(getClass().getResource(this.strImage));
		this.imgTuyau = this.icoTuyau.getImage();
				
	}


	//GETTERS & SETTERS
	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public int getLargeur() {
		return largeur;
	}


	public int getHauteur() {
		return hauteur;
	}


	public Image getImgTuyau() {
		return imgTuyau;
	}
	
	
	
}
