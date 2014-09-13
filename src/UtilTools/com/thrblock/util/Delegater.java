package com.thrblock.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
/**Delegater类使用RTTI及反射实现Java下的委托机制
 * @author 三向板砖
 * */
public class Delegater {
	static int register = Integer.MIN_VALUE;//ID分配变量
	Hashtable<Integer,DelegateNode> nodeTable;//管理ID与对应委托的容器
	public Delegater()
	{
		nodeTable = new Hashtable<Integer,DelegateNode>();
	}
	//添加静态方法委托
	public synchronized int addFunctionDelegate(Class<?> srcClass,String methodName,Object... params)
	{
		Class<?>[] paramTypes = getParamTypes(params);
		Method refMethod;
		if((refMethod = getDstMethod(srcClass,methodName,paramTypes)) != null)
		{
			register++;
			nodeTable.put(register,new DelegateNode(refMethod, params));
			return register;
		}
		else
		{
			return -1;
		}
	}
	//添加动态方法委托
	public synchronized int addFunctionDelegate(Object srcObj,String methodName,Object... params)
	{
		Class<?>[] paramTypes = getParamTypes(params);
		Method refMethod;
		if((refMethod = getDstMethod(srcObj.getClass(),methodName,paramTypes)) != null)
		{
			register++;
			nodeTable.put(register,new DelegateNode(srcObj,refMethod, params));
			return register;
		}
		else
		{
			return -1;
		}
	}
	//删除一个方法委托
	public synchronized boolean removeMethod(int registerID)
	{
		if(nodeTable.containsKey(registerID))
		{
			nodeTable.remove(registerID);
			return true;
		}
		return false;
	}
	//无序地执行委托方法
	public synchronized void invokeAllMethod()
	{
		for(DelegateNode node:nodeTable.values())
		{
			node.invokeMethod();
		}
	}
	//将参数表转化为参数类型表
	private Class<?>[] getParamTypes(Object[] params)
	{
		Class<?>[] paramTypes = new Class<?>[params.length];
		for(int i = 0;i < params.length;i++)
		{
			paramTypes[i] = params[i].getClass();
		}
		return paramTypes;
	}
	//根据Class类实例、方法名、参数类型表获得一个Method实例
	private Method getDstMethod(Class<?> srcClass,String methodName,Class<?>[] paramTypes)
	{
		Method result = null;
		try {
			result = srcClass.getMethod(methodName, paramTypes);
			if(result.getReturnType() != void.class)
			{
				System.out.println("Warning,Method:"+methodName+" has a return value!");
			}
		} catch (NoSuchMethodException | SecurityException e) {
			System.out.println("Can Not Found Method:"+methodName+",ensure it's exist and visible!");
		}
		return result;
	}
}
class DelegateNode
{
	Object srcObj;
	Method refMethod;
	Object[] params;
	public DelegateNode(Method refMethod,Object[] params)
	{
		this.refMethod = refMethod;
		this.params = params;
	}
	public DelegateNode(Object srcObj,Method refMethod,Object[] params)
	{
		this.srcObj = srcObj;
		this.refMethod = refMethod;
		this.params = params;
	}
	public void invokeMethod()
	{
		try {
			refMethod.invoke(srcObj,params);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			System.out.println("Method:"+refMethod.toString()+" invoke fail!");
		}
	}
}
