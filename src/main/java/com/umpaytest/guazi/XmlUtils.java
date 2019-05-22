package com.umpaytest.guazi;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.*;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlUtils {
	
	/**
	 * <P>map����ת����xml�ַ�</P>
	 * @param map
	 * @param rootName
	 * @return
	 */
	public static String mapToXml(Map<String, String> map, String rootName)
	{
		Element root = new Element(rootName);
		if (map == null)
			return xmlToString(root);
		for (String str : map.keySet())
			root.addContent(new Element(str).setText((map.get(str) == null ? ""
					: map.get(str) + "")));
		return xmlToString(root);
	}
	
	/**
	 * <P>xml�ַ�ת����map����</P>
	 * @param xmlStr�ַ�
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> xmlToMap(String xmlStr)
	{
		
		SAXBuilder builder = new SAXBuilder();
		Map<String, String> map = new HashMap<String, String>();
		try {
			xmlStr = URLDecoder.decode(xmlStr, "UTF-8");
			Reader in = new StringReader(xmlStr);
			Document doc = builder.build(in);
			Element root = doc.getRootElement();
			List<Element> list = root.getChildren();
			for (Element e : list) 
				map.put(e.getName(), e.getText());
			return map;
		} catch (JDOMException e) {
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		} catch (Exception e) {
		}
		return map;
		
	}
	
	
	/**
	 * <P>list����ת����xml�ַ�</P>
	 * @param list
	 * @param rootName
	 * @param parentName
	 * @return
	 */
	public static String listToXml(List<Map<String, String>> list,
			String rootName, String parentName)
	{
		Element root = new Element(rootName);
		boolean flag = true;
		Element parentElement = null;
		Element child = null;
		if (list == null)
			return xmlToString(root);
		for (Map<String, String> map : list)
			if (flag) {
				flag = false;
				for (String str : map.keySet()) {
					child = new Element(str).setText(map.get(str) == null ? ""
							: (map.get(str) + ""));
					root.addContent(child);
				}
			} else {
				parentElement = new Element(parentName);
				root.addContent(parentElement);
				for (String str : map.keySet()) {
					child = new Element(str).setText(map.get(str) == null ? ""
							: (map.get(str) + ""));
					parentElement.addContent(child);
				}
			}
		return xmlToString(root);
	}
	

	/**
	 * <P>xml�ַ�ת����list����</P>
	 * @param xmlStr
	 * @return
	 */
	public static List<Map<String, String>> xmlToList(String xmlStr) 
	{
		SAXBuilder builder = new SAXBuilder();
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		boolean flag = true;
		try {
			xmlStr = URLDecoder.decode(xmlStr, "UTF-8");
			Reader in = new StringReader(xmlStr);
			Document doc = builder.build(in);
			Element root = doc.getRootElement();
			List<Element> list = root.getChildren();
			for (Element e : list) {
				if (e.getChildren().size() == 0) {
					if (flag) {
						flag = false;
						map = new HashMap<String, String>();
						resultList.add(map);
					}
					map.put(e.getName(), e.getText());
				} else {
					map = new HashMap<String, String>();
					List<Element> childrenList = e.getChildren();
					resultList.add(map);
					for (Element element : childrenList) {
						map.put(element.getName(), element.getText());
					}
				}
			}
			return resultList;
		} catch (JDOMException e) {
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		} catch (Exception e) {
		}
		return resultList;
	}
	
	/**
	 * ��Element����ת�����ִ�
	 * @param element
	 * @return
	 */
	public static String xmlToString(Element element) 
	{
		XMLOutputter output = new XMLOutputter();
		output.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
		Document doc = new Document(element);
		String str = output.outputString(doc);
		return str;
	}
	



	/**
	 * ��ɴ����ݵĽڵ�
	 * @param parentElement���ڵ�
	 * @param map
	 *            ��ݼ�
	 * @return
	 */
	public static Element createNodes(Element parentElement,
			Map<String, String> map)
	{
		String msg = "";
		Iterator<String> it = map.keySet().iterator();
		String tempStr = "";
		Element sonElement = null;
		while (it.hasNext()) {
			tempStr = it.next();
			msg = (map.get(tempStr)) == null ? "" : (map.get(tempStr) + "");
			sonElement = new Element(tempStr);
			parentElement.addContent(sonElement.setText(msg));
		}
		return parentElement;
	}

	/**
	 * ��ɲ������ݵĽڵ�
	 * @param root��ڵ�
	 * @param strArr
	 *            �ڵ��ַ�
	 */
	public static void createNodes(Element root, String[] strArr) 
	{
		Element e = null;
		for (String str : strArr) {
			e = new Element(str);
			root.addContent(e);
		}

	}
	
	
	
	/**
	 * <P>xml�ַ�ת����map����</P>
	 * @param xml·��
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String xmlFileToString(String xmlPath,String rootName)
	{
		
		SAXBuilder builder = new SAXBuilder();
		Map<String, String> map = new HashMap<String, String>();
		try {
			Document doc = builder.build(new File(xmlPath));
			Element root = doc.getRootElement();
			List<Element> list = root.getChildren();
			for (Element e : list) 
				map.put(e.getName(), e.getText());
		} catch (JDOMException e) {
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		} catch (Exception e) {
		}
		return mapToXml(map,rootName);	
	}
	
	
	public static Map<String, String> xml12ToMap(String xmlStr) 
	{
		SAXBuilder builder = new SAXBuilder();
		Map<String, String> map = new HashMap<String,String>();
		try {
			xmlStr = URLDecoder.decode(xmlStr, "UTF-8");
			Reader in = new StringReader(xmlStr);
			Document doc = builder.build(in);
			Element root = doc.getRootElement();
			List<Element> list = root.getChildren();
			for (Element e : list) {
				if (e.getChildren().size() == 0) {
					map.put(e.getName(), e.getText());
				} else {
					List<Element> childrenList = e.getChildren();
					for (Element element : childrenList) {
						String tag=element.getName();
						String id=element.getAttributeValue("id");
						String value=element.getText();
						if(id==null){
							map.put(tag,value);
						}else{
							map.put(tag+",id="+id,value);
						}
						
					}
				}
			}
			return map;
		} catch (JDOMException e) {
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		} catch (Exception e) {
		}
		return map;
	}
	
		
	static Pattern p = Pattern.compile("\\s*|\t|\r|\n");

	
	public static void main(String args[]){
		String path=System.getProperty("user.dir")+"/requestDemo.xml";
		System.out.println(path);
		String str=xmlFileToString(path,"request");
		
		String s="  \r\n "+str;
		System.out.println("s:"+s);
		
		
		Matcher m = p.matcher(s);

		String dest = m.replaceAll("");

		System.out.println("dest:"+dest);
		xmlToMap(dest);
	}
	
	
	/**
	 * DOCUMENT格式化输出保存为XML
	 * 
	 * @param doc JDOM的Document
	 * @param filePath 输出文件路径
	 * @throws Exception
	 */
	public static void doc2XML(Document doc, String filePath) throws Exception{
		Format format = Format.getCompactFormat(); 
		format.setEncoding("UTF-8"); //设置XML文件的字符为UTF-8
		format.setIndent("     ");//设置缩进 
	
        XMLOutputter outputter = new XMLOutputter(format);//定义输出 ,在元素后换行，每一层元素缩排四格 
        FileWriter writer = new FileWriter(filePath);//输出流
        outputter.output(doc, writer);
        writer.close();
	}
	
	/**
	 * 字符串转换为DOCUMENT
	 * 
	 * @param xmlStr 字符串
	 * @return doc JDOM的Document
	 * @throws Exception
	 */
	public static Document string2Doc(String xmlStr) throws Exception {
		Reader in = new StringReader(xmlStr);
		Document doc = (new SAXBuilder()).build(in);       
        return doc;
	}

	/**
	 * Document转换为字符串
	 * 
	 * @param xmlFilePath XML文件路径
	 * @return xmlStr 字符串
	 * @throws Exception
	 */
	public static String doc2String(Document doc) throws Exception {
		Format format = Format.getPrettyFormat();
		format.setEncoding("UTF-8");// 设置xml文件的字符为UTF-8，解决中文问题
		XMLOutputter xmlout = new XMLOutputter(format);
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		xmlout.output(doc, bo);
		return bo.toString();
	}

	/**
	 * XML转换为Document
	 * 
	 * @param xmlFilePath XML文件路径
	 * @return doc Document对象
	 * @throws Exception
	 */
	public static Document xml2Doc(String xmlFilePath) throws Exception {
		File file = new File(xmlFilePath);
		return (new SAXBuilder()).build(file);
	}
	
	
	
}
