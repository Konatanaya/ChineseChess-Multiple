/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChineseChess2D;

import java.sql.*;
/**
 *
 * @author konatan
 */
public class Database {
    Connection con = null;
//    String url="jdbc:derby://192.168.1.155:1527/ChineseChess";
    String username="konatan";
    String password="konatan";
    
    public Database(String url){
        try{
            con=DriverManager.getConnection(url, username, password);
        }
        catch(SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
    }
    
    public void createPromotionTable(String s, String sql)
    {
        try {
            Statement statement = con.createStatement();
            String tableName = s;

            this.checkTableExisting(tableName);

            statement.executeUpdate("CREATE TABLE " + tableName + sql);
            statement.close();
        } catch(SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
        }
    }
    
    public void insertTable(String sql){
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch(SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
        }
    } 
    
    public ResultSet selectUserCheck(String sql, String username, String password){
        ResultSet rs=null;
        try{
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            rs = preparedStatement.executeQuery();
        } catch (SQLException ex){
            System.err.println("SQL Exception: " + ex.getMessage());
        }
        return(rs);
    }
    
    public ResultSet selectTable(String sql){
        ResultSet rs=null;
        try{
            Statement statement = con.createStatement();
            rs=statement.executeQuery(sql);
        } catch (SQLException ex){
            System.err.println("SQL Exception: " + ex.getMessage());
        }
        return(rs);
    }
    
    public void deleteTable(String sql){
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch(SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
        }
    }
    
    public void updateTable(String sql){
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch(SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
        }
    }
    
    public void checkTableExisting(String s){
        try{
            DatabaseMetaData dbmd = con.getMetaData();
            ResultSet rsdbmd=dbmd.getTables(null,null,null,null);
            Statement drop = null;
            while(rsdbmd.next()){
                String tableName = rsdbmd.getString("TABLE_NAME");
                if(tableName.compareToIgnoreCase(s)==0){
                    
                    String sqlDropTable="Drop table"+s;
                    drop=con.createStatement();
                    drop.executeUpdate(sqlDropTable);
                }
                    
            }
            if(rsdbmd!=null)
                rsdbmd.close();
            if(drop!=null)
                drop.close();
        } catch (SQLException ex){
            
        }
    }
    
    public void insert3DTable(String sql){
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch(SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
        }
    } 
}
