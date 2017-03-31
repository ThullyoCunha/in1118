package storageservice;

import java.io.IOException;

/**
 * Created by tjamir on 30/03/17.
 */
public class LocalStorageServiceImpl implements StorageService{



    private StoreEngine storeEngine;

    public StoreEngine getStoreEngine() {
        return storeEngine;
    }

    public void setStoreEngine(StoreEngine storeEngine) {
        this.storeEngine = storeEngine;
    }

    public LocalStorageServiceImpl(){
        setStoreEngine(new FileSystemStoreEngineImpl());
    }


    @Override
    public ValueObject storePair(String key, ValueObject value) throws ConsistencyException {

        try {
            ValueObject previous=storeEngine.get(key);

            if(previous!=null &&previous.getVersion()>value.getVersion()){
                throw new ConsistencyException("Current version does not match");
            }
            value.setVersion(System.currentTimeMillis());
            storeEngine.save(key, value);
            return value;
        } catch (IOException e) {
            throw new StorageAccessException(e);
        }
    }

    @Override
    public ValueObject getValue(String key) {
        try {
            return storeEngine.get(key);
        } catch (IOException e) {
            throw new StorageAccessException(e);
        }
    }
}
