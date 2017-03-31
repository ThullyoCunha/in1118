package rpc;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tjamir on 30/03/17.
 */
public class ServiceProxy<T> implements InvocationHandler {

    private T service;

    private Class<T> serviceInterface;


    private CallInvoker callInvoker;

    public void setServiceInterface(Class<T> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public void setCallInvoker(CallInvoker callInvoker) {
        this.callInvoker = callInvoker;
    }

    public T getService() {
        return service;
    }

    public void init(){
        service = (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class[] {serviceInterface}, this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] params) throws Throwable {

        Call call=new Call();
        call.setMethod(method.getName());
        List<Serializable> paramsList = new ArrayList<>();
        for(Object param: params){
            if(!(param instanceof Serializable)){
                throw new Exception(String.format("%s is not Serializable", param.getClass().toString()));
            }
            paramsList.add((Serializable) param);
        }
        call.setParams(paramsList);

        CallResult callResult=callInvoker.invoke(call);
        if(!callResult.isSuccess()){
            throw callResult.getError();
        }
        return callResult.getReturnValue();
    }
}
