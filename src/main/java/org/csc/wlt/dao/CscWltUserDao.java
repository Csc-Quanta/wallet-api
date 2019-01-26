package org.csc.wlt.dao;

import lombok.Data;
import lombok.EqualsAndHashCode;
import onight.tfw.ojpa.api.annotations.Tab;
import onight.tfw.ojpa.ordb.ExtendDaoSupper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.csc.wlt.entity.CscKey;
import org.csc.wlt.mapper.CscWltUserMapper;
import org.csc.wlt.entity.CscWltUser;
import org.csc.wlt.entity.CscWltUserModel;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Tab(name = "csc_wlt_user")
public class CscWltUserDao extends ExtendDaoSupper<CscWltUser, CscWltUserModel, CscKey> {

    private CscWltUserMapper mapper;

    private SqlSessionFactory sqlSessionFactory;
    @Override
    public CscWltUser selectOneByExample(CscWltUserModel example) {
        List<CscWltUser> cscWltUserList = mapper.queryList(example);
        if(CollectionUtils.isEmpty(cscWltUserList)){
            return null;
        }
        return cscWltUserList.get(0);
    }
    @Override
    public int countByExample(CscWltUserModel example) {
        return mapper.queryTotal(example);
    }

    @Override
    public List<CscWltUser> selectByExample(CscWltUserModel example){
        return mapper.queryList(example);
    }
    @Override
    public int insert(CscWltUser record){
        return mapper.save(record);
    }
    @Override
    public int updateByPrimaryKeySelective(CscWltUser record){
        return mapper.update(record);
    }
    @Override
    public int batchInsert(List<CscWltUser> list){
        return mapper.batchInsert(list);
    }
}
