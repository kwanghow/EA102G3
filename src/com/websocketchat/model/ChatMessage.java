package com.websocketchat.model;

public class ChatMessage {
	private String type;
	private String sender;
	private String receiver;
	private String message;
	private String receiverName;
	private String senderName;

	
	public ChatMessage(String type, String sender, String receiver, String message, String receiverName) {
		this.type = type;
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
		this.receiverName = receiverName;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	
	
	
	
}
