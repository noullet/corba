package server;

import static server.generated.Tables.USER;
import interfaces.WorldManager;
import interfaces.WorldManagerHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import server.generated.VworldFactory;

public class Main {

	private static final String DB_NAME = "vworld";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";

	public static void main(String[] args) {
		try {
			// initialisation de l’ORB et création de l’objet servant
			ORB orb = ORB.init(args, null);
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
			WorldManagerImpl server = new WorldManagerImpl();
			server.setORB(orb);
			// enregistrement de l’objet dans le naming service
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(server);
			WorldManager href = WorldManagerHelper.narrow(ref);
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			NameComponent path[] = ncRef.to_name("Server");
			ncRef.rebind(path, href);
			System.out.println("Server ready and waiting ...");
			doDBStuff();
			orb.run();
		} catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}
		System.out.println("HelloServer Exiting ...");
	}

	private static void doDBStuff() throws SQLException {
		Connection conn = null;

		String url = "jdbc:mysql://localhost:3306/" + DB_NAME;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
			VworldFactory create = new VworldFactory(conn);
			create.insertInto(USER, USER.ID, USER.LOGIN, USER.PASSWORD).values(0, "John", "Lennon")
					.values(1, "Bertrand", "Pagès").values(2, "Nicolas", "Noullet").execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}
