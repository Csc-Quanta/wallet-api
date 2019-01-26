package org.csc.wlt.enums;

public enum TransTypeEnum {
    /**
     * 默认交易
     */
    TYPE_DEFAULT(0),
    /**
     * 创建联合账户
     */
    TYPE_CreateUnionAccount(1),
    /**
     * Token交易
     */
    TYPE_TokenTransaction(2),
    /**
     * 联合账户交易
     */
    TYPE_UnionAccountTransaction(3),
    /**
     *
     */
    TYPE_CallInternalFunction(4),
    /**
     *CryptoToken交易
     */
    TYPE_CryptoTokenTransaction(5),
    /**
     *
     */
    TYPE_LockTokenTransaction(6),
    /**
     * 创建合约
     */
    TYPE_CreateContract(7),
    /**
     *
     */
    TYPE_CallContract(8),
    /**
     * 创建Token
     */
    TYPE_CreateToken(9),
    /**
     *
     */
    TYPE_CreateCryptoToken(10);
    /**
     *
     */
    private int value = 0;

    private TransTypeEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public static TransTypeEnum transf(int t) {
        for (TransTypeEnum type : values()) {
            if (type.value == t) {
                return type;
            }
        }
        return TYPE_DEFAULT;
    }
}
