package worker;

import java.io.IOException;

/**
 * Created by tjamir on 30/03/17.
 */
public abstract class AbstractWoker {
    public abstract byte[] handle(byte [] param) throws ClassNotFoundException, IOException;

    public abstract void  open() throws Exception;

    public abstract void close() throws Exception;

    public abstract void start();

    public abstract void stop() throws Exception;
}
