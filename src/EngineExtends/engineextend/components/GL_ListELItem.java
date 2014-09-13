package engineextend.components;

import engineextend.components.views.GL_ELableItem;
import geivcore.UESI;

public class GL_ListELItem extends GL_ListItem{
	GL_ELableItem itemView;
	public GL_ListELItem(UESI UES, GL_ELableItem itemView) {
		super(UES, itemView);
		this.itemView = itemView;
	}
	public String ELNext()
	{
		return this.getInfo()+itemView.ELNext();
	}
	public String ELPrevious()
	{
		return this.getInfo()+itemView.ELPrevious();
	}
}
