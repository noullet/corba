package client;

import static utils.ClientUtils.getWorldManager;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManager;

import utils.ClientUtils;

import interfaces.WorldManager;

public class Client {

	private static UserManager userManager;
	private static ORB orb;

	public static void main(String[] args) {
		try {
			orb = ClientUtils.getOrb(args);
			org.omg.CORBA.Object obj = orb.resolve_initial_references("RootPOA");
			POA rootpoa = POAHelper.narrow(obj);
			POAManager manager = rootpoa.the_POAManager();
			manager.activate();
			WorldManager worldManager = getWorldManager(args, orb);
			userManager = new UserManager(worldManager);
			userManager.run();
			orb.run();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

	}

	public static UserManager getUserManager() {
		return userManager;
	}
	
	public static ORB getOrbInst(){
		return orb;
	}
}
