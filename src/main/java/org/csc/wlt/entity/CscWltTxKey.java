package org.csc.wlt.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CscWltTxKey {
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database column cwv_wlt_tx.tx_id
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	private String txId;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value
	 * of the database column cwv_wlt_tx.tx_id
	 *
	 * @return the value of cwv_wlt_tx.tx_id
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public String getTxId() {
		return txId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of
	 * the database column cwv_wlt_tx.tx_id
	 *
	 * @param txId the value for cwv_wlt_tx.tx_id
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public void setTxId(String txId) {
		this.txId = txId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table cwv_wlt_tx
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
		CscWltTxKey other = (CscWltTxKey) that;
		return (this.getTxId() == null ? other.getTxId() == null : this.getTxId().equals(other.getTxId()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table cwv_wlt_tx
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getTxId() == null) ? 0 : getTxId().hashCode());
		return result;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table cwv_wlt_tx
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", txId=").append(txId);
		sb.append("]");
		return sb.toString();
	}
}