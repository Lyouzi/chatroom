package model;

import java.io.Serializable;

import model.User;

public class MessageBox implements Serializable{
    private User  from;
	private User to;
	private String type;
	private String content;
	private String time;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public MessageBox(User from, User to, String type, String content, String time) {
		super();
		this.setFrom(from);
		this.setTo(to);
		this.setType(type);
		this.setContent(content);
		this.setTime(time);
	}

	public MessageBox() {
		super();
	}
	public String toString() {
		return "MessageBox [from=" + from + ", to=" + to + ", type=" + type + ", content=" + content + ", time=" + time
				+ "]";
	}

}
