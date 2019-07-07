/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package cn.yusite.modules.test.client;

import cn.yusite.autoconfigure.cloud.feign.FeignConfiguration;
import cn.yusite.modules.test.api.TestDataServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 如果
 * Created by shijie on 2019/4/2
 */
@FeignClient( serviceId = "yusite-cloud-module-test1",configuration = FeignConfiguration.class)
public interface TestDataServiceClient extends TestDataServiceApi {
	
}
