package io.openvidu.js.java.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONparsing {

	public JSONObject stringToJson(String str) {
		JSONParser parser = new JSONParser();
		Object obj = null;
		
		try {
			obj = parser.parse( str );
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		JSONObject jsonObj = (JSONObject) obj;
		
		return jsonObj;
	}

}
