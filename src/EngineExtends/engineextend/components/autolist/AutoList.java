package engineextend.components.autolist;

import engineextend.components.GL_List;
import engineextend.components.GL_ListItem;
import geivcore.KeyFactor;
import geivcore.KeyListener;
import geivcore.UESI;

import java.awt.event.KeyEvent;
import java.util.Hashtable;

public abstract class AutoList implements KeyListener{
	protected UESI UES;
	Hashtable<String,GL_List> listMap;
	GL_List root = null;
	AutoListXmlBuilder builder;
	public AutoList(UESI UES,String xmlMapping){
		this.UES = UES;
		listMap = new Hashtable<>();
		builder = new AutoListXmlBuilder(UES,this);
		builder.startBuilder(xmlMapping);
	}

	public void enableRoot(){
		if(root != null){
			UES.pushKeyBoardIO(root);
			root.show();
		}
	}
	public boolean hasRoot(){
		return root == null;
	}
	public GL_List getRoot(){
		return root;
	}
	public void mapGLlist(String listName,GL_List list){
		listMap.put(listName, list);
		list.setKeyListener(this);
	}
	public void mapRoot(String rootListName){
		root = listMap.get(rootListName);
	}
	public boolean hasGLlist(String listName){
		return listMap.containsKey(listName);
	}
	public GL_List getGLlist(String listName){
		return listMap.get(listName);
	}
	@Override
	public void doKeyBord(KeyFactor whom, int keyCode, boolean ispressed) {
		if(ispressed){
			switch(keyCode){
				case KeyEvent.VK_Z:{
					if(whom instanceof GL_List){
						GL_List list = (GL_List)whom;
						GL_ListItem item = list.getCurrentSelected();
						if(item != null){
							if(item.hasSubList()){
								GL_List subList = item.getSubList();
								subList.baseList = item.getFather();
								UES.pushKeyBoardIO(subList);
								list.hide();
								subList.show();
							}
							item.activite();
						}
					}
				}break;
				case KeyEvent.VK_X:{
					if(whom instanceof GL_List){
						GL_List list = (GL_List)whom;
						if(!list.isBase){
							list.hide();
							UES.popKeyBoardIO();
							list.baseList.show();
						}else{
							onRootExit();
						}
					}
				}break;
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_UP:{
					if(whom instanceof GL_List){
						GL_List list = (GL_List)whom;
						list.selectPrevious();
					}
				}break;
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_DOWN:{
					if(whom instanceof GL_List){
						GL_List list = (GL_List)whom;
						list.selectNext();
					}
				}break;
			}
		}
	}
	public abstract void onRootExit();
}
