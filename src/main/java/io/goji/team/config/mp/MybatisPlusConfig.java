package io.goji.team.config.mp;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import io.goji.team.config.mp.handler.IntegerArrayJsonTypeHandler;
import io.goji.team.config.mp.handler.LongArrayJsonTypeHandler;
import io.goji.team.config.mp.handler.MyMetaObjectHandler;
import io.goji.team.config.mp.handler.StringArrayJsonTypeHandler;
import javax.sql.DataSource;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis-plus 配置类
 */
@Configuration
@EnableTransactionManagement
public class MybatisPlusConfig {

    /**
     * 分页插件和数据权限插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        //大拦截器
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //攻击 SQL 阻断解析器,防止全表更新与删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        //分页拦截器
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        // 页码溢出时跳转第一页, 默认 false 不做处理
        paginationInnerInterceptor.setOverflow(true);
        // 限制单页的最大记录数, 默认 null 不受限制
        paginationInnerInterceptor.setMaxLimit(100L);
        // 生成 count sql 时优化掉 left join (会导致1对N的结果值偏小), 默认 true
        paginationInnerInterceptor.setOptimizeJoin(true);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }


    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }



    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            // 全局注册自定义TypeHandler
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            typeHandlerRegistry.register(String[].class, JdbcType.OTHER, StringArrayJsonTypeHandler.class);
            typeHandlerRegistry.register(Long[].class, JdbcType.OTHER, LongArrayJsonTypeHandler.class);
            typeHandlerRegistry.register(Integer[].class, JdbcType.OTHER, IntegerArrayJsonTypeHandler.class);
        };
    }

    /**
     * 自动填充数据库创建人、创建时间、更新人、更新时间
     */
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(new MyMetaObjectHandler());
        return globalConfig;
    }

}
