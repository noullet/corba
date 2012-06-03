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

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import ui.LoginDialog;
import ui.MainFrame;
import utils.ClientUtils;

public class UserManager {

	private WorldManager worldManager;
	private User user;
	private Room room;
	private MainFrame mainFrame;

	public UserManager(WorldManager worldManager) {
		this.worldManager = worldManager;
	}

	public void start() {
		initializeMainFrame();
		LoginDialog loginDialog = new LoginDialog(this.mainFrame);
		loginDialog.setVisible(true);

	}

	public void login(String login, String password) {
		UserService service = null;
		try {
			service = getUserServiceFromPoa(Client.getRootpoa(), new UserServiceImpl());
		} catch (Exception e) {
			e.printStackTrace();
		}
		LoginDTO loginDTO = worldManager.login(login, password, service);
		if (loginDTO != null) {
			this.user = loginDTO.user;
			this.room = loginDTO.room;
			ClientUtils.printMessages(loginDTO.pendingMessages);
			mainFrame.setVisible(true);
			mainFrame.updateListConnected(room.loginList());
			mainFrame.newConnection(user.login, room.name());
		} else {
			LoginDialog loginDialog = new LoginDialog(this.mainFrame);
			loginDialog.setVisible(true);
		}
	}

	public void changeMood(UserMood mood) {
		user = room.changeMood(user, mood);
		mainFrame.newMood(user.login, mood);
	}

	public void changeSex(UserSex sex) {
		user = room.changeSex(user, sex);
		mainFrame.newSex(user.login, sex);
	}

	public void changeSize(UserSize size) {
		user = room.changeSize(user, size);
		mainFrame.newSize(user.login, size);
	}

	public void changePassword(String password) {
		room.changePassword(user, password);
	}

	public void changeRoom(Orientation orientation) {
		Room oldRoom = room;
		room = worldManager.changeRoom(room, user, orientation);
		if (!oldRoom.equals(room)) {
			mainFrame.clearChatArea();
			mainFrame.newConnection(user.login, room.name());
			mainFrame.updateListConnected(room.loginList());
		}
	}

	public void sendBroadCastMessage(String content) {
		Message message = new Message();
		message.content = content;
		message.sender = user.login;
		message.receiver = "";
		message.type = MessageType.BROADCAST;
		mainFrame.newMessage(user.login, content);
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
		mainFrame.newSendSingleMessage(message.receiver, newContent.toString());
		room.sendMessage(message);
	}

	public void notifyMessage(Message message) {
		if (message.type == MessageType.BROADCAST) {
			mainFrame.newMessage(message.sender, message.content);
		} else {
			mainFrame.newSingleMessage(message.sender, message.content);
		}

	}

	public void notifyConnection(User user) {
		if (user.login.equals(this.user.login)) {
			mainFrame.clearChatArea();
		}
		mainFrame.newConnection(user.login, room.name());
		mainFrame.updateListConnected(room.loginList());
	}

	public void notifyChangeSize(User user, UserSize size) {
		mainFrame.newSize(user.login, size);
	}

	public void notifyChangeSex(User user, UserSex sex) {
		mainFrame.newSex(user.login, sex);
	}

	public void notifyChangeMood(User user, UserMood mood) {
		mainFrame.newMood(user.login, mood);
	}

	public void notifyLogout(User user) {
		mainFrame.newLogout(user.login);
		mainFrame.updateListConnected(room.loginList());
	}

	private void initializeMainFrame() {
		this.mainFrame = new MainFrame();
		this.mainFrame.initialize(this);
		this.mainFrame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent arg0) {

			}

			@Override
			public void windowIconified(WindowEvent arg0) {

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {

			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				worldManager.logout(user, room);
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent arg0) {

			}

			@Override
			public void windowActivated(WindowEvent arg0) {

			}
		});

	}

}
