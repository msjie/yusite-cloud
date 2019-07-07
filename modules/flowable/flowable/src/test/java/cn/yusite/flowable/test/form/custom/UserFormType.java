package cn.yusite.flowable.test.form.custom;

import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.engine.form.AbstractFormType;

import java.text.ParseException;

/**
 * Created by shijie on 2019/4/25
 */
public class UserFormType extends AbstractFormType {

    @Override
    public Object convertFormValueToModelValue(String s) {

        if (StringUtils.isEmpty(s)) {
            return null;
        } else {
            return s;
        }
    }

    @Override
    public String convertModelValueToFormValue(Object o) {

        if (null == o) {
            return null;
        } else {
            return o.toString();
        }
    }

    @Override
    public String getName() {

        return "user";
    }
}
