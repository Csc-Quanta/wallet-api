package org.csc.wlt.dao;

import lombok.Data;
import lombok.EqualsAndHashCode;
import onight.tfw.ojpa.api.annotations.Tab;
import onight.tfw.ojpa.ordb.ExtendDaoSupper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.csc.wlt.entity.CscKey;
import org.csc.wlt.mapper.CscWltAddressMapper;
import org.csc.wlt.entity.CscWltAddress;
import org.csc.wlt.entity.CscWltAddressModel;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Tab(name = "csc_wlt_address")
public class CscWltAddressDao extends ExtendDaoSupper<CscWltAddress, CscWltAddressModel, CscKey> {

    private CscWltAddressMapper mapper;

    private SqlSessionFactory sqlSessionFactory;
    @Override
    public CscWltAddress selectOneByExample(CscWltAddressModel example) {
        List<CscWltAddress> cscWltAddressList = mapper.queryList(example);
        if(CollectionUtils.isEmpty(cscWltAddressList)){
            return null;
        }
        return cscWltAddressList.get(0);
    }
    @Override
    public int countByExample(CscWltAddressModel example) {
        return mapper.queryTotal(example);
    }

    @Override
    public List<CscWltAddress> selectByExample(CscWltAddressModel example){
        return mapper.queryList(example);
    }
    @Override
    public int insert(CscWltAddress record){
        return mapper.save(record);
    }
    @Override
    public int updateByPrimaryKeySelective(CscWltAddress record){
        return mapper.update(record);
    }
    @Override
    public int batchInsert(List<CscWltAddress> list){
        return mapper.batchInsert(list);
    }
}
