package org.csc.wlt.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CscWltContractRecordKey {
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database column csc_wlt_contract_record.recoed_id
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	private String recoedId;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value
	 * of the database column csc_wlt_contract_record.recoed_id
	 *
	 * @return the value of csc_wlt_contract_record.recoed_id
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public String getRecoedId() {
		return recoedId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of
	 * the database column csc_wlt_contract_record.recoed_id
	 *
	 * @param recoedId the value for csc_wlt_contract_record.recoed_id
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public void setRecoedId(String recoedId) {
		this.recoedId = recoedId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_contract_record
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
		CscWltContractRecordKey other = (CscWltContractRecordKey) that;
		return (this.getRecoedId() == null ? other.getRecoedId() == null
				: this.getRecoedId().equals(other.getRecoedId()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_contract_record
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getRecoedId() == null) ? 0 : getRecoedId().hashCode());
		return result;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_contract_record
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", recoedId=").append(recoedId);
		sb.append("]");
		return sb.toString();
	}
}