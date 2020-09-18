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
		System.out.printf("(socket)會員[%s]連線，目前連線用戶群%s%n", userName, userNames);
	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
		SocketMessage msg = gson.fromJson(message, SocketMessage.class); // 使用gson包裝成物件
		String receiver = msg.getReceiver(); // 找出通知要傳給誰
		Session receiverSession = sessionsMap.get(receiver);
		if (receiverSession != null && receiverSession.isOpen()) {
			receiverSession.getAsyncRemote().sendText(message);
			System.out.printf("(socket)[%s]發送通知給[%s]，成功，內容:%s%n", msg.getSender(), msg.getReceiver(), msg.getMessage());
		}else {
			System.out.printf("(socket)[%s]發送通知給[%s]，失敗，內容:%s%n", msg.getSender(), msg.getReceiver(), msg.getMessage());
		}		
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		Set<String> userNames = sessionsMap.keySet();
		for (String userName : userNames) {
			if (sessionsMap.get(userName).equals(userSession)) {
				sessionsMap.remove(userName);
				System.out.printf("(socket)會員[%s]離線，目前連線用戶群%s%n", userName, userNames);
				break;
			}
		}	
	}
	
	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("(socket)Error: " + e.toString());
	}
}
