package model;

import java.util.ArrayList;

import customExceptions.EmptyFieldException;
import customExceptions.ExistUserExceptions;
import customExceptions.SingleShiftException;

public class ShiftControl {

	private ArrayList<Shift> shifts;
	private ArrayList<User> users;
	private char letters[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	public ShiftControl() {
		super();
		shifts = new ArrayList<Shift>();
		users = new ArrayList<User>();
		addShifts();
		findShift('A', 0).setCurrent(true);
	}

	public ArrayList<Shift> getShifts() {
		return shifts;
	}

	public void setShifts(ArrayList<Shift> shifts) {
		this.shifts = shifts;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public char[] getLetters() {
		return letters;
	}

	public void setLetters(char[] letters) {
		this.letters = letters;
	}

	public void addShifts() {

		for (int i = 0; i < letters.length; i++) {
			for (int j = 0; j < 100; j++) {

				Shift shift = new Shift(letters[i], j);
				shifts.add(shift);
			}
		}

	}

	public String addUser(int typeId, String id, String name, String lastName, String telephone, String adress)
			throws ExistUserExceptions, EmptyFieldException {
		String mgs = "";
		String type = "";

		if (String.valueOf(typeId) != null && id != null && name != null && lastName != null && telephone != null
				&& adress != null) {
			if (typeId == User.CEDULA_CIUDADANIA) {
				type = "Cedula de ciudadania";
			}
			if (typeId == User.TARJETA_IDENTIDAD) {
				type = "Tarjeta de identidad";
			}
			if (typeId == User.CEDULA_EXTRANJERA) {
				type = "Cedula extranjera";
			}
			if (typeId == User.PASAPORTE) {
				type = "Pasaporte";
			}
			if (typeId == User.REGISTROCIVIL) {
				type = "Registro civil";
			}

			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).getId().equals(id) && users.get(i).getTypeId() == typeId) {
					throw new ExistUserExceptions(id, type);

				}
			}
			users.add(new User(typeId, id, name, lastName, telephone, adress));
			mgs = "El usuario fue agregado con exito";
		} else {
			throw new EmptyFieldException();
		}
		return mgs;
	}

//	public Shift getCurrentShift() {
//		return currentShift;
//	}
//
//	public void setCurrentShift(Shift currentShift) {
//		restartCurrent();
//		this.currentShift.setCurrent(true);
//		this.currentShift = currentShift;
//	}

	public User findUserbyID(String id, int typeId) {

		User user = null;

		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId().equals(id) && users.get(i).getTypeId() == typeId) {
				user = users.get(i);

			}

		}
		return user;
	}

	public int getIndexOfCurrent() {
		for (int i = 0; i < shifts.size(); i++) {
			if (shifts.get(i).isCurrent()) {
				return i;
			}
		}
		return 0;
	}

	public void AssignementShift(String id, int typeId) throws SingleShiftException {

		User user = findUserbyID(id, typeId);

		if (user != null && user.getShift() == null) {
			for (int i = getIndexOfCurrent(); i < shifts.size(); i++) {
				if (shifts.get(i).isAvailable()) {
					if (user.getShift() == null) {
						user.setShift(shifts.get(i));
						shifts.get(i).setUser(user);
						i = shifts.size();
					} else {
						throw new SingleShiftException(user.getId(), user.getTypeId());
					}
				}
			}
		}

	}

	public void restartShift(Shift s) {
		if (s.getUser() != null) {
			s.getUser().setShift(null);
			s.setUser(null);
		}
	}

	public void serveShift(String state) {

		if (shifts.get(shifts.size() - 1).isCurrent()) {
			shifts.get(shifts.size() - 1).setCurrent(false);
			shifts.get(shifts.size() - 1).setState(state);
			restartShift(shifts.get(shifts.size() - 1));
			shifts.get(0).setCurrent(true);
		} else {
			for (int i = 0; i < shifts.size(); i++) {
				if (shifts.get(i).isCurrent()) {
					shifts.get(i + 1).setCurrent(true);
					shifts.get(i).setCurrent(false);
					shifts.get(i).setState(state);
					restartShift(shifts.get(i));
					i = shifts.size();
				}
			}

		}
	}

	public String showCurrentShift() {

		String current = "";

		if (shifts.isEmpty()) {
			current = " NO HAY TURNOS ACTUALES";

		} else {
			for (int i = 0; i < shifts.size(); i++) {

				if (shifts.get(i).isCurrent()) {
					current = shifts.get(i).toString();
				}
			}
		}
		return current;
	}

	public void showUser() {
		String shift = "";

		for (int i = 0; i < users.size(); i++) {

			if (users.get(i) != null) {
				if (users.get(i).getShift() != null) {
					shift = users.get(i).getShift().toString();
				} else {
					shift = "No tiene asignado un turno";
				}
				System.out.println("nombre :  " + users.get(i).getName() + " apellido :  " + users.get(i).getLastName()
						+ " identificacion :  " + users.get(i).getId() + " Turno :" + shift);

			}
		}

	}

	public Shift findShift(char letter, int num) {

		for (int i = 0; i < shifts.size(); i++) {
			if (shifts.get(i).getLetter() == letter && shifts.get(i).getNum() == num) {
				return shifts.get(i);

			}

		}
		return null;
	}

	public void restartCurrent() {
		for (int i = 0; i < shifts.size(); i++) {
			shifts.get(i).setCurrent(false);

		}
	}

}