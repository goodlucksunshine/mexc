package com.mexc.vcoin;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/1/10
 * Time: 上午10:17
 */
public enum TokenEnum {

    BIT_COIN("BTC", "比特币"), ETH_COIN("ETH", "以太坊");

    private String code;

    private String desc;

    TokenEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static TokenEnum forCode(String code) {
        for (TokenEnum e : TokenEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        throw new IllegalArgumentException("not find TokenEnum." + code);
    }
}
