package client;

import static client.ClientUtils.getWorldManager;
import interfaces.WorldManager;

public class Client {

	public static void main(String[] args) {
		try {
			WorldManager worldManager = getWorldManager(args);
			UserManager userManager = ClientUtils.getUserManager(worldManager);
			userManager.run();

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

	}
}
