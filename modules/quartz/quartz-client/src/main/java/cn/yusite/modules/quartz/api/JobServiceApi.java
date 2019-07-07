package cn.yusite.modules.quartz.api;

import cn.yusite.common.rest.CrudServiceRest;
import cn.yusite.modules.quartz.entity.Job;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 定义内部
 * Created by shijie on 2019/4/2
 */
@RequestMapping(value = "/api/test1/testData")
public interface JobServiceApi extends CrudServiceRest<Job> {
}
