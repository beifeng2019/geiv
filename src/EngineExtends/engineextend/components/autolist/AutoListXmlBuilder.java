package engineextend.components.autolist;

import engineextend.components.GL_List;
import engineextend.components.GL_ListItem;
import geivcore.UESI;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class AutoListXmlBuilder {
	AutoList auto;
	UESI UES;
	public AutoListXmlBuilder(UESI UES,AutoList auto){
		this.auto = auto;
		this.UES = UES;
	}
	public void startBuilder(String xmlFilePath){
		File xml = new File(xmlFilePath);
		if (xml.exists()) {
			SAXBuilder builder = new SAXBuilder();
			Document doc;
			try {
				doc = builder.build(xml);
				Element root = doc.getRootElement();
				auto.mapRoot(autoBuild(root));
			} catch (JDOMException | IOException e) {
				System.out.println("Action Xml IO Error!");
			}
		}
	}
	private String autoBuild(Element listEle){
		//创建List
		String name = listEle.getAttributeValue("name");
		String listPackage = listEle.getAttributeValue("class");
		List<Element> paraElList = listEle.getChildren("param");
		String[] params = new String[paraElList.size()];
		for(int i = 0;i < params.length;i++){
			params[i] = paraElList.get(i).getValue();
		}
		GL_List list = GLAutoListGenerator.reflactionList(UES, listPackage,params);
		//创建list下的item
		String itemPackage = listEle.getAttributeValue("itemclass");
		List<Element> itemList = listEle.getChildren("item");
		for(Element item:itemList){
			List<Element> itemParam = item.getChildren("param");
			String[] itemParamValues = new String[itemParam.size()];
			for(int i = 0;i < itemParamValues.length;i++){
				itemParamValues[i] = itemParam.get(i).getValue();
			}
			GL_ListItem glItem = GLAutoListGenerator.reflactionListItem(UES, itemPackage,itemParamValues);
			
			//对于item下有list的节点：如果存在了同名list，则直接检索list并关联，否则递归list并关联。
			Element sublistEle = item.getChild("list");
			if(sublistEle != null){
				GL_List sublist = auto.getGLlist(sublistEle.getAttributeValue("name"));
				if(sublist == null){
					sublist = auto.getGLlist(autoBuild(sublistEle));
				}
				glItem.setSubList(sublist);
			}
			list.addItem(glItem);
		}
		list.positionItem();
		//将该list关联其名称。
		auto.mapGLlist(name, list);
		return name;
	}
}
