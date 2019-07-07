package cn.yusite.modules.test.dao;

import cn.yusite.common.repository.BaseRepository;
import cn.yusite.modules.test.entity.TestData;
import org.springframework.stereotype.Repository;

/**
 * Created by shijie on 2018/8/2.
 */
@Repository
public interface TestDataRepository extends BaseRepository<TestData,String> {
}
