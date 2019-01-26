package org.csc.wlt.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;


/**
 * 
 *
 * @author fjy
 * @email 
 * @date 2019-01-09 16:16:16
 */
@Data
public class CscWltAppParamModel extends CscWltAppParam {
    /**
    * 偏移量
    */
    private Integer offset;
    /**
    * 分页数
    */
    private Integer limit;
}
