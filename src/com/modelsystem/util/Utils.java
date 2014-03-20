package com.modelsystem.util;

import java.io.File;
import java.util.Calendar;
import java.util.regex.Pattern;
/**
 * -----------------------------------------
 * @描述       工具类
 * @作者  fancy
 * @邮箱  fancydeepin@yeah.net
 * @日期  2012-10-20 <BR>
 * -----------------------------------------
 */
public class Utils {

	/**
	 * <BR>
	 * <span style="color: #008080;"> Writer by fancy { fancydeepin@yeah.net } </span>
	 * <BR><BR><B>Description：</B>
	 * <p style="margin-left:40px;color:#A52A2A;">
	 * 解析参数
	 * @param encode - 参数(由;分隔的参数)
	 * @return<span style="color: #008080;"> String[] </span>
	 */
	public String[] getEncodeStringList(String encode) throws Exception {
		
		Pattern pattern = Pattern.compile(";+"); // 正则匹配;号
		return  pattern.split(encode); //分割字符串成数组
	}
	
	/**
	 * <BR>
	 * <span style="color: #008080;"> Writer by fancy { fancydeepin@yeah.net } </span>
	 * <BR><BR><B>Description：</B>
	 * <p style="margin-left:40px;color:#A52A2A;">
	 * 取得文件存储的相对路径
	 * @param path - 路径
	 * @return<span style="color: #008080;"> 路径 </span>
	 */
	public String getOppositePath(String path) throws Exception {
		
		String regex = ".*" + Config.RESOURCE_DIR; //从资源文件根目录开始切割字符串
		return Config.RESOURCE_DIR + path.split(regex)[1];
	}
	
	/**
	 * <BR>
	 * <span style="color: #008080;"> Writer by fancy { fancydeepin@yeah.net } </span>
	 * <BR><BR><B>Description：</B>
	 * <p style="margin-left:40px;color:#A52A2A;">
	 * 在当前参数指定的路径下创建资源存放目录
	 * @param path 当前参数指定的路径
	 * @return<span style="color: #008080;"> String </span>
	 */
	public String createResourcePath(String path) throws Exception {

		/***********************************/
		// 创建资源存放目录
		// 目录创建规则  ---> ../Resources/年/月/日/
		/***********************************/
		Calendar calendar = Calendar.getInstance();      //日历对象实例
		int year  = calendar.get(Calendar.YEAR);         //当前年份
		int month = calendar.get(Calendar.MONTH) + 1;    //当前月份
		int day   = calendar.get(Calendar.DAY_OF_MONTH); //当前天数
		String fileSeparator = File.separator;           //文件路径分隔符
		String dirPath = path + fileSeparator + year + fileSeparator + month + fileSeparator + day + fileSeparator;
		File dir = new File(dirPath); //指定路径下的文件实例对象
		if(!dir.exists()){	//指定的路径不存在
			dir.mkdirs();	//创建该路径
		}
		return dirPath; //资源存放目录
	}
}
