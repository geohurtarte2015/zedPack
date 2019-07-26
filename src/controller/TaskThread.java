/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Pojo.Subscription;
import configuration.ConnectionProperties;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ConnectionBd;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;


public class TaskThread extends Thread{
    
    private  Process process;
    private String[] params;
    private String url;    
    private String path;    
    private String nameTask;
    private int num;
    private Connection con = null;
    private PreparedStatement pr = null;
    private ResultSet rs = null;
    private String sql=""; 
    
    
    @Override
    public void run() {
        int resp = 0;
        System.out.println(this.nameTask + " " + this.num + " " + "start process");
        int cont = 0;
        int response = 0;
        int size = 0;
        size = process.getResultSend().size();
        
        
      
        while (size > cont) {
            
            try {
                try {
                    
                    this.wait(1);
                    int CONNECTION_TIMEOUT_MS = 1 * 1000; // Timeout in millis.
                    RequestConfig requestConfig = RequestConfig.custom()
                            .setConnectionRequestTimeout(CONNECTION_TIMEOUT_MS)
                            .setConnectTimeout(CONNECTION_TIMEOUT_MS)
                            .setSocketTimeout(CONNECTION_TIMEOUT_MS)
                            .build();
                    
                    HttpClient httpClient = HttpClientBuilder.create().build();
                    HttpPost httppost = new HttpPost(url);
                    httppost.setConfig(requestConfig);
                    
// Request parameters and other properties.
                    List<NameValuePair> paramsArray = new ArrayList<NameValuePair>(2);
                    paramsArray.add(new BasicNameValuePair(params[0], process.getResultSend().get(cont)[0]));
                    paramsArray.add(new BasicNameValuePair(params[1], process.getResultSend().get(cont)[1]));
                    paramsArray.add(new BasicNameValuePair(params[2], process.getResultSend().get(cont)[2]));
                    paramsArray.add(new BasicNameValuePair(params[3], process.getResultSend().get(cont)[4]));
                    
//            while (paramsArray.length >cont) {
//            params.add(new BasicNameValuePair(paramsArray[cont], paramVal[cont]));
//            cont++;
//            }
                    httppost.setEntity(new UrlEncodedFormEntity(paramsArray, "UTF-8"));
                    
                    //Execute and get the response.
                    HttpResponse responseHttp = null;
                    try {
                        responseHttp = httpClient.execute(httppost);
                        System.out.println("Done Send");
                        resp = responseHttp.getStatusLine().getStatusCode();
                        
                    } catch (IOException ex) {
                        System.out.println("Error connection post timeout" + ex);
                        resp = responseHttp.getStatusLine().getStatusCode();
                        
                    }
                    //HttpEntity entity = response.getEntity();
//             System.out.println("Response: "+response.getStatusLine().getStatusCode()+
//             params.get(0).getName()+": "+params.get(0).getValue()+"\n"+
//             params.get(1).getName()+": "+params.get(1).getValue()+"\n"+
//             params.get(2).getName()+": "+params.get(2).getValue()+"\n"+
//             params.get(3).getName()+": "+params.get(3).getValue()+"\n");
                    
                    switch (resp) {
                        case 200:
                            process.getResultSend().get(cont)[3] = "2";
                            break;
                        default:
                            process.getResultSend().get(cont)[3] = "3";
                    }
                    
                    cont++;
                    
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(TaskThread.class.getName()).log(Level.SEVERE, null, ex);
                    
                }
                
                
                ConnectionProperties properties = new ConnectionProperties(path);
                con = DriverManager.getConnection(properties.getUrlconnection(), properties.getUser(), properties.getPassword());
                sql = "update tb_zed set state=" + process.getResultSend().get(cont)[3] + " where id=" + process.getResultSend().get(cont)[0];
                pr = con.prepareStatement(sql);
                pr.executeUpdate();
                
                
                
                
                //HttpEntity entity = response.getEntity();
//             System.out.println("Response: "+response.getStatusLine().getStatusCode()+
//             params.get(0).getName()+": "+params.get(0).getValue()+"\n"+
//             params.get(1).getName()+": "+params.get(1).getValue()+"\n"+
//             params.get(2).getName()+": "+params.get(2).getValue()+"\n"+
//             params.get(3).getName()+": "+params.get(3).getValue()+"\n");        
                //ConnectionBd connection = new ConnectionBd();
            }// connection.getUpdateState(process.getResultSend(), path);
            catch (SQLException ex) {
                System.out.println("Error SQL" + " " + ex);
                System.out.println("Rolling back data here.....");
                try {
                    if (con != null) {
                        con.rollback();
                    }
                } catch (SQLException se2) {
                    se2.printStackTrace();
                }//end try

            } finally {
                if (con != null) {
                    try {
                        pr.close();
                        con.close();
                    } catch (Exception ignored) {
                        // ignore
                    }

                }
            }
            }
        //end while
        
        try {
            con.commit();
        }  catch (SQLException ex) {
                System.out.println("Error SQL" + " " + ex);
                System.out.println("Rolling back data here.....");
                try {
                    if (con != null) {
                        con.rollback();
                    }
                } catch (SQLException se2) {
                    se2.printStackTrace();
                }//end try

            } finally {
                if (con != null) {
                    try {
                        pr.close();
                        con.close();
                    } catch (Exception ignored) {
                        // ignore
                    }

                }
            }
         
         
         

         
    }
    
    
    private void wait(int segundos) {
		try {
			Thread.sleep(segundos * 1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
    
    public TaskThread(String[] params,String url,String path,String nameTask,int num,Process process){
        
        this.params=params;
        this.url = url;
        this.path=path;
        this.nameTask=nameTask;
        this.num=num;
        this.process=process;
    }
    
    


    public String[] getParams() {
        return params;
    }

 
    public void setParams(String[] params) {
        this.params = params;
    }

    public String getPath() {
        return path;
    }

 
    public void setPath(String path) {
        this.path = path;
    }

 
    public String getNameTask() {
        return nameTask;
    }


    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

 
    public int getNum() {
        return num;
    }

  
    public void setNum(int num) {
        this.num = num;
    }

   
    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }
    
    
    
    
    
}
