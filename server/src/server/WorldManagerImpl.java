package server;

import interfaces.Admin;
import interfaces.LoginDTO;
import interfaces.Message;
import interfaces.Room;
import interfaces.RoomHelper;
import interfaces.User;
import interfaces.UserMood;
import interfaces.UserService;
import interfaces.UserSex;
import interfaces.UserSize;
import interfaces.WorldManagerPOA;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

public class WorldManagerImpl extends WorldManagerPOA {

	private ORB orb;
	private List<RoomImpl> rooms = new ArrayList<RoomImpl>();

	public WorldManagerImpl() {
		this.rooms.add(new RoomImpl());
	}

	public void setORB(ORB orb_val) {
		orb = orb_val;
	}

	public void shutdown() {
		orb.shutdown(false);
	}

	@Override
	public String register(String login) {
		return null;
	}

	@Override
	public LoginDTO login(String login, String password, UserService userService) {
		User user = new User(login, UserSize.MOYEN, UserMood.CONTENT, UserSex.MALE);
		System.out.println("User " + login + " logged in");
		POA rootpoa;
		Room room = null;
		try {
			rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			org.omg.CORBA.Object roomObject = rootpoa.servant_to_reference(rooms.get(0));
			room = RoomHelper.narrow(roomObject);
		} catch (InvalidName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServantNotActive e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WrongPolicy e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new LoginDTO(user, room, new Message[0]);
	}

	@Override
	public Admin loginAdmin(String login, String password) {
		return null;
	}
}
