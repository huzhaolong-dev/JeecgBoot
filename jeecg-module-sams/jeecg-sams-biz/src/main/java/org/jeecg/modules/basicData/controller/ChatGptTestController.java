package org.jeecg.modules.basicData.controller;

import com.plexpt.chatgpt.entity.chat.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.basicData.service.openai.OpenAIUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * <p>
 * ChatGptTest 前端控制器
 * <p>
 * 
 * @Author: Hu
 * @Since： 2024-3-22
 */
@RestController
@RequestMapping("/openai/chatgpt")
@Slf4j
@Api(tags="ChatGpt测试")
public class ChatGptTestController {


	/**
	 * testChat
	 * @return
	 */
	@ApiOperation("testChat接口")
	@RequestMapping(value = "/testChat", method = RequestMethod.GET)
	public Result<String> testChat() {
		Result<String> result = new Result<String>();
		//创建一个系统消息对象，用于设置聊天话题
		Message system = Message.ofSystem("你现在是一个脱口秀达人，精通娱乐段子");
		//创建一个用户消息对象，用于输入聊天内容
		Message message = Message.of("给我讲一个与动物有关的段子");
		Message message2 = Message.of("这里要包括老虎、大象这两种动物");
		Message res = OpenAIUtils.chatGptRes(Arrays.asList(system, message, message2));
		result.setResult(res.getContent());
		return result;
	}

}
