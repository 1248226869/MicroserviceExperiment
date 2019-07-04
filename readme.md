# 微服务设计

##  康威定律（1967年)
* 定义
```
 设计系统的结构，其产生的设计结果等同于团队（项目组）内部的结构以及团队（项目组）之间的沟通结构  
 用通俗的说法就是： 系统设计的架构类似于团队的角色（可兼职，重点是开发的角色定义）结构
```
* 延伸
```
第一定律:团队之间的沟通方式会通过系统设计表达出来
沟通成本 = n(n-1)/2，沟通成本随着团队的人员增加呈指数级增长
```

```
第二定律:时间再多、能力再强，一件事情也不可能做的完美，但总有时间做完一件事情
对于一个软件产品，做到没有bug、异常，可以响应任何用户的任何输入，并且保持高稳定的性能，只是理想的产品。
另一种则是保持弹性的性能，可以满足用户的正常输入的响应，即使发生错误，只要及时恢复【自动或者人工】，也能正常工作，这是现实。
```

```
第三定律:线型系统和线型组织架构间有潜在的异质同态特性，做独立自治的系统减少沟通成本，需要什么样的系统就配置什么样的团队
```

```
第四定律:大的系统组织总是比小系统更倾向于分解
```
## 微服务概念
* 定义
```
微服务架构的设计是将一个系统应用程序根据业务拆分开发为一个小型的、无中心的、只做一件（一类）事情的、独立组件的小型服务的架构设计方法。 
每个小型服务独立中运行、自治（至少是同一台机器的不同进程、可以一同一个机架的不同机器、同一个机房的不同机架上的机器、甚至是不同机房的机器）。
各个小型服务之间轻量级机制（通常是HTTP资源API、RPC，gRPC）进行通信。 
这些服务围绕业务功能构建，可通过全自动部署机制独立部署，可以根据业务流量进行不同的集群部署。 
这些服务保持最低限度的集中式管理。
可以用不同的编程语言编写，并使用不同的数据存储技术。
实现敏捷开发和部署。
```
* 特性
```
100%的组件化（组件是一个可独立替换和升级的业务软件单元。可以快速的、简单的开发、维护与升级）
围绕业务能力进行组织，以产品为中心，零件分离化。
分布式服务组成的系统
按照业务而不是技术来划分组织
做有生命的产品而不是项目
强服务个体和弱通信
自动化运维
容错、限流
快速演化
```
* 好处
```
1、技术异构性
    根据不同服务的业务中：用户流量、业务复杂度、数据读写频率和存储量采取不同的技术来实现
2、超级弹性
    软件工程中的弹性主要是指某个功能的故障影响到整体系统的使用情况。显然，微服务的弹性是目前弹性最好的。
3、任意扩张性【永远变化】
4、简单部署性
5、组织相关性
6、简单的可组合性
7、增强的可替代性
8、降低单位系统复杂度

```
* 坏处
```
1、网络请求负担
2、去中心化之后各个自治团队能力不均衡
3、学习成本
    微服务的实施会采用新的架构，或者是直接新架构的替换，
    或者是架构的渐进演化，但是都需要参与人员熟悉新架构的使用，
    以及全新的开发模式的适应
```

## 微服务的实施：Spring Cloud作为核心框架（https://springcloud.cc/）
* CAP原则
```
CAP原则又称CAP定理，指的是在一个分布式系统中，一致性（Consistency）、可用性（Availability）、分区容忍性（Partition tolerance）。
CAP 原则指的是，这三个要素最多只能同时实现两点，不可能三者兼顾
```
* BASE一致性
```
Basically Available(基本可用)，Soft state（软状态）,和 Eventually consistent（最终一致性）三个短语的缩写
```
* 分区
```
    1 确定服务边界以及服务上下文【提供的API和调用的API】
    2 服务直接的交互规范与协议【rpc？http？grpc?】
    3 服务直接通信要少，基于版本的通信，低版本API要及时关闭【不废除】
    4 服务直接必须隐藏内部实现细节，所有API要只做该服务内原子型的事情，并且在传参和返回结果上保持版本号一致
        【为了高效的性能，对于处理耗时的服务应该提供同步和异步的两个API；对于频繁调用的服务，应该增加批处理的API，并且在返回结果中明确、友好的告知被调用服务
          同时，针对处理耗时或者频繁的服务，被调用方必须要调用开始做好校验，必要的时候禁止服务调用】
    5 异构技术的服务之间的通信要保证技术永远无关性：
        5.1 比如服务A用Java编写的用户照片美化服务,服务B用Python采用开源的TensorFlow基于CPU编写的用户照片处理服务，
            A采用http方式调用B服务，将用户照片传送到B服务，B服务验证、修复照片，然后将处理后照片的结果再返回给A服务。
            后期由于TensorFlow功能的局限型，采用C语言基于GPU从零开始研发自己的照片处理软件，使其性能更高、更稳定。
            从而代替python开发的B服务。
            此时，A服务继续采用http调用可以准确的返回所需的结果。
        5.2 对于同一种技术实现的服务，服务直接的通信要采用同一种标准。要么用REST的http，要么用RPC，最好不要同时使用两种方式.
    6 分区过程：按照业务功能分服务 - 确定上下文 - 整理共享库【数据、工具】 - 反馈  整个工程是渐进式的逐步进行 
    7 服务内部要严格遵循DDY(don't repeat youself),而在服务直接要尽量避免DDY
```
* 原则（简单、量少）：
```
    1 整体原则
    2 分区原则：耦合低、松 ；内聚高、紧；
    3 服务原则
    4 技术原则
    5 上下文原则：对上限流、对下容错；不应该复杂，当上下文过于复杂时要及时的继续分区
    
```
* 原则债务
``` 
一些紧急的需求或者异常修复，为了快速响应需求，尽早上线。对于一些原则可能没有精力顾及，这是允许的。
```
* 原则预支
``` 
一些新需求的开发中，可能某些设计遵循一个新的原则会带来更好的结果，但是该原则还暂未公布，可以先使用该原则
然后上线之后在将该原则按照标准流程公布。
```
* 实践：
```
    1 需求驱动发布
    2 测试驱动开发
    3 回归驱动发布
    4 发布驱动原则
```
* 基础设施建设：
```
    1 版本控制（git）
    2 自动化构建、持续交付与集成、流水线发布
    3 日志系统、数据仓、数据挖掘与分析
    4 监控、报警系统
    5 devops（容器技术、k8s）
    6 自动化测试
```

