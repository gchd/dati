package com.sdust.select;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.sdust.cutword.CutWords;
import com.sdust.entity.Question;

/**
 * 根据文本选择选项，规则是选择出现次数最多的选项，也可以实现自己算法
 * 
 * @author 江北望江南
 */
public class MaximumSelect implements Select {

	@Override
	public Question select(Question question, List<String> list) {
		StringBuilder sb = new StringBuilder();
		for (String s : list) {
			sb.append(s);
		}
		String ques = null;
		try {
			ques = CutWords.cutWords(question.getQuestion(), " ").trim();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int a[] = new int[question.getOptionCount()];
		for (int i = 0; i < question.getOption().size(); i++) {
			String option = question.getOption().get(i);
			if (option.length() >= 6) {
				// 答案超过6各字分词统计
				try {
					int count = 0;
					List<String> cutWords = CutWords.cutWords(option);
					for(String word : cutWords){
						count = count + appearNumber(sb.toString(), word);
					}
					a[i] = count;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				a[i] = appearNumber(sb.toString(), option);
			}
		}
		if (question.getQuestion().contains("没有")
				|| question.getQuestion().contains("不是")
				|| question.getQuestion().contains("不可能")) {
			// 选择出现次数最少的
			if (getMinIndex(a) == -1) {
				// 选择失败，随机选
				question.setAnswer(0);
			} else {
				question.setAnswer(getMinIndex(a));
			}
		} else {
			// 选择出现次数最多的
			if (getMaxIndex(a) == -1) {
				// 选择失败，随机选
				question.setAnswer(0);
			} else {
				question.setAnswer(getMaxIndex(a));
			}
		}
		return question;
	}

	public static int appearNumber(String srcText, String findText) {
		int count = 0;
		Pattern p = Pattern.compile(findText);
		Matcher m = p.matcher(srcText);
		while (m.find()) {
			count++;
		}
		return count;
	}

	public int getMaxIndex(int[] arr) {
		if (arr == null || arr.length == 0) {
			return -1;// 如果数组为空 或者是长度为0 就返回null
		}
		int maxIndex = 0;// 假设第一个元素为最大值 那么下标设为0
		int[] arrnew = new int[2];// 设置一个 长度为2的数组 用作记录 规定第一个元素存储最大值 第二个元素存储下标
		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[maxIndex] < arr[i + 1]) {
				maxIndex = i + 1;
				arrnew[0] = arr[maxIndex];
				arrnew[1] = maxIndex;
			}
		}
		return arrnew[1];
	}

	public int getMinIndex(int[] arr) {
		if (arr == null || arr.length == 0) {
			return -1;
		}
		int minIndex = 0;
		int[] arrnew = new int[2];
		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[minIndex] > arr[i + 1]) {
				minIndex = i + 1;
				arrnew[0] = arr[minIndex];
				arrnew[1] = minIndex;
			}
		}
		return arrnew[1];
	}
	
}
