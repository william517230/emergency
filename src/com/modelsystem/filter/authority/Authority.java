/**
 * 
 */
package com.modelsystem.filter.authority;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**     
 * @class Authority   
 * @declare    
 * @author ZWZ   
 * @time 2013年12月6日 下午11:45:02
 */
public class Authority extends AbstractAuthority {

	private Logger log = Logger.getLogger(this.getClass());
	
	
	@Override
	public Boolean validate(HttpServletRequest req, String uri) {
		log.debug("this is the class Authority validate method!");
		
		return true;
		
		//默认什么都不做
//		Set<String> urls = (Set<String>) req.getSession().getAttribute("URLS");
//		for(String url : urls) {
//			if(url.equals(uri)) {
//				log.debug("匹配了\t" + url);
//				return true;
//			}
//		}
//		log.debug("未匹配到URL" + uri);
//		return false;
	}
}
