package org.csc.wlt.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CscWltContractKey {
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database column csc_wlt_contract.contract_id
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	private String contractId;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value
	 * of the database column csc_wlt_contract.contract_id
	 *
	 * @return the value of csc_wlt_contract.contract_id
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public String getContractId() {
		return contractId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of
	 * the database column csc_wlt_contract.contract_id
	 *
	 * @param contractId the value for csc_wlt_contract.contract_id
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_contract
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
		CscWltContractKey other = (CscWltContractKey) that;
		return (this.getContractId() == null ? other.getContractId() == null
				: this.getContractId().equals(other.getContractId()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_contract
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getContractId() == null) ? 0 : getContractId().hashCode());
		return result;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_contract
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", contractId=").append(contractId);
		sb.append("]");
		return sb.toString();
	}
}