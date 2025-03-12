# gRPC
- 高性能、开源且通用的远程过程调用remote procedure call 框架
- 基于http/2协议，允许应用程序之间进行高效通信，尤其适用于微服务架构中的服务间调用

# Pros
- 高效性：基于http/2 和Protobuf (protocol buffers), 相比传统的REST + JSON 更加高效
- 双向流：支持客户端和服务端的双向流通信。
- 简单易用：通过 Protobuf 定义服务接口，自动生成代码。
- 强类型支持：使用 Protobuf 定义消息格式，确保了通信数据的类型安全。

# gRPC 的工作流程
- 定义服务：通过 Protobuf 文件（.proto）定义服务和消息。
- 生成代码：通过 protoc 工具将 .proto 文件编译成各种编程语言的代码。
  ```protoc --java_out=./src/main/java --grpc-java_out=./src/main/java --proto_path=./ src/main/proto/hello.proto
  ```
  这会生成两个 Java 类：
    HelloRequest.java
    HelloResponse.java
    GreeterGrpc.java（包括客户端存根和服务端实现接口）

- 实现服务：在服务端实现定义好的服务逻辑。
- 客户端调用：客户端使用自动生成的存根（stub）进行服务调用。

