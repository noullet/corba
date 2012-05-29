package utils;

import static server.generated.tables.User.USER;
import interfaces.Orientation;
import interfaces.WorldManager;
import interfaces.WorldManagerHelper;

import java.sql.Connection;
import java.sql.DriverManager;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import server.WorldManagerImpl;
import server.generated.VworldFactory;

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

	public static Connection getConnection() throws Exception {
		Class.forName(JDBC_CLASS).newInstance();
		Connection connexion = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		return connexion;
	}

	public static ORB getOrb(String[] args) throws Exception {
		// initialisation de l’ORB et création de l’objet servant
		ORB orb = ORB.init(args, null);
		POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		rootpoa.the_POAManager().activate();
		WorldManagerImpl worldManagerImpl = new WorldManagerImpl();
		worldManagerImpl.initialiseOrb(orb);
		// enregistrement de l’objet dans le naming service
		org.omg.CORBA.Object ref = rootpoa.servant_to_reference(worldManagerImpl);
		WorldManager worldManager = WorldManagerHelper.narrow(ref);
		org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
		NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
		NameComponent path[] = ncRef.to_name(TNAMESERV_COMPONENT);
		ncRef.rebind(path, worldManager);
		return orb;
	}

	public static void initializeDB(VworldFactory db) {
		db.insertInto(USER, USER.LOGIN, USER.PASSWORD).values("a", "a").values("b", "b").values("c", "c").execute();
	}
}
