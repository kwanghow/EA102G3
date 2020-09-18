package com.websocketchat.jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisHandleMessage {
	// æ­¤ç?„ä?‹key??„è¨­è¨ˆç‚º(?™¼?è?…å?ç¨±:?¥?”¶?…å?ç¨±)ï¼Œå¯¦??›æ?‰æ¡?”¨(?™¼?è?…æ?ƒå“¡ç·¨è??:?¥?”¶?…æ?ƒå“¡ç·¨è??)

	private static JedisPool pool = JedisPoolUtil.getJedisPool();

	public static List<String> getHistoryMsg(String sender, String receiver) {
		String key = new StringBuilder(sender).append(":").append(receiver).toString();
		Jedis jedis = null;
		jedis = pool.getResource();
		jedis.auth("123456");
		List<String> historyData = jedis.lrange(key, 0, -1);
		jedis.close();
		return historyData;
	}

	public static void saveChatMessage(String sender, String receiver, String message) {
		// å°é?™æ–¹ä¾†èªªï¼Œéƒ½è¦å?„å?˜è?—æ­·?²??Šå¤©è¨˜é??
		String senderKey = new StringBuilder(sender).append(":").append(receiver).toString();
		String receiverKey = new StringBuilder(receiver).append(":").append(sender).toString();
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		jedis.rpush(senderKey, message);
		jedis.rpush(receiverKey, message);

		jedis.close();
	}
	
	public static List<String> getKeys(String sender) {
		ArrayList<String> list = new ArrayList<String>();
		Jedis jedis = null;
		jedis = pool.getResource();
		jedis.auth("123456");
        Set<String> set = jedis.keys(sender+":*"); 
        for (String key : set) {  
            list.add(key.substring(key.indexOf(":")+1));
            System.out.println("key "+key);
        }
        jedis.close();
        return list;
	}
	public static void main(String args[]) {
		ArrayList<String> list = new ArrayList<String>();
		Jedis jedis = null;
		jedis = pool.getResource();
		jedis.auth("123456");
        Set<String> set = jedis.keys("C01:*");  
        for (String key : set) {  
            list.add(key.substring(key.indexOf(":")));
            System.out.println(key);
        }
        String jsonStr = new JSONArray(list).toString();
	}

}
