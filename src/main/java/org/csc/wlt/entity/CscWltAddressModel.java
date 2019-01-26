package org.csc.wlt.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;


/**
 * 
 *
 * @author fjy
 * @email 
 * @date 2019-01-24 18:24:47
 */
@Data
public class CscWltAddressModel extends CscWltAddress {
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
    public static CscWltAddressModel getInstance(){
        return new CscWltAddressModel();
    }
}
