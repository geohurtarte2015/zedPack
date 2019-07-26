
package model;

import Pojo.Subscription;
import configuration.ConnectionProperties;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;



public class ConnectionBd {
    
    private Connection con = null;
    private PreparedStatement pr = null;
    private ResultSet rs = null;
    private Statement ps = null;


    public ArrayList<Subscription> getPendientFiles(String limit, String path, String[] parameters, DriverConnection connection) throws SQLException {
          ArrayList<Subscription> subscriptions = new ArrayList<Subscription>();
          Long idMax = null;      
        try {           
            ConnectionProperties properties = new ConnectionProperties(path);
            con = connection.getCon();
            idMax= this.getUltimateId();
            String sql = "SELECT id,phone,product_service_id,result_code as state,registered_date,to_char(registered_date,'DDMMYYYYHH24MISS') FROM mpm_transaction_packs WHERE result_code=0 and ROWNUM <=? and id>? order by id asc";
           
            pr = con.prepareStatement(sql);
            pr.setInt(1, Integer.parseInt(limit));
            pr.setLong(2, idMax);
            rs = pr.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();    
          
            while (rs.next()) {
                String[] column = new String[columnsNumber];  
                Subscription subscription = new Subscription();  
                subscription.setId(rs.getLong(1));
                subscription.setPhone(rs.getLong(2));
                subscription.setValue(rs.getInt(3));
                subscription.setState(rs.getInt(4));
                subscription.setDateRegistered(rs.getTimestamp(5));
                subscription.setDateString(rs.getString(6)); 
                subscriptions.add(subscription);
            }
                      
        } catch (SQLException ex) {
            System.out.println("Error SQL"+" "+ex); 
        }
          pr.close();        
          rs.close();
         return subscriptions;
     }
    
    public void insertPack(ArrayList<Subscription> list,String path,DriverConnection connection) {
          int cont =  0;
          String sql="";
          ArrayList<Subscription> subscriptions = new ArrayList<Subscription>();
          SimpleDateFormat dateFormat =  new SimpleDateFormat ("dd-MM-yy hh:mm:ss a");   
        try {           
            ConnectionProperties properties = new ConnectionProperties(path);    
            while (list.size()>cont) { 
            
            sql = "INSERT INTO tb_zed_packs (ID,PHONE,PRODUCT_SERVICE_ID,STATE,DATE_REGISTERED,DATE_SEND) "
                    + "VALUES (?,?,?,?, TO_DATE(?, 'DD-MM-YY HH12:MI:SS AM')"+",?)";   
            
            con = connection.getCon();
            pr = con.prepareStatement(sql);
            pr.setLong(1, list.get(cont).getId());
            pr.setLong(2, list.get(cont).getPhone());
            pr.setInt(3, list.get(cont).getValue());
            pr.setInt(4, list.get(cont).getState());     
            pr.setString(5, dateFormat.format(list.get(cont).getDateRegistered()));
            pr.setString(6, list.get(cont).getDateString());
            pr.executeUpdate();    
            cont++;
            }           
           
            connection.getCon().commit();
           
        } catch (SQLException ex) {
            System.out.println("Error SQL"+" "+ex);
            System.out.println("Rolling back data here.....");
            try{
		 if(con!=null)
                 con.rollback();
            }catch(SQLException se2){
         se2.printStackTrace();
      }//end try
            
        }finally {
            if (con != null) {
                try {
                   System.out.println("Insert");
                     pr.close();                    
                     rs.close();
                     con.close();
                } catch (Exception ignored) {
                    // ignore
                }

            }
        }
        
     }
    
    public Long getUltimateId() {
          ArrayList<String[]> maxId = new ArrayList<String[]>();
          String val = "";
          Long idMax = null;
        try {          
          
            String sql = "select max(id) from tb_zed_packs";
            pr = con.prepareStatement(sql);            
            rs = pr.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();          
            
            while (rs.next()) {
               String[] column = new String[columnsNumber];  
                int cont=0;
               while (columnsNumber >cont) {
                idMax = rs.getLong(cont+1);
                cont++;
               }
                
            }
           
           
        } catch (SQLException ex) {
            System.out.println("Error SQL"+" "+ex);        
        }
        try {        
            pr.close();
            rs.close();
        } catch (SQLException ex) {
          System.out.println("Error result SQL getUltimateId "+ex);
        }
        
         return idMax;
     }
    
    
     
     
  }
