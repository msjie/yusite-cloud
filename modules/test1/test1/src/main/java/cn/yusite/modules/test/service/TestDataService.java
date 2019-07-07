package cn.yusite.modules.test.service;

import cn.yusite.common.service.QueryService;
import cn.yusite.modules.test.api.TestDataServiceApi;
import cn.yusite.modules.test.dao.TestDataRepository;
import cn.yusite.modules.test.entity.TestData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 内部api实现，内部调用
 * Created by shijie on 2019/4/2
 */
@Slf4j
@Service
@RestController
@Transactional(readOnly=true)
public class TestDataService implements TestDataServiceApi {

    @Autowired
    TestDataRepository testDataRepository;

    @Override
    public void update(TestData var1) {

    }

    @Override
    public void save(TestData var1) {

    }

    @Override
    public void delete(TestData var1) {

    }

    @Override
    public List<TestData> findList(TestData testData) {
        return null;
    }

    @Override
    public Page<TestData> findPage(TestData testData, PageRequest pageRequest) {
        List<TestData> all = testDataRepository.findAll();
        log.info("find all success");
        return null;
    }

    @Override
    public TestData get(String var1) {
        return null;
    }

}
