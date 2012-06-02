package client;

import static utils.ClientUtils.getWorldManager;
import interfaces.WorldManager;

public class Client {

	private static UserManager userManager;

	public static void main(String[] args) {
		try {
			WorldManager worldManager = getWorldManager(args);
			userManager = new UserManager(worldManager);
			userManager.run();

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

	}

	public static UserManager getUserManager() {
		return userManager;
	}
}
