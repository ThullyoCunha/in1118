package storageservice;

/**
 * Created by tjamir on 30/03/17.
 */
public class ConsistencyException extends Exception {

    public ConsistencyException() {
    }

    public ConsistencyException(String s) {
        super(s);
    }

    public ConsistencyException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ConsistencyException(Throwable throwable) {
        super(throwable);
    }

    public ConsistencyException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
