# Enterprise Proxy (Transaction Module)
Caver-Java를 이용하여 signed Transaction을 Klaytn 노드에 전송하기 위한 모듈이며 send방식은 sync,async 모두 지원하고 있습니다.



### CONFIGURATION
- TxProperties
  - Transaction 전송 타겟 노드 정보를 관리하고 있습니다.
  - properties 파일 정보로 설정되지만 정보가 없다고 해서 운영시 문제 되지 않습니다.
    ```java
    @Component
    @EnableConfigurationProperties
    @ConfigurationProperties(value="klaytn.rpc.server",ignoreUnknownFields=true)
    public class TxProperties implements Serializable {
        private static final long serialVersionUID=-5978293391451565915L;
        /**
         * caverj klaytn server host
         * @type string
         */
        private String host;
      
        /**
         * caverj klaytn server port
         * @type number
         */
        private int port;
    
        public String getHost() {
            return host;
        }
    
        public void setHost(String host) {
            this.host=host;
        }
    
        public int getPort() {
            return port;
        }
    
        public void setPort(int port) {
            this.port=port;
        }
    
        @Override
        public String toString() {
            return JSONSerializer.toJSON(this).toString();
        }
    }
    ```

---

### TRANSFER
- Caver-Java를 이용하여 Send Transaction의 sync,async 메소드를 구현한 클래스입니다.
  - host 정보 즉 타겟 노드 정보는 TxProperties에서 기본적으로 받고는 있지만 특정 노드로도 호출가능합니다.
  ```java
  public class Transfer {
  		....
  		
  		/**
       * klay send async klay transaction
       *
       * @param host
       * @param klayTransaction
       * @return
       * @throws TransactionException
       */
      private static Response<String> sendAsync(String host,KlayTransaction klayTransaction) throws TransactionException {
          try {
              return Caverj.build(host).klay()
                      .sendTransaction(klayTransaction)
                      .sendAsync()
                      .get();
          }
  
          catch (Exception e) {
              throw new TransactionException(TransactionResponseCode.TX_ERROR,e);
          }
      }
  
  
      /**
       * send signed transcation
       *
       * @param host
       * @param rawtx
       * @return
       * @throws TransactionException
       */
      private static Response<String> send(String host,String rawtx) throws TransactionException {
          try {
              return Caverj.build(host).klay()
                      .sendSignedTransaction(rawtx)
                      .send();
          }
  
          catch (Exception e) {
              throw new TransactionException(TransactionResponseCode.TX_ERROR,e);
          }
      }
  }
  ```

---

### TRANSACTION RESPONSE
- Send Transaction Response 모델 정보입니다.
  ```java
  public class TransactionResponse implements Serializable {
      private static final long serialVersionUID = -4719564050993688336L;
  
      /**
       * response code
       * @private
       */
      private int code;
  
  
      /**
       * response result
       * @private
       */
      private String result;
  
  
      /**
       * response return data
       * @private
       */
      private Object data;
      
      ....
      
  }
  ```

---

### TRANSACTION RESPONSE CODE
- Transaction Response Code에 들어가는 내용입니다.

  ```java
  UNKNOWN(999,"UNKNOWN"),
  EMPTY_DATA(998,"EMPTY DATA"),
  TX_PARAMETER_INVALID(997,"TRANSACTION REQUIREMENT PARAMETER INVALID"),
  TX_FORMAT_INVALID(996,"TRANSACTION HASH FORMAT INVALID"),
  TX_TYPE_INVALID(995,"TRANSACTION TYPE INVALID"),
  ....
  SIGNED_TX_ERROR(899,"SIGNED TRANSACTION ERROR"),
  TX_ERROR(898,"TRANSACTION ERROR"),
  FEE_DELEGATED_TX_ERROR(897,"FEE DELEGATED TRANSACTION ERROR"),
  FEE_DELEGATED_TX_PARAMETER_ERROR(896,"FEE DELEGATED TRANSACTION PARAMETER ERROR"),
  FEE_PAYER_NEED_TO_CHECK_CONFIGURATION_INFORMATION(895,"FEE PAYER NEED TO CHECK CONFIGURATION INFORMATION"),
  ....
  SUCCESS(0,"SUCCESS");
  ```