# Enterprise Proxy (Restful API)
By building backend server using EP module, you can use powerful and useful features of Kalytn platform as HTTP APIs. In addition, the deliverables are complied as WAR files so you can deploy, operate, and maintain them like WAS.

Caver-Java, WEB3J, and EP modules, which are embedded by default, allow you to develop and build a system with various functions and scalability.

The supported modules will be updated gradually via user’s live feedback. Please send us many bug reports, feature requests and other opinions.



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
  - Please make sure to consider the code dependencies when modifying properties.
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

