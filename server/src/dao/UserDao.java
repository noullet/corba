package dao;

import static generated.tables.User.USER;
import static server.Server.getDb;
import static utils.ServerUtils.byteToBoolean;
import generated.tables.records.RoomRecord;
import generated.tables.records.UserRecord;
import interfaces.User;
import interfaces.UserMood;
import interfaces.UserSex;
import interfaces.UserSize;

import java.util.List;

import server.RoomImpl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class UserDao {

	public static int getNbRoom() {
		return getDb().selectCount().from(USER).fetchOne(0, Integer.class);
	}

	public static User getUserFromLoginAndPassword(String login, String password) {
		User user = null;
		UserRecord userRecord = getDb().selectFrom(USER).where(USER.LOGIN.equal(login))
				.and(USER.PASSWORD.equal(password)).fetchAny();
		if (userRecord != null) {
			user = getUserFromRecord(userRecord);
		}
		return user;
	}

	public static boolean isAdmin(String login, String password) {
		User user = getUserFromLoginAndPassword(login, password);
		if (user != null) {
			return user.isAdmin;
		} else {
			return false;
		}
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
		boolean isAdmin = byteToBoolean(userRecord.getAdmin());
		return new User(userRecord.getLogin(), UserSize.from_int(userRecord.getSize()), UserMood.from_int(userRecord
				.getMood()), UserSex.from_int(userRecord.getSex()), isAdmin);
	}

	public static List<User> getUsersFromRecords(List<UserRecord> userRecords) {
		return Lists.transform(userRecords, new Function<UserRecord, User>() {
			public User apply(UserRecord userRecord) {
				return getUserFromRecord(userRecord);
			}
		});
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

	public static List<User> getAllUsers() {
		List<UserRecord> userRecords = getDb().selectFrom(USER).orderBy(USER.LOGIN).fetch();
		return getUsersFromRecords(userRecords);
	}

	public static void deleteAllUsers() {
		getDb().delete(USER).execute();
	}

	public static void createInitUsers() {
		int firstRoomId = RoomDao.getIdFromCoordinates(0, 0);
		// Create sample users
		getDb().insertInto(USER, USER.LOGIN, USER.PASSWORD, USER.ROOM).values("a", "a", firstRoomId)
				.values("b", "b", firstRoomId).values("c", "c", firstRoomId).execute();
		getDb().insertInto(USER, USER.LOGIN, USER.PASSWORD, USER.ROOM, USER.ADMIN)
				.values("admin", "admin", firstRoomId, (new Integer(1)).byteValue()).execute();
	}
}
