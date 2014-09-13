package engineextend.components;

import engineextend.components.views.GL_ItemView;
import geivcore.UESI;

public class GL_ListItem extends GL_Component
{
	GL_List father;
	GL_List sublist;
	GL_ItemView itemView;
	public GL_ListItem(UESI UES,GL_ItemView itemView) 
	{
		super(UES,itemView);
		this.itemView = itemView;
	}
	public void onSelect()
	{
		itemView.onSelect();
	}//�������ָ��ÿؼ���Ч��仯
	public void offSelect()
	{
		itemView.offSelect();
	};//��������뿪ָ��ÿؼ���Ч��仯
	public String getInfo()
	{
		return itemView.getViewInfo();
	}
	public void setSubList(GL_List subList)
	{
		sublist = subList;
	}
	public GL_List getSubList()
	{
		return sublist;
	}
	public boolean hasSubList()
	{
		return sublist != null;
	}
	public void setFather(GL_List father){
		this.father = father;
	}
	public GL_List getFather(){
		return father;
	}
	public void activite(){}
}
