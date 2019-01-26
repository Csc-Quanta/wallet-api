package org.csc.wlt.entity;

import lombok.Data;


/**
 * 
 *
 * @author fjy
 * @email 
 * @date 2019-01-14 20:07:35
 */
@Data
public class CscCodeMapModel extends CscCodeMap {
    /**
    * 偏移量
    */
    private Integer offset;
    /**
    * 分页数
    */
    private Integer limit;
    public static CscCodeMapModel getInstance(){
        return new CscCodeMapModel();
    }
}
