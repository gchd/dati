package com.sdust.select;

import java.util.List;

import com.sdust.entity.Question;
/**
 * 根据文本选择选项
 * @author 江北望江南
 *
 */
public interface Select {
	public Question select(Question question, List<String> list);
}
