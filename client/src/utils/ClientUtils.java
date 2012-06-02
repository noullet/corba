package utils;

import interfaces.Room;
import interfaces.User;
import interfaces.WorldManager;
import interfaces.WorldManagerHelper;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;

public class ClientUtils {

	private static final String TNAMESERV_COMPONENT = "WorldManager";

	public static final WorldManager getWorldManager(String[] args) throws Exception {
		// create and initialize the ORB
		ORB orb = ORB.init(args, null);
		// get the root naming context
		org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
		NamingContext ncRef = NamingContextHelper.narrow(objRef);
		// resolve the Object Reference in Naming
		NameComponent nc = new NameComponent(TNAMESERV_COMPONENT, "");
		NameComponent path[] = { nc };
		WorldManager worldManager = WorldManagerHelper.narrow(ncRef.resolve(path));
		return worldManager;
	}

	public static final void printInfo(Room room, User user) {
		System.out.println("-----------------------");
		System.out.println("User : " + user.login);
		System.out.println("Room : " + room.name());
		System.out.println("-- Sex : " + user.sex.value());
		System.out.println("-- Size : " + user.size.value());
		System.out.println("-- Mood : " + user.mood.value());
	}
}
