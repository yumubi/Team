package io.goji.team.config.mp.handler;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.goji.team.utils.MD5Utils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * mybatis-plus 字段自动填充
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 新增填充创建时间
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "itemAddTime", LocalDateTime::now, LocalDateTime.class);
        this.strictUpdateFill(metaObject, "itemUpdateTime", LocalDateTime::now, LocalDateTime.class);
        this.strictUpdateFill(metaObject, "recordAddTime", LocalDateTime::now, LocalDateTime.class);
        this.strictUpdateFill(metaObject, "recordUpdateTime", LocalDateTime::now, LocalDateTime.class);
        this.setFieldValByName("getCode", RandomUtil.randomString(6), metaObject);
        this.setFieldValByName("password", MD5Utils.getMD5("123456"), metaObject);
    }

    /**
     * 更新填充更新时间
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "itemUpdateTime", LocalDateTime::now, LocalDateTime.class);
        this.strictUpdateFill(metaObject, "recordUpdateTime", LocalDateTime::now, LocalDateTime.class);
    }

}
