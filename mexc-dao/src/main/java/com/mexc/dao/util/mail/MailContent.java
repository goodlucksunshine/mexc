package com.mexc.dao.util.mail;

public class MailContent {

    /** 发送人 */
    private String userForm;

    /** 收件人 */
    private String[] userTo;

    /** 标题 */
    private String title;

    /** 内容 */
    private String body;

    public String getUserForm() {
        return userForm;
    }

    public void setUserForm(String userForm) {
        this.userForm = userForm;
    }

    public String[] getUserTo() {
        return userTo;
    }

    public void setUserTo(String[] userTo) {
        this.userTo = userTo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
