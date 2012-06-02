package server;

import static utils.ServerUtils.getImplFromRoom;
import static utils.ServerUtils.getRoomFromPoa;
import interfaces.Admin;
import interfaces.LoginDTO;
import interfaces.Message;
import interfaces.Orientation;
import interfaces.Room;
import interfaces.User;
import interfaces.UserService;
import interfaces.WorldManagerPOA;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;

import utils.ServerUtils;

import com.google.common.collect.Table;

import dao.RoomDao;
import dao.UserDao;

public class WorldManagerImpl extends WorldManagerPOA {

	private ORB orb;
	private POA rootpoa;
	private Table<Integer, Integer, RoomImpl> rooms;

	public WorldManagerImpl(ORB orb, POA rootpoa) {
		this.orb = orb;
		this.rootpoa = rootpoa;
		rooms = RoomDao.findAllRooms();
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
			return null;
		}
		RoomImpl roomToConnect = rooms.get(0, 0);
		Room room = getRoomFromPoa(rootpoa, roomToConnect);
		roomToConnect.login(user, userService);
		return new LoginDTO(user, room, new Message[0]);
	}

	@Override
	public Admin loginAdmin(String login, String password) {
		return null;
	}

	@Override
	public Room changeRoom(Room oldRoom, User user, Orientation orientation) {
		RoomImpl oldRoomImpl = getImplFromRoom(rootpoa, oldRoom);
		int[] coordinates = ServerUtils.getNewCoordinates(oldRoomImpl.getX(), oldRoomImpl.getY(), orientation);
		RoomImpl newRoomImpl = rooms.get(coordinates[0], coordinates[1]);
		if (newRoomImpl == null) {
			System.out.println("No room found");
			return oldRoom;
		} else {
			return getRoomFromPoa(rootpoa, newRoomImpl);
		}
	}

	@Override
	public void logout(User user, Room room) {
		RoomImpl roomImpl = getImplFromRoom(rootpoa, room);
		roomImpl.logout(user);
	}
}
