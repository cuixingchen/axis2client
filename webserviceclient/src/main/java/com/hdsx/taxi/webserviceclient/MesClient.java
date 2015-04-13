package com.hdsx.taxi.webserviceclient;

import java.util.HashMap;

public class MesClient {

	public static void main(String[] args) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("CorpID", "");
		map.put("Pwd", "");
		map.put("Mobile", "");
		map.put("Content", "");
		map.put("Cell", "");//子号
		map.put("SendTime", "");//固定14位长度字符串，比如：20060912152435代表2006年9月12日15时24分35秒，为空表示立即

	}

}
