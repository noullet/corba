package utils;

import interfaces.Orientation;
import interfaces.Room;
import interfaces.RoomHelper;
import interfaces.WorldManager;
import interfaces.WorldManagerHelper;

import java.sql.Connection;
import java.sql.DriverManager;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

import server.RoomImpl;
import server.WorldManagerImpl;
import dao.MessageDao;
import dao.RoomDao;
import dao.UserDao;

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
		int[] coordinates = { x, y };
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

	public static String generateRandomPassword() {
		return "password";
	}

	public static boolean byteToBoolean(byte byteValue) {
		int intValue = new Integer(byteValue);
		return (intValue != 0);
	}

	public static byte booleanToByte(boolean boolValue) {
		int intValue = boolValue ? 1 : 0;
		return new Integer(intValue).byteValue();
	}

	public static void initializeDbIfEmpty() {
		if (RoomDao.getNbRoom() == 0 && UserDao.getNbRoom() == 0) {
			initializeDb();
		}
	}

	// Need the DB to be empty
	public static void initializeDb() {
		RoomDao.createInitRooms();
		UserDao.createInitUsers();
	}

	public static void reinitializeDb() {
		MessageDao.deleteAllMessages();
		UserDao.deleteAllUsers();
		RoomDao.deleteAllRooms();
		initializeDb();
	}
}
