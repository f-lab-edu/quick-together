package com.flab.quicktogether.dummy;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DummyDataInitializer implements InitializingBean {

    @Autowired
    private ProjectDummyService projectDummyService;

    @Override
    public void afterPropertiesSet() throws Exception {
        //projectDummyService.insertDummyProjects(100);
    }

}