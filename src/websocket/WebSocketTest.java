package websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @ServerEndpoint ���ѬO�@�����h�������ѡA�����\��D�n�O�N�ثe�����w�q���@��websocket���A����,
 *                 ���Ѫ��ȱN�Q�Ω��ť�ϥΪ̳s�u���׺ݳX��URL�a�},�Ȥ�ݥi�H�q�L�o��URL�ӳs�u��WebSocket���A����
 */
@ServerEndpoint("/websocket/{empChatId}")
public class WebSocketTest {
//�R�A�ܼơA�ΨӰO����e�u�W�s�u�ơC���ӧ⥦�]�p��������w�����C
	private static final Set<Session> connectedSessions = Collections.synchronizedSet(new HashSet<>());

	/*
	 * �p�G�Q���oHttpSession�PServletContext������@
	 * ServerEndpointConfig.Configurator.modifyHandshake()�A
	 * �Ѧ�https://stackoverflow.com/questions/21888425/accessing-servletcontext-and-httpsession-in-onmessage-of-a-jsr-356-serverendpoint
	 */
	@OnOpen
	public void onOpen(@PathParam("empChatId") String empChatId, Session userSession) throws IOException {
		connectedSessions.add(userSession);
		String text = String.format("Session ID = %s, connected; userName = %s", userSession.getId(), empChatId);
		System.out.println(text);
	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
		for (Session session : connectedSessions) {
			if (session.isOpen())
				session.getAsyncRemote().sendText(message);
		}
		System.out.println("Message received: " + message);
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		connectedSessions.remove(userSession);
		String text = String.format("session ID = %s, disconnected; close code = %d; reason phrase = %s",
				userSession.getId(), reason.getCloseCode().getCode(), reason.getReasonPhrase());
		System.out.println(text);
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

}