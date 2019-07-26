package Pojo;


import java.util.Date;


public class Subscription {
    
    private Long Id;
    private Long phone;
    private int value;
    private int state;    
    private String dateString;
    private Date dateRegistered;


    public Long getId() {
        return Id;
    }

   
    public void setId(Long Id) {
        this.Id = Id;
    }

 
    public Long getPhone() {
        return phone;
    }

   
    public void setPhone(Long phone) {
        this.phone = phone;
    }

 
    public int getValue() {
        return value;
    }


    public void setValue(int value) {
        this.value = value;
    }
 
    
    public Date getDateRegistered() {
        return dateRegistered;
    }


    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

 
    public int getState() {
        return state;
    }

    
    public void setState(int state) {
        this.state = state;
    }
    
    
    public String getSubscription(){
        String valSubscrition=null; 
        valSubscrition=this.Id+"|"+this.phone+"|"+this.state+"|"+this.value+"|"+this.dateRegistered;
        return valSubscrition;
    }

    /**
     * @return the dateString
     */
    public String getDateString() {
        return dateString;
    }

    /**
     * @param dateString the dateString to set
     */
    public void setDateString(String dateString) {
        this.dateString = dateString;
    }
    
    
}
