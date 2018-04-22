package com.mexc.mq.vo;

public class MqMsgVo<T> {

	public String msgId;
	public T content;
	public String from;
	public String to;
	public String isValid;
	public String insertTime;
	public String lastUpdateTime;
	public Integer count;

	
	public MqMsgVo(){}
	
	public MqMsgVo(String msgId, T content, String from, String to, String isValid, String insertTime,
                   String lastUpdateTime, Integer count) {
		this.msgId = msgId;
		this.content = content;
		this.from = from;
		this.to = to;
		this.isValid = isValid;
		this.insertTime = insertTime;
		this.lastUpdateTime = lastUpdateTime;
		this.count = count;
	}

	public String getMsgId() {
		return msgId;
	}

	public T getContent() {
		return content;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public String getIsValid() {
		return isValid;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public MqMsgVo<T> setMsgId(String msgId) {
		this.msgId = msgId;
		return this;
	}

	public MqMsgVo<T> setContent(T content) {
		this.content = content;
		return this;
	}

	public MqMsgVo<T> setFrom(String from) {
		this.from = from;
		return this;
	}

	public MqMsgVo<T> setTo(String to) {
		this.to = to;
		return this;
	}

	public MqMsgVo<T> setIsValid(String isValid) {
		this.isValid = isValid;
		return this;
	}

	public MqMsgVo<T> setInsertTime(String insertTime) {
		this.insertTime = insertTime;
		return this;
	}

	public MqMsgVo<T> setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
		return this;
	}

	public Integer getCount() {
		return count;
	}

	public MqMsgVo<T> setCount(Integer count) {
		
		this.count = count;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MqMsgVo [msgId=");
		builder.append(msgId);
		builder.append(", content=");
		builder.append(content);
		builder.append(", from=");
		builder.append(from);
		builder.append(", to=");
		builder.append(to);
		builder.append(", isValid=");
		builder.append(isValid);
		builder.append(", insertTime=");
		builder.append(insertTime);
		builder.append(", lastUpdateTime=");
		builder.append(lastUpdateTime);
		builder.append(", count=");
		builder.append(count);
		builder.append("]");
		return builder.toString();
	}



	
	
}
