package com.yuncai.core.util;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
public class Jsons {
	protected static Logger log = Logger.getLogger(Jsons.class);

	public static <T> String toJson(T obj) {
		String json = null;
		ObjectMapper mapper = null;
		try {
			mapper = new ObjectMapper();
			json = mapper.writeValueAsString(obj);// 把map或者是list转换成
		} catch (Exception e) {
			e.printStackTrace();
			log.error("生成json串成产生异常", e);
		} finally {
			mapper = null;
		}
		return json;
	}

	public static <T> T toObject(String jsonStr, Class<T> clazz) {
		ObjectMapper mapper = null;
		try {
			mapper = new ObjectMapper();
			mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			return mapper.readValue(jsonStr,clazz);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("json串成转换为对象产生异常", e);
		} finally {
			mapper = null;
		}
		return null;
	}
}
