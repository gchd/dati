package com.sdust.main;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.sdust.search.Search;
import com.sdust.select.Select;

public class DatiMain {
	
	private Select select;
	
	public DatiMain(Select select){
		this.select = select;
	}
	
	public Select getSelect() {
		return select;
	}

	public void setSelect(Select select) {
		this.select = select;
	}



	public static void main(String[] args) throws IOException, InterruptedException {
		Search search = new Search("味精有什么好处",10);
		search.start();
		TimeUnit.MILLISECONDS.sleep(5000);
		
		//统计选项在文本中出现次数，并选择
		
		System.out.println("第二次"+search.getContentList().size());
	}
}
