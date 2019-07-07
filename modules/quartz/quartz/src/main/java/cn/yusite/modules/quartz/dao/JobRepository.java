package cn.yusite.modules.quartz.dao;

import cn.yusite.common.repository.BaseRepository;
import cn.yusite.modules.quartz.entity.Job;
import org.springframework.stereotype.Repository;

/**
 * Created by shijie on 2018/8/2.
 */
@Repository
public interface JobRepository extends BaseRepository<Job,String> {
}
