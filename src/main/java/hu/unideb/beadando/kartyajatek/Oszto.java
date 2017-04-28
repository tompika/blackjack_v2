package hu.unideb.beadando.kartyajatek;

import java.util.ArrayList;
import java.util.List;

public class Oszto {

	private List<Card> cards;
	private int egyenleg;
	private String nev;

	
	public Oszto(){
		this.egyenleg = 0;
		this.cards = new ArrayList<Card>();
		this.nev = "Oszto";
	}
	
	
	
	public List<Card> getCards() {
		return cards;
	}

	public int getEgyenleg() {
		return egyenleg;
	}
	public void setEgyenleg(int egyenleg) {
		this.egyenleg = egyenleg;
	}
	public String getNev() {
		return nev;
	}
	public void setNev(String nev) {
		this.nev = nev;
	}
	
	
	
}
