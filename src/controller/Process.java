/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;


public class Process {
    
    private final ArrayList<String[]> resultSend;
    private final String name;
    private final int num;
    
    
    public Process(ArrayList<String[]> resultSend,String name,int num){    
    this.resultSend=resultSend;
    this.name=name;
    this.num=num;    
    }    

  
    public ArrayList<String[]> getResultSend() {
        return resultSend;
    }

  
    public String getName() {
        return name;
    }

   
    public int getNum() {
        return num;
    }
    
}
