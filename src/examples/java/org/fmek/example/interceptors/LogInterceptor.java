package org.fmek.example.interceptors;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@Log
public class LogInterceptor {

    @Inject
    private org.apache.commons.logging.Log log;

    @AroundInvoke
    public Object logInvocation(final InvocationContext ic) throws Exception {
        StringBuilder sb = new StringBuilder("(");
        for (Object obj : ic.getParameters()) {
            sb.append(obj.toString());
            sb.append(", ");
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(")");
        String message = ">>> " + ic.getTarget().getClass().getSuperclass().getSimpleName() +
                "." + ic.getMethod().getName() + sb.toString();
        log.info(message);

        return ic.proceed();
    }

}
