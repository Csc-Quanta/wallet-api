package org.csc.wlt.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;


/**
 * 
 *
 * @author fjy
 * @email 
 * @date 2019-01-15 10:25:31
 */
@Data
public class CscSwitchCodeModel extends CscSwitchCode {
    /**
    * 偏移量
    */
    private Integer offset;
    /**
    * 分页数
    */
    private Integer limit;

    /**
    * 获取当前实列
* @return
*/
    public static CscSwitchCodeModel getInstance(){
        return new CscSwitchCodeModel();
    }
}
