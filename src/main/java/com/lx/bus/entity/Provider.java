package com.lx.bus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lidada
 * @since 2020-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_provider")
public class Provider implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String providername;

    private String zip;

    private String address;

    private String telephone;

    private String connectionperson;

    private String phone;

    private String bank;

    private String account;

    private String email;

    private String fax;

    private Integer available;


}
