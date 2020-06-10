package cn.laojunsen.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.laojunsen.dao.userManageDao;

public class registerAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	String registeruserName;
	String registernickName;
	String registerpassword;
	
	userManageDao userManageDao = new userManageDao();

	@Override
	public String execute() throws Exception {
		
		String flag = userManageDao.register(registeruserName,registernickName,registerpassword);
		
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		if(flag=="success") {
			request.setAttribute("Message", "ע��ɹ���");
			return "success";
		}else if(flag=="error"){
			request.setAttribute("Message", "ע��ʧ�ܣ�");
			return "input";
		}else if(flag=="userName"){
			request.setAttribute("Message", "�û����Ѵ��ڣ�");
			return "input";
		}else {
			request.setAttribute("Message", "�ǳ��Ѵ��ڣ�");
			return "input";
		}
	}

	public String getRegisteruserName() {
		return registeruserName;
	}

	public void setRegisteruserName(String registeruserName) {
		this.registeruserName = registeruserName;
	}

	public String getRegisternickName() {
		return registernickName;
	}

	public void setRegisternickName(String registernickName) {
		this.registernickName = registernickName;
	}

	public String getRegisterpassword() {
		return registerpassword;
	}

	public void setRegisterpassword(String registerpassword) {
		this.registerpassword = registerpassword;
	}

	

}
