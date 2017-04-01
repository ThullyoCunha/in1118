package storageservice;

/**
 * Created by tjamir on 30/03/17.
 */
public interface StorageService {


    public ValueObject storePair(String key, ValueObject value) throws ConsistencyException;

    public ValueObject getValue(String key);


}
