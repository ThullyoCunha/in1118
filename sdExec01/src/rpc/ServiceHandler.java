package rpc;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tjamir on 30/03/17.
 */
public class ServiceHandler<T> implements CallInvoker{



    private T service;

    private Class<?> serviceInterface;

    public ServiceHandler(T service, Class<?> serviceInterface) {
        this.service = service;
        this.serviceInterface = serviceInterface;
    }

    @Override
    public CallResult invoke(Call call){
        CallResult callResult = new CallResult();

        try {
           Class<?>[] parameterTypes= new Class<?>[call.getParams().size()];
           for(int i=0;i<call.getParams().size();i++){
               parameterTypes[i] = call.getParams().get(i).getClass();
           }
            Method method=serviceInterface.getMethod(call.getMethod(), parameterTypes);
            Object retvalue = method.invoke(service, call.getParams().toArray());
            callResult.setSuccess(true);
            callResult.setReturnValue((Serializable) retvalue);
        } catch (Throwable e) {
            callResult.setSuccess(false);
            callResult.setError(e);
        }

        return callResult;

    }
}
