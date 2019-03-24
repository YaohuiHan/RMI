import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Impl implements InterRL {

    public int register(String username, String passwd) throws RemoteException {
		try { 
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Cloud_Computing_Assignment?useSSL=true&useUnicode=true&characterEncoding=utf8", "root","");
            Statement stmt = conn.createStatement();
			String query = "select * from User_Login where username='"+username+"'";
			ResultSet result = stmt.executeQuery(query);
			if (result.next()) {
				result.close();
				stmt.close();
				conn.close();
				return 0;
			}else{
				byte[] resultBytes = eccrypt(passwd);
				passwd = new String(resultBytes); // use MD5, Encryption process
				stmt.executeUpdate("insert User_Login values(null,'"+username+"','"+passwd+"')");
				result.close();
				stmt.close();
				conn.close();
				return 1;
			}			
		} catch (ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
        System.out.println("Sorry, the system is wrong, please inform 11611223@mail.sustc.edu.cn");
        return -1;
    }

    public int login(String username, String passwd) throws RemoteException {
        try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Cloud_Computing_Assignment?useSSL=true&useUnicode=true&characterEncoding=utf8", "root","");
            Statement stmt = conn.createStatement();
			String query = "select * from User_Login where username='"+username+"'";
			ResultSet result = stmt.executeQuery(query);
			if (result.next()) {
				String tname = result.getString("username");
				String tpasswd = result.getString("passwd");
				byte[] resultBytes = eccrypt(passwd);
				passwd = new String(resultBytes); // use MD5, Encryption process
				if(tname.equals(username)&&tpasswd.equals(passwd)){
					result.close();
					stmt.close();
					conn.close();
					return 1;
				}
			}
			result.close();
			stmt.close();
			conn.close();
			return 0;
		} catch (ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
        System.out.println("Sorry, the system is wrong, please inform 11611223@mail.sustc.edu.cn");
		return -1; // Error return (never hope)
    }

	public byte[] eccrypt(String info) throws NoSuchAlgorithmException{ // use MD5, Encryption process
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] srcBytes = info.getBytes();
		md5.update(srcBytes);
		byte[] resultBytes = md5.digest();
		return resultBytes;
	}
}