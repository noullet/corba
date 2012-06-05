package dao;

import static generated.tables.Room.ROOM;
import static server.Server.getDb;
import generated.tables.records.RoomRecord;
import generated.tables.records.UserRecord;
import interfaces.User;

import java.util.ArrayList;
import java.util.List;

import server.RoomImpl;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class RoomDao {

	public static int getNbRoom() {
		return getDb().selectCount().from(ROOM).fetchOne(0, Integer.class);
	}

	public static int getIdFromCoordinates(int x, int y) {
		return getRecordFromCoordinates(x, y).getId();
	}

	public static Table<Integer, Integer, RoomImpl> findAllRooms() {
		List<RoomRecord> roomRecords = getDb().selectFrom(ROOM).fetch();
		Table<Integer, Integer, RoomImpl> rooms = HashBasedTable.create();
		for (RoomRecord roomRecord : roomRecords) {
			RoomImpl room = getRoomFromRecord(roomRecord);
			rooms.put(room.getX(), room.getY(), room);
		}
		return rooms;
	}

	public static RoomImpl getRoomFromRecord(RoomRecord roomRecord) {

		return new RoomImpl(roomRecord.getName(), roomRecord.getX(), roomRecord.getY());
	}

	public static RoomRecord getRecordFromCoordinates(int x, int y) {
		return getDb().selectFrom(ROOM).where(ROOM.X.equal(x)).and(ROOM.Y.equal(y)).fetchOne();
	}

	public static List<User> getUsersFromCoordinates(int x, int y) {
		RoomRecord roomRecord = getRecordFromCoordinates(x, y);
		List<UserRecord> userRecords = roomRecord.fetchUserList();
		List<User> users = UserDao.getUsersFromRecords(userRecords);
		return users;
	}

	public static void deleteAllRooms() {
		getDb().delete(ROOM).execute();
	}

	public static void createInitRooms() {
		int size = 3;
		List<RoomRecord> roomRecords = new ArrayList<RoomRecord>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				RoomRecord roomRecord = getDb().newRecord(ROOM);
				roomRecord.setName("Room " + i + '-' + j);
				roomRecord.setX(i);
				roomRecord.setY(j);
				roomRecords.add(roomRecord);
			}
		}
		getDb().batchStore(roomRecords.toArray(new RoomRecord[roomRecords.size()])).execute();
	}
}
