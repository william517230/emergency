/**
 * 
 */
package com.modelsystem.filter.authority;

import javax.servlet.http.HttpServletRequest;


/**     
 * @class AbstractAuthority   
 * @declare    
 * @author ZWZ   
 * @time 2013年12月6日 下午11:37:31
 */
public abstract class AbstractAuthority  {
	
	/**
	 * 提供验证权限方法，验证成功则返回true，否则返回false
	 * @param req
	 * @param uri
	 * @return
	 */
	public abstract Boolean validate(HttpServletRequest req, String uri);
	
}
