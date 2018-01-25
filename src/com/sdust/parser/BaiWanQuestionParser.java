package com.sdust.parser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.sdust.entity.Point;
import com.sdust.entity.Question;
import com.sdust.entity.RGBValue;
import com.sdust.entity.WordsResult;
import com.sdust.utils.BaiduOcr;
import com.sdust.utils.CommandUtils;
import com.sdust.utils.ImageUtils;

/**
 * 百万英雄题目截图解析；不同平台解析请实现QuestionParser接口
 * @author 江北望江南
 */
public class BaiWanQuestionParser implements QuestionParser{
	private final String STORE_PATH = "F:\\";
	private final String PIC_NAME = "xiga";
	private final String QUESTION_PIC_NAME = "questionxiga";
	private final String OPTION_PIC_NAME = "optionxiga";
	
	//不同手机分辨率不同，手动设置以下参数
	//截取题干部分的图片左上角起始位置，宽度，高度
	private int questionX, questionY, questionWidth, questionHeight;
	//截取选项部分的图片左上角起始位置，宽度，高度
	private int optionX, optionY, optionWidth, optionHeight;
	
	@Override
	public Question getQuestion() {
		//截图
		//CommandUtils.getScreenshotImageFile(PIC_NAME, STORE_PATH);
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(new File(STORE_PATH+PIC_NAME+".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		Question question = new Question();
		//根据颜色自动识别题干和选项位置
		//----------------------------------------------------------------
		//题目背景接近白色的颜色
		String backgroundColor = "#F8F8F8";
		//选项边框颜色
		String optionBorder = "#E2E2E2";
		int minh = height-1;
		int maxh = 0;
		for(int h = 0; h < height; h++){
			RGBValue rgbV = ImageUtils.getRGBByPoint(bufferedImage,width/4,h);
			if(backgroundColor.equalsIgnoreCase(rgbV.getHexColor())){
				if(h < minh){
					minh = h;
				}
				if(h > maxh){
					maxh = h;
				}
			}
		}
		//第一条边框高度，用于分割题干和选项
		int firstborder = 0;
		for(int h = 0; h < height; h++){
			RGBValue rgbV = ImageUtils.getRGBByPoint(bufferedImage,width/4,h);
			if(optionBorder.equalsIgnoreCase(rgbV.getHexColor())){
				firstborder = h;
				break;
			}
		}
		
		int minw = width-1;
		int maxw = 0;
		for(int w = 0; w < width; w++){
			RGBValue rgbV = ImageUtils.getRGBByPoint(bufferedImage,w,height/3);
			if(backgroundColor.equalsIgnoreCase(rgbV.getHexColor())){
				if(w < minw){
					minw = w;
				}
				if(w > maxw){
					maxw = w;
				}
			}
		}
		
		//将题目和选项部分截取出来
		ImageUtils.cut(STORE_PATH+PIC_NAME+".jpg", STORE_PATH+QUESTION_PIC_NAME+".jpg", minw, minh,maxw-minw ,firstborder-minh);
		WordsResult questionwrods = BaiduOcr.recognizeWords(STORE_PATH+QUESTION_PIC_NAME+".jpg");
		String questionString = "";
		for(int i = 0 ; i < questionwrods.words_result.size(); i++){
//			System.out.println(questionwrods.words_result.get(i).words);
			questionString = questionString + questionwrods.words_result.get(i).words;
		}
		ImageUtils.cut(STORE_PATH+PIC_NAME+".jpg", STORE_PATH+OPTION_PIC_NAME+".jpg", minw, firstborder,maxw-minw ,maxh-firstborder);
		WordsResult optionwrods = BaiduOcr.recognizeWords(STORE_PATH+OPTION_PIC_NAME+".jpg");
		List<String> option = new ArrayList<String>();
		for(int i = 0 ; i < optionwrods.words_result.size(); i++){
//			System.out.println(optionwrods.words_result.get(i).words);
			option.add(optionwrods.words_result.get(i).words);
		}
		//----------------------------------------------------------------
		//手动设置截取位置
//		questionX, questionY, questionWidth, questionHeight;
//		optionX, optionY, optionWidth, optionHeight;
//		ImageUtils.cut(STORE_PATH+PIC_NAME+".jpg", STORE_PATH+QUESTION_PIC_NAME+".jpg", questionX, questionY,questionWidth ,questionHeight);
//		WordsResult questionwrods = BaiduOcr.recognizeWords(STORE_PATH+QUESTION_PIC_NAME+".jpg");
//		String questionString = "";
//		for(int i = 0 ; i < questionwrods.words_result.size(); i++){
//			System.out.println(questionwrods.words_result.get(i).words);
//			questionString = questionString + questionwrods.words_result.get(i).words;
//		}
//		ImageUtils.cut(STORE_PATH+PIC_NAME+".jpg", STORE_PATH+OPTION_PIC_NAME+".jpg", optionX, optionY,optionWidth ,optionHeight);
//		WordsResult optionwrods = BaiduOcr.recognizeWords(STORE_PATH+OPTION_PIC_NAME+".jpg");
//		List<String> option = new ArrayList<String>();
//		for(int i = 0 ; i < optionwrods.words_result.size(); i++){
//			System.out.println(optionwrods.words_result.get(i).words);
//			option.add(optionwrods.words_result.get(i).words);
//		}
		//----------------------------------------------------------------
		
		question.setQuestion(questionString);
		question.setOption(option);
		question.setOptionCount(option.size());
		
		return question;
	}

	@Test
	public void main2() {
		getQuestion();
	}
}
