package client;

import interfaces.LoginDTO;
import interfaces.Message;
import interfaces.MessageType;
import interfaces.Orientation;
import interfaces.Room;
import interfaces.User;
import interfaces.UserMood;
import interfaces.UserSex;
import interfaces.UserSize;
import interfaces.WorldManager;

import java.util.ArrayList;

import ui.LoginDialog;
import ui.MainFrame;

public class UserManager {

	private WorldManager worldManager;
	private User user;
	private Room room;
	private MainFrame mainFrame;

	public UserManager(WorldManager worldManager) {
		this.worldManager = worldManager;
	}

	public void run() {
		this.mainFrame = new MainFrame();
		LoginDialog loginDialog = new LoginDialog(this.mainFrame);
		loginDialog.setVisible(true);

	}

	public void loginFromFrame(String login, String password) {
		LoginDTO loginDTO = worldManager.login(login, password, null);
		this.user = loginDTO.user;
		this.room = loginDTO.room;
		Message message = new Message();
		message.content = "Client connecté";
		message.sender = this.user;
		message.type = MessageType.BROADCAST;
		this.mainFrame.setVisible(true);
		ArrayList<String> listConnected = new ArrayList<String>();
		listConnected.add(user.login);
		this.mainFrame.initializeList(listConnected, this);
		// this.room.sendMessage(message);
		scenario2();
	}

	public void scenario2() {
		this.notifyConnection(this.user);
		Message message = new Message();
		message.content = "Bonjour !";
		message.sender = this.user;
		message.type = MessageType.BROADCAST;
		this.notifyMessage(message);
		Message message2 = new Message();
		message2.content = "Ca va ?";
		message2.sender = this.user;
		message2.type = MessageType.BROADCAST;
		this.notifyMessage(message2);
		this.notifyChangeMood(user, UserMood.EFFRAYE);
		this.notifyChangeSex(user, UserSex.FEMALE);
		this.notifyChangeSize(user, UserSize.NAIN);
	}

	public void changeMood(UserMood mood) {
		this.notifyChangeMood(user, mood);
		user = room.changeMood(user, mood);

	}

	public void changeSex(UserSex sex) {
		this.notifyChangeSex(user, sex);
		user = room.changeSex(user, sex);
	}

	public void changeSize(UserSize size) {
		this.notifyChangeSize(user, size);
		user = room.changeSize(user, size);
	}

	public void changePassword(String password) {
		user = room.changePassword(user, password);
	}

	public void changeRoom(Orientation orientation) {
		room = worldManager.changeRoom(room, user, orientation);
	}

	public void sendBroadCastMessage(String content) {
		Message message = new Message();
		message.content = content;
		message.sender = user;
		message.type = MessageType.BROADCAST;
		// this.room.sendMessage(message);
		// A enlever
		this.notifyMessage(message);

	}

	public void notifyMessage(Message message) {
		this.mainFrame.newMessage(message.sender.login, message.content);
	}

	public void notifyConnection(User user) {
		this.mainFrame.newConnection(user.login);
	}

	public void notifyChangeSize(User user, UserSize userSize) {
		this.mainFrame.newSize(user.login, userSize);
	}

	public void notifyChangeSex(User user, UserSex userSex) {
		this.mainFrame.newSex(user.login, userSex);
	}

	public void notifyChangeMood(User user, UserMood userMood) {
		this.mainFrame.newMood(user.login, userMood);
	}
}
