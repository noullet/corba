package server;

import interfaces.WorldManager;
import interfaces.WorldManagerHelper;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class Main {

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
			orb.run();
		} catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}
		System.out.println("HelloServer Exiting ...");
	}
}
