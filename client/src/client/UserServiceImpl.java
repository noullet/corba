package client;

import interfaces.Message;
import interfaces.User;
import interfaces.UserMood;
import interfaces.UserServicePOA;
import interfaces.UserSex;
import interfaces.UserSize;

public class UserServiceImpl extends UserServicePOA {

	@Override
	public void notifyMessage(Message message) {
		Client.getUserManager().notifyMessage(message);
	}

	@Override
	public void notifyLogin(User user) {
		Client.getUserManager().notifyLogin(user);
	}

	@Override
	public void notifyChangeSize(User user, UserSize size) {
		Client.getUserManager().notifyChangeSize(user, size);
	}

	@Override
	public void notifyChangeSex(User user, UserSex sex) {
		Client.getUserManager().notifyChangeSex(user, sex);
	}

	@Override
	public void notifyChangeMood(User user, UserMood mood) {
		Client.getUserManager().notifyChangeMood(user, mood);
	}

	@Override
	public void notifyLogout(User user) {
		Client.getUserManager().notifyLogout(user);
	}

	@Override
	public void notifyEnterRoom(User user) {
		Client.getUserManager().notifyEnterRoom(user);
	}

	@Override
	public void notifyLeaveRoom(User user) {
		Client.getUserManager().notifyLeaveRoom(user);
	}

	@Override
	public void kick() {
		Client.getUserManager().kick();
	}
}
