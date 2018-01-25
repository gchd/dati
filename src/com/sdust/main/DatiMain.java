package com.sdust.main;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.helper.StringUtil;

import com.sdust.entity.Question;
import com.sdust.parser.QuestionParser;
import com.sdust.search.Search;
import com.sdust.select.Select;

public class DatiMain {
	
	private Select select;
	
	private QuestionParser questionParser;
	
	public DatiMain( QuestionParser questionParser, Select select){
		this.select = select;
		this.questionParser = questionParser;
	}
	
	public QuestionParser getQuestionParser() {
		return questionParser;
	}

	public void setQuestionParser(QuestionParser questionParser) {
		this.questionParser = questionParser;
	}

	public Select getSelect() {
		return select;
	}

	public void setSelect(Select select) {
		this.select = select;
	}

	public void start(){
		System.out.println("开始执行");
		long startTime = System.currentTimeMillis();    //获取开始时间
		
		//解析图片得到题目
		Question question = null;
		try {
			question = questionParser.getQuestion();
			System.out.println("题干是："+question.getQuestion());
			for(String s : question.getOption()){
				System.out.println(s);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long zhongTime = System.currentTimeMillis();
		System.out.println("识别文字运行时间：" + (zhongTime - startTime) + "ms");
		
		System.out.println("开始搜索题目");
		//搜索题目，得到题干搜索的前n条文章（2秒钟之后开始选择选项）
		Search baiduSearch = new Search(question.getQuestion(),10);
		List<String> art = baiduSearch.getArt();

		long souTime = System.currentTimeMillis();
		System.out.println("搜索题目运行时间：" + (souTime - zhongTime) + "ms");
		
		System.out.println("开始选择选项");
		question = select.select(question, art);
		System.out.println("答案是：" + (char)(question.getAnswer()+65));
		//统计选项在文本中出现次数，并选择
		long endTime = System.currentTimeMillis();    //获取结束时间
		System.out.println("选择选项运行时间：" + (endTime - souTime) + "ms");
		
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
//		System.out.println("第二次"+baiduSearch.getContentList().size());
	}
	
	
}
