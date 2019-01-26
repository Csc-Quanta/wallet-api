package org.csc.wlt.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
//import org.springframework.transaction.annotation.Transactional;
import org.csc.wlt.entity.CscWltParameter;
import org.csc.wlt.entity.CscWltParameterExample;
import org.csc.wlt.entity.CscWltParameterExample.Criteria;
import org.csc.wlt.entity.CscWltParameterKey;
import org.csc.wlt.mapper.CscWltParameterMapper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import onight.tfw.mservice.ThreadContext;
import onight.tfw.ojpa.api.annotations.Tab;
import onight.tfw.ojpa.ordb.ExtendDaoSupper;

@Data
@EqualsAndHashCode(callSuper = false)
@Tab(name = "CSC_WLT_PARAMETER")
public class CscWltParameterDao extends ExtendDaoSupper<CscWltParameter, CscWltParameterExample, CscWltParameterKey> {

	private CscWltParameterMapper mapper;

	private SqlSessionFactory sqlSessionFactory;

	@Override
	public int countByExample(CscWltParameterExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(CscWltParameterExample example) throws Exception {
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(CscWltParameterKey key) throws Exception {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(CscWltParameter record) throws Exception {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(CscWltParameter record) throws Exception {
		return mapper.insertSelective(record);
	}

	@Override
	// @Transactional
	public int batchUpdate(List<CscWltParameter> records) throws Exception {
		for (CscWltParameter record : records) {
			mapper.updateByPrimaryKeySelective(record);
		}
		return records.size();
	}

	@Override
	// @Transactional
	public int batchDelete(List<CscWltParameter> records) throws Exception {
		for (CscWltParameter record : records) {
			mapper.deleteByPrimaryKey(record);
		}
		return records.size();
	}

	@Override
	public List<CscWltParameter> selectByExample(CscWltParameterExample example) {
		return mapper.selectByExample(example);
	}

	@Override
	public CscWltParameter selectByPrimaryKey(CscWltParameterKey key) {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<CscWltParameter> findAll(List<CscWltParameter> records) {
		if (records == null || records.size() <= 0) {
			return mapper.selectByExample(new CscWltParameterExample());
		}
		List<CscWltParameter> list = new ArrayList<>();
		for (CscWltParameter record : records) {
			CscWltParameter result = mapper.selectByPrimaryKey(record);
			if (result != null) {
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public int updateByExampleSelective(CscWltParameter record, CscWltParameterExample example) throws Exception {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(CscWltParameter record, CscWltParameterExample example) throws Exception {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(CscWltParameter record) throws Exception {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CscWltParameter record) throws Exception {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int sumByExample(CscWltParameterExample example) {
		return 0;
	}

	@Override
	public void deleteAll() throws Exception {
		mapper.deleteByExample(new CscWltParameterExample());
	}

	@Override
	public CscWltParameterExample getExample(CscWltParameter record) {
		CscWltParameterExample example = new CscWltParameterExample();
		if (record != null) {
			Criteria criteria = example.createCriteria();
			if (record.getParamId() != null) {
				criteria.andParamIdEqualTo(record.getParamId());
			}
			if (record.getParamCode() != null) {
				criteria.andParamCodeEqualTo(record.getParamCode());
			}
			if (record.getParamValue() != null) {
				criteria.andParamValueEqualTo(record.getParamValue());
			}
			if (record.getParamDesc() != null) {
				criteria.andParamDescEqualTo(record.getParamDesc());
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

	public CscWltParameter selectOneByExample(CscWltParameterExample example) {
		example.setLimit(1);
		List<CscWltParameter> list = mapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	// @Transactional
	public int batchInsert(List<CscWltParameter> records) throws Exception {
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
					"INSERT INTO csc_wlt_parameter(param_id,param_code,param_value,param_desc,created_time,updated_time,reserved1,reserved2) values");

			int i = 0;
			st = conn.createStatement();
			for (CscWltParameter record : records) {
				if (i > 0) {
					sb.append(",");
				}
				i++;

				sb.append("(");

				if (record.getParamId() == null) {
					sb.append("null");
				} else {
					// java type==Integer
					sb.append("'" + record.getParamId() + "'");
				}

				sb.append(",");

				if (record.getParamCode() == null) {
					sb.append("null");
				} else {
					// java type==String
					sb.append("'" + record.getParamCode() + "'");
				}

				sb.append(",");

				if (record.getParamValue() == null) {
					sb.append("null");
				} else {
					// java type==String
					sb.append("'" + record.getParamValue() + "'");
				}

				sb.append(",");

				if (record.getParamDesc() == null) {
					sb.append("null");
				} else {
					// java type==String
					sb.append("'" + record.getParamDesc() + "'");
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
