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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notifyMessage(Message message) {
		// TODO Auto-generated method stub
		ClientUtils.getUserManager().notifyMessage(message);
	}

	@Override
	public void notifyConnection(User user) {
		// TODO Auto-generated method stub
		ClientUtils.getUserManager().notifyConnection(user);

	}

	@Override
	public void notifyChangeSize(User user, UserSize size) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyChangeMood(User user, UserMood mood) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyChangeSex(User user, UserSex sex) {
		// TODO Auto-generated method stub

	}

}
