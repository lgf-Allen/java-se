/**
 * 
 */
package com.allen.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author first
 *
 */
public class ConnectOracle {

    private static Map<String ,String> map = new HashMap<>();
    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {

//        formatUnixTimeStamp();
        
        //get localdb callNumber
//        getLostCallNumber();
        //set localdb callNumber
//        setLostCallNumber();
//        setSepcialCallNumber();
        
        //generate update callNumber sql script.
        getUpdateSqlScript();
        
        //generate verify sql script.
        generateVerifySqlScript();
        
//        formatUnixTimeStamp2();
    }

    /**
     * Get data from oracle database.
     * Write a text (location:C:\\Work\\localdb.txt)
     * @throws FileNotFoundException
     */
    private static void getLocalData() throws FileNotFoundException {
        File file = new File("C:\\Work\\localdb.txt");
        FileOutputStream out = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            out = new FileOutputStream(file);
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","xxx","xxx");
            statement = conn.prepareStatement("select a.CALLNUMBER,t.transactionId ,t.STARTTIME,t.ENDTIME,t.TERMINALCODE,t.AGENTCODE from ACCOUNTOPENING a left join TRANSACTION t on a.id = t.id order by t.STARTTIME");
            rs = statement.executeQuery();
            int i = 1;
            //set cloumn name
            out.write(("id"+","+"callNumber"+","+"transactionId"+","+"startTime"+","+"endTime"+","+"terminalCode"+","+"agentCode"+"\n").getBytes());
            while(rs.next()){
                String callNumber = rs.getString("CALLNUMBER");
                String transactionId = rs.getString("TRANSACTIONID");
                Timestamp startTime = rs.getTimestamp("STARTTIME");
                Timestamp endTime = rs.getTimestamp("ENDTIME");
                String terminalCode = rs.getString("TERMINALCODE");
                String agentCode = rs.getString("AGENTCODE");
                System.out.println(callNumber+"\t"+transactionId);
                out.write((callNumber+","+transactionId+","+startTime+","+endTime+","+terminalCode+","+agentCode+"\n").getBytes());
                i++;
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(out != null){
                    out.close();
                }
                if(rs!=null){
                  rs.close();
                }
                if(statement!=null){ 
                    statement.close();
                }
                if(conn!=null){
                    conn.close();
                }  
              } catch (Exception e) {
                e.printStackTrace();
              }
        }
        
    }
    
    /**
     * Format Unix TimeStamp to oracle Timestamp,
     * write a text(location:C:\\Work\\huawei.txt)
     * @throws IOException
     */
    private static void formatUnixTimeStamp() throws IOException {
        File file = new File("C:\\Work\\huawei.txt");
        FileOutputStream out = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            out = new FileOutputStream(file);
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","mvtmdb","mvtmdb");
            statement = conn.prepareStatement("select id,callnumber,logCode,agentCode,SUBSTR(CALLNUMBER,0,10) from HUAWEI where callnumber not in(select callnumber from localdb where callnumber is not null)");
            rs = statement.executeQuery(); 
            int i = 1;
            out.write(("id"+","+"callnumber"+","+"logCode"+","+"agentCode"+","+"callTime\n").getBytes());
            while(rs.next()){
               String callNumber = rs.getString("callnumber");
               String logCode = rs.getString("logCode");
               String agentCode = rs.getString("agentCode");
               String sbs = rs.getString("SUBSTR(CALLNUMBER,0,10)");
               long time = Long.parseLong(sbs);
               String callTime = new java.text.SimpleDateFormat("YYYY-MM-dd HH:mm:ss.sss").format(new Date(time * 1000));
               System.out.println(i+","+callNumber+","+logCode+","+agentCode+","+callTime+"\n");
               out.write((i+","+callNumber+","+logCode+","+agentCode+","+callTime+"\n").getBytes());
               i++;
            }
        } catch (ClassNotFoundException | SQLException  e) {
            e.printStackTrace();
        }finally{
            try {
                if(out != null){
                    out.close();
                }
                if(rs!=null){
                  rs.close();
                }
                if(statement!=null){ 
                    statement.close();
                }
                if(conn!=null){
                    conn.close();
                }  
              } catch (Exception e) {
                e.printStackTrace();
              }
        }
        
    }
    
    /**
     * select ldb.transactionId,hw.callNumber from localdb ldb left join TEMPTAB tab on (ldb.TERMINALCODE=tab.TERMINALCODE) left join huawei hw on (tab.logcode=hw.logcode) where ldb.STARTTIME<=hw.callTime and ldb.ENDTIME>=hw.callTime order by LDB.STARTTIME;
     * ResultSet to Map
     * @throws IOException
     */
    private static void getLostCallNumber() throws IOException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","mvtmdb","mvtmdb");
            statement = conn.prepareStatement("select ldb.transactionId,hw.callNumber from localdb ldb left join TEMPTAB tab on (ldb.TERMINALCODE=tab.TERMINALCODE) left join huawei hw on (tab.logcode=hw.logcode) where ldb.STARTTIME<=hw.callTime and ldb.ENDTIME>=hw.callTime and LDB.AGENTCODE=hw.AGENTCODE order by LDB.STARTTIME");
            rs = statement.executeQuery(); 
            int i = 1;
            while(rs.next()){
                String transactionId = rs.getString("transactionId");
                String callNumber = rs.getString("callNumber");
                if(map.get(transactionId)!=null){
                    System.out.println(i);
                }
                map.put(transactionId, callNumber);
                System.out.println(transactionId+","+callNumber+"----------------------"+i);
                i++;
            }
            System.out.println(map.size());
        } catch (ClassNotFoundException | SQLException  e) {
            e.printStackTrace();
        }finally{
            try {
                if(rs!=null){
                  rs.close();
                }
                if(statement!=null){ 
                    statement.close();
                }
                if(conn!=null){
                    conn.close();
                }  
              } catch (Exception e) {
                e.printStackTrace();
              }
        }
        
    }
    
    /**
     * update localdb set callNumber=? where transactionId= ?
     * @throws IOException
     */
    private static void setLostCallNumber() throws IOException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","mvtmdb","mvtmdb");
             Set<Entry<String, String>> entrys = map.entrySet();
            for (Map.Entry<String, String> entry : entrys) {
                statement = conn.prepareStatement("update localdb set callNumber=? where transactionId= ?");
                statement.setString(2, entry.getKey());
                statement.setString(1, entry.getValue());
                statement.executeUpdate();
            }
            System.out.println("Excute update success.");
        } catch (ClassNotFoundException | SQLException  e) {
            e.printStackTrace();
        }finally{
            try {
                if(rs!=null){
                  rs.close();
                }
                if(statement!=null){ 
                    statement.close();
                }
                if(conn!=null){
                    conn.close();
                }  
              } catch (Exception e) {
                e.printStackTrace();
              }
        }
        
    }
    
    /**
     * 
     * @throws IOException
     */
    private static void setSepcialCallNumber() throws IOException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","mvtmdb","mvtmdb");
            String[] sqls = {"update localdb set callNumber='1512974796-25' where id=1",
                    "update localdb set callNumber='1513239060-524324' where id=10",
                    "update localdb set callNumber='1513833906-56' where id=23",
                    "update localdb set callNumber='1516171393-524451' where id=142",
                    "update localdb set callNumber='1516172182-144' where id=145",
                    "update localdb set callNumber='1516176968-149' where id=151",
                    "update localdb set callNumber='1522046455-1613' where id=1455"};
            for(int i = 0 ;i<sqls.length;i++){
                statement = conn.prepareStatement(sqls[i]);
                statement.executeUpdate();
            }
            System.out.println("Excute setSepcialCallNumber success.");
        } catch (ClassNotFoundException | SQLException  e) {
            e.printStackTrace();
        }finally{
            try {
                if(rs!=null){
                  rs.close();
                }
                if(statement!=null){ 
                    statement.close();
                }
                if(conn!=null){
                    conn.close();
                }  
              } catch (Exception e) {
                e.printStackTrace();
              }
        }
        
    }
    
    /**
     * generate update sql
     * @throws IOException
     */
    private static void getUpdateSqlScript() throws IOException {
        File file = new File("C:\\Users\\first\\Desktop\\CallNumber\\sql\\updateSql.txt");
        FileOutputStream out = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            out = new FileOutputStream(file);
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","mvtmdb","mvtmdb");
            statement = conn.prepareStatement("select id,identityNumber,callNumber from localdb");
            rs = statement.executeQuery();
            while(rs.next()){
                String id = rs.getString("id");
                String identityNumber = rs.getString("identityNumber");
                String callNumber = rs.getString("callNumber");
                out.write(("update AccountOpening set callNumber='"+callNumber+"'"+" where id="+id+" and identityNumber='"+identityNumber+"';\n").getBytes());
        
            }
            System.out.println("Excute update and write success.");
        } catch (ClassNotFoundException | SQLException  e) {
            e.printStackTrace();
        }finally{
            try {
                if(rs!=null){
                  rs.close();
                }
                if(statement!=null){ 
                    statement.close();
                }
                if(conn!=null){
                    conn.close();
                }
                if(out!=null){
                    out.close();
                }
              } catch (Exception e) {
                e.printStackTrace();
              }
        }
        
    }
    
    
    /**
     * Generate verify sql to verify callNumber add success.
     * @throws IOException
     */
    private static void generateVerifySqlScript() throws IOException {
        File file = new File("C:\\Users\\first\\Desktop\\CallNumber\\sql\\verifySql.txt");
        FileOutputStream out = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            out = new FileOutputStream(file);
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","mvtmdb","mvtmdb");
            statement = conn.prepareStatement("select id,identityNumber,callNumber from localdb ");
            rs = statement.executeQuery();
            while(rs.next()){
                String id = rs.getString("id");
                String identityNumber = rs.getString("identityNumber");
                String callNumber = rs.getString("callNumber");
                out.write(("select * from AccountOpening where id="+id+" and callNumber='"+callNumber+"'"+" and identityNumber='"+identityNumber+"';\n").getBytes());
            }
            System.out.println("Generate verify sql success.");
        } catch (ClassNotFoundException | SQLException  e) {
            e.printStackTrace();
        }finally{
            try {
                if(rs!=null){
                  rs.close();
                }
                if(statement!=null){ 
                    statement.close();
                }
                if(conn!=null){
                    conn.close();
                }
                if(out!=null){
                    out.close();
                }
              } catch (Exception e) {
                e.printStackTrace();
              }
        }
        
    }
    
    /**
     * Format all huawei table timestamp 
     * @throws IOException
     */
    private static void formatUnixTimeStamp2() throws IOException {
        File file = new File("C:\\Work\\formatAll.txt");
        FileOutputStream out = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            out = new FileOutputStream(file);
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","mvtmdb","mvtmdb");
            statement = conn.prepareStatement("select id,callnumber,logCode,agentCode,SUBSTR(CALLNUMBER,0,10) from HUAWEI ");
            rs = statement.executeQuery(); 
            int i = 1;
            out.write(("id"+","+"callnumber"+","+"logCode"+","+"agentCode"+","+"callTime\n").getBytes());
            while(rs.next()){
               String callNumber = rs.getString("callnumber");
               String logCode = rs.getString("logCode");
               String agentCode = rs.getString("agentCode");
               String sbs = rs.getString("SUBSTR(CALLNUMBER,0,10)");
               long time = Long.parseLong(sbs);
               String callTime = new java.text.SimpleDateFormat("YYYY-MM-dd HH:mm:ss.sss").format(new Date(time * 1000));
               System.out.println(i+","+callNumber+","+logCode+","+agentCode+","+callTime+"\n");
               out.write((i+","+callNumber+","+logCode+","+agentCode+","+callTime+"\n").getBytes());
               i++;
            }
        } catch (ClassNotFoundException | SQLException  e) {
            e.printStackTrace();
        }finally{
            try {
                if(out != null){
                    out.close();
                }
                if(rs!=null){
                  rs.close();
                }
                if(statement!=null){ 
                    statement.close();
                }
                if(conn!=null){
                    conn.close();
                }  
              } catch (Exception e) {
                e.printStackTrace();
              }
        }
        
    }
    
}
