# Enterprise Proxy (Restful API)
EP 모듈을 사용하여 API 백엔드 서버를 구축함으로써 Klaytn 플랫폼의 강력하고 유용한 기능등을 HTTP API로 사용 가능하며 최종 결과물이 WAR로 컴파일 되어 WAS와 같이 배포,운영,관리가 가능합니다.

기본적으로 적용되어있는 Caver-Java,WEB3J,EP 모듈등을 이용하여 다양한 기능과 확장 가능한 구조로 좀더 쉽게 개발,구축할수 있습니다.

지원되는 프로토콜은 계속해서 개발할 예정입니다.



### CONFIGURATION

- Maven 3.5+
- Tomcat 8.5
- Jdk 1.8
- SpringBoot 2.1.3 Release
- Swagger 2.9.2
  ```java
  @Configuration
  @EnableSwagger2
  public class SwaggerConfig {
      @Value("${swagger.api.title:'EP RESTFUL API'}")
      private String title;
  
      @Value("${swagger.api.controller.basepackage:'com.klaytn.enterpriseproxy'}")
      private String basePackage;
  
      @Bean
      public Docket swaggerAPI() {
          return new Docket(DocumentationType.SWAGGER_2)
                  .select()
                  .apis(RequestHandlerSelectors.basePackage(basePackage))
                  .paths(PathSelectors.any())
                  .build()
                  .apiInfo(metaData());
      }
  
      private ApiInfo metaData() {
          return new ApiInfoBuilder().title(title).build();
      }
  }
  ```
