package dao;

import static generated.tables.User.USER;
import static server.Server.getDb;
import generated.tables.records.UserRecord;
import interfaces.User;
import interfaces.UserMood;
import interfaces.UserSex;
import interfaces.UserSize;

import java.util.List;

public class UserDao {

	public static User findByLoginAndPassword(String login, String password) {
		User user = null;
		List<UserRecord> userRecords = getDb().selectFrom(USER).where(USER.LOGIN.equal(login))
				.and(USER.PASSWORD.equal(password)).fetch();
		if (userRecords.size() == 1) {
			user = getUserFromUserRecord(userRecords.get(0));
		}
		return user;
	}

	public void setPassword(User user, String password) {

	}

	public void setMood(User user, UserMood mood) {

	}

	public void setSex(User user, UserSex sex) {

	}

	public void setSize(User user, UserSize size) {

	}

	private static User getUserFromUserRecord(UserRecord userRecord) {
		return new User(userRecord.getLogin(), UserSize.from_int(userRecord.getSize()), UserMood.from_int(userRecord
				.getMood()), UserSex.from_int(userRecord.getSex()));
	}
}
