1. Set: Interface. (Java's Collections Framework. ) - being an interface, cannot be instantiated directly. need a class that implements it. - Common methods: add(E e), remove(Object o), contains(Object o), size(), iterator()

Impls (Concreate classes):

- HashSet

  1. use hashtables, add, remove, contains all O(1)
  2. no duplicates, UNORDERED, allows null values
  3. syntex: Set<String> = new HashSet<>()

- TreeSet

  1. use Red-Black Tree
  2. maintains SORTED order
  3. add/remove/contains - O(logn)

- LinkedHashSet
  1. hashTable w linked list -> maintain Insertion order
     1.1 add/remove/contains O(1), iteration O(n)
  2. Slighly slower than HashSet but maintains order

======== String.format() ========
String.format("formatString", arguments);

- "formatString" 包含占位符 %d, %s 等的模版字母串，占位符会被后续的参数替代
- arguments 时将要插入到模版中的数据，顺序和占位符的位置要一一对应

======== Java Artifact ==========

1. artifact: Maven 项目构建和依赖管理中一个可部署的输出单元，通常是一个 JAR，WAR, EAR, POM 文件等
2. 4 个核心属性唯一标识：

   - groupid
   - artifactid
   - version
   - packaging (jar, war, pom)

3. 整的 Maven 坐标（Maven Coordinates）：

<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.12.0</version>
</dependency>
这个依赖的 artifact 是 commons-lang3-3.12.0.jar，即 Maven 下载的文件名。

在 Maven 的语境里，artifact（构件） 指的是 Maven 项目构建和依赖管理中的一个可部署输出单元，通常是一个 JAR、WAR、EAR、POM 文件等。它可以是你的项目产出物，也可以是你的项目所依赖的第三方库。

1. Artifact 的构成
   每个 Maven artifact 都由以下四个核心属性唯一标识：

属性 说明 示例
groupId 组织或项目的唯一标识 org.apache.commons
artifactId 项目的唯一名称 commons-lang3
version 版本号 3.12.0
packaging 打包类型，默认为 JAR jar, war, pom
完整的 Maven 坐标（Maven Coordinates）：

<dependency>
<groupId>org.apache.commons</groupId>
<artifactId>commons-lang3</artifactId>
<version>3.12.0</version>
</dependency>
这个依赖的 artifact 是 commons-lang3-3.12.0.jar，即 Maven 下载的文件名。

2. Artifact 在 Maven 生命周期中的作用
   在 Maven 构建过程中，artifact 是构建的最终产物，可以是：

可执行的 JAR（my-app-1.0.jar）
Web 应用 WAR（my-app-1.0.war）
父 POM 文件（my-parent-1.0.pom）
源码 JAR（my-app-1.0-sources.jar）
Javadoc JAR（my-app-1.0-javadoc.jar）

Maven 使用 install 命令时，artifact 会被安装到本地仓库, 会在 ~/.m2/repository/ 目录下生成：

~/.m2/repository/com/example/my-app/1.0/
├── my-app-1.0.jar
├── my-app-1.0.pom

3. 依赖管理中的 Artifact

- 你可以在 pom.xml 中引用其他项目的 artifact 作为依赖。
- 你可以上传自己的 artifact 到私有仓库（如 Nexus、Artifactory）或公共仓库（如 Maven Central）。

4. 典型的 Artifact 类型
   jar: Java 库或可执行 JAR
   war: Web 应用程序
   ear: 企业级 Java 应用
   pom: 父 POM（管理依赖）
   tar/zip: 压缩包（一般用于资源文件）

总结
Artifact 是 Maven 项目产出的构件（JAR、WAR、POM 等）。
每个 artifact 由 groupId, artifactId, version, packaging 唯一标识。
Maven 依赖管理基于 artifact，它们可以存储在本地或远程仓库。
可以使用 mvn install 本地安装，或 mvn deploy 发布到远程仓库。

======= POM =======
POM 是 Project Object Model 的缩写。

POM (Project Object Model) is the fundamental unit of configuration in Apache Maven.
It is an XML file (pom.xml) that defines a Maven project’s dependencies, build settings, plugins, and other configurations.

======= Java Files =======

1. JAR（Java Archive）
   扩展名: .jar
   用途:

Java 库（Library），如 commons-lang3-3.12.0.jar
可执行的 Java 应用（需要 META-INF/MANIFEST.MF）
Maven 依赖管理的构件（artifact）

在 Java 开发中，主要的文件类型涉及不同的应用场景，比如可执行的 JAR 文件、Web 应用的 WAR 文件、企业级 EAR 文件等。以下是常见的 Java 文件类型及其用途：

1. JAR（Java Archive）
   扩展名: .jar
   用途:

Java 库（Library），如 commons-lang3-3.12.0.jar
可执行的 Java 应用（需要 META-INF/MANIFEST.MF）
Maven 依赖管理的构件（artifact）

2. WAR（Web Application Archive）
   扩展名: .war
   用途:

Web 应用程序的打包格式，包含 WEB-INF 目录、JSP、Servlet 等。
由 Tomcat、Jetty、WildFly 等 Web 服务器部署。
myapp.war
├── index.jsp
├── META-INF/
├── WEB-INF/
│ ├── web.xml
│ ├── classes/ (Java 类)
│ ├── lib/ (依赖 JAR)

部署 WAR 到 Tomcat:

- 复制 .war 文件到 webapps/
- Tomcat 会自动解压并部署

3. EAR（Enterprise Archive）
   扩展名: .ear
   用途:

用于 Java EE（Jakarta EE）企业级应用
包含多个 WAR、JAR，用于部署到 WebLogic、WildFly、GlassFish 等应用服务器
myapp.ear
├── META-INF/application.xml
├── my-web.war
├── my-ejb.jar
├── lib/

4. BAR（Business Archive）
   扩展名: .bar
   用途:

IBM BPM（业务流程管理）中的业务流程打包文件
主要用于 IBM Integration Bus（IIB，原 WebSphere Message Broker）

5. RAR（Resource Adapter Archive）
   扩展名: .rar
   用途:

JCA（Java Connector Architecture）连接器的部署包
主要用于企业应用连接外部资源（如数据库、消息队列）

6. APK（Android Package）
   扩展名: .apk
   用途:

Android 应用程序的安装包
由 .dex（Dalvik Executable）字节码组成

7.其他 Java 相关文件

.class - Java 字节码文件，由 javac 编译生成
.java - Java 源代码文件
.properties - 配置文件，如 log4j.properties
.xml - 配置文件（Spring、Maven、Web.xml 等）
.json - 配置文件或 API 交互
.keystore - Java 证书存储文件（用于 SSL/TLS）
