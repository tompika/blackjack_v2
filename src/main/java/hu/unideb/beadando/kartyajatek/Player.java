package hu.unideb.beadando.kartyajatek;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private String name;
	private String nickname;
	private String password;
	
	private int egyenleg;
	private List<Card> cards;
	
	public Player(){
	}
	
	
	public Player(String name, String nickname, String password, int egyenleg) {
		this.name = name;
		this.nickname = nickname;
		this.password = password;
		this.egyenleg = egyenleg;
		this.cards = new ArrayList<Card>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getEgyenleg() {
		return egyenleg;
	}

	public void setEgyenleg(int egyenleg) {
		this.egyenleg = egyenleg;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void addCard(Card card) {
		this.cards.add(card);
	}
	
	public int getCardsPoint(){
		return cards.stream().mapToInt(e ->e.getValue()).sum();
	}
	
	
	
}
