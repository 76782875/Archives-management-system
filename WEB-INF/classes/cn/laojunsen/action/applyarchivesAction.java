package cn.laojunsen.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.laojunsen.dao.archivesManageDao;

public class applyarchivesAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	int userType;
	String userName;
	String archivesType;
	archivesManageDao archivesManageDao = new archivesManageDao();

	@Override
	public String execute() throws Exception {
		
//		String cnuserName = new String(userName.getBytes("ISO-8859-1"),"utf-8");
		
		String flag = archivesManageDao.applyarchivesfile(id,userName);
		
//		HttpSession session = ServletActionContext.getRequest().getSession();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
//		String cnarchivesType = new String(archivesType.getBytes("ISO-8859-1"),"utf-8");
		
		String list = archivesManageDao.checkArchives(archivesType,userType);

		request.setAttribute("archivesType", archivesType);
		request.setAttribute("userType", userType);
		request.setAttribute("data", list);
		
		if(flag=="apply") {
			request.setAttribute("flag", "������������ɹ���");
//			return "success";
		}else if(flag == "applying"){
			request.setAttribute("flag", "�����������У���ȴ���");
//			return "success";
		}else if(flag == "pass") {
			request.setAttribute("flag", "����������ͨ������ȥ���ļ�¼�鿴��");
//			return "success";
		}else if(flag == "refuse") {
			request.setAttribute("flag", "�����ѱ��ܾ��������������룡");
//			return "success";
		}else if(flag == "applyerror") {
			request.setAttribute("flag", "����������ִ�������ϵ����Ա��");
//			return "success";
		}else if(flag == "archiveserror") {
			request.setAttribute("flag", "δ�����������������³��ԣ�");
//			return "input";
		}
		return "success";
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getArchivesType() {
		return archivesType;
	}

	public void setArchivesType(String archivesType) {
		this.archivesType = archivesType;
	}
	
}
