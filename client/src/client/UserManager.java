package client;

import interfaces.LoginDTO;
import interfaces.Message;
import interfaces.MessageType;
import interfaces.Orientation;
import interfaces.Room;
import interfaces.User;
import interfaces.UserMood;
import interfaces.UserService;
import interfaces.UserServiceHelper;
import interfaces.UserSex;
import interfaces.UserSize;
import interfaces.WorldManager;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import ui.LoginDialog;
import ui.MainFrame;

public class UserManager {

	private WorldManager worldManager;
	private User user;
	private Room room;
	private MainFrame mainFrame;
	private UserServiceImpl userService;

	public UserManager(WorldManager worldManager) {
		this.worldManager = worldManager;
		userService = new UserServiceImpl();
	}

	public void run() {
		initializeMainFrame();
		LoginDialog loginDialog = new LoginDialog(this.mainFrame);
		loginDialog.setVisible(true);

	}
	
	private UserService getUserServiceFromPoa(UserServiceImpl userServicePoa) throws Exception {
		POA rootpoa = POAHelper.narrow(Client.getOrbInst().resolve_initial_references("RootPOA"));
		org.omg.CORBA.Object userServiceObject;
		try {
			userServiceObject = rootpoa.servant_to_reference(userServicePoa);
			return UserServiceHelper.narrow(userServiceObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void loginFromFrame(String login, String password) {
		UserService service = null;
		try {
			service = getUserServiceFromPoa(new UserServiceImpl());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoginDTO loginDTO = worldManager.login(login, password, service);
		if(loginDTO != null){
			this.user = loginDTO.user;
			this.room = loginDTO.room;
			Message message = new Message();
			message.content = "Client connecté";
			message.sender = this.user;
			message.type = MessageType.BROADCAST;
			mainFrame.setVisible(true);
			ArrayList<String> listConnected = new ArrayList<String>();
			listConnected.add(user.login);
			mainFrame.initializeList(listConnected, this);
			mainFrame.newConnection(user.login);
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
		user = room.changePassword(user, password);
	}

	public void changeRoom(Orientation orientation) {
		room = worldManager.changeRoom(room, user, orientation);
	}

	public void sendBroadCastMessage(String content) {
		Message message = new Message();
		message.content = content;
		message.sender = user;
		message.receiver = user;
		message.type = MessageType.BROADCAST;
		mainFrame.newMessage(user.login, content);
		room.sendMessage(message);			
	}
	
	public void notifyMessage(Message message) {
		mainFrame.newMessage(message.sender.login, message.content);
	}
	
	public void notifyConnection(User user) {
		mainFrame.newConnection(user.login);
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
	}
	
	private void initializeMainFrame(){
		this.mainFrame = new MainFrame();
		this.mainFrame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				worldManager.logout(user, room);
				System.exit(0);			
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
}