## 部署
* 持续集成（CI） ：不同分支编码到构建再到测试的反复持续过程，就叫作持续集成
* 持续交付(CD) ：在持续集成之后，获取外部对软件的反馈再通过持续集成进行优化的过程叫做持续交付，是持续集成的延伸
* 持续部署 ：将持续交付的结果在测试、预发、生产环境部署的过程叫做持续部署。
* devops : 研发【dev】与运维【ops】的综合体
```
dev:为软件新增功能和修复已有功能，使软件适应客户的市场需求和用户的最终体验，需要软件频繁的变更。
ops:保证软件的高稳定性，软件变更越频繁，越不稳定
devops的思想：通过技术升级和软件工程文化的进化，使dev与ops的矛盾最小化
devops是持续集成、交付和部署与容器化、容器管理、自动化运维的技术的整合，
    目的是使软件频繁变更的过程成本与代价最小，同时还能够保持较高的稳定性。
```

## 测试
* 单元测试：junit【测试驱动开发】
* 服务API测试:swagger
* 全链路测试:人工
* 全面测试:人工
## 监控
* 资源监控：cpu、io、内存、磁盘资源使用情况
* API监控：API热度、失败比例
* 服务监控：各个服务健康情况、异常报警、集群负载均衡度、调用响应时长
* 用户行为监控：全链路追踪
* 数据库监控、消息队列监控等持久化存储层
* 第三方依赖监控：使用了第三方服务，比如百度地图，对其接口的健康监控

# 微服务实施【Spring Cloud】

## 什么时候使用微服务架构
```
SOA是对单体服务的改进，将单体服务拆分为多个服务，通过总线形成一个整体系统。微服务是SOA的进化，在服务拆分方面更加彻底，没有中心服务。
由于微服务无中心，职责单一，开发、迭代周期短、频率高。所以对微服务架构的基础设施特别重要，如果只是单纯的采取微服务框架作为业务服务的开发，
基础设施仍旧是传统的做法。那么微服务的使用只会增加开发、运维的管理的成本，反而得不偿失。如果是业务开发由绿皮车改为高铁，而铁轨依旧是老铁轨，
那么这辆高铁肯定无法正常开动，更谈不上高速行驶。

```
### 首先，简单的、功能更新慢的系统、无需考虑微服务。微服务时候功能复杂、迭代频繁的系统，在基础设施完善的情况下可以考虑微服务。
* 基于以上分析，出现一下情况，可以考虑微服务的实施：
* 多人开发同一个系统，功能较多，迭代频繁；
* 系统内的子模块复杂度高，修改困难。单个模块的功能变的会影响到其他模块的正常使用
* 各个模块耦合度高，横向展困难，
* 熔断降级全靠入侵式代码实现 

## 一个微服务的团队组成

* 两个全家桶够所有成员享用
* 全栈，团队中的成员，包含用户开发（前端、客户端，非必须）、后端开发，基本架构设计。
* 团队所开发的业务高内聚。团队之间业务低耦合
* 扁平化管理、自治力强

## 如何拆分一个单体服务
```
首先，基础设施建设完成
其次，定义好微服务的工程规范、版本控制规范、发布流程等原则
然后，进行拆分
最后，做最坏的打算（拆分失败，快速回归到之前的服务），做最充分的准备（知识学习、规范熟悉、测试完整）
以上也是微服务实施的整体过程
```
### 拆分方向

* 业务逻辑（高内聚、低耦合）拆分
* 扩展性（迭代频率）拆分
* 性能（可用性、一致性、响应速率）拆分

### 拆分原则

#### 横向拆分

