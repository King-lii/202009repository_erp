package com.lx;

import com.lx.sys.commen.TreeNode;
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
      TreeNode treeNode =  new TreeNode(1, 1, "1", "1","1", true);
        System.out.printf(treeNode.toString());
    }

}
