package dao;

import static generated.tables.User.USER;
import static server.Server.getDb;
import generated.tables.records.RoomRecord;
import generated.tables.records.UserRecord;
import interfaces.User;
import interfaces.UserMood;
import interfaces.UserSex;
import interfaces.UserSize;
import server.RoomImpl;

public class UserDao {

	public static User findByLoginAndPassword(String login, String password) {
		User user = null;
		UserRecord userRecord = getDb().selectFrom(USER).where(USER.LOGIN.equal(login))
				.and(USER.PASSWORD.equal(password)).fetchAny();
		if (userRecord != null) {
			user = getUserFromUserRecord(userRecord);
		}
		return user;
	}

	public static void setPassword(User user, String password) {
		getDb().update(USER).set(USER.PASSWORD, password).where(USER.LOGIN.equal(user.login)).execute();
	}

	public static void setMood(User user, UserMood mood) {
		getDb().update(USER).set(USER.MOOD, mood.value()).where(USER.LOGIN.equal(user.login)).execute();
	}

	public static void setSex(User user, UserSex sex) {
		getDb().update(USER).set(USER.SEX, sex.value()).where(USER.LOGIN.equal(user.login)).execute();
	}

	public static void setSize(User user, UserSize size) {
		getDb().update(USER).set(USER.SIZE, size.value()).where(USER.LOGIN.equal(user.login)).execute();
	}

	public static void setRoom(User user, RoomImpl room) {
		RoomRecord roomRecord = RoomDao.getRoomRecordFromCoordinates(room.getX(), room.getY());
		getDb().update(USER).set(USER.ROOM, roomRecord.getId()).where(USER.LOGIN.equal(user.login)).execute();
	}

	public static User getUserFromUserRecord(UserRecord userRecord) {
		return new User(userRecord.getLogin(), UserSize.from_int(userRecord.getSize()), UserMood.from_int(userRecord
				.getMood()), UserSex.from_int(userRecord.getSex()));
	}
}
