package com.sdust.main;

import java.util.concurrent.TimeUnit;

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
		//解析图片得到题目
		Question question = questionParser.getQuestion();
		System.out.println("开始搜索题目");
		//搜索题目，得到题干搜索的前n条文章（4秒钟之后开始选择选项）
		Search baiduSearch = new Search(question.getQuestion(),10);
		baiduSearch.start();
		try {
			TimeUnit.MILLISECONDS.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		question = select.select(question, baiduSearch.getContentList());
		System.out.println("答案是：" + question.getAnswer());
		//统计选项在文本中出现次数，并选择
		
//		System.out.println("第二次"+baiduSearch.getContentList().size());
	}
}
