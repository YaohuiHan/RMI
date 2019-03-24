import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterRL extends Remote {

    public int register(String username, String passwd) throws RemoteException;

    public int login(String username, String passwd) throws RemoteException;

}
