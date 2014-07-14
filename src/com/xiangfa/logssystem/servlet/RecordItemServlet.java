package com.xiangfa.logssystem.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiangfa.logssystem.dao.IRecordItemDao;
import com.xiangfa.logssystem.dao.mysqlimpl.RecordItemDao;
import com.xiangfa.logssystem.entity.RecordItem;
import com.xiangfa.logssystem.util.JSONUtil;
import com.xiangfa.logssystem.util.ModifyAction;

public class RecordItemServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1937061942780533473L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		super.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		PrintWriter writer = resp.getWriter();
		try {
			Integer pid = req.getParameter("pid") == null ? null : Integer
					.valueOf(req.getParameter("pid"));
			if (null == pid)
				return;
			String names = req.getParameter("ritemName") == null ? null : req
					.getParameter("ritemName");
	

			if (null == names) {
				// ����RecordItem�б�
				IRecordItemDao ridao = new RecordItemDao();
				List<RecordItem> items = ridao.listItem(pid);
				if (items.size() == 0) {
					writer.write("");
					return;
				}
				StringBuilder sb = new StringBuilder(100);
				int index = 0;
				for (RecordItem item : items) {
					if (index > 0)
						sb.append(",");
					sb.append(JSONUtil.fromObject(item));
					index++;
				}
				writer.write("[" + sb.toString() + "]");
				return;
			}
			// ��RecordItem��������ɾ����
			Map<Integer, List<RecordItem>> data = getSerializableData(pid,
					names);
			IRecordItemDao rdao = new RecordItemDao();
			if (rdao.update(data)) {
				writer.write("true");
			} else {
				writer.write("false");
			}
		} catch (Exception e) {
			if (e.getMessage().contains("exists")) {
				writer.write("����־�����Ѵ���");
			} else {
				writer.write("false");
			}
		}
		writer.flush();
	}

	/**
	 * ��ȡRecordItemҪ��������ɾ���ĵ�һ��Map����
	 * 
	 * @param ���̱��
	 * @param �����Ҫ������Map
	 *            <RecordItem>�Ķ���
	 * @return �������Map<RecordItem>����
	 * @throws Exception
	 */
	private Map<Integer, List<RecordItem>> getSerializableData(Integer pid,
			String names) throws Exception {

		StringTokenizer st = new StringTokenizer(names, ",");

		Map<Integer, List<RecordItem>> rdatas = new HashMap<Integer, List<RecordItem>>();
		RecordItem item = null;
		while (st.hasMoreElements()) {
			String[] s = st.nextElement().toString().split("=>");

			String id = s[0];
			String value = s[1];
		
			if ("".equals(id) || null == id)
				throw new Exception("������Ŀ��ƥ��");
			// ����RecordItem
			if ("?".equals(id.trim())) {
				List<RecordItem> insertItem = rdatas.get(ModifyAction.INSERT);
				if (null == insertItem) {
					insertItem = new ArrayList<RecordItem>();
					rdatas.put(ModifyAction.INSERT, insertItem);
				}
				item = new RecordItem();

				if ("".equals(value.trim())) {
					throw new Exception("����ķ������Ʋ���Ϊ��");
				}
				item.setRitemName(URLDecoder.decode(value,"UTF-8"));
				item.setPid(pid);
				insertItem.add(item);
				continue;
			}
			// ɾ��RecordItem
			if ("!".equals(value.trim())) {
				List<RecordItem> deleteItem = rdatas.get(ModifyAction.DELETE);
				if (null == deleteItem) {
					deleteItem = new ArrayList<RecordItem>();
					rdatas.put(ModifyAction.DELETE, deleteItem);
				}
				item = new RecordItem();
				item.setRitemId(Integer.valueOf(id));
				deleteItem.add(item);
				continue;
			}
			// �޸�RecordItem
			List<RecordItem> updateItem = rdatas.get(ModifyAction.MODIFY);
			if (null == updateItem) {
				updateItem = new ArrayList<RecordItem>();
				rdatas.put(ModifyAction.MODIFY, updateItem);
			}
			item = new RecordItem();
			item.setRitemId(Integer.valueOf(id));
			item.setRitemName(URLDecoder.decode(value,"UTF-8"));
			updateItem.add(item);

		}
		return rdatas;
	}
}
