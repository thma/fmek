package com.schlimm.springcdi.interceptor.processor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.ReflectionUtils;

import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;

/**
 * CGLIB {@link MethodInterceptor} to JSR318 interceptor bridge.
 *
 * @author Niklas Schlimm
 */
public class InterceptorMethodAdapter implements MethodInterceptor {

    private Method jsr318InterceptorMethod;

    private Object interceptor;

    public InterceptorMethodAdapter(Method interceptorMethod, Object interceptor) {
        super();
        this.jsr318InterceptorMethod = interceptorMethod;
        this.interceptor = interceptor;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        InvocationContext context = new MethodInvocationWrapper(invocation);
        Object retVal = ReflectionUtils.invokeMethod(jsr318InterceptorMethod, interceptor, context);
        return retVal;
    }

    public Method getJsr318InterceptorMethod() {
        return jsr318InterceptorMethod;
    }

    public Object getInterceptor() {
        return interceptor;
    }

}
