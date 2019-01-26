package org.csc.wlt.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;


/**
 * 
 *
 * @author fjy
 * @email 
 * @date 2019-01-11 10:01:51
 */
@Data
public class CscWltUserModel extends CscWltUser {
    /**
    * 偏移量
    */
    private Integer offset;
    /**
    * 分页数
    */
    private Integer limit;

    private List<String> data;
}
