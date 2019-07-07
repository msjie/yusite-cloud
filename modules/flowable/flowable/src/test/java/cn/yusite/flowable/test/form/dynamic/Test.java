package cn.yusite.flowable.test.form.dynamic;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.form.FormProperty;
import org.flowable.engine.form.StartFormData;
import org.flowable.engine.form.TaskFormData;
import org.flowable.engine.history.HistoricDetail;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.engine.test.FlowableRule;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.junit.Rule;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * 内置表单
 * 表单内容以key和value的形式保存在引擎表中
 */
@Slf4j
public class Test {

    private String filename = "/Users/shijie/Documents/framework/yusite-cloud/modules/flowable/flowable/src/test/java/cn/yusite/flowable/test/form/dynamic/test1.bpmn";

    @Rule
    public FlowableRule activitiRule = new FlowableRule();


    @org.junit.Test
    public void deploymentProcess() throws Exception {
        RepositoryService repositoryService = activitiRule.getRepositoryService();
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        Deployment risk = deploymentBuilder.category("risk")
                .addInputStream("test1.bpmn20.xml", new FileInputStream(filename))
                .deploy();
        System.out.println("部署ID：" + risk.getId());
        System.out.println("部署名称：" + risk.getName());
        //risk_youxing
        //获取最新的流程定义id
//		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("DymaticForm").latestVersion().singleResult();
//		FormService formService = activitiRule.getFormService();
//		//获取流程开始表单
//		StartFormData startFormData = formService.getStartFormData(processDefinition.getId());
//		//定义的formItem
//        List<FormProperty> formProperties1 = startFormData.getFormProperties();
//        assertNull(startFormData.getFormKey());
//
//		Map<String, String> formProperties = new HashMap<String, String>();
//		formProperties.put("name", "HenryYan");
//
//		//根据流程定义id，以及开始表单数据，启动流程，返回流程实例
//		ProcessInstance processInstance = formService.submitStartFormData(processDefinition.getId(), formProperties);
//		assertNotNull(processInstance);
//
//		// 运行时变量
//		RuntimeService runtimeService = activitiRule.getRuntimeService();
//		//根据流程实例id，获取变量
//		Map<String, Object> variables = runtimeService.getVariables(processInstance.getId());
//		assertEquals(variables.size(), 1);
//		Set<Entry<String, Object>> entrySet = variables.entrySet();
//		for (Entry<String, Object> entry : entrySet) {
//			System.out.println(entry.getKey() + "=" + entry.getValue());
//		}
//
//		// 历史记录
//		HistoryService historyService = activitiRule.getHistoryService();
//		List<HistoricDetail> list = historyService.createHistoricDetailQuery().formProperties().list();
//		assertEquals(1, list.size());
//
//		// 获取第一个节点
//		TaskService taskService = activitiRule.getTaskService();
//		Task task = taskService.createTaskQuery().singleResult();
//		assertEquals("First Step", task.getName());
//
//		TaskFormData taskFormData = formService.getTaskFormData(task.getId());
//		assertNotNull(taskFormData);
//		assertNull(taskFormData.getFormKey());
//		List<FormProperty> taskFormProperties = taskFormData.getFormProperties();
//		assertNotNull(taskFormProperties);
//		for (FormProperty formProperty : taskFormProperties) {
//			System.out.println(ToStringBuilder.reflectionToString(formProperty));
//		}
//		formProperties = new HashMap<String, String>();
//		formProperties.put("setInFirstStep", "01/12/2012");
//		//提交任务
//		formService.submitTaskFormData(task.getId(), formProperties);
//
//		// 获取第二个节点
//		task = taskService.createTaskQuery().taskName("Second Step").singleResult();
//		assertNotNull(task);
//		taskFormData = formService.getTaskFormData(task.getId());
//		assertNotNull(taskFormData);
//		List<FormProperty> formProperties2 = taskFormData.getFormProperties();
//		assertNotNull(formProperties2);
//		assertEquals(1, formProperties2.size());
//		assertNotNull(formProperties2.get(0).getValue());
//		assertEquals(formProperties2.get(0).getValue(), "01/12/2012");
    }


    /**
     * 获取流程启动表单
     */
    @org.junit.Test
    public void getStartForm() {

        RepositoryService repositoryService = activitiRule.getRepositoryService();
        //根据流程定义key，获取数据库最新版本流程定义。
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("risk_youxing").latestVersion().singleResult();
        FormService formService = activitiRule.getFormService();
//		//获取流程开始表单
        StartFormData startFormData = formService.getStartFormData(processDefinition.getId());
        List<FormProperty> formProperties = startFormData.getFormProperties();
        formProperties.forEach(item -> {
            System.out.println(item.getType());
        });
        //返回流程定义id,以及表单
    }

