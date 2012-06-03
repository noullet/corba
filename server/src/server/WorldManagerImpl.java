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

import java.util.List;

import utils.ServerUtils;

import com.google.common.collect.Table;

import dao.MessageDao;
import dao.RoomDao;
import dao.UserDao;

public class WorldManagerImpl extends WorldManagerPOA {

	private Table<Integer, Integer, RoomImpl> rooms;

	public WorldManagerImpl() {
		rooms = RoomDao.findAllRooms();
	}

	public void shutdown() {
		Server.getOrb().shutdown(false);
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
		// Connect the user to his room
		// TODO récupérer la room en base
		RoomImpl roomToConnect = rooms.get(0, 0);
		Room room = getRoomFromPoa(Server.getRootpoa(), roomToConnect);
		roomToConnect.login(user, userService);
		// Retrieve and delete the user's pending messages
		List<Message> userMessages = MessageDao.getAllMessagesForUser(user);
		MessageDao.deleteAllMessagesForUser(user);
		// Return the complete DTO
		return new LoginDTO(user, room, userMessages.toArray(new Message[userMessages.size()]));
	}

	@Override
	public Admin loginAdmin(String login, String password) {
		return null;
	}

	@Override
	public Room changeRoom(Room oldRoom, User user, Orientation orientation) {
		RoomImpl oldRoomImpl = getImplFromRoom(Server.getRootpoa(), oldRoom);
		int[] coordinates = ServerUtils.getNewCoordinates(oldRoomImpl.getX(), oldRoomImpl.getY(), orientation);
		RoomImpl newRoomImpl = rooms.get(coordinates[0], coordinates[1]);
		if (newRoomImpl == null) {
			System.out.println("No room found");
			return oldRoom;
		} else {
			UserDao.setRoom(user, newRoomImpl);
			UserService userService = oldRoomImpl.getUserService(user.login);
			oldRoomImpl.logout(user);
			newRoomImpl.login(user, userService);
			System.out.println("User " + user.login + " change room " + oldRoomImpl.name() + " to "
					+ newRoomImpl.name());
			return getRoomFromPoa(Server.getRootpoa(), newRoomImpl);
		}
	}

	@Override
	public void logout(User user, Room room) {
		RoomImpl roomImpl = getImplFromRoom(Server.getRootpoa(), room);
		roomImpl.logout(user);
		System.out.println("User " + user.login + " logged out");
	}
}
