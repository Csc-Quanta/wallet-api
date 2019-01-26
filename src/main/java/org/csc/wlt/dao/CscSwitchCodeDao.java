package org.csc.wlt.dao;

import lombok.Data;
import lombok.EqualsAndHashCode;
import onight.tfw.ojpa.api.annotations.Tab;
import onight.tfw.ojpa.ordb.ExtendDaoSupper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.csc.wlt.entity.CscKey;
import org.csc.wlt.mapper.CscSwitchCodeMapper;
import org.csc.wlt.entity.CscSwitchCode;
import org.csc.wlt.entity.CscSwitchCodeModel;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Tab(name = "csc_switch_code")
public class CscSwitchCodeDao extends ExtendDaoSupper<CscSwitchCode, CscSwitchCodeModel, CscKey> {

    private CscSwitchCodeMapper mapper;

    private SqlSessionFactory sqlSessionFactory;
    @Override
    public CscSwitchCode selectOneByExample(CscSwitchCodeModel example) {
        List<CscSwitchCode> cscSwitchCodeList = mapper.queryList(example);
        if(CollectionUtils.isEmpty(cscSwitchCodeList)){
            return null;
        }
        return cscSwitchCodeList.get(0);
    }
    @Override
    public int countByExample(CscSwitchCodeModel example) {
        return mapper.queryTotal(example);
    }

    @Override
    public List<CscSwitchCode> selectByExample(CscSwitchCodeModel example){
        return mapper.queryList(example);
    }
    @Override
    public int insert(CscSwitchCode record){
        return mapper.save(record);
    }
    @Override
    public int updateByPrimaryKeySelective(CscSwitchCode record){
        return mapper.update(record);
    }
    @Override
    public int batchInsert(List<CscSwitchCode> list){
        return mapper.batchInsert(list);
    }
}
