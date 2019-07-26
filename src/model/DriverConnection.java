/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import configuration.ConnectionProperties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DriverConnection {
    private Connection con = null;
    private PreparedStatement pr = null;
    private Statement ps = null;
    
    public DriverConnection(ConnectionProperties properties){
        
        try {
            this.con = DriverManager.getConnection(properties.getUrlconnection(), properties.getUser(), properties.getPassword());
        } catch (SQLException ex) {
            System.out.println("Error connection parameters login"+ex);
        }
    }

    
    public Connection getCon() {
        return con;
    }

  
    public void setCon(Connection con) {
        this.con = con;
    }

    /**
     * @return the pr
     */
    public PreparedStatement getPr() {
        return pr;
    }

    /**
     * @param pr the pr to set
     */
    public void setPr(PreparedStatement pr) {
        this.pr = pr;
    }

    /**
     * @return the ps
     */
    public Statement getPs() {
        return ps;
    }

    /**
     * @param ps the ps to set
     */
    public void setPs(Statement ps) {
        this.ps = ps;
    }

}