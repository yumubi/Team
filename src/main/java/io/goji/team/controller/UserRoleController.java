package io.goji.team.controller;

import io.goji.team.common.result.Result;
import io.goji.team.pojo.entity.UserRole;
import io.goji.team.service.UserRoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("userRole")
@RequiredArgsConstructor
public class UserRoleController {


    private final UserRoleService userRoleService;


    @GetMapping("userRoleAll")
    public List<UserRole> userRoleAll() {
        return userRoleService.list();
    }



    @PostMapping("save")
    public Result<Void> userRoleSave(@RequestBody UserRole item) {
        if(userRoleService.saveOrUpdate(item)) {
            return Result.success();
        } else {
            return Result.failed();
        }
    }

    @DeleteMapping("del/{id}")
    public Result<Void> userRoleDelete(@PathVariable Integer id) {
        if(userRoleService.removeById(id)){
            return Result.success();
        }else{
            return Result.failed();
        }

    }

    @PostMapping("/batch/del")
    public Result<Void> userRoleBatchDelete(@RequestBody List<Integer> ids) {
        if(userRoleService.removeBatchByIds(ids)) {
            return Result.success();
        }
        return Result.failed();
    }

}
