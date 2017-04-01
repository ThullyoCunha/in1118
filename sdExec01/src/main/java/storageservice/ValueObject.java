package storageservice;

import java.io.Serializable;

/**
 * Created by tjamir on 30/03/17.
 */
public class ValueObject implements Serializable{

    private long version;

    private byte[] content;


    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
