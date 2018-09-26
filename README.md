## SpringBoot集成MyBatis

### ORM 框架是什么

Object Relational Mapping，简称 ORM，对象关系映射模式是一种为了解决面向对象与关系数据库存在的互不匹配的现象技术。简单的说，ORM 是通过使用描述对象和数据库之间映射的元数据，将程序中的对象自动持久化到关系数据库中。

### 为什么需要 ORM

当开发一个应用程序的时候（不使用 O/R Mapping），你可能会写不少数据访问层的代码，用来从数据库保存、删除、读取对象信息等。在 数据访问层，DAL （Data Access Layer），中写了很多的方法来读取对象数据、改变状态对象等任务，而这些代码写起来总是重复的。 针对这些问题 ORM 提供了解决方案，简化了将程序中的对象持久化到关系数据库中的操作，让程序员完全地面向对象编程。

### MyBatis 介绍

#### 历史

MyBatis 就是一款标准的 ORM 框架，被广泛的应用于各企业开发中。MyBatis 本是 Apache 的一个开源项目 IBatis，2010 年这个项目由 Apache Software Foundation 迁移到了 Google Code，并且改名为 MyBatis，2013 年 11 月又迁移到 Github 上。

#### 作用

MyBatis 支持普通 SQL 查询，存储过程和高级映射的优秀持久层框架。MyBatis 消除了几乎所有的 JDBC 代码和参数的手工设置以及对结果集的检索封装。MaBatis 可以使用简单的 XML 或注解用于配置和原始映射。将接口和 Java 的 POJO（Plain Old Java Objects，普通的 Java 对象）映射成数据库中的记录。

#### 特点

##### 1.优点

（1）SQL 被统一提取出来，便于统一管理和优化

（2）SQL 和代码解耦，将业务逻辑和数据访问逻辑分离，使系统的设计更清晰，更易维护，更易单元测试

（3）提供映射标签，支持对象与数据库的 ORM 字段关系映射

（4）提供对象关系映射标签，支持对象关系组件维护

（5）灵活书写动态 SQL，支持各种条件来动态生成不同的 SQL

##### 2.缺点

（1）编写 SQL 语句时工作量很大，尤其是字段多、关联表多时，更是如此

（2）SQL 语句依赖于数据库，导致数据库移植性差

#### MyBatis 几个重要的概念：

**Mapper配置：**基于 XML 的 Mapper 配置文件和基于 Java 注解的 MyBatis 注解来实现。

**Mapper接口：**即DAO（Data Access Object ）数据访问对象是一个面向对象的数据库接口，这类 Mapper 接口是指自行定义的一个数据操作接口，MyBatis 会自动为 Mapper 接口创建动态代理对象。Mapper 接口的方法通常与 Mapper 配置文件中的 select、insert、update、delete 等 XML 节点存在一一对应关系。

**Executor：** MyBatis 中所有的 Mapper 语句的执行都是通过 Executor 进行的，Executor 是 MyBatis 的一个核心接口。

**SqlSession：** SqlSession 是 MyBatis 的关键对象，是执行持久化操作的独享，类似于 JDBC 中的 Connection，SqlSession 对象完全包含以数据库为背景的所有执行 SQL 操作的方法，它的底层封装了 JDBC 连接，可以用 SqlSession 实例来直接执行被映射的 SQL 语句。

**SqlSessionFactory：** SqlSessionFactory 是 MyBatis 的关键对象，它是单个数据库映射关系经过编译后的内存镜像。SqlSessionFactory 对象的实例可以通过 SqlSessionFactoryBuilder 对象类获得，而 SqlSessionFactoryBuilder 则可以从 XML 配置文件或一个预先定制的 Configuration 的实例构建出。

MyBatis 的工作流程如下：

https://oscimg.oschina.net/oscnet/b408d7563ecab6d608aeef3ab6d263b3f35.jpg

