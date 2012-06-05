package server;

import interfaces.Message;
import interfaces.MessageType;
import interfaces.RoomPOA;
import interfaces.User;
import interfaces.UserMood;
import interfaces.UserService;
import interfaces.UserSex;
import interfaces.UserSize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.MessageDao;
import dao.UserDao;

public class RoomImpl extends RoomPOA {

	private String name;
	private Map<String, UserService> connectedUsers = new HashMap<String, UserService>();
	private List<User> allUsers;
	private int x;
	private int y;

	public RoomImpl(String name, int x, int y, List<User> initialUsers) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.allUsers = initialUsers;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String[] connectedUsers() {
		return connectedUsers.keySet().toArray(new String[connectedUsers.size()]);
	}

	@Override
	public User[] allUsers() {
		return allUsers.toArray(new User[allUsers.size()]);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public void sendMessage(Message message) {
		if (message.type.equals(MessageType.BROADCAST)) {
			for (String key : connectedUsers.keySet()) {
				if (!key.equals(message.sender))
					connectedUsers.get(key).notifyMessage(message);
			}
		} else {
			if (!message.receiver.equals(message.sender)) {
				UserService userService = connectedUsers.get(message.receiver);
				if (userService != null) {
					userService.notifyMessage(message);
				} else {
					MessageDao.saveMessage(message);
				}
			}
		}

	}

	@Override
	public User changeSize(User user, UserSize size) {
		user.size = size;
		UserDao.setSize(user, size);
		for (String key : connectedUsers.keySet()) {
			if (!key.equals(user.login))
				connectedUsers.get(key).notifyChangeSize(user, size);
		}
		return user;
	}

	@Override
	public User changeMood(User user, UserMood mood) {
		user.mood = mood;
		UserDao.setMood(user, mood);
		for (String key : connectedUsers.keySet()) {
			if (!key.equals(user.login))
				connectedUsers.get(key).notifyChangeMood(user, mood);
		}
		return user;
	}

	@Override
	public User changeSex(User user, UserSex sex) {
		user.sex = sex;
		UserDao.setSex(user, sex);
		for (String key : connectedUsers.keySet()) {
			if (!key.equals(user.login))
				connectedUsers.get(key).notifyChangeSex(user, sex);
		}
		return user;
	}

	public void login(User user, UserService newUserService) {
		connectedUsers.put(user.login, newUserService);
		for (String key : connectedUsers.keySet()) {
			if (!key.equals(user.login)) {
				connectedUsers.get(key).notifyConnection(user);
			}
		}
	}

	public void logout(User user) {
		connectedUsers.remove(user.login);
		for (String key : connectedUsers.keySet()) {
			connectedUsers.get(key).notifyLogout(user);
		}
	}

	public UserService getUserService(String login) {
		return connectedUsers.get(login);
	}
}