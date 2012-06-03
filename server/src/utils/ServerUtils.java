package utils;

import static generated.tables.Room.ROOM;
import static generated.tables.User.USER;
import generated.VworldFactory;
import generated.tables.records.RoomRecord;
import interfaces.Orientation;
import interfaces.Room;
import interfaces.RoomHelper;
import interfaces.WorldManager;
import interfaces.WorldManagerHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

import server.RoomImpl;
import server.WorldManagerImpl;
import dao.RoomDao;

public class ServerUtils {

	// TNAMESERV CONFIG
	private static final String TNAMESERV_COMPONENT = "WorldManager";
	// DB CONFIG
	private static final String DB_URL = "jdbc:mysql://localhost:3306/vworld";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	private static final String JDBC_CLASS = "com.mysql.jdbc.Driver";

	public static int[] getNewCoordinates(int x, int y, Orientation orientation) {
		if (Orientation.EAST.equals(orientation)) {
			x++;
		} else if (Orientation.WEST.equals(orientation)) {
			x--;
		} else if (Orientation.SOUTH.equals(orientation)) {
			y++;
		} else if (Orientation.NORTH.equals(orientation)) {
			y--;
		}
		int[] coordinates = new int[2];
		coordinates[0] = x;
		coordinates[1] = y;
		return coordinates;
	}

	public static Connection getDbConnection() throws Exception {
		Class.forName(JDBC_CLASS).newInstance();
		Connection connexion = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		return connexion;
	}

	public static void registerWorldManager(ORB orb, POA rootpoa, WorldManagerImpl worldManagerImpl) throws Exception {
		org.omg.CORBA.Object ref = rootpoa.servant_to_reference(worldManagerImpl);
		WorldManager worldManager = WorldManagerHelper.narrow(ref);
		org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
		NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
		NameComponent path[] = ncRef.to_name(TNAMESERV_COMPONENT);
		ncRef.rebind(path, worldManager);
	}

	public static void initializeDB(VworldFactory db) {
		// Batch create rooms
		int size = 3;
		List<RoomRecord> roomRecords = new ArrayList<RoomRecord>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				RoomRecord roomRecord = db.newRecord(ROOM);
				roomRecord.setName("Room " + i + '-' + j);
				roomRecord.setX(i);
				roomRecord.setY(j);
				roomRecords.add(roomRecord);
			}
		}
		db.batchStore(roomRecords.toArray(new RoomRecord[roomRecords.size()])).execute();
		int firstRoomId = RoomDao.getIdFromCoordinates(0, 0);
		// Create sample users
		db.insertInto(USER, USER.LOGIN, USER.PASSWORD, USER.ROOM).values("a", "a", firstRoomId)
				.values("b", "b", firstRoomId).values("c", "c", firstRoomId).execute();
	}

	public static Room getRoomFromPoa(POA rootpoa, RoomImpl roomPoa) {
		org.omg.CORBA.Object roomObject;
		try {
			roomObject = rootpoa.servant_to_reference(roomPoa);
			return RoomHelper.narrow(roomObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static RoomImpl getImplFromRoom(POA rootpoa, Room room) {
		Servant roomObject;
		try {
			roomObject = rootpoa.reference_to_servant(room);
			return (RoomImpl) roomObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
