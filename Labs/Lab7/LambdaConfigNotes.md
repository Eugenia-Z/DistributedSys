# Using Lambda in Java Projects

1. reference https://www.baeldung.com/java-aws-lambda

# Steps:

1. add Lambda core dependency in pom.xml
2. add maven-shade-plugin in pom.xml -> allows to package application and its dependencies into a single, self-contained JAR file. ("fat" JAR file)
3. Create fat JAR in the target directory by executing "mvn clean package"

4. Create a handler -> entry point for Lambda function is a handler method, which processes the incoming request and returns a response.

Three Different ways to define a handler method:
a. Implementing the RequestHandler Interface and overriding its handleRequest() method
b. Implementing the requestStreamHandler Interface
c. Custom Handler Method

5. Testing Lambda Function locally using LocalStack

LocalStack: a tool to run an emulated AWS environment on our machine.

a. Start a LocalStack container using Docker

```sh
docker run \
    --rm -it \
    -p 127.0.0.1:4566:4566 \
    -v /var/run/docker.sock:/var/run/docker.sock \
    -v ./target:/opt/code/localstack/target \
    localstack/localstack
```

- map the required port
- mount our project's target directory (which contains out fat JAR) into the container.

所以这一行 command line 要在 project folder 里跑，./target 才能正确 mount

b. get into container's shell and create Lambda function

```sh
docker ps -a
docker exec -it da9ac5255f41 sh  # replace container id
```

c. after establishing connection with the container shell, create and invoke function using this shell

reference: https://www.baeldung.com/ops/docker-container-shell

```sh
awslocal lambda create-function \
  --function-name Eugenia-lambda-function \
  --runtime java17 \
  --handler org.example.LambdaHandler \
  --role arn:aws:iam::000000000000:role/lambda-role \
  --zip-file fileb:///opt/code/localstack/target/AWSLambda-1.0-SNAPSHOT.jar
```

```sh
awslocal lambda invoke \
    --function-name Eugenia-lambda-function \
    --payload '{"name": "big kitty", "questions": "When are you gonna move to LA?"}' \
    output.txt
```

- pass function name and JSON request payload. The response from Lambda function is saved in the specified output.txt file. In case of errors, the error details will also be logged in output.txt file. (use cat to read)

6. Deploy Lambda Function

AWS CloudFormation -> Infrastructure as a Code

a. Create AWS CloudFormation Template
