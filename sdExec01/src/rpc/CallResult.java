package rpc;

import java.io.Serializable;

/**
 * Created by tjamir on 30/03/17.
 */
public class CallResult implements Serializable{

    private Serializable returnValue;

    private boolean success;

    private Throwable error;

    public Serializable getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Serializable returnValue) {
        this.returnValue = returnValue;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
