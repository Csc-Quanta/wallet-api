package org.csc.wlt.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
//import org.springframework.transaction.annotation.Transactional;
import org.csc.wlt.entity.CscWltContract;
import org.csc.wlt.entity.CscWltContractExample;
import org.csc.wlt.entity.CscWltContractExample.Criteria;
import org.csc.wlt.entity.CscWltContractKey;
import org.csc.wlt.mapper.CscWltContractMapper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import onight.tfw.mservice.ThreadContext;
import onight.tfw.ojpa.api.annotations.Tab;
import onight.tfw.ojpa.ordb.ExtendDaoSupper;

@Data
@EqualsAndHashCode(callSuper = false)
@Tab(name = "CSC_WLT_CONTRACT")
public class CscWltContractDao extends ExtendDaoSupper<CscWltContract, CscWltContractExample, CscWltContractKey> {

	private CscWltContractMapper mapper;

	private SqlSessionFactory sqlSessionFactory;

	@Override
	public int countByExample(CscWltContractExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(CscWltContractExample example) throws Exception {
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(CscWltContractKey key) throws Exception {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(CscWltContract record) throws Exception {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(CscWltContract record) throws Exception {
		return mapper.insertSelective(record);
	}

	@Override
	// @Transactional
	public int batchUpdate(List<CscWltContract> records) throws Exception {
		for (CscWltContract record : records) {
			mapper.updateByPrimaryKeySelective(record);
		}
		return records.size();
	}

	@Override
	// @Transactional
	public int batchDelete(List<CscWltContract> records) throws Exception {
		for (CscWltContract record : records) {
			mapper.deleteByPrimaryKey(record);
		}
		return records.size();
	}

	@Override
	public List<CscWltContract> selectByExample(CscWltContractExample example) {
		return mapper.selectByExample(example);
	}

	@Override
	public CscWltContract selectByPrimaryKey(CscWltContractKey key) {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<CscWltContract> findAll(List<CscWltContract> records) {
		if (records == null || records.size() <= 0) {
			return mapper.selectByExample(new CscWltContractExample());
		}
		List<CscWltContract> list = new ArrayList<>();
		for (CscWltContract record : records) {
			CscWltContract result = mapper.selectByPrimaryKey(record);
			if (result != null) {
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public int updateByExampleSelective(CscWltContract record, CscWltContractExample example) throws Exception {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(CscWltContract record, CscWltContractExample example) throws Exception {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(CscWltContract record) throws Exception {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CscWltContract record) throws Exception {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int sumByExample(CscWltContractExample example) {
		return 0;
	}

	@Override
	public void deleteAll() throws Exception {
		mapper.deleteByExample(new CscWltContractExample());
	}

	@Override
	public CscWltContractExample getExample(CscWltContract record) {
		CscWltContractExample example = new CscWltContractExample();
		if (record != null) {
			Criteria criteria = example.createCriteria();
			if (record.getContractId() != null) {
				criteria.andContractIdEqualTo(record.getContractId());
			}
			if (record.getContractAddress() != null) {
				criteria.andContractAddressEqualTo(record.getContractAddress());
			}
			if (record.getContractTxHash() != null) {
				criteria.andContractTxHashEqualTo(record.getContractTxHash());
			}
			if (record.getContractType() != null) {
				criteria.andContractTypeEqualTo(record.getContractType());
			}
			if (record.getContractName() != null) {
				criteria.andContractNameEqualTo(record.getContractName());
			}
			if (record.getContractStatus() != null) {
				criteria.andContractStatusEqualTo(record.getContractStatus());
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

	public CscWltContract selectOneByExample(CscWltContractExample example) {
		example.setLimit(1);
		List<CscWltContract> list = mapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	// @Transactional
	public int batchInsert(List<CscWltContract> records) throws Exception {
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
					"INSERT INTO csc_wlt_contract(contract_id,contract_address,contract_tx_hash,contract_type,contract_name,contract_status,created_time,updated_time,reserved1,reserved2) values");

			int i = 0;
			st = conn.createStatement();
			for (CscWltContract record : records) {
				if (i > 0) {
					sb.append(",");
				}
				i++;

				sb.append("(");

				if (record.getContractId() == null) {
					sb.append("null");
				} else {
					// java type==String
					sb.append("'" + record.getContractId() + "'");
				}

				sb.append(",");

				if (record.getContractAddress() == null) {
					sb.append("null");
				} else {
					// java type==String
					sb.append("'" + record.getContractAddress() + "'");
				}

				sb.append(",");

				if (record.getContractTxHash() == null) {
					sb.append("null");
				} else {
					// java type==String
					sb.append("'" + record.getContractTxHash() + "'");
				}

				sb.append(",");

				if (record.getContractType() == null) {
					sb.append("null");
				} else {
					// java type==String
					sb.append("'" + record.getContractType() + "'");
				}

				sb.append(",");

				if (record.getContractName() == null) {
					sb.append("null");
				} else {
					// java type==String
					sb.append("'" + record.getContractName() + "'");
				}

				sb.append(",");

				if (record.getContractStatus() == null) {
					sb.append("null");
				} else {
					// java type==String
					sb.append("'" + record.getContractStatus() + "'");
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
