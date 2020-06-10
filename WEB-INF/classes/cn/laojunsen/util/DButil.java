package cn.laojunsen.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DButil {
	private String dbUrl="jdbc:mysql:///jbdb?characterEncoding=utf8";
    private String dbUserName="root";
    private String dbPassword="123456";
    private String jdbcName="com.mysql.jdbc.Driver";

    /**
     * ��ȡ���ݿ�����
     * @return
     * @throws Exception
     */
    public Connection getCon() throws Exception{
        Class.forName(jdbcName);
        Connection con= DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
        return con;
    }

    /**
     * �ر����ݿ�����
     * @param con
     * @throws Exception
     */
    public void closeCon(Connection con) throws  Exception{
        if (con!=null){
            con.close();
        }
    }

    /**
     * �������ݿ�����
     * @param args
     */
    public static void main(String[] args){
        DButil dbUtil = new DButil();
        try {
            dbUtil.getCon();
            System.out.println("���ݿ����ӳɹ�");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
