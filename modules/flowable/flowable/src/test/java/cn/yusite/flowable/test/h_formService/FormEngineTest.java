package cn.yusite.flowable.test.h_formService;


import org.flowable.engine.FormService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.form.StartFormData;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.form.engine.FormEngineConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

/**
 * Created by shijie on 2019/3/5
 */
public class FormEngineTest {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署
     */
    @Test
    public void deployment() {

        FormEngineConfiguration standaloneFormEngineConfiguration =
                FormEngineConfiguration.createStandaloneFormEngineConfiguration();
        FormRepositoryService formRepositoryService = standaloneFormEngineConfiguration.getFormRepositoryService();
        formRepositoryService.createDeployment().addClasspathResource("processes/form/test.form").name("testForm").deploy();
    }

    @Test
    public void test() {

        FormService formService = processEngine.getFormService();
        String s = formService.toString();
        StartFormData startFormData = formService.getStartFormData("");
    }

}
