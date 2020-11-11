package com.lx;

import com.lx.sys.controller.MenuController;
import com.lx.sys.controller.NoticeController;
import com.lx.sys.vo.NoticeVo;
import com.lx.sys.vo.PermissionVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RepositoryErpApplicationTests {

    @Autowired
    MenuController menuController;
    @Test
    void contextLoads() {
        System.out.printf("contextLoads\n\n\n\n\n"+menuController.loadMenuManagerLeftTreeJson(new PermissionVo()).toString());
    }

}
