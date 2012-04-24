package client;

import interfaces.LoginDTO;
import interfaces.Room;
import interfaces.User;
import interfaces.UserMood;
import interfaces.WorldManager;

import java.util.Scanner;

public class Client {

	private WorldManager worldManager;
	private User user;
	private Room room;

	public void run(WorldManager worldManager) {
		this.worldManager = worldManager;
		login();
		System.out.println("Login Successfull");
		room.changeMood(user, UserMood.EFFRAYE);
	}

	private void login() {
		Scanner in = new Scanner(System.in);
		System.out.print("Entrez votre login : ");
		String login = in.nextLine();
		System.out.println();
		System.out.print("Entrez votre password : ");
		String password = in.nextLine();
		LoginDTO loginDTO = worldManager.login(login, password, null);
		this.user = loginDTO.user;
		this.room = loginDTO.room;
	}

}
