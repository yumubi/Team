package io.goji.team.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.microsoft.schemas.office.visio.x2012.main.PageSheetType;
import io.goji.team.common.result.PageResult;
import io.goji.team.common.result.Result;
import io.goji.team.pojo.entity.UserRole;
import io.goji.team.service.UserRoleService;
import io.goji.team.service.impl.UserRoleServiceImpl;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.MediaType;
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


    @GetMapping("export")
    public void exportRole(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> list = CollectionUtil.newArrayList();
        List<UserRole> userRoles = userRoleAll();
        for (UserRole role: userRoles) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("唯一表示符", role.getId());
            row.put("角色编号", role.getRoleId());
            row.put("角色名称", role.getRolename());
            row.put("角色描述", role.getDescription());
            list.add(row);
        }

        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

    }




}
