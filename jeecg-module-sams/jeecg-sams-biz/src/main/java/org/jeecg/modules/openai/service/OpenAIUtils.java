package org.jeecg.modules.openai.service;

import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.util.Proxys;

import java.net.Proxy;
import java.util.Arrays;
import java.util.List;

public class OpenAIUtils {
    private static final String apiKey = System.getProperty("OPENAI-API-KEY");
    private static final String apiHost = "https://api.openai.com/";
    private static final String model = "gpt-4-0125-preview";

    public static Message chatGptRes(List<Message> messageList) {
        //国内需要代理 国外不需要
        //创建一个HTTP代理对象，指定代理服务器的地址和端口
        Proxy proxy = Proxys.http("192.168.1.108", 7890);

        //创建一个ChatGPT对象，用于调用OpenAI的GPT聊天接口
        ChatGPT chatGPT = ChatGPT.builder()
                .apiKey(apiKey) //设置API密钥
                .proxy(proxy) //设置代理
                .timeout(900) //设置超时时间（秒）
                .apiHost(apiHost) //设置API主机地址
                .build() //构建对象
                .init(); //初始化

        //创建一个ChatCompletion对象，用于设置聊天参数
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(model) //设置模型名称
                .messages(messageList) //设置消息列表
                .maxTokens(3000) //设置最大生成的单词数
                .temperature(0.9) //设置生成的随机性
                .build(); //构建对象
        //调用chatCompletion方法，传入ChatCompletion对象，返回一个ChatCompletionResponse对象
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        //从ChatCompletionResponse对象中获取第一个生成的消息对象
        Message res = response.getChoices().get(0).getMessage();
        //打印生成的消息内容
        return res;
    }

}
