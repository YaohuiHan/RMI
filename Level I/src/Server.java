import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.net.InetAddress;
import java.util.Scanner;
	
public class Server {
    public Server() {}
    public static void main(String args[]) {
		int port = Integer.parseInt(args[0]);
		Scanner sc = new Scanner(System.in);
		try {
			Impl robj = new Impl();
			InterRL stub = (InterRL) UnicastRemoteObject.exportObject(robj,port);

			LocateRegistry.createRegistry(port);    
		    Registry registry = LocateRegistry.getRegistry(port);
			registry.bind("RL_Server", stub);
			System.out.print("RL_Server is ready to listen on ");
            System.out.println(InetAddress.getLocalHost().getHostName()+" at port " + port);
		} catch (Exception e) {
			System.err.println("Server exception thrown: " + e.toString());
			e.printStackTrace();
			sc.next();
		}
    }
}