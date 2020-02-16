package ui;

import java.util.Scanner;

import customExceptions.EmptyFieldException;
import customExceptions.ExistUserExceptions;
import customExceptions.SingleShiftException;
import model.Shift;
import model.ShiftControl;
import model.User;

public class Main {

	static Scanner reader = new Scanner(System.in);
	static ShiftControl sc = new ShiftControl();

	public static void main(String[] args)  {

		int option = 0;
		String id;
		int typeId;

		do {
			System.out.println("");
			System.out.println(" SISTEMA DE CONTROL DE TURNOS ");
			System.out.println("");
			System.out.println("1. Asignar turno");
			System.out.println("2. Agregar usuario");
			System.out.println("3. Atender turno: ");
			System.out.println("4. Turno actual");
			System.out.println("5. Mostar lista de usuarios:");
			System.out.println("6. SALIR");
			option = Integer.parseInt(reader.nextLine());
			

			switch (option) {
			case 1:
				System.out.println("Ingrese el tipo de identificacion:" + "\n 1.Cedula de ciudadania "
						+ "\n 2.Tarjeta de identidad" + "\n 3.Registro civil" + "\n 4.Pasaporte"
						+ "\n 5.cedula Extranjera");
				typeId = Integer.parseInt(reader.nextLine());
				System.out.println("Ingrese su id  : ");
				System.out.println(" ");
				id = reader.nextLine();

				User user = sc.findUserbyID(id, typeId);
				if (user == null) {
					System.out.println(
							"El usuario no esta registrado en el sistema  por lo tanto debe agregar un usuario "
									+ "\n");
					addUserInMain();

				} else {

					try {
						sc.AssignementShift(id, typeId);
					} catch (SingleShiftException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Se asigno el turno " + user.getShift().toString() + " a: " + user.toString());

				}

				break;

			case 2:
				addUserInMain();
				break;

			case 3:
				int optionState;
				String state;
				System.out.println("TURNO ACTUAL" + "\n");
				System.out.println(sc.showCurrentShift());

				System.out.println("Ingrese : " + "\n 1. El usuario fue atendido " + "\n 2. El usuario no esta ");
				optionState = Integer.parseInt(reader.nextLine());

				if (optionState == 1) {

					state = Shift.ATTENDED;
				} else {

					state = Shift.NO_ATTENDED;
				}

				sc.serveShift(state);
				break;

			case 4:
				System.out.println("TURNO ACTUAL" + "\n");
				System.out.println(sc.showCurrentShift());
				break;
			case 5:
				sc.showUser();

			}
			
			
		} while (option != 6)
			
			;


	}

	public static void addUserInMain() {

		String id;
		int typeId = 0;
		String name;
		String lastName;
		String telephone;
		String adress;

		System.out.println("Ingrese el tipo de identificacion:" + "\n 1.Cedula de ciudadania "
				+ "\n 2.Tarjeta de identidad" + "\n 3.Registro civil" + "\n 4.Pasaporte" + "\n 5.cedula Extranjera");
		typeId = Integer.parseInt(reader.nextLine());
		System.out.println("Ingrese su id  : ");
		id = reader.nextLine();
		System.out.println("Ingrese su nombre  : ");
		name = reader.nextLine();
		System.out.println("Ingrese su apellido  : ");
		lastName = reader.nextLine();
		System.out.println("Ingrese su telefono : ");
		telephone = reader.nextLine();
		System.out.println("Ingrese su direccion : ");
		adress = reader.nextLine();

		try {
			System.out.println(sc.addUser(typeId, id, name, lastName, telephone, adress));
		} catch (ExistUserExceptions | EmptyFieldException e) {
			// TODO Auto-generated catch block
			System.err.print(e.getMessage());
		}

	}

}
