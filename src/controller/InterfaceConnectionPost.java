
package controller;

import Pojo.Subscription;
import java.util.ArrayList;

public interface InterfaceConnectionPost {
    
    public int sendPostHttp(String url,String[] paramsArray,String[] paramVal);
    
}
