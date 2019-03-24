import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static Scanner sc = new Scanner(System.in);
    private static InterRL stub = null;
    private Client() {}
    public static void main(String[] args) {
        try {
			String host = "localhost";
			int port = Integer.parseInt(args[0]);
			String ServiceName = "RL_Server";
			String InterfaceName = "InterRL";
			
            //Registry reg = LocateRegistry.getRegistry("localhost",port);
			SimpleRegistry sr = LocateSimpleRegistry.getRegistry(host, port);
            //stub = (InterRL) reg.lookup("RL_Server");
			
			if (sr != null){
			RemoteObjectRef ror = sr.lookup(ServiceName);
				if (ror != null){
					stub = (InterRL)ror.localise();
				}else{
					System.out.println("The service is bound to no remote object.");
				}
			}else{
				System.out.println("no registry found.");
			}
			
        } catch (Exception e) {
            System.err.println("Client exception thrown: " + e.toString());
            e.printStackTrace();
			System.exit(0);
        } // some things about getRegistry
		
        System.out.println("Hello, welcome!");
        System.out.println("Do you want to Login, Register or Quit? (Please enter \"L\", \"R\" or \"Q\")");
        String opt = sc.next();
        while(!opt.equals("Q")){ // Judge Login, Register or Quit
            if(opt.equals("L")){
                login();
            }else if(opt.equals("R")){
                register();
            }else{
                System.out.println("Please enter it correctly");
            }
            System.out.println("Do you want to Login, Register or Quit? (Please enter \"L\", \"R\" or \"Q\")");
            opt = sc.next();
        }
        System.out.println("bye");
        System.exit(0);
    }
    
    public static void login(){
        System.out.println("Please enter user name");
        String name = sc.next();
        System.out.println("Please enter password");
        String passwd = sc.next();
        int res = 0;
        try {
            res = stub.login(name, passwd); // Calling a remote function
        }catch(Exception e) {
            System.out.println("Remote method exception thrown: " + e.getMessage());
            System.exit(0);
        }
        if(res==1){ // successfully, then paly repeat mode game
            System.out.println("Congratulations, you have successfully entered the system.");
            System.out.println("Enter repeat mode. (Press \"Q\" to exit)");
            String re = " ";
            while(!re.equals("Q")){
                re = sc.next();
                System.out.println(re);
            }
		}else if(res==-1){ // Error return (never hope)
	        System.out.println("Sorry, the system is wrong, please inform 11611223@mail.sustc.edu.cn");
        }else{
            System.out.println("Username or password entered incorrectly");
        }
    }

    public static void register(){
        System.out.println("Please enter user name");
        String name = sc.next();
        System.out.println("Please enter password");
        String passwd = sc.next();
        int res = 0;
        try {
            res = stub.register(name, passwd); // Calling a remote function
        }catch(Exception e) {
            System.out.println("Remote method exception thrown: " + e.getMessage());
            System.exit(0);
        }
        if(res==1){
            System.out.println("Congratulations.");
        }else if(res==-1){ // Error return (never hope)
			System.out.println("Sorry, the system is wrong, please inform 11611223@mail.sustc.edu.cn");
		}else {
            System.out.println("Error, username already exists");
        }

    }

}