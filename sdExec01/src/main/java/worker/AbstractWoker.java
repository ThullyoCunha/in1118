package worker;

import java.io.IOException;

/**
 * Created by tjamir on 30/03/17.
 */
public interface AbstractWoker {
    public abstract byte[] handle(byte [] param) throws ClassNotFoundException, IOException;

}
