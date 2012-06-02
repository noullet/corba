package client;

import interfaces.Message;
import interfaces.User;
import interfaces.UserMood;
import interfaces.UserServicePOA;
import interfaces.UserSex;
import interfaces.UserSize;

public class UserServiceImpl extends UserServicePOA {

	@Override
	public String login() {
		return null;
	}

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

	}

	@Override
	public void notifyChangeMood(User user, UserMood mood) {

	}

	@Override
	public void notifyChangeSex(User user, UserSex sex) {

	}

}
