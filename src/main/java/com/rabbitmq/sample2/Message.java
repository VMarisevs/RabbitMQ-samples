package com.rabbitmq.sample2;

import java.io.Serializable;

public class Message implements Serializable{

	private String msg = "";
	private int delay = 1000;
	
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getDelay() {
		return delay;
	}
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	
}
