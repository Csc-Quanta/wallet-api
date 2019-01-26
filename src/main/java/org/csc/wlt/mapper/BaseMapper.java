package org.csc.wlt.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseMapper<T,D> {

    /**
     * 根据主键查询对象
     * @param autoId
     * @return
     */
    T queryOne(@Param("autoId") int autoId);

    /**
     * 根据条件查询数据
     * @return
     */
    List<T> queryList(D d);

    /**
     * 查询总条数
     * @param d
     * @return
     */
    int queryTotal(D d);

    /**
     * 保存数据
     * @param t
     */
    int save(T t);

    /**
     * 批量插入
     * @param list
     * @return
     */
    int batchInsert(List<T> list);

    /**
     * 根据条件更新数据
     * @param t
     */
    int update(T t);

    /**
     * 根据条件批量删除数据
     * @param
     */
    int deleteBatch(@Param("array") Long[] array);

}
