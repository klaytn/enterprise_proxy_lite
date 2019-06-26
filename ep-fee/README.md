# Enterprise Proxy (Fee Delegated)
This module is used when you want to delegate a gas fee payment for transactions which occur in services.

Please keep in mind that you need to create the delegated account in advance and the account should have enough balance in advance, to execute EP-FEE module properly.



### CONFIGURATION
- Jasypt 2.1.0
  - We highly recommend to modify ENCRYPT_KEY information to something else to avoid security problems.
  - You can modify SimpleStringPBEConfig settings according to your dev or prod environments.
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
- Use of properties
  - In order to use the fee delegation functions, address, password, and keyStoreFilePath should be configured in properties file.
  - As it receives encrypted data, you need to encrypt the data by using Jasypt in advance.
  - The encryption key value should be applied to EncryptorConfig.java ENCRYPT_KEY so that the information can be decrypted properly when the service is running.
    ```shell
    feepayer.address=ENC(...)
    feepayer.password=ENC(...)
    feepayer.keyStoreFilePath=ENC(...)
    ```
    ```shell
      ## How to use Jasypt to encrypt ##
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
  - It loads address, password, and keyStoreFilePath information for the fee delegated account.
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
  - The fee delegation requests are processed properly only after the delegated account is validated.
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
    * The followings are returned if the account information is incorrect.
    {
        "code": 899,
        "data": "Invalid password provided",
        "result": "TRANSACTION ERROR",
        "target": "fee delegated"
    }
    ```

---

### SUPPORTED FUNCTIONS
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
- It is developed based on Caver-Java.
- It validates data and account information for the fee delegation.
- The delegation transaction uses Caver-Java.
- The delegation request RawTransaction is transmitted after being signed by the delegated account.
