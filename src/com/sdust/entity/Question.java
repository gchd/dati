package com.sdust.entity;

import java.util.List;

public class Question {
	private String question;
	private int optionCount;
	private List<String> option;
	//从0开始记
	private int answer;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getOptionCount() {
		return optionCount;
	}

	public void setOptionCount(int optionCount) {
		this.optionCount = optionCount;
	}

	public List<String> getOption() {
		return option;
	}

	public void setOption(List<String> option) {
		this.option = option;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "Question [question=" + question + ", optionCount="
				+ optionCount + ", option=" + option + ", answer=" + answer
				+ "]";
	}
}
