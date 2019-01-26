package org.csc.wlt.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
//import org.springframework.transaction.annotation.Transactional;
import org.csc.wlt.entity.CscWltContractRecord;
import org.csc.wlt.entity.CscWltContractRecordExample;
import org.csc.wlt.entity.CscWltContractRecordExample.Criteria;
import org.csc.wlt.entity.CscWltContractRecordKey;
import org.csc.wlt.mapper.CscWltContractRecordMapper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import onight.tfw.mservice.ThreadContext;
import onight.tfw.ojpa.api.annotations.Tab;
import onight.tfw.ojpa.ordb.ExtendDaoSupper;

@Data
@EqualsAndHashCode(callSuper = false)
@Tab(name = "CSC_WLT_CONTRACT_RECORD")
public class CscWltContractRecordDao
		extends ExtendDaoSupper<CscWltContractRecord, CscWltContractRecordExample, CscWltContractRecordKey> {

	private CscWltContractRecordMapper mapper;

	private SqlSessionFactory sqlSessionFactory;

	@Override
	public int countByExample(CscWltContractRecordExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(CscWltContractRecordExample example) throws Exception {
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(CscWltContractRecordKey key) throws Exception {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(CscWltContractRecord record) throws Exception {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(CscWltContractRecord record) throws Exception {
		return mapper.insertSelective(record);
	}

	@Override
	// @Transactional
	public int batchUpdate(List<CscWltContractRecord> records) throws Exception {
		for (CscWltContractRecord record : records) {
			mapper.updateByPrimaryKeySelective(record);
		}
		return records.size();
	}

	@Override
	// @Transactional
	public int batchDelete(List<CscWltContractRecord> records) throws Exception {
		for (CscWltContractRecord record : records) {
			mapper.deleteByPrimaryKey(record);
		}
		return records.size();
	}

	@Override
	public List<CscWltContractRecord> selectByExample(CscWltContractRecordExample example) {
		return mapper.selectByExample(example);
	}

	@Override
	public CscWltContractRecord selectByPrimaryKey(CscWltContractRecordKey key) {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<CscWltContractRecord> findAll(List<CscWltContractRecord> records) {
		if (records == null || records.size() <= 0) {
			return mapper.selectByExample(new CscWltContractRecordExample());
		}
		List<CscWltContractRecord> list = new ArrayList<>();
		for (CscWltContractRecord record : records) {
			CscWltContractRecord result = mapper.selectByPrimaryKey(record);
			if (result != null) {
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public int updateByExampleSelective(CscWltContractRecord record, CscWltContractRecordExample example)
			throws Exception {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(CscWltContractRecord record, CscWltContractRecordExample example) throws Exception {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(CscWltContractRecord record) throws Exception {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CscWltContractRecord record) throws Exception {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int sumByExample(CscWltContractRecordExample example) {
		return 0;
	}

	@Override
	public void deleteAll() throws Exception {
		mapper.deleteByExample(new CscWltContractRecordExample());
	}

	@Override
	public CscWltContractRecordExample getExample(CscWltContractRecord record) {
		CscWltContractRecordExample example = new CscWltContractRecordExample();
		if (record != null) {
			Criteria criteria = example.createCriteria();
			if (record.getRecoedId() != null) {
				criteria.andRecoedIdEqualTo(record.getRecoedId());
			}
			if (record.getContractId() != null) {
				criteria.andContractIdEqualTo(record.getContractId());
			}
			if (record.getRecordType() != null) {
				criteria.andRecordTypeEqualTo(record.getRecordType());
			}
			if (record.getAddress() != null) {
				criteria.andAddressEqualTo(record.getAddress());
			}
			if (record.getTxHash() != null) {
				criteria.andTxHashEqualTo(record.getTxHash());
			}
			if (record.getCreatedTime() != null) {
				criteria.andCreatedTimeEqualTo(record.getCreatedTime());
			}
			if (record.getUpdatedTime() != null) {
				criteria.andUpdatedTimeEqualTo(record.getUpdatedTime());
			}
			if (record.getReserved1() != null) {
				criteria.andReserved1EqualTo(record.getReserved1());
			}
			if (record.getReserved2() != null) {
				criteria.andReserved2EqualTo(record.getReserved2());
			}

		}
		return example;
	}

	public CscWltContractRecord selectOneByExample(CscWltContractRecordExample example) {
		example.setLimit(1);
		List<CscWltContractRecord> list = mapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	// @Transactional
	public int batchInsert(List<CscWltContractRecord> records) throws Exception {
		if (records.size() <= 0)
			return 0;

		Connection txconn = (Connection) ThreadContext.getContext("__connection");
		Connection conn = txconn;
		SqlSession session = null;
		if (txconn == null) {
			session = sqlSessionFactory.openSession();
			conn = session.getConnection();
		}
		Statement st = null;

		int result = 0;
		try {
			if (txconn == null) {
				conn.setAutoCommit(false);
			}
			StringBuffer sb = new StringBuffer();
			sb.append(
					"INSERT INTO csc_wlt_contract_record(recoed_id,contract_id,record_type,address,tx_hash,created_time,updated_time,reserved1,reserved2) values");

			int i = 0;
			st = conn.createStatement();
			for (CscWltContractRecord record : records) {
				if (i > 0) {
					sb.append(",");
				}
				i++;

				sb.append("(");

				if (record.getRecoedId() == null) {
					sb.append("null");
				} else {
					// java type==String
					sb.append("'" + record.getRecoedId() + "'");
				}

				sb.append(",");

				if (record.getContractId() == null) {
					sb.append("null");
				} else {
					// java type==String
					sb.append("'" + record.getContractId() + "'");
				}

				sb.append(",");

				if (record.getRecordType() == null) {
					sb.append("null");
				} else {
					// java type==String
					sb.append("'" + record.getRecordType() + "'");
				}

				sb.append(",");

				if (record.getAddress() == null) {
					sb.append("null");
				} else {
					// java type==String
					sb.append("'" + record.getAddress() + "'");
				}

				sb.append(",");

				if (record.getTxHash() == null) {
					sb.append("null");
				} else {
					// java type==String
					sb.append("'" + record.getTxHash() + "'");
				}

				sb.append(",");

				if (record.getCreatedTime() == null) {
					sb.append("null");
				} else {
					// java type==Date
					java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					sb.append("'" + sdf.format(record.getCreatedTime()) + "'");
				}

				sb.append(",");

				if (record.getUpdatedTime() == null) {
					sb.append("null");
				} else {
					// java type==Date
					java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					sb.append("'" + sdf.format(record.getUpdatedTime()) + "'");
				}

				sb.append(",");

				if (record.getReserved1() == null) {
					sb.append("null");
				} else {
					// java type==String
					sb.append("'" + record.getReserved1() + "'");
				}

				sb.append(",");

				if (record.getReserved2() == null) {
					sb.append("null");
				} else {
					// java type==String
					sb.append("'" + record.getReserved2() + "'");
				}
				sb.append(")");

			}
			result = st.executeUpdate(sb.toString());

			if (txconn == null) {
				conn.commit();
			}
		} catch (SQLException e) {
			if (txconn == null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw e;
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception est) {
					est.printStackTrace();
				}
			}
			if (session != null)
				session.close();
		}
		return result;
	}

}
