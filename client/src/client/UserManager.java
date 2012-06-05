package client;

import static utils.ClientUtils.getUserServiceFromPoa;
import interfaces.LoginDTO;
import interfaces.Message;
import interfaces.MessageType;
import interfaces.Orientation;
import interfaces.Room;
import interfaces.User;
import interfaces.UserMood;
import interfaces.UserService;
import interfaces.UserSex;
import interfaces.UserSize;
import interfaces.WorldManager;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ui.AdminFrame;
import ui.LoginDialog;
import ui.MainFrame;
import ui.PasswordDialog;
import ui.RegisterDialog;
import utils.ClientUtils;

public class UserManager {

	private WorldManager worldManager;
	private User user;
	private Room room;
	private MainFrame mainFrame;
	private Map<String, User> allUsers;
	private Set<String> connectedUsers;
	private String adminPassword;

	public UserManager(WorldManager worldManager) {
		this.worldManager = worldManager;
	}

	public void start() {
		initializeMainFrame();
		LoginDialog loginDialog = new LoginDialog(this.mainFrame);
		loginDialog.setVisible(true);

	}

	public void login(String login, String password) {
		UserService service = getUserServiceFromPoa(Client.getRootpoa(), new UserServiceImpl());
		LoginDTO loginDTO = worldManager.login(login, password, service);
		this.user = loginDTO.user;
		enterRoom(loginDTO.room);
		if (!user.isAdmin) {
			mainFrame.hideAdmin();
		}
		mainFrame.setVisible(true);
		mainFrame.updateListConnected(connectedUsers);
		mainFrame.newLogin(user.login);
		mainFrame.showOldMessages(loginDTO.pendingMessages);
		if (this.user.isAdmin) {
			this.adminPassword = password;
		}
	}

	public void changeMood(UserMood mood) {
		user = room.changeMood(user, mood);
		allUsers.put(user.login, user);
		mainFrame.newMood(user.login, mood);
	}

	public void changeSex(UserSex sex) {
		user = room.changeSex(user, sex);
		allUsers.put(user.login, user);
		mainFrame.newSex(user.login, sex);
	}

	public void changeSize(UserSize size) {
		user = room.changeSize(user, size);
		allUsers.put(user.login, user);
		mainFrame.newSize(user.login, size);
	}

	public void changeRoom(Orientation orientation) {
		Room newRoom = worldManager.changeRoom(room, user, orientation);
		if (!newRoom.equals(room)) {
			enterRoom(newRoom);
			mainFrame.clearChatArea();
			mainFrame.newEnterRoom(user.login);
			mainFrame.updateListConnected(connectedUsers);
		}
	}

	public void sendBroadCastMessage(String content) {
		Message message = new Message();
		message.content = content;
		message.sender = user.login;
		message.receiver = "";
		message.type = MessageType.BROADCAST;
		Date date = new Date();
		message.timestamp = date.getTime();
		mainFrame.newMessage(user.login, content, ClientUtils.getDateString(date));
		room.sendMessage(message);
	}

	public void sendSingleCastMessage(String content) {
		String[] split = content.split(" ");
		StringBuffer newContent = new StringBuffer();
		for (int i = 2; i < split.length; i++) {
			newContent.append(split[i] + " ");
		}
		Message message = new Message();
		message.content = newContent.toString();
		message.sender = user.login;
		message.receiver = split[1];
		message.type = MessageType.SINGLECAST;
		Date date = new Date();
		message.timestamp = date.getTime();
		mainFrame.newSendSingleMessage(message.receiver, newContent.toString(), ClientUtils.getDateString(date));
		room.sendMessage(message);
	}

	public void notifyMessage(Message message) {
		Date date = new Date(message.timestamp);
		if (message.type == MessageType.BROADCAST) {
			mainFrame.newMessage(message.sender, message.content, ClientUtils.getDateString(date));
		} else {
			mainFrame.newSingleMessage(message.sender, message.content, ClientUtils.getDateString(date));
		}

	}

	public void notifyLogin(User user) {
		connectedUsers.add(user.login);
		mainFrame.newLogin(user.login);
		mainFrame.updateListConnected(connectedUsers);
	}

	public void notifyChangeSize(User user, UserSize size) {
		allUsers.put(user.login, user);
		mainFrame.newSize(user.login, size);
	}

	public void notifyChangeSex(User user, UserSex sex) {
		allUsers.put(user.login, user);
		mainFrame.newSex(user.login, sex);
	}

	public void notifyChangeMood(User user, UserMood mood) {
		allUsers.put(user.login, user);
		mainFrame.newMood(user.login, mood);
	}

	public void notifyLogout(User user) {
		connectedUsers.remove(user.login);
		mainFrame.newLogout(user.login);
		mainFrame.updateListConnected(connectedUsers);
	}

	public void notifyEnterRoom(User user) {
		allUsers.put(user.login, user);
		mainFrame.newEnterRoom(user.login);
	}

	public void notifyLeaveRoom(User user) {
		allUsers.remove(user.login);
		mainFrame.newLeaveRoom(user.login);
	}

	public void register() {
		RegisterDialog register = new RegisterDialog(mainFrame);
		register.setVisible(true);
	}

	public void sendRegister(String login) {
		String password = worldManager.register(login);
		PasswordDialog passwordDial = new PasswordDialog(mainFrame, password);
		passwordDial.setVisible(true);
	}

	public User getUserInRoom(String login) {
		return allUsers.get(login);
	}

	private void initializeMainFrame() {
		this.mainFrame = new MainFrame();
		this.mainFrame.initialize();
	}

	public void logout() {
		worldManager.logout(user, room);
		System.exit(0);
	}

	public void showAdminFrame() {
		AdminFrame adminFrame = new AdminFrame();
		User[] users = new User[1];
		users[0] = user;
		adminFrame.initialiseListUser(users);
		adminFrame.setVisible(true);
	}

	private void enterRoom(Room newRoom) {
		room = newRoom;
		mainFrame.setTitleFrame(room.name());
		allUsers = new HashMap<String, User>();
		for (User user : room.allUsers()) {
			allUsers.put(user.login, user);
		}
		connectedUsers = new HashSet<String>(Arrays.asList(room.connectedUsers()));
	}

	public void kick() {
		worldManager.logout(user, room);
		mainFrame.dispose();
	}

	public void adminKick(User user) {
		worldManager.adminKickUser(this.user.login, this.adminPassword, user);
		System.out.println("The admin kicked " + user.login);
	}

	public void adminGetConnectedUsers() {
		User[] connectedUsers = worldManager.adminGetConnectedUsers(user.login, adminPassword);
	}

	public void adminGetAllUsers() {
		User[] allUsers = worldManager.adminGetAllUsers(user.login, adminPassword);
	}
}
