package util.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBUtilClass {
 
 public DBUtilClass(){}
 
 public static int getInt(String sql, Statement stmt, ResultSet rs) throws Exception{
  int result = 0;
  
  try{
   rs = stmt.executeQuery(sql);
   
   if( rs!=null ){
    while( rs.next() ){
     result = rs.getInt(1);
     
     break;
    }
   }
  }catch(Exception e){
   throw e;
  }
  
  return result;
 }
 
 public static String getVal(String sql, Statement stmt, ResultSet rs) throws Exception{
  String result = "";
  
  try{
   rs = stmt.executeQuery(sql);
   
   if( rs!=null ){
    while( rs.next() ){
     result = rs.getString(1);
     
     break;
    }
   }
  }catch(Exception e){
   throw e;
  }
  
  return result;
 }
 
 public static HashMap<String,String> getRow(String sql, Statement stmt, ResultSet rs) throws Exception{
  HashMap<String,String> result = null;
  
  try{
   rs = stmt.executeQuery(sql);
   
   if( rs!=null ){
    ResultSetMetaData rsm = rs.getMetaData();
    
    while( rs.next() ){
     result = new HashMap<String,String>();
     
     for(int i=0;i<rsm.getColumnCount();i++){
      String key = rsm. getColumnName(i+1);
      String val = rs.getString(key);
      
      result.put(key.toLowerCase(), val);
     }
     
     break;
    }
   }
  }catch(Exception e){
   throw e;
  }
  
  return result;
 }
 
 public static List<HashMap<String,String>> getList(String sql, Statement stmt, ResultSet rs) throws Exception{
  List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
  
  try{
   rs = stmt.executeQuery(sql);
   
   if( rs!=null ){
    ResultSetMetaData rsm = rs.getMetaData();
    
    while( rs.next() ){
     HashMap<String,String> row = new HashMap<String,String>();
     
     for(int i=0;i<rsm.getColumnCount();i++){
      String key = rsm. getColumnName(i+1);
      String val = rs.getString(key);
      
      row.put(DBUtilClass.nvl(key).trim().toLowerCase(), val);
     }
     
     list.add(row);
    }
   }
  }catch(Exception e){
   throw e;
  }
  
  return list;
 }
 
}