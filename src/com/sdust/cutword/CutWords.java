package com.sdust.cutword;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class CutWords {

	public static String cutWords(String document,String separator) throws IOException {
		Analyzer anal = new IKAnalyzer(true);
		StringBuilder sb = new StringBuilder();
		StringReader reader = new StringReader(document);
		// 分词
		TokenStream ts = anal.tokenStream("", reader);
		CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
		// 遍历分词数据
		while (ts.incrementToken()) {
			sb.append((term.toString() + separator));
		}
		reader.close();
		return sb.toString().trim();
	}
	
	public static List<String> cutWords(String document) throws IOException {
		List<String> list = new ArrayList<String>();
		Analyzer anal = new IKAnalyzer(true);
		StringReader reader = new StringReader(document);
		// 分词
		TokenStream ts = anal.tokenStream("", reader);
		CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
		// 遍历分词数据
		while (ts.incrementToken()) {
			list.add(term.toString());
		}
		reader.close();
		return list;
	}
}