1.首先加载 Mapper 配置的 SQL 映射文件，或者是注解的相关 SQL 内容。
2.创建会话工厂，MyBatis 通过读取配置文件的信息来构造出会话工厂（SqlSessionFactory）。
3.创建会话，根据会话工厂，MyBatis 就可以通过它来创建会话对象（SqlSession）。会话对象是一个接口，该接口中包含了对数据库操作的增删改查方法。
4.创建执行器，因为会话对象本身不能直接操作数据库，所以它使用了一个叫做数据库执行器（Executor）的接口来帮它执行操作。
5.封装 SQL 对象，在这一步，执行器将待处理的 SQL 信息封装到一个对象中（MappedStatement），该对象包括 SQL 语句、输入参数映射信息（Java 简单类型、HashMap 或 POJO）和输出结果映射信息（Java 简单类型、HashMap 或 POJO）。
6.操作数据库。拥有了执行器和 SQL 信息封装对象就使用它们访问数据库了，最后再返回操作结果，结束流程。

#### MyBatis-Spring-Boot-Starter

MyBatis-Spring-Boot-Starter 是 MyBatis 帮助我们快速集成 Spring Boot 提供的一个组件包。

使用这个组件可以做到以下几点：

构建独立的应用
几乎可以零配置
需要很少的 XML 配置

mybatis-spring-boot-starter主要提供了两种解决方案，一种是简化后的 XML 配置版，另外一种是使用注解解决一切问题。

##### XML 版本

XML 版本保持映射文件的老传统，优化主要体现在不需要实现 Dao 的实现层，系统会自动根据方法名在映射文件中找到对应的 SQL。

###### 一、相关配置

1.依赖包配置

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.3.2</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
    <version></version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.6</version>
