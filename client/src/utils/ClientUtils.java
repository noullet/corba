package utils;

import interfaces.Message;
import interfaces.Room;
import interfaces.User;
import interfaces.UserService;
import interfaces.UserServiceHelper;
import interfaces.WorldManager;
import interfaces.WorldManagerHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.PortableServer.POA;

import client.UserServiceImpl;

public class ClientUtils {

	private static final String TNAMESERV_COMPONENT = "WorldManager";

	public static final WorldManager retrieveWorldManager(ORB orb) throws Exception {
		org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
		NamingContext ncRef = NamingContextHelper.narrow(objRef);
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

	public static final void printMessages(Message[] messages) {
		System.out.println("------------------------");
		if (messages.length == 0) {
			System.out.println("No pending message");
		}
		for (Message message : messages) {
			Date date = new Date(message.timestamp);
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			String formattedDate = formatter.format(date);
			System.out.println("User : " + message.receiver);
			System.out.println(formattedDate + " - " + message.sender + ": " + message.content);
		}
	}

	public static UserService getUserServiceFromPoa(POA rootpoa, UserServiceImpl userServicePoa) {
		org.omg.CORBA.Object userServiceObject;
		try {
			userServiceObject = rootpoa.servant_to_reference(userServicePoa);
			return UserServiceHelper.narrow(userServiceObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
