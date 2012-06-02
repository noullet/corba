package server;

import interfaces.Admin;
import interfaces.LoginDTO;
import interfaces.Message;
import interfaces.Orientation;
import interfaces.Room;
import interfaces.RoomHelper;
import interfaces.User;
import interfaces.UserService;
import interfaces.WorldManagerPOA;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.Servant;

import utils.ServerUtils;
import dao.UserDao;

public class WorldManagerImpl extends WorldManagerPOA {

	private ORB orb;
	private POA rootpoa;
	private List<List<RoomImpl>> rooms;
	public static final int X_ROOMS = 3;
	public static final int Y_ROOMS = 3;

	public WorldManagerImpl() {
		rooms = new ArrayList<List<RoomImpl>>();
		for (int i = 0; i < X_ROOMS; i++) {
			List<RoomImpl> roomsToAdd = new ArrayList<RoomImpl>();
			for (int j = 0; j < Y_ROOMS; j++) {
				roomsToAdd.add(new RoomImpl("Room " + i + "-" + j, i, j));
			}
			rooms.add(roomsToAdd);
		}
	}

	public void initialiseOrb(ORB orb_val) {
		orb = orb_val;
		try {
			rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		} catch (InvalidName e) {
			e.printStackTrace();
		}
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
		User user = UserDao.findByLoginAndPassword(login, password);
		if (user != null) {
			System.out.println("User " + login + " logged in");
		} else {
			System.out.println("User " + login + " cannot be logged in: wrong login or password");
		}
		Room room = getRoomFromPoa(rooms.get(0).get(0));
		return new LoginDTO(user, room, new Message[0]);
	}

	private Room getRoomFromPoa(RoomImpl roomPoa) {
		org.omg.CORBA.Object roomObject;
		try {
			roomObject = rootpoa.servant_to_reference(roomPoa);
			return RoomHelper.narrow(roomObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private RoomImpl getImplFromRoom(Room room) {
		Servant roomObject;
		try {
			roomObject = rootpoa.reference_to_servant(room);
			return (RoomImpl) roomObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Admin loginAdmin(String login, String password) {
		return null;
	}

	@Override
	public Room changeRoom(Room oldRoom, User user, Orientation orientation) {
		RoomImpl oldRoomImpl = getImplFromRoom(oldRoom);
		int[] coordinates = ServerUtils.getNewCoordinates(oldRoomImpl.getX(), oldRoomImpl.getY(), orientation);
		int x = coordinates[0];
		int y = coordinates[1];
		if (x < 0 || y < 0 || x >= rooms.size() || y >= rooms.get(x).size()) {
			System.out.println("No room found");
			return oldRoom;
		} else {
			return getRoomFromPoa(rooms.get(x).get(y));
		}
	}
}