</dependency>
```

2.application 配置

application.property配置：

```properties
#配置 mybatis-config.xml 路径，mybatis-config.xml 中配置 MyBatis 基础属性。
mybatis.config-locations=classpath:mybatis/mybatis-config.xml
#配置 Mapper 对应的 XML 文件路径
mybatis.mapper-locations=classpath:mybatis/mapper/*.xml
#配置项目中实体类包路径
mybatis.type-aliases-package=com.grit.learning.entity
#数据源配置信息
spring.datasource.driverClassName = com.mysql.jdbc.Driver
spring.datasource.url = jdbc:mysql://localhost:3306/db_test?useUnicode=true&characterEncoding=utf-8
spring.datasource.username = root
spring.datasource.password = root
```

Spring Boot 启动时数据源会自动注入到 SqlSessionFactory 中，使用 SqlSessionFactory 构建 SqlSessionFactory，再自动注入到 Mapper 中，最后我们直接使用 Mapper 即可。

###### 二、启动类上添加注解

在启动类中添加对 Mapper 包扫描`@MapperScan`，Spring Boot 启动的时候会自动加载包路径下的 Mapper。

```java
@SpringBootApplication
@MapperScan("com.grit.learning.mapper")
public class MybatisInSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisInSpringbootApplication.class, args);
	}
}
```

或者直接在 Mapper 类上面添加注解`@Mapper`，建议使用上面那种，不然每个 Mapper 加个注解会很麻烦。

###### 三、开发 SQL

（1）MyBatis 公共属性

`mybatis-config.xml`，主要配置常用的 typeAliases，设置类型别名为 Java 类型设置一个短的名字。它只和 XML 配置有关，存在的意义仅在于用来减少类完全限定名的冗余。

```xml
<configuration>
    <typeAliases>
        <typeAlias alias="Integer" type="java.lang.Integer" />
        <typeAlias alias="Long" type="java.lang.Long" />
        <typeAlias alias="HashMap" type="java.util.HashMap" />
        <typeAlias alias="LinkedHashMap" type="java.util.LinkedHashMap" />
        <typeAlias alias="ArrayList" type="java.util.ArrayList" />
        <typeAlias alias="LinkedList" type="java.util.LinkedList" />
    </typeAliases>
</configuration>
```

这样在使用 Mapper.xml 的时候，需要引入可以直接这样写：

```xml
resultType="Integer" 
//或者
parameterType="Long"
```

（2）添加 User 的映射文件

指明对应文件的 Mapper 类地址：

```xml
<mapper namespace="com.grit.learning.mapper.UserMapper" >
```

配置表结构和类的对应关系：

```xml
 <resultMap id="BaseResultMap" type="com.grit.learning.entity.UserEntity" >
     <id column="id" property="id" jdbcType="BIGINT" />
     <result column="userName" property="userName" jdbcType="VARCHAR" />
     <result column="passWord" property="passWord" jdbcType="VARCHAR" />
     <result column="user_sex" property="userSex"                javaType="com.grit.learning.enums.UserSexEnum"/>
     <result column="nick_name" property="nickName" jdbcType="VARCHAR" />
</resultMap>
```

这里为了更好的贴近工作情况，故意将类的两个字段和数据库字段设置为不一致，并且有一个使用了枚举。使用枚举有一个非常大的优点，插入此属性的数据会自动进行校验，如果不是枚举的内容会报错

写具体的 SQL 语句，比如这样:

```xml
<select id="getAll" resultMap="BaseResultMap">
   SELECT 
   *
   FROM users
</select>
```

MyBatisXML 有一个特点是可以复用 XML，比如公用的一些 XML 片段可以提取出来，在其他 SQL 中去引用，如：

```xml
<sql id="Base_Column_List" >
    id, userName, passWord, user_sex, nick_name
</sql>

<select id="getAll" resultMap="BaseResultMap"  >
   SELECT 
   <include refid="Base_Column_List" />
   FROM users
</select>  
```

这个例子就是，上面定义了需要查询的表字段，下面 SQL 使用 include 引入，避免了写太多重复的配置内容。

下面就是常用的增删改查的例子：

```xml
    <select id="getOne" parameterType="Long" resultMap="BaseResultMap" >
        SELECT 
       <include refid="Base_Column_List" />
	   FROM users
	   WHERE id = #{id}
    </select>

    <insert id="insert" parameterType="com.grit.learning.entity.UserEntity" >
       INSERT INTO 
       		users
       		(userName,passWord,user_sex) 
       	VALUES
       		(#{userName}, #{passWord}, #{userSex})
    </insert>
    
    <update id="update" parameterType="com.grit.learning.entity.UserEntity" >
       UPDATE 
       		users 
       SET 
       	<if test="userName != null">userName = #{userName},</if>
       	<if test="passWord != null">passWord = #{passWord},</if>
       	nick_name = #{nickName}
       WHERE 
       		id = #{id}
    </update>
    
    <delete id="delete" parameterType="Long" >
       DELETE FROM
       		 users 
       WHERE 
       		 id =#{id}
    </delete>
```

上面 update 的 SQL 使用了`if`标签，可以根据不同的条件动态的生产 SQL，这就是 MyBatis 最大的特点。

###### 四、编写 Dao 层的代码

```java
public interface UserMapper {

    List<UserEntity> getAll();

    UserEntity getOne(Long id);

    void insert(UserEntity user);

    void update(UserEntity user);

    void delete(Long id);
}
```

```注意：这里的方法名需要和 XML 配置中的 id 属性一致，不然会找不到方法去对应执行的 SQL。```

###### 五、测试使用

按照 Spring 一贯使用形式，直接将对应的 Mapper 注入即可。

```java
@Resource
private UserMapper userMapper;
```

接下来直接使用 userMapper 进行数据库操作即可。

```java
@Test
public void testUser()  {
    //增加
    userMapper.insert(new UserEntity("aa", "a123456", UserSexEnum.MAN));
    //删除
    int count=userMapper.delete(29l);
    //修改
    userMapper.update(user);
    //查询
    List<UserEntity> users = userMapper.getAll();
    UserEntity user = userMapper.getOne(1l);
}
```

在示例代码中，写了两份的使用，一个是 Test，一个在 Controller 层，方便查看。

###### 六、分页查询

多条件分页查询是我们在实际工作中最常使用的一个功能，MyBatis 也特别擅长处理这类的问题。在实际工作中，会对分页进行简单的封装，方便前端使用。另外在 Web 开发规范使用中，一般 Web 层的参数会以 param 为后缀的对象进行传参，以 entity 为后缀的对象负责和数据库打交道，以 result 结尾的实体类一般用于封装返回的数据。

下面给大家以 User 多条件分页查询为例进行讲解：

先定义一个分页的基础类：

```java
public class PageParam {
    private int beginLine;                   //起始行
    private Integer pageSize = 3;
    private Integer currentPage=0;        // 当前页
    //getter setter 省略
    public int getBeginLine() {
        return pageSize*currentPage;        //自动计算起始行
    }
}
```

默认每页 3 条记录，可以根据前端传参进行修改。

user 的查询条件参数类继承分页基础类：

```java
public class UserParam extends PageParam{
    private String userName;
    private String userSex;
    //getter setter省略
}
```

接下来配置具体的 SQL，先将查询条件提取出来。

```xml
<sql id="Base_Where_List">
    <if test="userName != null  and userName != ''">
        and userName = #{userName}
    </if>
    <if test="userSex != null and userSex != ''">
        and user_sex = #{userSex}
    </if>
</sql>
```

从对象 UserParam 中获取分页信息和查询条件，最后进行组合。

```xml
 <select id="getList" resultMap="BaseResultMap" 			       parameterType="com.grit.learning.param.UserParam">
        select
        <include refid="Base_Column_List" />
        from users
        where 1=1
        <include refid="Base_Where_List" />
        order by id desc
        limit #{beginLine} , #{pageSize}
 </select>
```

前端需要展示总共的页码，因此需要统计出查询结果的总数。

```xml
<select id="getCount" resultType="Integer" parameterType="com.grit.learning.param.UserParam">
        select
        count(1)
        from users
        where 1=1
        <include refid="Base_Where_List" />
 </select>
```

Mapper 中定义的两个方法和配置文件相互对应。

```java
public interface UserMapper {
    List<UserEntity> getList(UserParam userParam);
    int getCount(UserParam userParam);
}
```

具体使用如下：

```java
@Test
public void testPage() {
    UserParam userParam=new UserParam();
    userParam.setUserSex("WOMAN");
    userParam.setCurrentPage(1);
    List<UserEntity> users=userMapper.getList(userParam);
    long count=userMapper.getCount(userParam);
    Page page = new Page(userParam,count,users);
    System.out.println(page);
}
```

实际使用中，只需要传入 CurrentPage 参数即可，默认 0 就是第一页，传 1 就是第二页的内容，最后将结果封装为 Page 返回给前端。

```java
public class Page<E> implements Serializable {
    private int currentPage = 0; //当前页数
    private long totalPage;       //总页数
    private long totalNumber;    //总记录数
    private List<E> list;        //数据集
}
```

Page 将分页信息和数据信息进行封装，方便前端显示第几页、总条数和数据。这样常用的场景就演示完了。

##### 无配置文件注解版

该部分代码，如有感兴趣的同学请下载源码来研究对比
https://github.com/Grit-tan/mybatisForAnnotation-in-springboot

### 如何选择

两种模式各有特点，注解版适合简单快速的模式，在微服务架构中，一般微服务都有自己对应的数据库，多表连接查询的需求会大大的降低，会越来越适合注解版。XML 模式比适合大型项目，可以灵活的动态生成 SQL，方便调整 SQL，也有痛痛快快、洋洋洒洒地写 SQL 的感觉。在具体开发过程中，根据公司业务和团队技术基础进行选型即可。

