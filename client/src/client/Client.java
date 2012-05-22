package client;

import interfaces.LoginDTO;
import interfaces.Orientation;
import interfaces.Room;
import interfaces.User;
import interfaces.UserMood;
import interfaces.UserSex;
import interfaces.UserSize;
import interfaces.WorldManager;

import java.util.Scanner;

public class Client {

	private WorldManager worldManager;
	private User user;
	private Room room;

	public void run(WorldManager worldManager) {
		this.worldManager = worldManager;
		login();
		scenario();
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

	private void scenario() {
		System.out.println("Login Successfull");
		printInfo();
		changeMood(UserMood.EFFRAYE);
		changeSex(UserSex.FEMALE);
		changeSize(UserSize.NAIN);
		printInfo();
		changeRoom(Orientation.EAST);
		printInfo();
		changeRoom(Orientation.SOUTH);
		printInfo();
		changeRoom(Orientation.NORTH);
		printInfo();
		changeRoom(Orientation.NORTH);
		printInfo();
	}

	private void changeMood(UserMood mood) {
		user = room.changeMood(user, mood);
	}

	private void changeSex(UserSex sex) {
		user = room.changeSex(user, sex);
	}

	private void changeSize(UserSize size) {
		user = room.changeSize(user, size);
	}

	private void changePassword(String password) {
		user = room.changePassword(user, password);
	}

	private void changeRoom(Orientation orientation) {
		room = worldManager.changeRoom(room, user, orientation);
	}

	private void printInfo() {
		System.out.println("-----------------------");
		System.out.println("User : " + user.login);
		System.out.println("Room : " + room.name());
		System.out.println("-- Sex : " + user.sex.value());
		System.out.println("-- Size : " + user.size.value());
		System.out.println("-- Mood : " + user.mood.value());
	}
}
