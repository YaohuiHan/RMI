import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.lang.reflect.Method;
import java.rmi.Remote;

public class RORInvocationHandler implements InvocationHandler {
 
	private Remote o ;	
	public RORInvocationHandler(Remote o ) {
		this.o = o;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {

		return method.invoke(o,args);
	}
 
}
 