package model;

public class Shift {

	public final static String ATTENDED = "Usuario atendido";
	public final static String NO_ATTENDED = "El usuario no esta";

	private User user;
	private String state;

	private char letter;
	private int num;
	private boolean current;

	public Shift(char letter, int num) {
		user = null;
		current=false;
		this.letter = letter;
		this.num = num;
	}

	public boolean isCurrent() {
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public char getLetter() {
		return letter;
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isAvailable() {
		if (user == null) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {

		if (num < 10) {
			return String.valueOf(letter) + "0" + String.valueOf(num);
		} else

			return String.valueOf(letter) + String.valueOf(num);

	}

}
