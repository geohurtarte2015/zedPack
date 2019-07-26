/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FunctionProperties  {
    String path;
    

public void setPath(String v_path){
    this.path = v_path;
    
}
    
public String getProperties(String key) throws FileNotFoundException, IOException  {

        String versionString = null;
        //to load application's properties, we use this class
        java.util.Properties mainProperties = new java.util.Properties();

        FileInputStream file;
        //the base folder is ./, the root of the main.properties file    
        //String path = "C:\\Users\\Giovanni\\Documents\\NetBeansProjects\\cdrFile_PA\\src\\properties\\configuration.properties";
        //String path = "./configuration.properties";
        
        
        //load the file handle for main.properties
        file = new FileInputStream(path);

        //load all the properties from this file
        mainProperties.load(file);

        //we have loaded the properties, so close the file handle
        file.close();

        //retrieve the property we are intrested, the app.version
        
        versionString = mainProperties.getProperty(key);

        return versionString;
    }

public String getProperties(String path, String key) throws FileNotFoundException, IOException  {

        String versionString = null;
        //to load application's properties, we use this class
        java.util.Properties mainProperties = new java.util.Properties();

        FileInputStream file;
        //the base folder is ./, the root of the main.properties file    
        //String path = "C:\\Users\\Giovanni\\Documents\\NetBeansProjects\\cdrFile_PA\\src\\properties\\configuration.properties";
        //String path = "./configuration.properties";
        
        
        //load the file handle for main.properties
        file = new FileInputStream(path);

        //load all the properties from this file
        mainProperties.load(file);

        //we have loaded the properties, so close the file handle
        file.close();

        //retrieve the property we are intrested, the app.version
        
        versionString = mainProperties.getProperty(key);

        return versionString;
    }
    
}
