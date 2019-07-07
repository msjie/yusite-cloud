/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package cn.yusite.modules.quartz.client;

import cn.yusite.autoconfigure.cloud.feign.FeignConfiguration;
import cn.yusite.modules.quartz.api.JobLogServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by shijie on 2019/4/2
 */
@FeignClient( serviceId = "yusite-cloud-module-quartz",configuration = FeignConfiguration.class)
public interface JobLogClient extends JobLogServiceApi {
	
}
