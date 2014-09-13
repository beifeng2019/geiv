package engineextend.components;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import engineextend.components.views.GL_ComponentView;
import geivcore.UESI;
import geivcore.enginedata.obj.Obj;

public class GL_List extends GL_Component
{
	public static class NullListView extends Obj implements GL_ComponentView{
		public NullListView(UESI UES) {
			super((geivcore.R)UES,UESI.UIIndex);
		}
	}
	public boolean isBase = true;
	public GL_List baseList = null;
	public List<GL_ListItem> ItemList = new ArrayList<GL_ListItem>();
	int CrtItemIndex = -1;
	ReentrantLock ListLock = new ReentrantLock();
	public GL_List(UESI UES,GL_ComponentView View) 
	{
		super(UES,View);
	}
	public void positionItem(){}
	@Override
	public void show()
	{
		super.show();
		ListLock.lock();
		for(GL_ListItem LI:ItemList)
		{
			LI.show();
		}
		ListLock.unlock();
		this.selectItem(CrtItemIndex);
	}
	@Override
	public void hide()
	{
		super.hide();
		ListLock.lock();
		for(GL_ListItem LI:ItemList)
		{
			LI.hide();
		}
		ListLock.unlock();
	}
	public void addItem(GL_ListItem LI)
	{
		addComponent(LI);
		ListLock.lock();
		ItemList.add(LI);
		LI.setFather(this);
		if(ItemList.size() == 1)
		{
			CrtItemIndex = 0;
		}
		if(LI.hasSubList()){
			LI.sublist.isBase = false;
			LI.sublist.baseList = this;
		}
		ListLock.unlock();
	}
	public void removeAllItem()
	{
		removeAllComponent();
		ListLock.lock();
		for(GL_ListItem e:ItemList)
		{
			e.destroy();
		}
		ItemList.removeAll(ItemList);
		ListLock.unlock();
		CrtItemIndex = -1;
	}
	public void removeItem(GL_ListItem LI)
	{
		removeComponent(LI);
		ListLock.lock();
		ItemList.remove(LI);
		LI.destroy();
		if(ItemList.size() == 0)
		{
			CrtItemIndex = -1;
		}
		ListLock.unlock();
	}
	public void selectPrevious()
	{
		selectItem(CrtItemIndex - 1);
	}
	public void selectNext()
	{
		selectItem(CrtItemIndex + 1);
	}
	public void selectLast() {
		selectItem(ItemList.size()-1);
	}
	public GL_ListItem getFirst()
	{
		if(ItemList.size()>0)
		{
			return ItemList.get(0);
		}
		else
		{
			return null;
		}
	}
	public GL_ListItem getLast()
	{
		if(ItemList.size()>0)
		{
			return ItemList.get(ItemList.size() - 1);
		}
		else
		{
			return null;
		}
	}
	public GL_ListItem getItemByView(String viewInfo)
	{
		GL_ListItem result = null;
		ListLock.lock();
		for(GL_ListItem LI:ItemList)
		{
			if(LI.getInfo().equals(viewInfo))
			{
				result = LI;
				break;
			}
		}
		ListLock.unlock();
		return result;
	}
	public GL_ListItem getItemByIndex(int index)
	{
		if(index >= 0&&index <= ItemList.size() - 1)
		{
			return ItemList.get(index);
		}
		else
		{
			return null;
		}
	}
	public GL_ListItem getCurrentSelected()
	{
		return getItemByIndex(this.CrtItemIndex);
	}
	public String getSelectedViewInfo()
	{
		if(CrtItemIndex == -1)
		{
			return null;
		}
		return ItemList.get(CrtItemIndex).getInfo();
	}
	public void selectItem(int ItemIndex)
	{
		if(ItemIndex<0||ItemIndex>ItemList.size()-1)
		{
			return;
		}
		ItemList.get(CrtItemIndex).offSelect();
		ItemList.get(ItemIndex).onSelect();
		
		CrtItemIndex = ItemIndex;
	}
	public int getCrtIndex()
	{
		return this.CrtItemIndex;
	}
	public boolean hasSubList()
	{
		boolean result = false;
		ListLock.lock();
		for(GL_ListItem LI:ItemList)
		{
			if(LI.hasSubList())
			{
				result = true;
				break;
			}
		}
		ListLock.unlock();
		return result;
	}
	public void setSubMenuRefance(GL_List subList,int refIndex)
	{
		GL_ListItem LI = this.getItemByIndex(refIndex);
		if(LI != null)
		{
			subList.isBase = false;
			subList.baseList = this;
			LI.setSubList(subList);
		}
	}
	public void setSubMenuRefance(GL_List subList,String viewInfo)
	{
		GL_ListItem LI = this.getItemByView(viewInfo);
		if(LI != null)
		{
			subList.isBase = false;
			subList.baseList = this;
			LI.setSubList(subList);
		}
	}
	public void setSubMenuRefance(GL_List subList,GL_ListItem represent){
		subList.isBase = false;
		subList.baseList = this;
		represent.setSubList(subList);
		
		this.addItem(represent);
	}
}
