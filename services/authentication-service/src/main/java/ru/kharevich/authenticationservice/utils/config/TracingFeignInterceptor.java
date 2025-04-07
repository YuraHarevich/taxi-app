package ru.kharevich.authenticationservice.utils.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.propagation.Propagator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TracingFeignInterceptor implements RequestInterceptor {

    @Autowired
    private Tracer tracer;

    @Autowired
    private Propagator propagator;

    @Override
    public void apply(RequestTemplate template) {
        propagator.inject(tracer.currentTraceContext().context(), template,
                (carrier, key, value) -> carrier.header(key, value));
    }

}
