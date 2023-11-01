package io.goji.team.pojo.dto;

import io.goji.team.pojo.entity.Menu;
import java.util.List;

public record LoginDTO(String username, String pwd, String userToken,
                       List<Menu> menus, String roleName, String RULPath) {

}
