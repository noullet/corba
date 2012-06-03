package dao;

import static generated.tables.Room.ROOM;
import static server.Server.getDb;
import generated.tables.records.RoomRecord;

import java.util.List;

import server.RoomImpl;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class RoomDao {

	public static int getIdFromCoordinates(int x, int y) {
		return getDb().selectFrom(ROOM).where(ROOM.X.equal(x)).and(ROOM.Y.equal(y)).fetchOne().getId();
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

}
