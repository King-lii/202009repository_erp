package com.lx;

import com.lx.sys.controller.NoticeController;
import com.lx.sys.vo.NoticeVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RepositoryErpApplicationTests {

    @Autowired
    NoticeController noticeController;
    @Test
    void contextLoads() {
        System.out.println(this.noticeController.loadAllNotice(new NoticeVo()));
    }

}
