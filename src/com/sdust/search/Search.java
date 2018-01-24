package com.sdust.search;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Search {

	private Vector<String> contentList = new Vector<String>();

	private String keyWord;

	private int itemNum = 10;
	
	public Search(String keyWord) {
		this.keyWord = keyWord;
	}

	public Search(String keyWord, int itemNum) {
		this.keyWord = keyWord;
		this.itemNum = itemNum;
	}

	public Vector<String> getContentList() {
		return contentList;
	}

	public void setContentList(Vector<String> contentList) {
		this.contentList = contentList;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	/**
	 * 获得百度的搜索页面，前itemNum个搜索结果
	 * 
	 * @param keyWord 关键词
	 * @param itemNum 搜索条数
	 * @return
	 * @throws IOException
	 */
	public List<String> getUrls(String keyWord, int itemNum) throws IOException {
		List<String> urlList = new ArrayList<String>();
		String urlString = "http://www.baidu.com/s?tn=ichuner&lm=-1&word="
				+ URLEncoder.encode(keyWord, "gb2312") + "&rn=" + itemNum;
		Document doc = null;
		try {
			doc = Jsoup.connect(urlString).userAgent("Mozilla/5.0")
					.timeout(3000).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements elements = doc.getElementsByClass("result");
//		System.out.println(elements.size());
		for (Element element : elements) {
			String url = element.getElementsByClass("t").get(0)
					.getElementsByTag("a").get(0).attr("href");
			urlList.add(url);
//			System.out.println(url);
		}
		return urlList;
	}

	/**
	 * 得到url的html页面
	 * @param urlString
	 * @return
	 * @throws IOException
	 */
	public String getHTML(String urlString) throws IOException {
		Document doc = null;
		try {
			doc = Jsoup.connect(urlString).userAgent("Mozilla/5.0")
					.timeout(3000).get();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return "";
		}
		return doc.toString();
	}

	/**
	 * 保存文章到本地
	 * @param content
	 */
	public static void saveArticle(String content) {
		String lujing = "D:\\MyLoadArticle\\" + UUID.randomUUID() + ".txt";
		File file = new File(lujing);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.flush();
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void start() {
		 
		// 创建一个定长的线程池
		final ScheduledExecutorService exes = Executors.newScheduledThreadPool(5);
		Set<Future<Boolean>> setThreads = new java.util.HashSet<Future<Boolean>>();
		List<String> urls = null;
		try {
			urls = getUrls(keyWord, itemNum);
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
			return;
		}
		for (String links : urls) {
			// 创建线程任务
			GetPageContentThread fetchThread = new GetPageContentThread(links,contentList);
			// 提交线程任务
			setThreads.add(exes.submit(fetchThread));
		}
		// 执行多线程任务
		for (Future<Boolean> future : setThreads) {
			try {
				future.get();
//				System.out.println(flagSucc);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		//保证4秒内一定要退出
		Runnable runnable = new Runnable() {  
            public void run() {  
            	exes.shutdownNow();
            	System.out.println("搜索完毕，下一步进行选择");
            	/*for(String s : contentList){
            		saveArticle(s);
            	}*/
            }
        };
		exes.schedule(runnable, 3500, TimeUnit.MILLISECONDS); 
	}

	/**
	 * 线程内部类，执行url将网页内容保存到list中
	 * @author 江北望江南
	 */
	private class GetPageContentThread implements Callable<Boolean> {
		private String url;
		private Vector<String> vector;

		public GetPageContentThread(String url, Vector<String> vector) {
			super();
			this.url = url;
			this.vector = vector;
		}

		@Override
		public Boolean call() throws Exception {
			String content = getHTML(url);

			Boolean flagSucc = false;
			if (content != null && !"".equals(content)) {
				vector.add(content);
				return true;
			}
			return flagSucc;
		}

	}

	public static void main(String[] args) throws IOException, InterruptedException {
		Search test = new Search("味精有什么好处");
		System.out.println("第一次"+test.getContentList().size());
		test.start();
		TimeUnit.MILLISECONDS.sleep(5000);
		System.out.println("第二次"+test.getContentList().size());
	}
}