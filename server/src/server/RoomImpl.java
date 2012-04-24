package server;

import interfaces.Message;
import interfaces.Orientation;
import interfaces.Room;
import interfaces.RoomPOA;
import interfaces.User;
import interfaces.UserMood;
import interfaces.UserSex;
import interfaces.UserSize;

public class RoomImpl extends RoomPOA {

	@Override
	public User[] users() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(Message message) {
		// TODO Auto-generated method stub

	}

	@Override
	public Room changeRoom(User user, Orientation orientation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changePassword(User user, String password) {
		// TODO Auto-generated method stub

	}

	@Override
	public void changeSize(User user, UserSize size) {
		user.size = size;		
	}

	@Override
	public void changeMood(User user, UserMood mood) {
		user.mood = mood;
	}

	@Override
	public void changeSex(User user, UserSex sex) {
		user.sex = sex;
	}

}