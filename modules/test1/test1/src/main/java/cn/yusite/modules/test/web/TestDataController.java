package cn.yusite.modules.test.web;

import cn.yusite.modules.test.entity.TestData;
import cn.yusite.modules.test.service.TestDataService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 前后端分离，服务只提供数据，不参与视图渲染
 * Created by shijie on 2019/4/2
 */
@RestController
@RequestMapping(value = "/testData")
public class TestDataController {

    @Autowired
    TestDataService testDataService;

    /**
     * 分页查询
     * @param antdPage
     * @param model
     * @return
     */
    @GetMapping(value = "queryByPage")
    public Page<TestData> queryByPage(PageRequest pageRequest, TestData testData) {

        return testDataService.findPage(testData,pageRequest);
    }

    /**
     * 保存
     *
     * @param model
     * @return
     */
    @PostMapping("")
    public void save(@RequestBody TestData testData) {

        testDataService.save(testData);
    }

    /**
     * 修改
     *
     * @param demo
     * @return
     */
    @PatchMapping("")
    public void edit(@RequestBody TestData testData) {

        testDataService.update(testData);
    }

    /**
     * 删除，批量
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public void delete(TestData testData) {

        testDataService.delete(testData);
    }

    /**
     * 根据主键获取记录
     *
     * @param id
     * @return
     */
    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public TestData queryById(String id) {

        return testDataService.get(id);
    }

}
