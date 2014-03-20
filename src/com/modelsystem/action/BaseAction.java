package com.modelsystem.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.modelsystem.bean.Pager;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 所有Action类的基类
 * @Title: BaseAction.java 
 * @Description: 主要用来将数据转换成json数据传送到前台
 * @author	huangjj
 * @date 2012-10-6 上午11:13:03 
 * @version V1.0
 */
public class BaseAction extends ActionSupport {

	private static final long serialVersionUID = 6718838822334455667L;

	public static final String MESSAGE = "message";
	
	protected String id;
	protected String[] ids;
	protected String start = "0";	//分页中Ext传来的数据开始序列号
	protected String limit = "20";	//分页中Ext传来的数据每页显示条数
	protected String redirectionUrl;// 操作提示后的跳转URL,为null则返回前一页
	private Logger log = Logger.getLogger(BaseAction.class);
	protected Pager pager;

	// 获取HttpSession
	public HttpSession getHttpSession(){
		return getRequest().getSession();
	}

	// 获取Request
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	// 获取Response
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	// 获取ServletContext
	public ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}
	
	/**
	 * 设置 Session属性
	 * @param name 属性名
	 * @param value 属性值
	 */
	public void setSessionAttribute(String name, Object value) {
		getHttpSession().setAttribute(name, value);
	}

	/**
	 * 获取session 属性
	 * @param name 属性名
	 * @return 返回Session中名字为[name]的对象
	 */
	public Object getSessionAttribute(String name) {
		return  getHttpSession().getAttribute(name);
	}

	/**
	 * 文件下载
	 * @param fileName 需要下载的文件的名字（需要先转成ISO8859-1编码，防止乱码问题出现）
	 * @param fileInputStream
	 * @return
	 */
	public String ajaxdownLoad(String fileName, InputStream fileInputStream) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType( "application/octet-stream;charset=ISO8859-1");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);  
			response.setDateHeader("Expires", 0);
			//得到响应的输出流  即向客户端输出信息的输出流。  
			ServletOutputStream servletOutputStream = response.getOutputStream();  
			byte[] b = new byte[1024];  
			int len;  
			
			while((len=fileInputStream.read(b)) >0) {
				servletOutputStream.write(b,0,len);
			}
			response.setStatus(HttpServletResponse.SC_OK );  
			response.flushBuffer();  
			servletOutputStream.close();  
			fileInputStream.close();  
		} catch (IOException e) {
			log.error("[文件下载]异常信息 ：", e);
		}
		return null;
	}

	/**
	 * 根据Map输出JSON，返回null
	 * @param jsonMap 需要以json形式输出的Map<String,Object>对象
	 * @return null
	 */
	public String ajaxjson(Map<String,Object> jsonMap){
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html"); 
	}
	
	/**
	 * 提交form表单后返回信息
	 * @param isSuccess 是否保存成功
	 * @param message 返回的信息
	 * @return null
	 */
	public String ajaxJsonAfterSubmitForm(boolean isSuccess, String message) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(SUCCESS, isSuccess);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html"); 
	}
	
	/**
	 * 将List，Map里面对象转换为Json数据返回到前台(List中对象在转换过程中会过滤指定的需要拦截的属性)
	 * @param jList 需要转换json数组的List集合
	 * @param jMap  需要转换为json数组的Map集合
	 * @param fileterNames List转换为Json时需要拦截的属性
	 * @return null
	 */
	@SuppressWarnings("unchecked")
	public String ajaxJsonByListAndMap(List list, Map<String, Object> map, String[] filterNames){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		if(list!=null) {
			JsonConfig jsonConfig = new JsonConfig();
			
			if(filterNames != null) {
				jsonConfig .setExcludes(filterNames) ;
				jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);	//防止自包含
			}
			JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
			jsonMap.put("list", jsonArray);
		}
		
		if(map!=null){
			jsonMap.putAll(map);
		}
		return ajaxJsonByObjectMap(jsonMap);
	}
	
	/**
	 * 直接返回List
	 * @param list 需要转成JSON数据的集合
	 * @param propertyNames 需要过滤掉的属性集合
	 * @return null
	 */
	@SuppressWarnings("unchecked")
	public String ajaxJsonByListDirecdt(List list, String[] filterNames) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setIgnoreDefaultExcludes(false);    
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);	//防止自包含
		
		if(filterNames != null){
			jsonConfig .setExcludes(filterNames) ;
		}
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		return ajax(jsonArray.toString(), "text/html");
	}
	
	/**
	 * 设置页面不缓存
	 */
	public void setResponseNoCache() {
		getResponse().setHeader("progma", "no-cache");
		getResponse().setHeader("Cache-Control", "no-cache");
		getResponse().setHeader("Cache-Control", "no-store");
		getResponse().setDateHeader("Expires", 0);
	}
	
	/**
	 *  根据Map输出JSON，返回null
	 *  如果是数组，最好传jsonArray进来
	 *  注意对象是否有级联关系!没必要的级联要去掉!
	 * @param jsonMap
	 * @return
	 */
	public String ajaxJsonByObjectMap(Map<String, Object> jsonMap) {
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	/**
	 *  AJAX输出，返回null
	 * @param content 需要传出去的内容
	 * @param type Ajax输出类型 ： 1、"text/plain"(输出文本)	<br> 
	 * 							 2、text/html(输出HTML)		<br> 
	 * 							 3、text/xml(输出XML)
	 * @return null
	 */
	public String ajax(String content, String type) {
		try {
			HttpServletResponse response = getResponse();
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			log.error("[Ajax信息输出]异常信息 ： ", e);
		}
		return null;
	}
	
	/**
	 *  AJAX输出文本，返回null
	 * @param text 需要输出的文本内容
	 * @return null
	 */
	public String ajaxText(String text) {
		return ajax(text, "text/plain");
	}

	/**
	 *  AJAX输出HTML，返回null
	 * @param html 需要出的HTML内容
	 * @return null
	 */
	public String ajaxHtml(String html) {
		return ajax(html, "text/html");
	}

	/**
	 * AJAX输出XML，返回null
	 * @param xml 需要输出的XML内容
	 * @return　null
	 */
	public String ajaxXml(String xml) {
		return ajax(xml, "text/xml");
	}
	
	/**
	 *  根据字符串输出JSON，返回null
	 * @param jsonString 需要输出的json字符串对象
	 * @return null
	 */
	public String ajaxJson(String jsonString) {
		return ajax(jsonString, "text/html");
	}

	
	
	/***********************************************/
	//			GETTER AND SETTER METHOD     	   //
	/***********************************************/
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String[] getIds() {
		return ids;
	}
	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getRedirectionUrl() {
		return redirectionUrl;
	}
	public void setRedirectionUrl(String redirectionUrl) {
		this.redirectionUrl = redirectionUrl;
	}

	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}

	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}

	public Pager getPager() {
		if(pager == null) {
			pager = new Pager();
		}
		int limit = Integer.parseInt(getLimit()); 
		int start = Integer.parseInt(getStart());
		int pagerNumer = (start / limit) + 1;
		pager.setPageSize(limit); pager.setPageNumber(pagerNumer);
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
}