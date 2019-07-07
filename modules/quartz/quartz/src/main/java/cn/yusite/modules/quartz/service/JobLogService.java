package cn.yusite.modules.quartz.service;

import cn.yusite.common.service.CrudService;
import cn.yusite.modules.quartz.api.JobLogServiceApi;
import cn.yusite.modules.quartz.client.JobLogClient;
import cn.yusite.modules.quartz.dao.JobLogRepository;
import cn.yusite.modules.quartz.entity.Job;
import cn.yusite.modules.quartz.entity.JobLog;
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
public class JobLogService extends CrudService<JobLogRepository,JobLog> implements JobLogServiceApi {

    @Override
    public List<JobLog> findList(JobLog var1) {


        return null;
    }

    @Override
    public Page<JobLog> findPage(PageRequest var1, JobLog var2) {


        return null;
    }

    @Override
    public void delete(JobLog var1) {
        super.delete(var1);
    }

    @Override
    public JobLog insert(JobLog var1) {
        return super.insert(var1);
    }

    @Override
    public JobLog update(JobLog var1) {
        return super.update(var1);
    }

    @Override
    public JobLog get(String id) {
        return super.get(id);
    }
}
