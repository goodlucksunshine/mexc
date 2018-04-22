package com.mexc.dao.dto.asset;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2017/12/30
 * Time: 下午12:13
 */
public class AssetBalanceCheckDto {

    /**
     *
     */
    private String memberId;
    /**
     * 用户账号
     */
    private String account;
    /**
     * 钱包地址
     */
    private String walletAddress;
    /**
     * 向量
     */
    private String iv;
    /**
     * 钱包密码
     */
    private String accountPwd;
    /**
     * 短语
     */
    private String assetPhrase;

    /**
     * 用户序号
     */
    private String memberSeq;
    /**
     * 钱包ID
     */
    private String walletId;
    /**
     * 币种
     */
    private String vcoinToken;
    /**
     * 是否是主币
     */
    private Integer mainCoin;
    /**
     * 合约地址
     */
    private String contractAddress;
    /**
     * 文件地址
     */
    private String filePath;
    /**
     *
     */
    private String assetId;
    /**
     * 小数位
     */
    private Integer scale;

    /**
     * 虚拟币id
     */

    private String vCoinId;

    public String getVCoinId() {
        return vCoinId;
    }

    public void setVCoinId(String vCoinId) {
        this.vCoinId = vCoinId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }


    public String getAccountPwd() {
        return accountPwd;
    }

    public void setAccountPwd(String accountPwd) {
        this.accountPwd = accountPwd;
    }

    public String getAssetPhrase() {
        return assetPhrase;
    }

    public void setAssetPhrase(String assetPhrase) {
        this.assetPhrase = assetPhrase;
    }

    public String getMemberSeq() {
        return memberSeq;
    }

    public void setMemberSeq(String memberSeq) {
        this.memberSeq = memberSeq;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getVcoinToken() {
        return vcoinToken;
    }

    public void setVcoinToken(String vcoinToken) {
        this.vcoinToken = vcoinToken;
    }

    public Integer getMainCoin() {
        return mainCoin;
    }

    public void setMainCoin(Integer mainCoin) {
        this.mainCoin = mainCoin;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }
}
