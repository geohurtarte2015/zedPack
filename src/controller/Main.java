
package controller;

import Pojo.Subscription;
import configuration.ConnectionProperties;
import configuration.FunctionProperties;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import java.util.logging.Level;
import java.util.logging.Logger;
import model.ConnectionBd;
import model.DriverConnection;


public class Main {

    private static String pathConfig = "";    

  
    public static void main(String[] args) throws InterruptedException, SQLException {   
        
            if (args != null && args.length > 0) {
                int cont=0;
                int size=0;
                int sizeZero=0;
              
                boolean active=true;
                pathConfig = String.valueOf(args[0]);
                System.out.println("Aceptando parametros "+pathConfig);
                ConnectionProperties properties = new ConnectionProperties(pathConfig);
                
                //ConnectionBd connectionBd = new ConnectionBd();                
                String[] parameters = properties.getParameters().split(";");
                ConnectionBd connectionBd = new ConnectionBd(); 
                                               
                do{                     
                    DriverConnection driverConnection = new DriverConnection(properties);
                    //get arrayList data
                    ArrayList<Subscription> resultSelect =  connectionBd.getPendientFiles(properties.getLimit(),pathConfig,parameters,driverConnection);
                    
                    
                    size = resultSelect.size();
                    
                    if(size!=0){
                    sizeZero=0;    
                    cont++;
                    
          
                  
                    
                 //send to server Post parameters
                 ArrayList<Subscription> resultSend = new ServiceConnectionZed().sendArrayPost(resultSelect, properties.getUrl(), parameters);
                   
                 //insert data 
                 connectionBd.insertPack(resultSend, pathConfig,driverConnection);  
                                 
                    }else{//count the number of attempts to search for data
                        
                        sizeZero++;
                        System.out.println("There are not changes in table, attempt number. "+sizeZero+"/3");
                        TimeUnit.SECONDS.sleep(Integer.parseInt(properties.getSleep()));
                        if(sizeZero>=3){
                          System.out.println("Finished process, not changes in table");
                          active=false;
                            
                    
                        }
                    }
                    
                }while(active);
               
            }   
}
    

   
    
    
   
}



