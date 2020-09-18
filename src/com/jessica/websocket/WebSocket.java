package com.jessica.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

@ServerEndpoint("/WebSocket/{userName}")
public class WebSocket {
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();

	@OnOpen
	public void onOpen(@PathParam("userName") String userName, Session userSession) throws IOException {
		sessionsMap.put(userName, userSession);
		Set<String> userNames = sessionsMap.keySet();
		System.out.printf("(socket)�|��[%s]�s�u�A�ثe�s�u�Τ�s%s%n", userName, userNames);
	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
		SocketMessage msg = gson.fromJson(message, SocketMessage.class); // �ϥ�gson�]�˦�����
		String receiver = msg.getReceiver(); // ��X�q���n�ǵ���
		Session receiverSession = sessionsMap.get(receiver);
		if (receiverSession != null && receiverSession.isOpen()) {
			receiverSession.getAsyncRemote().sendText(message);
			System.out.printf("(socket)[%s]�o�e�q����[%s]�A���\�A���e:%s%n", msg.getSender(), msg.getReceiver(), msg.getMessage());
		}else {
			System.out.printf("(socket)[%s]�o�e�q����[%s]�A���ѡA���e:%s%n", msg.getSender(), msg.getReceiver(), msg.getMessage());
		}		
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		Set<String> userNames = sessionsMap.keySet();
		for (String userName : userNames) {
			if (sessionsMap.get(userName).equals(userSession)) {
				sessionsMap.remove(userName);
				System.out.printf("(socket)�|��[%s]���u�A�ثe�s�u�Τ�s%s%n", userName, userNames);
				break;
			}
		}	
	}
	
	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("(socket)Error: " + e.toString());
	}
}
