package org.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.logging.LogLevel;

public class LambdaHandler implements RequestHandler<Request, Response> {

    // 无参构造函数
    public LambdaHandler() {
        // 你可以在这里进行任何初始化工作（如果需要）
    }

    @Override
    public Response handleRequest(Request request, Context context) {
        LambdaLogger logger = context.getLogger();
        // 打印日志信息
        logger.log("Processing question from " + request.name());

        // 返回固定响应
        return new Response("Hello Kitty!");
    }
}

record Request(String name, String questions){}
record Response(String answer){}
// Simple records that represent in the input and output of our Lambda function, which are generic parameters in the requestHandler Interface.