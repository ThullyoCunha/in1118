package rpc;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tjamir on 30/03/17.
 */
public class Call implements Serializable{

    private String method;

    private List<Serializable> params;


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Serializable> getParams() {
        return params;
    }

    public void setParams(List<Serializable> params) {
        this.params = params;
    }
}
