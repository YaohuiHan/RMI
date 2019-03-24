import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.net.InetAddress;
import java.util.Scanner;
	
public class Server {
    public Server() {}
    public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		String host = "localhost";
		int port = Integer.parseInt(args[0]);
		String ServiceName = "RL_Server";
		String InterfaceName = "InterRL";
		try {
			Impl robj = new Impl();
			InterRL stub = (InterRL) UnicastRemoteObject.exportObject(robj,1098);
			
			RORtbl rortbl = new RORtbl();
			
			RemoteObjectRef ror = new RemoteObjectRef(host, port, rortbl.counter, "InterRL");
			
			rortbl.addObj(ror, rortbl.counter); 
			
			LocateRegistry.createRegistry(1098);    
			SimpleRegistry sr = LocateSimpleRegistry.getRegistry(host, port);
		    //Registry registry = LocateRegistry.getRegistry(port);
			
			if (sr != null) {
				sr.rebind(ServiceName, ror);
			} else {
				System.out.println("no registry found.");
			}
			//registry.bind("RL_Server", stub);
			
			System.out.print(ServiceName + " is ready to listen on ");
            System.out.println(InetAddress.getLocalHost().getHostName()+" at port " + port);
		} catch (Exception e) {
			System.err.println("Server exception thrown: " + e.toString());
			e.printStackTrace();
			sc.next();
		}
    }
}