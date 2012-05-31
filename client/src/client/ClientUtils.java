package client;

import interfaces.WorldManager;
import interfaces.WorldManagerHelper;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;

public class ClientUtils {

	private static final String TNAMESERV_COMPONENT = "WorldManager";

	private static UserManager userManager;
	
	public static final WorldManager getWorldManager(String[] args) throws Exception {
		// create and initialize the ORB
		ORB orb = ORB.init(args, null);
		// get the root naming context
		org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
		NamingContext ncRef = NamingContextHelper.narrow(objRef);
		// resolve the Object Reference in Naming
		NameComponent nc = new NameComponent(TNAMESERV_COMPONENT, "");
		NameComponent path[] = { nc };
		WorldManager worldManager = WorldManagerHelper.narrow(ncRef.resolve(path));
		return worldManager;
	}
	
	protected static UserManager getUserManager(){
		return userManager;
	}
	
	protected static UserManager getUserManager(WorldManager worldManager){
		userManager = new UserManager(worldManager);
		return userManager;
	}

}
