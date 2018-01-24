package com.sdust.main;

import java.io.IOException;
import com.sdust.parser.BaiWanQuestionParser;
import com.sdust.select.MaximumSelect;

public class TestDatiMain {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		DatiMain datiMain = new DatiMain(new BaiWanQuestionParser(), new MaximumSelect());
		datiMain.start();
	}
}