    /**
     * 新增
     * 表单方式提交
     * 根据流程定义表单，以及流程定义id：启动流程
     * 检录业务与流程的双向关联关系
     */
    @org.junit.Test
    public void startProcessByForm() {

        String processDefinitionId = "risk_youxing:4:22504";//流程定义id
        String businessKey = "a-1";//业务key
        FormService formService = activitiRule.getFormService();
        Map<String, String> formProperties = new HashMap<String, String>();
        formProperties.put("user", "maoshijie");//表单数据
        ProcessInstance processInstance = formService.submitStartFormData(processDefinitionId, businessKey, formProperties);
        String processInstanceId = processInstance.getProcessInstanceId();//25001流程实例
        //返回流程个实例id，绑定到业务表单中
        System.out.println(processInstanceId);
    }

    /**
     * 我发起的（已提交）
     */
    @org.junit.Test
    public void getMyLaunch() {


    }

    /**
     * 任务(代办)
     */
    @org.junit.Test
    public void getMyAgencyTask() {

        TaskService taskService = activitiRule.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser("panghuiyun").list();
        tasks.forEach(item -> {
            String processInstanceId = item.getProcessInstanceId();//获取流程实例ID，即可定位关联业务
            String taskId = item.getId();
        });
    }

    /**
     * 已提交
     * 任务(已办)
     */
    @org.junit.Test
    public void getMySubmittedTask() {

        String assignee="";
        HistoryService historyService = activitiRule.getHistoryService();
        HistoricTaskInstanceQuery historicTaskInstanceQuery =
                historyService.createHistoricTaskInstanceQuery();
        List<HistoricTaskInstance> list = historicTaskInstanceQuery.taskAssignee(assignee).list();
        list.forEach(historicTaskInstance -> {
            String processInstanceId = historicTaskInstance.getProcessInstanceId();//即可关联业务
        });
    }

    /**
     * 抄送
     * 抄送，制定督办人员
     */
    @org.junit.Test
    public void getCcTask() {
    }

    /**
     * 根据任务id，获取当前任务表单
     */
    @org.junit.Test
    public void getTaskForm() {

        String taskId = "";
        FormService formService = activitiRule.getFormService();
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        List<FormProperty> formProperties = taskFormData.getFormProperties();
        formProperties.forEach(formProperty -> {
            System.out.println(formProperty.getName());
        });
    }

    /**
     * 完成我的任务
     */
    @org.junit.Test
    public void completeMyTask() {

        TaskService taskService = activitiRule.getTaskService();
        String taskId = "2507";
        String comment = "增加批注1";
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Map<String, Object> formProperties = new HashMap<String, Object>();
        formProperties.put("report", true);
        formProperties.put("user", "panghuiyun");
        taskService.addComment(taskId, task.getProcessInstanceId(), comment);
        taskService.complete(taskId, formProperties);
    }

    /**
     * 强制销毁流程
     * 根据流程实例ID，强制销毁任务，并增加销毁原因
     */
    @org.junit.Test
    public void stopProcess() {

        String processInstanceId = "17501";
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        runtimeService.deleteProcessInstance(processInstanceId, "ceshi");
    }

    /**
     * 根据流程实例id，获取当前流程图
     */
    @org.junit.Test
    public void getProcessImage() throws IOException {

        String processInstanceId = "25001";
        ProcessEngine processEngine = activitiRule.getProcessEngine();
        ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator processDiagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        RepositoryService repositoryService = activitiRule.getRepositoryService();
        HistoryService historyService = activitiRule.getHistoryService();
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        String processDefinitionId = historicProcessInstance.getProcessDefinitionId();
        //根据流程实例id，查询流称定义id
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        //获取当前实例正在执行的节点
        List<Execution> list = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).list();
        List<String> collect = list.stream()
                .map(execution -> runtimeService.getActiveActivityIds(execution.getId()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        InputStream png = processDiagramGenerator.generateDiagram(bpmnModel,
                "png",
                collect,
                new ArrayList<String>(),
                processEngineConfiguration.getActivityFontName(),
                processEngineConfiguration.getLabelFontName(),
                processEngineConfiguration.getAnnotationFontName(),
                processEngineConfiguration.getClassLoader(),
                1.0,
                false);
        //将图片保存到本地
        File file = new File("/Users/shijie/Downloads/1.png");
        FileUtils.copyInputStreamToFile(png, file);
    }
}