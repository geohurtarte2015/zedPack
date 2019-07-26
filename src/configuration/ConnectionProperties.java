/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConnectionProperties {
    
    private  String user;
    private  String password;
    private  String urlconnection;
    private  String limit;
    private  String sleep;
    private  String parameters;
    private  String url;

    
    public ConnectionProperties(String pathConfig){   
        
        try {
            FunctionProperties properties = new FunctionProperties();
            user = properties.getProperties(pathConfig,"userdb");
            password = properties.getProperties(pathConfig,"passdb");
            urlconnection = properties.getProperties(pathConfig,"urltns");
            limit = properties.getProperties(pathConfig,"limit");   
            url = properties.getProperties(pathConfig,"url");    
            parameters = properties.getProperties(pathConfig,"parameters"); 
            sleep=properties.getProperties(pathConfig,"sleep"); 
        } catch (IOException ex) {
            System.out.println("Error read file properties"+" "+ex);            
        }
    
    }


    public String getUser() {
        return user;
    }

 
    public String getPassword() {
        return password;
    }
 
    public String getLimit() {
        return limit;
    }


    public String getUrlconnection() {
        return urlconnection;
    }

   
    public String getParameters() {
        return parameters;
    }


    public String getUrl() {
        return url;
    }


    public String getTime() {
        return getSleep();
    }

    /**
     * @return the sleep
     */
    public String getSleep() {
        return sleep;
    }


    
}
