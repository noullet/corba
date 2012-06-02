package dao;

import static generated.tables.Room.ROOM;
import static server.Server.getDb;
import generated.tables.records.RoomRecord;

import java.util.List;

import server.RoomImpl;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class RoomDao {

	public static RoomRecord getRoomRecordFromCoordinates(int x, int y) {
		return getDb().selectFrom(ROOM).where(ROOM.X.equal(x)).and(ROOM.Y.equal(y)).fetchAny();
	}

	public static Table<Integer, Integer, RoomImpl> findAllRooms() {
		List<RoomRecord> roomRecords = getDb().selectFrom(ROOM).fetch();
		Table<Integer, Integer, RoomImpl> rooms = HashBasedTable.create();
		for (RoomRecord roomRecord : roomRecords) {
			RoomImpl room = getRoomImplFromRoomRecord(roomRecord);
			rooms.put(room.getX(), room.getY(), room);
		}
		return rooms;
	}

	private static RoomImpl getRoomImplFromRoomRecord(RoomRecord roomRecord) {
		return new RoomImpl(roomRecord.getName(), roomRecord.getX(), roomRecord.getY());
	}

}
