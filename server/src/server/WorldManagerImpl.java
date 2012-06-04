package server;

import static utils.ServerUtils.generateRandomPassword;
import static utils.ServerUtils.getImplFromRoom;
import static utils.ServerUtils.getRoomFromPoa;
import interfaces.Admin;
import interfaces.LoginDTO;
import interfaces.Message;
import interfaces.Orientation;
import interfaces.Room;
import interfaces.User;
import interfaces.UserMood;
import interfaces.UserService;
import interfaces.UserSex;
import interfaces.UserSize;
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
		User user = new User(login, UserSize.MOYEN, UserMood.CONTENT, UserSex.MALE, false);
		String password = generateRandomPassword();
		RoomImpl defaultRoom = rooms.get(0, 0);
		UserDao.saveUser(user, password, defaultRoom);
		System.out.println("User " + login + " registered");
		return password;
	}

	@Override
	public LoginDTO login(String login, String password, UserService userService) {
		// TODO manage the case where the user is not found
		User user = UserDao.getUserFromLoginAndPassword(login, password);
		if (user != null) {
			System.out.println("User " + login + " logged in");
			// Connect the user to his room
			int[] coordinates = UserDao.getRoomCoordinatesFromUser(user);
			RoomImpl roomImpl = rooms.get(coordinates[0], coordinates[1]);
			roomImpl.login(user, userService);
			Room room = getRoomFromPoa(Server.getRootpoa(), roomImpl);
			// Retrieve and delete the user's pending messages
			List<Message> userMessages = MessageDao.getMessagesFromUser(user);
			MessageDao.deleteMessagesFromUser(user);
			// Return the complete DTO
			return new LoginDTO(user, room, userMessages.toArray(new Message[userMessages.size()]));
		} else {
			return null;
		}
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
			System.out.println("User " + user.login + " changed room " + oldRoomImpl.name() + " to "
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
