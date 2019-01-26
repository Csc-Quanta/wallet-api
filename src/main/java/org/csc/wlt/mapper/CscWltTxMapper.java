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
import org.csc.wlt.entity.CscWltTx;
import org.csc.wlt.entity.CscWltTxExample;
import org.csc.wlt.entity.CscWltTxKey;

import onight.tfw.ojpa.ordb.StaticTableDaoSupport;

public interface CscWltTxMapper extends StaticTableDaoSupport<CscWltTx, CscWltTxExample, CscWltTxKey> {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table cwv_wlt_tx
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@SelectProvider(type = CscWltTxSqlProvider.class, method = "countByExample")
	int countByExample(CscWltTxExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table cwv_wlt_tx
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@DeleteProvider(type = CscWltTxSqlProvider.class, method = "deleteByExample")
	int deleteByExample(CscWltTxExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table cwv_wlt_tx
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@Delete({ "delete from cwv_wlt_tx", "where tx_id = #{txId,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(CscWltTxKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table cwv_wlt_tx
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@Insert({ "insert into cwv_wlt_tx (tx_id, tx_hash, ", "input_address, output_address, ", "amount, tx_status, ",
			"tx_type, created_time, ", "updated_time, reserved1, ", "reserved2)",
			"values (#{txId,jdbcType=VARCHAR}, #{txHash,jdbcType=VARCHAR}, ",
			"#{inputAddress,jdbcType=VARCHAR}, #{outputAddress,jdbcType=VARCHAR}, ",
			"#{amount,jdbcType=BIGINT}, #{txStatus,jdbcType=VARCHAR}, ",
			"#{txType,jdbcType=VARCHAR}, #{createdTime,jdbcType=TIMESTAMP}, ",
			"#{updatedTime,jdbcType=TIMESTAMP}, #{reserved1,jdbcType=VARCHAR}, ", "#{reserved2,jdbcType=VARCHAR})" })
	int insert(CscWltTx record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table cwv_wlt_tx
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@InsertProvider(type = CscWltTxSqlProvider.class, method = "insertSelective")
	int insertSelective(CscWltTx record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table cwv_wlt_tx
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@SelectProvider(type = CscWltTxSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "tx_id", property = "txId", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "tx_hash", property = "txHash", jdbcType = JdbcType.VARCHAR),
			@Result(column = "input_address", property = "inputAddress", jdbcType = JdbcType.VARCHAR),
			@Result(column = "output_address", property = "outputAddress", jdbcType = JdbcType.VARCHAR),
			@Result(column = "amount", property = "amount", jdbcType = JdbcType.BIGINT),
			@Result(column = "tx_status", property = "txStatus", jdbcType = JdbcType.VARCHAR),
			@Result(column = "tx_type", property = "txType", jdbcType = JdbcType.VARCHAR),
			@Result(column = "created_time", property = "createdTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "updated_time", property = "updatedTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "reserved1", property = "reserved1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "reserved2", property = "reserved2", jdbcType = JdbcType.VARCHAR) })
	List<CscWltTx> selectByExample(CscWltTxExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table cwv_wlt_tx
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@Select({ "select", "tx_id, tx_hash, input_address, output_address, amount, tx_status, tx_type, created_time, ",
			"updated_time, reserved1, reserved2", "from cwv_wlt_tx", "where tx_id = #{txId,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "tx_id", property = "txId", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "tx_hash", property = "txHash", jdbcType = JdbcType.VARCHAR),
			@Result(column = "input_address", property = "inputAddress", jdbcType = JdbcType.VARCHAR),
			@Result(column = "output_address", property = "outputAddress", jdbcType = JdbcType.VARCHAR),
			@Result(column = "amount", property = "amount", jdbcType = JdbcType.BIGINT),
			@Result(column = "tx_status", property = "txStatus", jdbcType = JdbcType.VARCHAR),
			@Result(column = "tx_type", property = "txType", jdbcType = JdbcType.VARCHAR),
			@Result(column = "created_time", property = "createdTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "updated_time", property = "updatedTime", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "reserved1", property = "reserved1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "reserved2", property = "reserved2", jdbcType = JdbcType.VARCHAR) })
	CscWltTx selectByPrimaryKey(CscWltTxKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table cwv_wlt_tx
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@UpdateProvider(type = CscWltTxSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") CscWltTx record, @Param("example") CscWltTxExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table cwv_wlt_tx
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@UpdateProvider(type = CscWltTxSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CscWltTx record, @Param("example") CscWltTxExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table cwv_wlt_tx
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@UpdateProvider(type = CscWltTxSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(CscWltTx record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table cwv_wlt_tx
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@Update({ "update cwv_wlt_tx", "set tx_hash = #{txHash,jdbcType=VARCHAR},",
			"input_address = #{inputAddress,jdbcType=VARCHAR},", "output_address = #{outputAddress,jdbcType=VARCHAR},",
			"amount = #{amount,jdbcType=BIGINT},", "tx_status = #{txStatus,jdbcType=VARCHAR},",
			"tx_type = #{txType,jdbcType=VARCHAR},", "created_time = #{createdTime,jdbcType=TIMESTAMP},",
			"updated_time = #{updatedTime,jdbcType=TIMESTAMP},", "reserved1 = #{reserved1,jdbcType=VARCHAR},",
			"reserved2 = #{reserved2,jdbcType=VARCHAR}", "where tx_id = #{txId,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(CscWltTx record);
}