package org.fmek;

import com.schlimm.springcdi.interceptor.InterceptorAwareBeanFactoryPostProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * Created by thma on 06.11.2015.
 */

@Configuration
@ComponentScan(includeFilters = @ComponentScan.Filter(value = javax.interceptor.Interceptor.class, type = FilterType.ANNOTATION))
public class CdiInterceptorConfiguration {

    private static final Log log = LogFactory.getLog("FMEK");

    @Bean
    public static InterceptorAwareBeanFactoryPostProcessor javaxInterceptorPostProcessor() {
        log.info("JSR-299/318 'javax.interceptor.Interceptor' annotation supported");
        return new InterceptorAwareBeanFactoryPostProcessor();
    }
}
