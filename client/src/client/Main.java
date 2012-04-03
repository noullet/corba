package client;

import interfaces.WorldManager;
import interfaces.WorldManagerHelper;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;

public class Main {

	public static void main(String[] args) {
		try {
			// create and initialize the ORB
			ORB orb = ORB.init(args, null);
			// get the root naming context
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContext ncRef = NamingContextHelper.narrow(objRef);
			// resolve the Object Reference in Naming
			NameComponent nc = new NameComponent("Server", "");
			NameComponent path[] = { nc };
			WorldManager worldManagerRef = WorldManagerHelper.narrow(ncRef.resolve(path));
			// Run the client
			Client client = new Client();
			client.run(worldManagerRef);

		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
	}
}
