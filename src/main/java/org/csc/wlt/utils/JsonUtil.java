package org.csc.wlt.utils;

import java.nio.charset.Charset;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;
import com.googlecode.protobuf.format.JsonFormat.ParseException;

public class JsonUtil {

	public final static JsonFormat jsonFormat = new JsonFormat();
//	public final static JsonPBFormat jsonPBFormat = new JsonPBFormat();

	public static void jsonToObject(String json, Message.Builder t) throws ParseException {
		jsonFormat.merge(json, ExtensionRegistry.getEmptyRegistry(), t);
	}

	public static void jsonToObject(byte[] jsonByte, Message.Builder t) throws ParseException {
		jsonToObject(new String(jsonByte, Charset.defaultCharset()), t);
	}
}
