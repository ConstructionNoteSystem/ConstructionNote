package com.xiangfa.logssystem.util;

import java.util.StringTokenizer;

/**
 * 文件上传和下载时，对文件名处理
 * 
 * @author Think
 * 
 */
public final class FileNameSwitch {

	/**
	 * 客户端上传时，生成新的文件名
	 * 
	 * @param clientFileName
	 * @param projectId
	 * @param logDate
	 * @return
	 * @throws Exception 
	 */
	public static final String fromClient(String clientFileName,
			Integer projectId, String logDate) throws Exception {
		if("".equals(clientFileName)){
			throw new Exception("文件名不合法");
		}
		clientFileName = clientFileName.replaceAll(" ", "");
		int index = clientFileName.lastIndexOf(".");
		String name = clientFileName.substring(0, index);
		String suffix = clientFileName
				.substring(index, clientFileName.length());
		return name + "_" + logDate + projectId + suffix;
	}

	/**
	 * 根据服务器存的文件名字符串解析成文件列表返回给客户端
	 * 
	 * @param fileName
	 * @return 一个文件名String[]
	 */
	public static final String[] toClient(String names) {
		if(null==names||"".equals(names)){
			return null;
		}
		StringTokenizer st = new StringTokenizer(names, ":");
		int count = st.countTokens();
		String[] files = new String[count];
		int index = 0;
		while (st.hasMoreElements()) {
			String fileURI = st.nextElement().toString();
			files[index]=fileURI;
			index++;
		}
		return files;
	}
}
