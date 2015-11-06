package org.fmek.test;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * Created by thma on 06.11.2015.
 */

@Interceptor
@Autocorrection
public class AutocorrectionInterceptor {

    @AroundInvoke
    public Object correct(final InvocationContext ic) throws Exception {
        return 42;
    }
}
