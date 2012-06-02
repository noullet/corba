package server;

import generated.VworldFactory;

import java.sql.Connection;
import java.sql.SQLException;

import org.omg.CORBA.ORB;

import utils.ServerUtils;

public class Server {

	private static ORB orb;
	private static VworldFactory db;
	private static final boolean SHOULD_INITIALIZE_DB = false;

	public static void main(String[] args) {
		Connection connection = null;
		try {
			orb = ServerUtils.getOrb(args);
			connection = ServerUtils.getConnection();
			db = new VworldFactory(connection);
			if (SHOULD_INITIALIZE_DB) {
				ServerUtils.initializeDB(db);
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

	public static ORB getOrb() {
		return orb;
	}

	public static VworldFactory getDb() {
		return db;
	}
}
