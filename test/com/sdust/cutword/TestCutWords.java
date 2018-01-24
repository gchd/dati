package com.sdust.cutword;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

public class TestCutWords {

	@Test
	public void test() throws IOException {
		HashSet<String> set = new HashSet<>();
		String data = CutWords.cutWords("谷氨酸钠","|");
		System.out.println(data);
	}
}