* 复制分离：老服务不动，将拆分出的业务重新为一个工程
* 粗粒度、渐进式
* 去除串行调用，避免长链路调用
* 去中心化

#### 纵向拆分
* 前端、BFF
* Controller、service\manager、dao(缓存 -> 数据库)
* 先拆分服务，再拆分数据库。
* 提取公共服务

## 前后端对产品的思维模式
 
* 前端的主要思维模式是页面数据，侧重页面生命周期内数据的表演，力求流畅、准确，目标是具体的用户
* 后端的主要思维模式是领域模式，侧重领域的生命周期的数据行为的演进和领域直接的交互，
力求稳定、安全，负责所有领域的整个生命周期的变化以及交互的完整性，目标是前端、其他领域模型（后端）

```
通过BFF可以很好地解耦两种思维模式，让彼此更专业高效。
前后端可以同一个人完成，甚至可以在一个工程内。但是思维模式一定要分离开，禁止使用同一种思维：前端页面直驱后端领域模型
```

## 前端

* 推荐vue.js
* BFF: 聚合后端、裁剪API、适配用户端

## 网关（devops）
* 前端、客户端与服务端网关（用户网关）,以安全认证、路由为组
* 同域服务API网关,以路由和复杂均衡为组
* 异域服务API网关,以安全认证、路由为组

## 注册中心
* Eureka
```
Register：服务注册
当Eureka Client(Feign、 Robbin具体的服务提供者，下同)向Eureka Server注册时，它提供自身的元数据（IP地址、端口，运行状况指示符URL，主页等）。
Eureka Server会将这些信息存放到具体的注册表中

Renew：服务续约
Eureka Client会默认每隔30秒发送一次心跳来续约。
正常情况下，如果Eureka Server在90秒没有收到Eureka客户的续约，它会将实例从其注册表中删除。

Fetch Registries：Eureka Client更新注册列表信息
Eureka Client从服务器获取注册表信息，并将其缓存在本地。客户端会使用该信息查找其他服务，从而进行远程调用。
该注册列表信息定期（每30秒钟）更新一次。
每次返回注册列表信息可能与Eureka Client的缓存信息不同， Eureka Client自动处理。
如果由于某种原因导致注册列表信息不能及时匹配，Eureka Client则会重新获取整个注册表信息。
Eureka Client和Eureka Server可以使用JSON / XML格式进行通讯。在默认的情况下Eureka客户端使用压缩JSON格式来获取注册列表的信息。

Cancel：服务下线
Eureka Client在程序关闭时向Eureka服务器发送取消请求。 发送请求后，该Eureka Client实例信息将从服务器的实例注册表中删除。

Eviction 服务剔除
在默认的情况下，当Eureka Client连续90秒没有向Eureka Server发送服务续约，Eureka服务器会将该服务实例从服务注册列表删除，即服务剔除。

```
## 服务调用
* Feign 
```
Feign 客户端通过通过jdk的代理处理注解生成http请求，然后由具体的执行http请求。
httpClient结合类Ribbon做负载均衡。
```

* ribbon （不常用）
```
RestTemplate:
 get
	getForEntry(String usl,Class responsetype,Object...urlVariables):urlVariables:为URL的占位符，responsetype：返回类型
	getForEntry(String usl,Class responsetype,Map urlVariables):urlVariables:为URL的占位符的map集合
	getForEntry(String usl,Class responsetype)无占位符	
    返回为ResponseEntry对象，然后通过getBody转换
	
	通过getForObject,可以去除业务层的转换，直接回去到所需的类型
	getForObject(String usl,Class responsetype,Object...urlVariables)
	getForObject(String usl,Class responsetype,Map urlVariables)
	getForObject(String usl,Class responsetype）
 Post
    同get类似。第二个参数，无请求体，第三个参数为返回类型
 Put请求：void类型，无返回
	put(string url,Object request,Object...urlVariables)
	put(string url,Object request)
	put(string url,Object request,map urlVariables)
 Delete:void类型，无返回	
	delete(string url Object...urlVariables)
	delete(string url)
	delete(string url,map urlVariables)
 负载均衡器:@LoadBalanced 注解
 区域亲和策略：将不同机房不同地域的配置为对应的区域值，在客户端通过区域值，连接到最近的区域服务。
```
## 服务治理
* 限流：保护自己（服务提供方）不被其他服务（服务消费方）消费而宕机 （漏桶、令牌）
* 容错：保护自己（服务消费方）不因调用其他服务（服务提供方）异常而宕机。（Hystrix）

## API 统一参数校验、权限认证、结果封装、异常处理
```
Spring AOP（CGLIB实现）
```
## JWT使用

## 分布式配置中心

### Apollo

## 分布式调度中心
### xxl-job

## 消息总线

# 分布式算法

## 分布式事务

## 分布式锁

## 数据一致性

# 日志系统（全链路追踪与ELK）

# 自动化测试

# 监控

# 持续交付与devops

# 工业级别的Spring cloud构建工具：JHipster

# Service Mesh：无侵入的下一代微服务技术