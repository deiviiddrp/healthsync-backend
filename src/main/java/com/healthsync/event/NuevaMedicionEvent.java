package com.healthsync.event;

import com.healthsync.model.ParametroVital;
import org.springframework.context.ApplicationEvent;

public class NuevaMedicionEvent extends ApplicationEvent {

    private final ParametroVital parametroVital;

    public NuevaMedicionEvent(Object source, ParametroVital parametroVital) {
        super(source);
        this.parametroVital = parametroVital;
    }

    public ParametroVital getParametroVital() {
        return parametroVital;
    }
}