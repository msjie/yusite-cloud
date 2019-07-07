package cn.yusite.flowable.test.form.dynamic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.flowable.engine.FormService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.form.FormProperty;
import org.flowable.engine.form.StartFormData;
import org.flowable.engine.form.TaskFormData;
import org.flowable.engine.history.HistoricDetail;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.test.FlowableRule;
import org.flowable.engine.test.FlowableRule;
import org.flowable.task.api.Task;
import org.junit.Rule;
import org.junit.Test;

/**
 * 内置表单
 * 表单内容以key和value的形式保存在引擎表中
 */
public class ProcessTestDymaticForm {

	private String filename = "/Users/henryyan/work/workspaces/eclipse/activiti-5.9/src/main/resources/diagrams/form/DymaticForm.bpmn";

	@Rule
	public FlowableRule activitiRule = new FlowableRule();

	@Test
	public void startProcess() throws Exception {
		RepositoryService repositoryService = activitiRule.getRepositoryService();
		repositoryService.createDeployment().addInputStream("DymaticForm.bpmn20.xml", new FileInputStream(filename)).deploy();
		//获取最新的流程定义id
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("DymaticForm").latestVersion().singleResult();
		FormService formService = activitiRule.getFormService();
		//获取流程开始表单
		StartFormData startFormData = formService.getStartFormData(processDefinition.getId());
		//定义的formItem
        List<FormProperty> formProperties1 = startFormData.getFormProperties();
        assertNull(startFormData.getFormKey());
		
		Map<String, String> formProperties = new HashMap<String, String>();
		formProperties.put("name", "HenryYan");

		//根据流程定义id，以及开始表单数据，启动流程，返回流程实例
		ProcessInstance processInstance = formService.submitStartFormData(processDefinition.getId(), formProperties);
		assertNotNull(processInstance);
		
		// 运行时变量
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		//根据流程实例id，获取变量
		Map<String, Object> variables = runtimeService.getVariables(processInstance.getId());
		assertEquals(variables.size(), 1);
		Set<Entry<String, Object>> entrySet = variables.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}
		
		// 历史记录
		HistoryService historyService = activitiRule.getHistoryService();
		List<HistoricDetail> list = historyService.createHistoricDetailQuery().formProperties().list();
		assertEquals(1, list.size());
		
		// 获取第一个节点
		TaskService taskService = activitiRule.getTaskService();
		Task task = taskService.createTaskQuery().singleResult();
		assertEquals("First Step", task.getName());
		
		TaskFormData taskFormData = formService.getTaskFormData(task.getId());
		assertNotNull(taskFormData);
		assertNull(taskFormData.getFormKey());
		List<FormProperty> taskFormProperties = taskFormData.getFormProperties();
		assertNotNull(taskFormProperties);
		for (FormProperty formProperty : taskFormProperties) {
			//System.out.println(ToStringBuilder.reflectionToString(formProperty));
		}
		formProperties = new HashMap<String, String>();
		formProperties.put("setInFirstStep", "01/12/2012");
		//提交任务
		formService.submitTaskFormData(task.getId(), formProperties);
		
		// 获取第二个节点
		task = taskService.createTaskQuery().taskName("Second Step").singleResult();
		assertNotNull(task);
		taskFormData = formService.getTaskFormData(task.getId());
		assertNotNull(taskFormData);
		List<FormProperty> formProperties2 = taskFormData.getFormProperties();
		assertNotNull(formProperties2);
		assertEquals(1, formProperties2.size());
		assertNotNull(formProperties2.get(0).getValue());
		assertEquals(formProperties2.get(0).getValue(), "01/12/2012");
	}
}