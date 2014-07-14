package com.xiangfa.logssystem.util;

import java.util.StringTokenizer;

/**
 * �ļ��ϴ�������ʱ�����ļ�������
 * 
 * @author Think
 * 
 */
public final class FileNameSwitch {

	/**
	 * �ͻ����ϴ�ʱ�������µ��ļ���
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
			throw new Exception("�ļ������Ϸ�");
		}
		clientFileName = clientFileName.replaceAll(" ", "");
		int index = clientFileName.lastIndexOf(".");
		String name = clientFileName.substring(0, index);
		String suffix = clientFileName
				.substring(index, clientFileName.length());
		return name + "_" + logDate + projectId + suffix;
	}

	/**
	 * ���ݷ���������ļ����ַ����������ļ��б��ظ��ͻ���
	 * 
	 * @param fileName
	 * @return һ���ļ���String[]
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
