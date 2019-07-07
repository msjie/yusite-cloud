package cn.yusite.flowable.test.form.external;

import java.util.HashMap;
import java.util.Map;

import org.flowable.engine.impl.test.PluggableFlowableTestCase;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.test.Deployment;
import org.flowable.task.api.Task;

public class ProcessTestFormKey extends PluggableFlowableTestCase {

	@Deployment(resources = { "diagrams/form/FormKey.bpmn20.xml", "diagrams/form/start.form", "diagrams/form/first-step.form", "diagrams/form/second-step.form" })
	public void testTaskFormsWithVacationRequestProcess() {

		// Get start form
		String procDefId = repositoryService.createProcessDefinitionQuery().singleResult().getId();
		Object startForm = formService.getRenderedStartForm(procDefId);
		assertNotNull(startForm);
		
		assertEquals("<input id=\"start-name\" />", startForm);
		
		Map<String, String> formProperties = new HashMap<String, String>();
		formProperties.put("startName", "HenryYan");
		ProcessInstance processInstance = formService.submitStartFormData(procDefId, formProperties);
		assertNotNull(processInstance);
		
		Task task = taskService.createTaskQuery().taskAssignee("user1").singleResult();
		Object renderedTaskForm = formService.getRenderedTaskForm(task.getId());
		assertEquals("<input id=\"start-name\" value=\"HenryYan\" />\n<input id=\"first-name\" />", renderedTaskForm);
		
		formProperties = new HashMap<String, String>();
		formProperties.put("firstName", "kafeitu");
		formService.submitTaskFormData(task.getId(), formProperties);
		
		task = taskService.createTaskQuery().taskAssignee("user2").singleResult();
		assertNotNull(task);
		renderedTaskForm = formService.getRenderedTaskForm(task.getId());
		assertEquals("<input id=\"first-name\" value=\"kafeitu\" />", renderedTaskForm);
	}
}