package cn.laojunsen.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import cn.laojunsen.dao.archivesTypeManageDao;

public class editarchivesTypeAction {
	
	private static final long serialVersionUID = 1L;
	
	int userType;
	int archivesTypeid;
	private String archivesType;
	
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = ServletActionContext.getRequest();

		String flag = archivesTypeManageDao.editarchivesType(archivesTypeid,archivesType);
		
		String list = archivesTypeManageDao.checkarchivesType(userType);
		
		request.setAttribute("userType", userType);
		
		if(flag=="success"){
			request.setAttribute("flag", "�޸ĳɹ���");
		}else if(flag=="archivestype"){
			request.setAttribute("flag", "���������Ѵ��ڣ�");
		}else {
			request.setAttribute("flag", "�޸�ʧ�ܣ�");
		}
		
		if (list.length() != 0 || null == list) {
			request.setAttribute("data", list);
			return "success";
		} else {
			request.setAttribute("data", "[]");
			request.setAttribute("flag", "�Բ�����û��Ȩ��");
			return "input";
		}

	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getArchivesTypeid() {
		return archivesTypeid;
	}

	public void setArchivesTypeid(int archivesTypeid) {
		this.archivesTypeid = archivesTypeid;
	}

	public String getArchivesType() {
		return archivesType;
	}

	public void setArchivesType(String archivesType) {
		this.archivesType = archivesType;
	}
	
	
}
