package server;

import interfaces.Admin;
import interfaces.LoginDTO;
import interfaces.Message;
import interfaces.User;
import interfaces.UserMood;
import interfaces.UserService;
import interfaces.UserSex;
import interfaces.UserSize;
import interfaces.WorldManagerPOA;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.ORB;

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
		System.out.println("User " + login + " logged in");
		User user = new User(login, UserSize.MOYEN, UserMood.CONTENT, UserSex.MALE);
		return new LoginDTO(user, rooms.get(0)._this(orb), new Message[0]);
	}

	@Override
	public Admin loginAdmin(String login, String password) {
		return null;
	}
}
