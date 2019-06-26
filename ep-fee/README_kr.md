# Enterprise Proxy (Fee Delegated)
서비스에서 발생하는 트랙젝션중 가스비 대납이 필요한경우 사용되는 모듈입니다.

EP-FEE 모듈을 정상적으로 사용하시려면 대납할수 있는 계정을 먼저 생성하신후
대납을 위한 충분한 balance가 있어야 한다는점 꼭 유념해주시길 바랍니다.



### CONFIGURATION
- Jasypt 2.1.0
  - ENCRYPT_KEY 정보는 보안상 문제가 있을수 있으니 꼭 다른 정보로 수정하시기를 권장합니다.
  - SimpleStringPBEConfig 설정은 개발 및 배포 환경에 따라 수정하시면 됩니다.
    ```java
    @Configuration
    @EnableEncryptableProperties
    public class EncryptorConfig {
        /**
        * Encrypt key
        * - custom area
        */
        private static final String ENCRYPT_KEY = "@klaytnep@";
    
        /**
        * string encryptor
        *
        * @return
        */
        @Bean("propertiesStringEncryptor")
        public StringEncryptor stringEncryptor() {
            PooledPBEStringEncryptor encrypt = new PooledPBEStringEncryptor();
            SimpleStringPBEConfig config = new SimpleStringPBEConfig();
            config.setPassword(ENCRYPT_KEY);
            config.setAlgorithm("PBEWithMD5AndDES");
            config.setKeyObtentionIterations("1000");
            config.setPoolSize("1");
            config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
            config.setStringOutputType("base64");
            encrypt.setConfig(config);
            return encrypt;
        }
    }
    ```
- properties 운영
  - 대납 기능을 사용하려면 properties 파일에 address,password,keyStoreFilePath 설정 해주셔야만 합니다.
  - 암호화된 정보를 받기 때문에 Jasypt를 이용,필요한 정보를 먼저 암호화해주셔야 합니다.
  - 암호화 키 값은 EncryptorConfig.java ENCRYPT_KEY에 반영해주셔야만 서비스 운영시 정상적으로 복호화가 가능합니다.
    ```shell
    feepayer.address=ENC(...)
    feepayer.password=ENC(...)
    feepayer.keyStoreFilePath=ENC(...)
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
- FeePayerProperties
  - 대납 계정 address,password,keyStoreFilePath 정보를 불러옵니다.
    ```java
    @Component
    @EnableConfigurationProperties
    @ConfigurationProperties(value="feepayer",ignoreUnknownFields=true)
    public class FeePayerProperties implements Serializable {
        private static final long serialVersionUID = -3746968118012353633L;
    
        /**
        * FeePayer Address
        * @type string
        */
        private String address;
    
        /**
        * FeePayer Password
        * @type string
        */
        private String password;
    
        /**
        * FeePayer KeyStoreFilePath
        * @type string
        */
        private String keyStoreFilePath;
    
        ...
    }
    ```
- FeePayerAspect ([SpringBoot AOP](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop))
  - 대납 요청 처리 전 대납 계정 검증을 통과해야만 정상 처리됩니다.
    ```java
    @Aspect
    @Component
    @Order(Ordered.LOWEST_PRECEDENCE)
    public class FeePayerAspect {
        @Autowired
        private FeeDelegatedHandler handler;
    
        /**
        * Validation of Fee payers
        *
        * @param joinPoint
        * @return
        * @throws Throwable
        */
        @Around("execution(* com.klaytn.enterpriseproxy.fee.delegated..*.*(..))")
        public Object validate(ProceedingJoinPoint joinPoint) throws Throwable {
            TransactionResponse result = handler.feePayerWalletValidation();
            if (TransactionResponseCode.SUCCESS.getCode() == result.getCode()) {
                return joinPoint.proceed();
            }
    
            return result;
        }
    }    
    ```
    ```
    * 잘못된 계정 정보인 경우 아래와 같이 리턴하도록 되어있습니다.
    {
        "code": 899,
        "data": "Invalid password provided",
        "result": "TRANSACTION ERROR",
        "target": "fee delegated"
    }
    ```

---

### SUPPORT FUNCTION
- Account Update
  - TxTypeFeeDelegatedAccountUpdate
  - TxTypeFeeDelegatedAccountUpdateRatio
- Value Transfer
  - TxTypeFeeDelegatedValueTransfer
  - TxTypeFeeDelegatedValueTransferWithRatio
  - TxTypeFeeDelegatedValueTransferMemo
  - TxTypeFeeDelegatedValueTransferMemoWithRatio
- Smart Contract
  - TxTypeFeeDelegatedSmartContractDeploy
  - TxTypeFeeDelegatedSmartContractDeployWithRatio
  - TxTypeFeeDelegatedSmartContractExecution
  - TxTypeFeeDelegatedSmartContractExecutionWithRatio
  - TxTypeFeeDelegatedSmartContractExecution
  - TxTypeFeeDelegatedSmartContractExecutionWithRatio
- Cancel
  - TxTypeFeeDelegatedCancel
  - TxTypeFeeDelegatedCancelWithRatio

---

### HANDLER
- Caver-Java 기준으로 개발 되어있습니다.
- 대납 기능에 필요한 데이터 및 계정 정보 검증
- 대납 Transaction 전송은 Caver-Java를 사용합니다.
- 대납 요청 Raw Transaction 대납계정으로 사이닝 후 전송됩니다.