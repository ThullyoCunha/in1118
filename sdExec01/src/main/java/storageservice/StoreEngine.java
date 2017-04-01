package storageservice;

import java.io.IOException;

/**
 * Created by tjamir on 30/03/17.
 */
public interface StoreEngine {

    public ValueObject get(String key) throws IOException;


    void save(String key, ValueObject valueObject) throws IOException;
}
