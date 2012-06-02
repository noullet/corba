package server;

import static utils.ServerUtils.getConnection;
import static utils.ServerUtils.getOrb;
import static utils.ServerUtils.initializeDB;
import generated.VworldFactory;

import java.sql.Connection;
import java.sql.SQLException;

import org.omg.CORBA.ORB;

public class Server {

	public static ORB orb;
	public static VworldFactory db;
	private static final boolean shouldInitializeDB = true;

	public static void main(String[] args) {
		Connection connection = null;
		try {
			orb = getOrb(args);
			connection = getConnection();
			db = new VworldFactory(connection);
			if (shouldInitializeDB) {
				initializeDB(db);
			}
			System.out.println("Server ready and waiting ...");
			orb.run();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Server exiting ...");
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
