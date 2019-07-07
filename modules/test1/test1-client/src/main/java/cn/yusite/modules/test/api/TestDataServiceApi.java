package cn.yusite.modules.test.api;

import cn.yusite.common.rest.CrudServiceRest;
import cn.yusite.modules.test.entity.TestData;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 定义内部
 * Created by shijie on 2019/4/2
 */
@RequestMapping(value = "/api/test1/testData")
public interface TestDataServiceApi extends CrudServiceRest<TestData> {
}
