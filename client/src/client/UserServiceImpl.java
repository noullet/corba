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
	public void notifyConnection(User user) {
		Client.getUserManager().notifyConnection(user);
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
}
