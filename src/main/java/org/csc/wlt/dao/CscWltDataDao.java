package org.csc.wlt.dao;

import lombok.Data;
import lombok.EqualsAndHashCode;
import onight.tfw.ojpa.api.annotations.Tab;
import onight.tfw.ojpa.ordb.ExtendDaoSupper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.csc.wlt.entity.CscKey;
import org.csc.wlt.mapper.CscWltDataMapper;
import org.csc.wlt.entity.CscWltData;
import org.csc.wlt.entity.CscWltDataModel;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Tab(name = "csc_wlt_data")
public class CscWltDataDao extends ExtendDaoSupper<CscWltData, CscWltDataModel, CscKey> {

    private CscWltDataMapper mapper;

    private SqlSessionFactory sqlSessionFactory;
    @Override
    public CscWltData selectOneByExample(CscWltDataModel example) {
        List<CscWltData> cscWltDataList = mapper.queryList(example);
        if(CollectionUtils.isEmpty(cscWltDataList)){
            return null;
        }
        return cscWltDataList.get(0);
    }
    @Override
    public int countByExample(CscWltDataModel example) {
        return mapper.queryTotal(example);
    }

    @Override
    public List<CscWltData> selectByExample(CscWltDataModel example){
        return mapper.queryList(example);
    }
    @Override
    public int insert(CscWltData record){
        return mapper.save(record);
    }
    @Override
    public int updateByPrimaryKeySelective(CscWltData record){
        return mapper.update(record);
    }
    @Override
    public int batchInsert(List<CscWltData> list){
        return mapper.batchInsert(list);
    }
}
