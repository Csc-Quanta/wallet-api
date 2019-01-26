package org.csc.wlt.dao;

import lombok.Data;
import lombok.EqualsAndHashCode;
import onight.tfw.ojpa.api.annotations.Tab;
import onight.tfw.ojpa.ordb.ExtendDaoSupper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.csc.wlt.entity.CscKey;
import org.csc.wlt.mapper.CscCodeMapMapper;
import org.csc.wlt.entity.CscCodeMap;
import org.csc.wlt.entity.CscCodeMapModel;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Tab(name = "csc_code_map")
public class CscCodeMapDao extends ExtendDaoSupper<CscCodeMap, CscCodeMapModel, CscKey> {

    private CscCodeMapMapper mapper;

    private SqlSessionFactory sqlSessionFactory;
    @Override
    public CscCodeMap selectOneByExample(CscCodeMapModel example) {
        List<CscCodeMap> cscCodeMapList = mapper.queryList(example);
        if(CollectionUtils.isEmpty(cscCodeMapList)){
            return null;
        }
        return cscCodeMapList.get(0);
    }
    @Override
    public int countByExample(CscCodeMapModel example) {
        return mapper.queryTotal(example);
    }

    @Override
    public List<CscCodeMap> selectByExample(CscCodeMapModel example){
        return mapper.queryList(example);
    }
    @Override
    public int insert(CscCodeMap record){
        return mapper.save(record);
    }
    @Override
    public int updateByPrimaryKeySelective(CscCodeMap record){
        return mapper.update(record);
    }
    @Override
    public int batchInsert(List<CscCodeMap> list){
        return mapper.batchInsert(list);
    }
}
