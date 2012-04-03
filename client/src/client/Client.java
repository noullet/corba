package client;

import interfaces.LoginDTO;
import interfaces.Room;
import interfaces.User;
import interfaces.WorldManager;

public class Client {

	private WorldManager worldManager;
	private User user;
	private Room room;

	public void run(WorldManager worldManager) {
		this.worldManager = worldManager;
		login();
	}

	private void login() {
		LoginDTO loginDTO = worldManager.login("Toto", "password", null);
		this.user = loginDTO.user;
		this.room = loginDTO.room;
	}

}
