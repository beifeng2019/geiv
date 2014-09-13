package engineextend.components.autolist;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import engineextend.components.GL_List;
import engineextend.components.GL_ListItem;
import geivcore.UESI;

public class GLAutoListGenerator {
	public static GL_List reflactionList(UESI UES, String packageName,
			String... param) {
		Class<?> cl;
		try {
			cl = Class.forName(packageName);
			if(!(GL_List.class.isAssignableFrom(cl))){
				System.out.println("[AutoListG]Error in Type:" + packageName);
				return null;
			}
			return (GL_List)reflaction(UES,cl,param);
		} catch (ClassNotFoundException e) {
			System.out.println("[AutoListG]Error in Load:"+packageName);
		}
		return null;
	}
	public static GL_ListItem reflactionListItem(UESI UES, String packageName,
			String... param) {
		Class<?> cl;
		try {
			cl = Class.forName(packageName);
			if(!(GL_ListItem.class.isAssignableFrom(cl))){
				System.out.println("[AutoListG]Error in Type:" + packageName);
				return null;
			}
			return (GL_ListItem)reflaction(UES,cl,param);
		} catch (ClassNotFoundException e) {
			System.out.println("[AutoListG]Error in Load:"+packageName);
		}
		return null;
	}
	public static Object reflaction(UESI UES, Class<?> packageClass,
			String... param) {
		try {
			Class<?> cl = packageClass;
			Class<?>[] conParamsClass = new Class<?>[param.length + 1];
			Object[] conParams = new Object[param.length + 1];
			conParams[0] = UES;
			for (int i = 1; i < conParams.length; i++) {
				conParams[i] = param[i - 1];
			}
			conParamsClass[0] = UESI.class;
			for (int i = 1; i < conParamsClass.length; i++) {
				conParamsClass[i] = String.class;
			}
			Constructor<?> c = cl.getConstructor(conParamsClass);
			return c.newInstance(conParams);
		} catch ( NoSuchMethodException
				| SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			System.out.println("[AutoListG]Error in Loading:" + packageClass);
		}
		return null;
	}
}
