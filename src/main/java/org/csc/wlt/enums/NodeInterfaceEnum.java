package org.csc.wlt.enums;

public enum NodeInterfaceEnum {
	QUERY_ADDRESS("queryAddressURL"), CREATE_TRANSACTION("createTransactionURL"), CREATE_CONTRACT("createContractURL"),
	QUERY_TRANSACTION("queryTransaction"), QUERY_LASTBLOCK("queryLastBlock"), QUERY_TXBLOCK("queryTxBlock"),
	NODE_URL_LIST("chain_node_list");
	private String value;

	private NodeInterfaceEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
