# Enterprise Proxy (Transaction Module)
This is the module to transfer signed transactions to Klaytn nodes via Caver-Java. It supports both sync and async to send the transaction.


### CONFIGURATION
- TxProperties
  - It manages the target node information to transfer transactions.
  - It is set from properties file information, but it wouldn't cause a problem even if it's missing when a service is operated.
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
- This class implements sync and async methods of Send Transaction, by using Caver-Java.
  - Host information, i.e. target node information, is received from TxProperties by default, but you can call a specific node, as well.
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
- The followings are model information for Send Transaction Response.
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