package com.sdust.parser;

import java.util.ArrayList;
import java.util.List;

import com.sdust.entity.Question;
import com.sdust.utils.CommandUtils;
import com.sdust.utils.ImageUtils;

/**
 * 百万英雄题目截图解析；不同平台解析请实现QuestionParser
 * @author 江北望江南
 */
public class BaiWanQuestionParser implements QuestionParser{
	private final String STORE_PATH = "F：\\";
	private final String PIC_NAME = "xinga";
	
	@Override
	public Question getQuestion() {
		
		CommandUtils.getScreenshotImageFile(PIC_NAME, STORE_PATH);
		
		ImageUtils.recognizeWords("F:/tupian/a.jpg");
		
		Question question = new Question();
		question.setQuestion("我国现役火箭的最大直径是3.35米，这个宽度根据什么决定的？");
		question.setOptionCount(3);
		List<String> option = new ArrayList<String>();
		option.add("火箭重量");
		option.add("马屁股宽度");
		option.add("货车宽度");
		question.setOption(option);
		return question;
	}

}
