package com.sdust.entity;

import java.util.List;

public class WordsResult{
	public List<word> words_result;
	public int direction;
	public int words_result_num;
	public int language;
	public long log_id;
	public class word{
		public String words;
		public Canshu probability;
		public class Canshu{
			public double min;
			public double variance;
			public double average;
		}
	}
}