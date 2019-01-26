package org.csc.wlt.enums;

public enum CoinSymbolEnum {
	ETH("1"), BTC("2"), EOS("3"), CSC("4"), RMB("5");
	String number;

	CoinSymbolEnum(String number) {
		this.number = number;
	}

	/**
	 * 通过编号获得枚举 不存在的编号返回null
	 * 
	 * @param number
	 * @return
	 */
	public static CoinSymbolEnum getCoinSymbolByNumber(String number) {
		for (CoinSymbolEnum coinSymbol : values()) {
			if (coinSymbol.number.equals(number)) {
				return coinSymbol;
			}
		}
		return null;
	}
}
