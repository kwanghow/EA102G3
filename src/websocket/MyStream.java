package websocket;

import java.io.*;
import java.util.*;

import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;

import javax.websocket.Session;
import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.OnError;
import javax.websocket.OnClose;
import javax.websocket.CloseReason;

@ServerEndpoint("/MyStream/{myRoom}")
public class MyStream {

	private static final Set<Session> allSessions = Collections.synchronizedSet(new HashSet<Session>());
	private boolean firstFlag = true;

	private static final HashMap<String, String> userMap = new HashMap<String, String>();
    
	
	private static final HashMap<String, String> roomMap = new HashMap<String, String>();
	@OnOpen
	public void onOpen(@PathParam("myRoom") String myRoom, Session userSession)
			throws IOException {

		allSessions.add(userSession);
		roomMap.put(userSession.getId(),myRoom);

	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
		JSONObject json;
		try {
			json = new JSONObject(message);

			if (firstFlag) {
				userMap.put(userSession.getId(), json.getString("userName"));

				String name = json.getString("userName");
				json.put("message", name + "已加入直播間");
				json.put("userName", "系統訊息");
				json.put("count", allSessions.size());

				for (Session session : allSessions) {
					if (session.isOpen()&&roomMap.get(session.getId()).equals(roomMap.get(userSession.getId()))) {
						session.getAsyncRemote().sendText(json.toString());
						System.out.println(json.toString());
					}
				}
				firstFlag = false;
			} else {
				for (Session session : allSessions) {
					if (session.isOpen()&&roomMap.get(session.getId()).equals(roomMap.get(userSession.getId()))) {
						json.put("count", allSessions.size());
						if (session != userSession) {
							json.put("userName", userMap.get(userSession.getId()));
						} else {
							json.put("userName", "我");
						}
						session.getAsyncRemote().sendText(json.toString());
						System.out.println(json.toString());
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@OnError
	public void onError(Session userSession, Throwable e) {
//		e.printStackTrace();
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		allSessions.remove(userSession);
		roomMap.remove(userSession.getId());
		System.out
				.println(userSession.getId() + ": Disconnected: " + Integer.toString(reason.getCloseCode().getCode()));
	}

}
