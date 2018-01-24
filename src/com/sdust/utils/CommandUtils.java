package com.sdust.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.sdust.entity.Point;

public class CommandUtils {

	public static CommandResult execCommand(String command) {
		return execCommand(new String[] { command }, true);
	}

	public static CommandResult execCommand(List<String> commands) {
		return execCommand(
				commands == null ? null : commands.toArray(new String[] {}), true);
	}

	public static CommandResult execCommand(String[] commands) {
		return execCommand(commands, true);
	}
	
	public static CommandResult execCommand(String command, boolean isRoot,
			boolean isNeedResultMsg) {
		return execCommand(new String[] { command }, isNeedResultMsg);
	}
	
	public static CommandResult execCommand(List<String> commands, boolean isRoot,
			boolean isNeedResultMsg) {
		return execCommand(
				commands == null ? null : commands.toArray(new String[] {}), isNeedResultMsg);
	}


	public static CommandResult execCommand(String[] commands, boolean isNeedResultMsg) {
		int result = -1;
		
		StringBuilder successMsg = new StringBuilder();
		StringBuilder errorMsg = new StringBuilder();;
		
		if (commands == null || commands.length == 0) {
			return new CommandResult(result, null, null);
		}
		
		for (String command : commands) {
			Process process = null;
			try {
				process = Runtime.getRuntime().exec(command);
				//System.out.println("exec command start: " + command);
				process.waitFor();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(process.getErrorStream()));
				String s = null;
				while ((s  = bufferedReader.readLine()) != null) {
					successMsg.append(s);
				}
				result = 1;
				//System.out.println("exec command end: " + command);
			} catch (Exception e) {
				//e.printStackTrace();
				errorMsg.append(e.getMessage());
				result = 0;
			} finally {
				if (process != null) {
					process.destroy();
				}
			}
		}
		return new CommandResult(result, successMsg == null ? null
				: successMsg.toString(), errorMsg == null ? null
				: errorMsg.toString());
	}

	public static class CommandResult {

		public int result;
		public String successMsg;
		public String errorMsg;

		public CommandResult(int result) {
			this.result = result;
		}

		public CommandResult(int result, String successMsg, String errorMsg) {
			this.result = result;
			this.successMsg = successMsg;
			this.errorMsg = errorMsg;
		}
	}
	
	/**
	 * 得到手机屏幕截图保存到storePath目录下
	 */
	public static void getScreenshotImageFile(String picName, String storePath) {
		String cmdString1 = "adb shell screencap -p /sdcard/" + picName
				+ ".png";
		execCommand(cmdString1);
		String cmdString2 = "adb pull /sdcard/" + picName + ".png "
				+ storePath;
		execCommand(cmdString2);
	}
	
	/**
	 * 执行按压操作
	 * @param p 按压位置
	 * @param pressTime 按压时间（单位毫秒）
	 */
	public static void press(Point p, int pressTime) {
		String command = String.format("adb shell input swipe %s %s %s %s %s",
				p.getPointX(), p.getPointY(), p.getPointX(), p.getPointY(),
				pressTime);
		execCommand(command);
	}
}