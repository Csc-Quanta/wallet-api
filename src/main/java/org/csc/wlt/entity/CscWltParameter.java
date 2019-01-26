package org.csc.wlt.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import onight.tfw.ojpa.api.annotations.Tab;

@Tab(name = "csc_wlt_parameter")
@AllArgsConstructor
@NoArgsConstructor
public class CscWltParameter extends CscWltParameterKey {
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database column csc_wlt_parameter.param_code
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	private String paramCode;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database column csc_wlt_parameter.param_value
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	private String paramValue;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database column csc_wlt_parameter.param_desc
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	private String paramDesc;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database column csc_wlt_parameter.created_time
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	private Date createdTime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database column csc_wlt_parameter.updated_time
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	private Date updatedTime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database column csc_wlt_parameter.reserved1
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	private String reserved1;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database column csc_wlt_parameter.reserved2
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	private String reserved2;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value
	 * of the database column csc_wlt_parameter.param_code
	 *
	 * @return the value of csc_wlt_parameter.param_code
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public String getParamCode() {
		return paramCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of
	 * the database column csc_wlt_parameter.param_code
	 *
	 * @param paramCode the value for csc_wlt_parameter.param_code
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value
	 * of the database column csc_wlt_parameter.param_value
	 *
	 * @return the value of csc_wlt_parameter.param_value
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public String getParamValue() {
		return paramValue;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of
	 * the database column csc_wlt_parameter.param_value
	 *
	 * @param paramValue the value for csc_wlt_parameter.param_value
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value
	 * of the database column csc_wlt_parameter.param_desc
	 *
	 * @return the value of csc_wlt_parameter.param_desc
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public String getParamDesc() {
		return paramDesc;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of
	 * the database column csc_wlt_parameter.param_desc
	 *
	 * @param paramDesc the value for csc_wlt_parameter.param_desc
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value
	 * of the database column csc_wlt_parameter.created_time
	 *
	 * @return the value of csc_wlt_parameter.created_time
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public Date getCreatedTime() {
		return createdTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of
	 * the database column csc_wlt_parameter.created_time
	 *
	 * @param createdTime the value for csc_wlt_parameter.created_time
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value
	 * of the database column csc_wlt_parameter.updated_time
	 *
	 * @return the value of csc_wlt_parameter.updated_time
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public Date getUpdatedTime() {
		return updatedTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of
	 * the database column csc_wlt_parameter.updated_time
	 *
	 * @param updatedTime the value for csc_wlt_parameter.updated_time
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value
	 * of the database column csc_wlt_parameter.reserved1
	 *
	 * @return the value of csc_wlt_parameter.reserved1
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public String getReserved1() {
		return reserved1;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of
	 * the database column csc_wlt_parameter.reserved1
	 *
	 * @param reserved1 the value for csc_wlt_parameter.reserved1
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value
	 * of the database column csc_wlt_parameter.reserved2
	 *
	 * @return the value of csc_wlt_parameter.reserved2
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public String getReserved2() {
		return reserved2;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of
	 * the database column csc_wlt_parameter.reserved2
	 *
	 * @param reserved2 the value for csc_wlt_parameter.reserved2
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		CscWltParameter other = (CscWltParameter) that;
		return (this.getParamId() == null ? other.getParamId() == null : this.getParamId().equals(other.getParamId()))
				&& (this.getParamCode() == null ? other.getParamCode() == null
						: this.getParamCode().equals(other.getParamCode()))
				&& (this.getParamValue() == null ? other.getParamValue() == null
						: this.getParamValue().equals(other.getParamValue()))
				&& (this.getParamDesc() == null ? other.getParamDesc() == null
						: this.getParamDesc().equals(other.getParamDesc()))
				&& (this.getCreatedTime() == null ? other.getCreatedTime() == null
						: this.getCreatedTime().equals(other.getCreatedTime()))
				&& (this.getUpdatedTime() == null ? other.getUpdatedTime() == null
						: this.getUpdatedTime().equals(other.getUpdatedTime()))
				&& (this.getReserved1() == null ? other.getReserved1() == null
						: this.getReserved1().equals(other.getReserved1()))
				&& (this.getReserved2() == null ? other.getReserved2() == null
						: this.getReserved2().equals(other.getReserved2()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getParamId() == null) ? 0 : getParamId().hashCode());
		result = prime * result + ((getParamCode() == null) ? 0 : getParamCode().hashCode());
		result = prime * result + ((getParamValue() == null) ? 0 : getParamValue().hashCode());
		result = prime * result + ((getParamDesc() == null) ? 0 : getParamDesc().hashCode());
		result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
		result = prime * result + ((getUpdatedTime() == null) ? 0 : getUpdatedTime().hashCode());
		result = prime * result + ((getReserved1() == null) ? 0 : getReserved1().hashCode());
		result = prime * result + ((getReserved2() == null) ? 0 : getReserved2().hashCode());
		return result;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", paramCode=").append(paramCode);
		sb.append(", paramValue=").append(paramValue);
		sb.append(", paramDesc=").append(paramDesc);
		sb.append(", createdTime=").append(createdTime);
		sb.append(", updatedTime=").append(updatedTime);
		sb.append(", reserved1=").append(reserved1);
		sb.append(", reserved2=").append(reserved2);
		sb.append("]");
		return sb.toString();
	}
}