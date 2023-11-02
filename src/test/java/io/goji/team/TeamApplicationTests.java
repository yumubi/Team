package io.goji.team;

import io.goji.team.pojo.entity.UserRole;
import io.goji.team.service.impl.UserRoleServiceImpl;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TeamApplicationTests {


    @Resource
    UserRoleServiceImpl userRoleService;

    @Test
    void contextLoads() {
        UserRole userRole = new UserRole();
        userRole.setRoleId(2L);
        userRole.setRolename("测试事务");
        userRole.setDescription("测试事务");
        userRoleService.saveUserRole(userRole);
    }

}
