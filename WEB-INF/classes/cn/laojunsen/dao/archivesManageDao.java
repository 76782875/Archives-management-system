package cn.laojunsen.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import cn.laojunsen.model.archives;
import cn.laojunsen.util.DButil;

public class archivesManageDao {
	
//	�����б�
	public static String checkArchives(String archivesType,int userType) throws Exception {
		
		String sql;
		List<archives> list = new ArrayList<archives>();
		Gson gson = new Gson();
			if(archivesType.equals("all")){
				sql = "select * from archives";
			}else {
				sql = "select * from archives where archivesType='"+archivesType+"'";
			}
			Statement stat = null;
			ResultSet rs = null;
			Connection con = new DButil().getCon();
			try {
				stat = con.createStatement();
				rs = stat.executeQuery(sql);
				while (rs.next()) {
					archives archives = new archives();
					archives.setId(rs.getString("Id"));
					archives.setTitle(rs.getString("title"));
					archives.setArchivesType(rs.getString("archivesType"));
					archives.setAuthor(rs.getString("author"));
					archives.setDate(rs.getString("date"));
//					archives.setSave(rs.getString("save"));
					archives.setUserType(userType);
					list.add(archives);
				}
			} catch (SQLException ex) {
			}
			String json = gson.toJson(list);
			return json;
			
	}
	
//	��������
	public String searcharchives(String search,int userType,String archivesType) throws Exception {

		List<archives> list = new ArrayList<archives>();
		
		Gson gson = new Gson();
		String sql;
		
		if(archivesType.equals("all")){
			sql = "select * from archives where concat(title,author) like '%"+ search +"%'";
		}else {
			sql = "select * from archives where concat(title,author) like '%"+ search +"%' and archivesType='"+archivesType+"'";
		}
		Statement stat = null;
		ResultSet rs = null;
		Connection con = new DButil().getCon();
			try {
				stat = con.createStatement();
				rs = stat.executeQuery(sql);
				while (rs.next()) {
					archives archives = new archives();
					archives.setId(rs.getString("Id"));
					archives.setTitle(rs.getString("title"));
					archives.setArchivesType(rs.getString("archivesType"));
					archives.setAuthor(rs.getString("author"));
					archives.setDate(rs.getString("date"));
					archives.setSave(rs.getString("save"));
					archives.setUserType(userType);
					list.add(archives);
				}
			} catch (SQLException ex) {
			}
			String json = gson.toJson(list);
			return json;
	}
	
	
//	��ӵ���
	public static String addarchives(String title,String archivestype,String author,String save,String fileName) throws Exception {
		
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String flag = null;
		String sql;
		ResultSet rs = null;
		Connection con = new DButil().getCon();
		Statement stat = null;
		
		sql = "select * from archives where title='"+title+"'";
		stat = con.createStatement();
		rs = stat.executeQuery(sql);
//		�����������򷵻ر������
		if(rs.next() == true) {
			flag = "archivestitle";
		}else {
			sql = "select * from archives where fileName='"+fileName+"'";
			stat = con.createStatement();
			rs = stat.executeQuery(sql);
//			����ļ��������򷵻��ļ�������
			if(rs.next() == true) {
				flag = "fileName";
			}else {
//				�����������
				sql = "insert into archives(title,archivestype,author,date,save,fileName) values('"+ title +"','"+ archivestype +"','"+ author +"','"+ df.format(date) +"','"+ save +"','"+fileName+"')";
	
				int rss = stat.executeUpdate(sql);
				
				if(rss>0) {
					flag = "success";
				}else {
					flag = "error";
				}
			}
			
		}
		return flag;
	}
	
//	ɾ������
	public String delarchives(int id) throws Exception {
		String flag = null;
		String sql = "delete from archives where id = "+id;
		Statement stat = null;
		Connection con = new DButil().getCon();
		
		stat = con.createStatement();
		int rs = stat.executeUpdate(sql);
		
		if(rs>0) {
			flag = "success";
		}else {
			flag = "error";
		}
		return flag;

	}
	
//	�༭����
	public static String editarchives(int archivesid,String title,String archivestype,String author,String save,String fileName) throws Exception {
		
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String flag = null;
		String sql;
		ResultSet rs = null;
		Connection con = new DButil().getCon();
		Statement stat = null;
		
		sql = "select * from archives where title='"+title+"' and id not in ("+archivesid+")";
		stat = con.createStatement();
		rs = stat.executeQuery(sql);
//		�����������򷵻ر���������ʧ��
		if(rs.next() == true) {
			flag = "archivestitle";
		}else {
//			����ļ��������򷵻��ļ����������ʧ��
			sql = "select * from archives where fileName='"+fileName+"' and id not in ("+archivesid+")";
			stat = con.createStatement();
			rs = stat.executeQuery(sql);
			if(rs.next() == true) {
				flag = "fileName";
			}else {
				//�����޸�����
				sql = "update archives set title='"+title+"',archivestype='"+archivestype+"',author='"+author+"',date='"+df.format(date)+"',save='"+save+"',fileName='"+fileName+"' where id = "+archivesid;
	
				int rss = stat.executeUpdate(sql);
				
				if(rss>0) {
					flag = "success";
				}else {
					flag = "error";
				}
			}
		}
		return flag;
	}
	
//ɾ�������ļ�
	public static String delarchivesfile(int id) throws Exception {
		
			String sql = "select * from archives where id="+id;
			String flag = null;
			Statement stat = null;
			ResultSet rs = null;
			Connection con = new DButil().getCon();
			try {
				stat = con.createStatement();
				rs = stat.executeQuery(sql);
				while (rs.next()) {
					try{
			            File file = new File(rs.getString("save"));
			            if(file.delete()){
			            	flag = "success";
			            }else{
			            	flag = "error";
			            }
			        }catch(Exception e){
			            e.printStackTrace();
			        }	
				}
			} catch (SQLException ex) {
			}
			return flag;

	}
	
//������ĵ���
	public static String applyarchivesfile(int id,String userName) throws Exception {
		
		
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String flag = null;
		String sql;
		ResultSet rs = null;
		ResultSet rss = null;
		Connection con = new DButil().getCon();
		Statement stat = null;
		sql = "select * from archives where id='"+id+"' ";
		stat = con.createStatement();
		rs = stat.executeQuery(sql);
		//�鿴�Ƿ���ڵ���
		if(rs.next() == true) {
			String title = rs.getString("title");
			String archivestype = rs.getString("archivestype");
			String author = rs.getString("author");
			sql = "select * from borrowing where userName='" + userName + "' and title='" + title + "' and author='" + author + "'";
			stat = con.createStatement();
			rss = stat.executeQuery(sql);
			if(rss.next() == true) {
				if(rss.getInt("progress")==1) {
					flag = "applying";
//					flag = "�����������У���ȴ�";
				}else if(rss.getInt("progress")==2) {
					flag = "pass";
//					flag = "����������ͨ������ȥ���ļ�¼�鿴";
				}else {
					int borrowingid = rss.getInt("id");
					sql = "update borrowing set progress=1,date='"+df.format(date)+"' where id = "+borrowingid;
					stat.executeUpdate(sql);
					flag = "refuse";
//					flag = "�����ѱ��ܾ���������������";
				}
			}else {
				sql = "insert into borrowing(userName,title,archivestype,author,progress,date) values('"+userName+"','"+title+"','"+archivestype+"','"+author+"',1,'"+df.format(date)+"')";
				int rsss = stat.executeUpdate(sql);
				if(rsss>0) {
					flag = "apply";
				}else {
					flag = "applyerror";
				}
			}
			
		}else {
			flag = "archiveserror";
		}
		return flag;
	}
	
//		�鿴�����ļ�
		public static String findfilename(String title) throws Exception {
			
				String filename = "";
				String sql = "select * from archives where title='"+title+"'";
				String flag = null;
				Statement stat = null;
				ResultSet rs = null;
				Connection con = new DButil().getCon();
				stat = con.createStatement();
				rs = stat.executeQuery(sql);
				if(rs.next() == true) {
					filename = rs.getString("fileName");
				}
				
				return filename;

		}
	
}
