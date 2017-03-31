package storageservice;

/**
 * Created by tjamir on 30/03/17.
 */
public class StorageAccessException extends RuntimeException{

    public StorageAccessException() {
    }

    public StorageAccessException(String s) {
        super(s);
    }

    public StorageAccessException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public StorageAccessException(Throwable throwable) {
        super(throwable);
    }

    public StorageAccessException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
