package com.sdust.cutword;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

public class TestCutWords {

	@Test
	public void test() throws IOException {
		HashSet<String> set = new HashSet<>();
		String data = CutWords.cutWords("测量人体体温的部位中，最接近人实际体温的是","|");
		System.out.println(data);
	}
}
