package com.mexc.dao.dto.member;

/**
 * Created by huangxinguang on 2018/3/10 下午12:44.
 */
public class MemberQueryDto {
    private Integer currentPage;

    private Integer showCount;

    private String account;
    /**
     * 1-正常，2-暂停，3-注销，9-异常
     */
    private String status;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getShowCount() {
        return showCount;
    }

    public void setShowCount(Integer showCount) {
        this.showCount = showCount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
