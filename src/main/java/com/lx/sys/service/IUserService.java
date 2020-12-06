package com.lx.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.sys.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lx
 * @since 2020-10-06
 */
public interface IUserService extends IService<User> {

    void saveUserRole(@Param("uid") Integer uid, @Param("rids") Integer[] rids);
}