- net.sf.ehcache 2.9.0
  ```java
  @Configuration
  @EnableCaching
  public class EhCacheConfig {
      @Bean
      public CacheManager cacheManager() {
          return new EhCacheCacheManager(Objects.requireNonNull(ehCacheManagerFactory().getObject()));
      }
  
      @Bean
      public EhCacheManagerFactoryBean ehCacheManagerFactory(){
          EhCacheManagerFactoryBean ehCacheBean = new EhCacheManagerFactoryBean();
          ehCacheBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
          ehCacheBean.setShared(true);
          return ehCacheBean;
      }
  }
  ```
  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:noNameSpaceSchemaLocation="ehcache.xsd"
           updateCheck="false"
           monitoring="autodetect"
           dynamicConfig="true">
  
      <diskStore path="java.io.tmpdir" />
      <cache name="EPLOCALCACHE"
             maxElementsInMemory="50000"
             eternal="false"
             timeToIdleSeconds="180"
             timeToLiveSeconds="180"
             overflowToDisk="false"
             diskPersistent="false"
             diskSpoolBufferSizeMB="100"
             diskExpiryThreadIntervalSeconds="180"
             memoryStoreEvictionPolicy="LRU">
      </cache>
  </ehcache>
  ```
- InterceptorConfig
  ```java
  @Configuration
  public class InterceptorConfig implements WebMvcConfigurer {
      @Resource
      private ApiRequestInterceptor interceptor;
  
      /**
       * Add Interceptors
       *  - custom area
       *
       * @param registry
       */
      @Override
      public void addInterceptors(InterceptorRegistry registry) {
          registry.addInterceptor(interceptor)
                  .addPathPatterns("/management/**")
                  .addPathPatterns("/platform/**")
                  .addPathPatterns("/transaction/**")
                  .addPathPatterns("/feeDelegated/**");
      }
  }
  ```
- application.properties
  - 정보 수정시 코드 의존성을 고려해야 합니다.
  ```json
  ## editable properties
  # jsonrpc host info
  klaytn.rpc.server.host=https://api.baobab.klaytn.net
  klaytn.rpc.server.port=8651
  
  # feepayer
  feepayer.address=ENC(<feepayer address encrypt string>)
  feepayer.password=ENC(<feepayer password encrypt string>)
  feepayer.keyStoreFilePath=ENC(<feepayer wallet key store file path>)
  
  # logging
  logging.level.root=info
  logging.pattern.console=[%d{yyyy-MM-dd HH:mm:ss}] [%-5p] %C{1}.%M[%L] %m%n
  
  ## do not edit properties
  # ehcache
  spring.cache.ehcache.config=classpath:ehcache.xml
  
  # swagger
  swagger.api.title=EP-API
  swagger.api.controller.basepackage=com.klaytn.enterpriseproxy
  
  # jasypt encryptor
  jasypt.encryptor.bean=propertiesStringEncryptor
  ```

---

### GETTING START
- maven compile
  - EP-API는 배포를 위해 WAR파일로 컴파일됩니다.
  ```shell
  mvn clean package
  cd epi-api/target
  ep-api-0.0.1-SNAPSHOT.war 파일생성 확인
  ```
- application.properties
  ```shell
  klaytn.rpc.server.host=<EN NODE RPC HOST>
  klaytn.rpc.server.port=<EN NODE RPC PORT>
  * API에서 tagetHost 정보를 넣지 않으면 등록한 호스트로 접속하게 됩니다.
  ```
  ```shell
  logging.level.root=log level (debug,info,error...)
  * 현재 소스에는 info 레벨이 적용되어있습니다.
  * 레벨은 개발 및 배포 환경에 따라 수정하시면 됩니다.
  ```
  ```shell
  feepayer.address=ENC(...)
  feepayer.password=ENC(...)
  feepayer.keyStoreFilePath=ENC(...)
  * 대납 기능을 사용하기 위해 대납용 계정을 꼭 먼저 생성해주어야 합니다.
  * 대납 계정 정보는 최소한의 보안을 위해 Jasypt 암호화된 정보를 사용하도록 되어있습니다.
  ```
  ```shell
    ## Jasypt를 사용한 암호화 방법 ##
    1. download jasypt-1.9.3-dist.zip (https://github.com/jasypt/jasypt/releases/tag/jasypt-1.9.3)
    2. unzip jasypt-1.9.3-dist.zip
    3. cd bin
    4. chmod 777 ./encrypt.sh
    5. ./encrypt.sh input="<target object to encrypt>" password="<decryption key: FYI, it is set as '@kalynep@' in EP source.>" algorithm="PBEWithMD5AndDES"
    6. RESULT
    ----ARGUMENTS-------------------
    algorithm: PBEWITHMD5ANDDES
    input: dbpassword
    password: encryptkey
    ----OUTPUT----------------------
    +VqidblzVqZJAGypmX65789787QrV0
  ```
- tomcat
  - tomcat 설정은 각각 개발 및 배포 환경에 따라 수정하면 됩니다.
  ```shell
  wget http://mirror.apache-kr.org/tomcat/tomcat-8/v8.5.41/bin/apache-tomcat-8.5.41.tar.gz
  tar xvfz apache-tomcat-8.5.41.tar.gz
  mv ep-api-0.0.1-SNAPSHOT.war apache-tomcat-8.5.41/webapps/ROOT.war
  cd apache-tomcat-8.5.41/bin
  ./startup.sh
  ```
- EP API SERVER
  ```http
  http(s)://<EP_HOST>/swagger-ui.html
  ```

---

### SUPPORT API LIST
- **Klaytn RPC**
  - Management
    - admin
    - debug
    - personal
    - txpool
  - Platform
    - Klay
      - Account
      - Block
      - Transaction
      - Configuration
      - Filter
    - Net
- **Fee Delegated Transaction**
  - Account
    - TxTypeFeeDelegatedAccountUpdate
    - TxTypeFeeDelegatedAccountUpdateWithRatio
  - Cancel
    - TxTypeFeeDelegatedCancel
    - TxTypeFeeDelegatedCancelWithRatio
  - SmartContract
    - TxTypeFeeDelegatedSmartContractDeploy
    - TxTypeFeeDelegatedSmartContractDeployWithRatio
    - TxTypeFeeDelegatedSmartContractExecution
    - TxTypeFeeDelegatedSmartContractExecutionWithRatio
  - ValueTransfer
    - TxTypeFeeDelegatedValueTransfer
    - TxTypeFeeDelegatedValueTransferWithRatio
    - TxTypeFeeDelegatedValueTransferMemo
    - TxTypeFeeDelegatedValueTransferMemoWithRatio
- **Raw Transaction Send**
  - Signed RawTransaction Send

---

### API CALL
- targetHost 설정
  - 직접 원하는 호스트를 지정하는 경우 targetHost 파라미터를 사용하시면 됩니다.
    ```http
    http://<EP_HOST>/management/admin/datadir?targetHost=http://localhost:8551
    ```
  - 파라미터를 사용하지 않는 경우 properties에 설정된 호스트 정보로 접속하게 됩니다.
- Management / Admin / datadir 예시
  ```http
  http://<EP_HOST>/management/admin/datadir?targetHost=<NODE HOST>
  ```
  ```json
  {
    "code": 0,
    "target": "api",
    "result": "SUCCESS",
    "data": "/data/kend/data"
  }
  ```
- 접속한 노드에서 지원하지 않는 RPC인 경우 **DISABLE API INTERFACE** 결과 리턴
  ```json
  {
    "code": 799,
    "data": {
      "klay": "1.0",
      "net": "1.0",
      "rpc": "1.0"
    },
    "result": "DISABLE API INTERFACE",
    "target": "api"
  }
  ```
- FeeDelegated / TxTypeFeeDelegatedValueTransfer 예시
  ```http
  http://<EP_HOST>/feeDelegated/valueTransfer/?serviceChainId=1&serviceChainHostId=1&chainId=<chainId>&rawTransaction=<TxTypeFeeDelegatedValueTransfer Raw Transaction>
  ```
  ```json
  {
    "code": 0,
    "data": "<fee delgated transaction result receipt>",
    "result": "SUCCESS",
    "target": "api"
  }
  ```
  ```json
  * 대납 계정 정보가 잘못된 경우 아래와 같이 리턴하도록 되어있습니다.
  {
    "code": 899,
    "data": "Invalid password provided",
    "result": "TRANSACTION ERROR",
    "target": "fee delegated"
  }
  ```

---

### API RESPONSE
- RESPONSE MODEL
  ```json
  {
    "code": 0,
    "data": {},
    "result": {},
    "target": "string"
  }
  * target 종류는 "api,rpc,transaction,router,fee delegated"가 있습니다.
  ```
- RESPONSE CODE
  ```java
  UNKNOWN(999,"UNKNOWN")
  PARAMETER_INVALID(998,"PARAMETER INVALID")
  EMPTY_DATA(997,"EMPTY DATA")
  RPC_CALL_ERROR(996,"RPC CALL ERROR")
  DB_ERROR(995,"DB EXECUTE ERROR")
  ...
  TX_ERROR(899,"TRANSACTION ERROR")
  ...
  DISABLE_APIS(799,"DISABLE API INTERFACE")
  ...
  SUCCESS(0,"SUCCESS")
  ```