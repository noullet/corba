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

	public static User getUserFromLoginAndPassword(String login, String password) {
		User user = null;
		UserRecord userRecord = getDb().selectFrom(USER).where(USER.LOGIN.equal(login))
				.and(USER.PASSWORD.equal(password)).fetchAny();
		if (userRecord != null) {
			user = getUserFromRecord(userRecord);
		}
		return user;
	}

	public static int getIdFromLogin(String login) {
		return getRecordFromLogin(login).getId();
	}

	public static UserRecord getRecordFromLogin(String login) {
		return getDb().selectFrom(USER).where(USER.LOGIN.equal(login)).fetchOne();
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
		int roomId = RoomDao.getIdFromCoordinates(room.getX(), room.getY());
		getDb().update(USER).set(USER.ROOM, roomId).where(USER.LOGIN.equal(user.login)).execute();
	}

	public static User getUserFromRecord(UserRecord userRecord) {
		int adminIntValue = new Integer(userRecord.getAdmin());
		boolean isAdmin = (adminIntValue != 0);
		return new User(userRecord.getLogin(), UserSize.from_int(userRecord.getSize()), UserMood.from_int(userRecord
				.getMood()), UserSex.from_int(userRecord.getSex()), isAdmin);
	}

	public static int[] getRoomCoordinatesFromUser(User user) {
		RoomRecord roomRecord = getRecordFromLogin(user.login).fetchRoom();
		int[] coordinates = { roomRecord.getX(), roomRecord.getY() };
		return coordinates;
	}

	public static void saveUser(User user, String password, RoomImpl room) {
		int roomId = RoomDao.getIdFromCoordinates(room.getX(), room.getY());
		getDb().insertInto(USER, USER.LOGIN, USER.SIZE, USER.MOOD, USER.SEX, USER.PASSWORD, USER.ROOM)
				.values(user.login, user.size.value(), user.mood.value(), user.sex.value(), password, roomId).execute();
	}
}
