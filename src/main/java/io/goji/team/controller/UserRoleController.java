package io.goji.team.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.microsoft.schemas.office.visio.x2012.main.PageSheetType;
import io.goji.team.common.result.PageResult;
import io.goji.team.common.result.Result;
import io.goji.team.pojo.entity.UserRole;
import io.goji.team.service.UserRoleService;
import io.goji.team.service.impl.UserRoleServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("userRole")
@RequiredArgsConstructor
public class UserRoleController {


    private final UserRoleService userRoleService;


    @GetMapping("userRoleAll")
    public List<UserRole> userRoleAll() {
        return userRoleService.listUserRole();
    }



    @PostMapping("save")
    public void userRoleSave(@RequestBody UserRole userRole) {
        userRoleService.saveUserRole(userRole);
    }

    @DeleteMapping("del/{id}")
    public void userRoleDelete(@PathVariable Integer id) {
        userRoleService.deleteUserRole(id);
    }

    @PostMapping("/batch/del")
    public void userRoleBatchDelete(@RequestBody List<Integer> ids) {
        userRoleService.deleteBatchUserRole(ids);
    }


    @GetMapping("pageAll")
    public PageResult<UserRole> PageUserRoleAll(@RequestParam("pageNum") Integer pageNum,
                                                @RequestParam("PageSize") Integer pageSize,
                                                @RequestParam(defaultValue = "")String itemName) {
        IPage<UserRole> userRoleIPage = userRoleService.listPageUserRole(pageNum, pageSize, itemName);
        return PageResult.success(userRoleIPage);
    }





}
