package org.csc.wlt.dao;

import lombok.Data;
import lombok.EqualsAndHashCode;
import onight.tfw.ojpa.api.annotations.Tab;
import onight.tfw.ojpa.ordb.ExtendDaoSupper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.csc.wlt.entity.CscKey;
import org.csc.wlt.mapper.CscWltAppParamMapper;
import org.csc.wlt.entity.CscWltAppParam;
import org.csc.wlt.entity.CscWltAppParamModel;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Tab(name = "csc_wlt_app_param")
public class CscWltAppParamDao extends ExtendDaoSupper<CscWltAppParam, CscWltAppParamModel, CscKey> {

    private CscWltAppParamMapper mapper;

    private SqlSessionFactory sqlSessionFactory;
    @Override
    public CscWltAppParam selectOneByExample(CscWltAppParamModel example) {
        List<CscWltAppParam> cscWltAppParamList = mapper.queryList(example);
        if(CollectionUtils.isEmpty(cscWltAppParamList)){
            return null;
        }
        return cscWltAppParamList.get(0);
    }
    @Override
    public int countByExample(CscWltAppParamModel example) {
        return mapper.queryTotal(example);
    }

    @Override
    public List<CscWltAppParam> selectByExample(CscWltAppParamModel example){
        return mapper.queryList(example);
    }
    @Override
    public int insert(CscWltAppParam record){
        return mapper.save(record);
    }
    @Override
    public int updateByPrimaryKeySelective(CscWltAppParam record){
        return mapper.update(record);
    }
    @Override
    public int batchInsert(List<CscWltAppParam> list){
        return mapper.batchInsert(list);
    }
}
