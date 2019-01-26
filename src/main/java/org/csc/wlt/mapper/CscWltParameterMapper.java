package org.csc.wlt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.csc.wlt.entity.CscWltParameter;
import org.csc.wlt.entity.CscWltParameterExample;
import org.csc.wlt.entity.CscWltParameterKey;

import onight.tfw.ojpa.ordb.StaticTableDaoSupport;

public interface CscWltParameterMapper
		extends StaticTableDaoSupport<CscWltParameter, CscWltParameterExample, CscWltParameterKey> {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@SelectProvider(type = CscWltParameterSqlProvider.class, method = "countByExample")
	int countByExample(CscWltParameterExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@DeleteProvider(type = CscWltParameterSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CscWltParameterExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@Delete({ "delete from csc_wlt_parameter", "where param_id = #{paramId,jdbcType=INTEGER}" })
	int deleteByPrimaryKey(CscWltParameterKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@Insert({ "insert into csc_wlt_parameter (param_id, param_code, ", "param_value, param_desc, ",
			"created_time, updated_time, ", "reserved1, reserved2)",
			"values (#{paramId,jdbcType=INTEGER}, #{paramCode,jdbcType=VARCHAR}, ",
			"#{paramValue,jdbcType=VARCHAR}, #{paramDesc,jdbcType=VARCHAR}, ",
			"#{createdTime,jdbcType=TIMESTAMP}, #{updatedTime,jdbcType=TIMESTAMP}, ",
			"#{reserved1,jdbcType=VARCHAR}, #{reserved2,jdbcType=VARCHAR})" })
	int insert(CscWltParameter record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@InsertProvider(type = CscWltParameterSqlProvider.class, method = "insertSelective")
	int insertSelective(CscWltParameter record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@SelectProvider(type = CscWltParameterSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "param_id", property = "paramId", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "param_code", property = "paramCode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "param_value", property = "paramValue", jdbcType = JdbcType.VARCHAR),
			@Result(column = "param_desc", property = "paramDesc", jdbcType = JdbcType.VARCHAR),
			@Result(column = "created_time", property = "createdTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "updated_time", property = "updatedTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "reserved1", property = "reserved1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "reserved2", property = "reserved2", jdbcType = JdbcType.VARCHAR) })
	List<CscWltParameter> selectByExample(CscWltParameterExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@Select({ "select", "param_id, param_code, param_value, param_desc, created_time, updated_time, reserved1, ",
			"reserved2", "from csc_wlt_parameter", "where param_id = #{paramId,jdbcType=INTEGER}" })
	@Results({ @Result(column = "param_id", property = "paramId", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "param_code", property = "paramCode", jdbcType = JdbcType.VARCHAR),
			@Result(column = "param_value", property = "paramValue", jdbcType = JdbcType.VARCHAR),
			@Result(column = "param_desc", property = "paramDesc", jdbcType = JdbcType.VARCHAR),
			@Result(column = "created_time", property = "createdTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "updated_time", property = "updatedTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "reserved1", property = "reserved1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "reserved2", property = "reserved2", jdbcType = JdbcType.VARCHAR) })
	CscWltParameter selectByPrimaryKey(CscWltParameterKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@UpdateProvider(type = CscWltParameterSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CscWltParameter record,
			@Param("example") CscWltParameterExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@UpdateProvider(type = CscWltParameterSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CscWltParameter record, @Param("example") CscWltParameterExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@UpdateProvider(type = CscWltParameterSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CscWltParameter record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@Update({ "update csc_wlt_parameter", "set param_code = #{paramCode,jdbcType=VARCHAR},",
			"param_value = #{paramValue,jdbcType=VARCHAR},", "param_desc = #{paramDesc,jdbcType=VARCHAR},",
			"created_time = #{createdTime,jdbcType=TIMESTAMP},", "updated_time = #{updatedTime,jdbcType=TIMESTAMP},",
			"reserved1 = #{reserved1,jdbcType=VARCHAR},", "reserved2 = #{reserved2,jdbcType=VARCHAR}",
			"where param_id = #{paramId,jdbcType=INTEGER}" })
	int updateByPrimaryKey(CscWltParameter record);
}