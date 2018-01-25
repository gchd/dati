package com.sdust.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;
import com.google.gson.Gson;
import com.sdust.entity.WordsResult;
import com.sdust.main.DatiMain;
import com.sdust.parser.BaiWanQuestionParser;
import com.sdust.select.MaximumSelect;

public class BaiduOcr {
	// 设置APPID/AK/SK
	public static final String APP_ID = "10740408";
	public static final String API_KEY = "6QS2mkXSIXKWyPxDwkl8uFha";
	public static final String SECRET_KEY = "pGvGEWuzcb1o4AOG1MQfGfi6d3y1yhzb";

	/**
	 * 调用百度ocr文字识别的接口，每天免费调用500次。APP_ID, API_KEY, SECRET_KEY要设置成自己的
	 * @param picStorePath
	 * @return
	 */
	public static WordsResult recognizeWords(String picStorePath) {
		AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);

		HashMap<String, String> options = new HashMap<String, String>();
	    options.put("language_type", "CHN_ENG");
	    options.put("detect_direction", "true");
	    options.put("detect_language", "true");
	    options.put("probability", "true");
	    // 参数为本地图片路径
	    JSONObject res = client.basicGeneral(picStorePath, options);
	    Gson gson = new Gson();
	    return gson.fromJson(res.toString(2),WordsResult.class);
	}
	
	public static void main(String[] args){
		System.out.println(recognizeWords("F:/xiga.jpg"));
	}
	
	
}
