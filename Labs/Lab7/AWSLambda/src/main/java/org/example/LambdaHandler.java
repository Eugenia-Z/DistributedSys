package org.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaHandler implements RequestHandler<Request, Response> {

    // public zero-argument constructor
    public LambdaHandler() {
    }

    @Override
    public Response handleRequest(Request request, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Processing question from " + request.name());

        return new Response("Hello Kitty!");
    }
}

record Request(String name, String questions){}
record Response(String answer){}
// Simple records that represent in the input and output of our Lambda function, which are generic parameters in the requestHandler Interface.
// record 类型是Java 14引入的功能，用来定义不可变的数据对象。record类会自动生成构造函数、getter方法，equals，hashcode和toString方法。