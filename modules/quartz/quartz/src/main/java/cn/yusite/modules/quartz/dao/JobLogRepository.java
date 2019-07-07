package cn.yusite.modules.quartz.dao;

import cn.yusite.common.repository.BaseRepository;
import cn.yusite.modules.quartz.entity.JobLog;
import org.springframework.stereotype.Repository;

/**
 * Created by shijie on 2018/8/2.
 */
@Repository
public interface JobLogRepository extends BaseRepository<JobLog,String> {
}
