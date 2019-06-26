# Enterprise Proxy (RPC Module)
Klaytn API(JSON_RPC)를 좀더 쉽게 연동하기 위한 모듈입니다.



### CONFIGURE
- RpcProperties
  - properties 파일 정보 기준으로 설정되지만 기준 정보가 없다고 해서 문제 되지 않습니다.
  ```java
  @Component
  public class RpcProperties {
      /**
       * jsonrpc server host
       * @type string
       */
      @Value("${klaytn.rpc.server.host:''}")
      private String host;
  
      /**
       * jsonrpc server port
       * @type int
       */
      @Value("${klaytn.rpc.server.port:''}")
      private String port;
      
      ...
  }
  ```

---

### 사용 방법
- Interface
  ```java
  public interface Admin {
      static Admin build(String targetHost) {
          return new AdminRpc(targetHost);
      }
      
      RpcResponse dataDir();
      RpcResponse addPeer(String url);
      RpcResponse removePeer(String url);
      RpcResponse nodeInfo();
      RpcResponse peers();
      RpcResponse startRPC(String host,int port,String cors,String[] apis);  
      RpcResponse stopRPC();
      RpcResponse startWS(String host,int port,String cors,String[] apis);
      RpcResponse stopWS();
      RpcResponse importChain(String fileName);
      RpcResponse exportChain(String fileName);
  }
  ```
  ```java
  Admin.build("<TARGET NODE HOST>").datadir();
  ```
- RpcHandler
  - OpenFeign 라이브러리 사용 ([OpenFeign](https://github.com/OpenFeign/feign))
  ```
  public interface RpcHandler {
      @RequestLine("POST /")
      @Headers("Content-Type: application/json")
      RpcResponse call(RpcRequest rpcRequest);
  }
  ```
- Transfer
  - OpenFeign 라이브러리 사용하여 JSON_RPC를 관리하게 됩니다.
  - OkHttpClient 설정은 개발 및 배포 환경에 맞추어 수정하시면 됩니다.
    - 현재 1500ms안에 응답이 없으면 timeout하도록 설정되어있습니다.
  - Retry 설정은 개발 및 배포 환경에 맞추어 수정 하시면 됩니다.
    - 현재 접속 실패시 1번 retry하도록 되어있습니다.
  ```java
  public class RpcTransfer {  
      ...

      /**
       * call rpc by OpenFeign
       *  - retryer
       *      = period : 100
       *      = maxPeriod : 300
       *      = maxAttempts : 2
       *
       *  - timeout
       *      = connectTimeoutMillis : 1500ms
       *      = readTimeoutMillis : 1500ms
       *
       * @param request
       * @param host
       * @return
       */
      private RpcResponse _call(RpcRequest request,String host) {
          okhttp3.OkHttpClient okHttpClient = new okhttp3.OkHttpClient()
          .newBuilder()
          .connectTimeout(1500,TimeUnit.MILLISECONDS)
          .readTimeout(1500,TimeUnit.MILLISECONDS)
          .connectionPool(new ConnectionPool(5,500,TimeUnit.MILLISECONDS))
          .build();
  
          RpcHandler handler = Feign.builder()
          .client(new OkHttpClient(okHttpClient))
          .retryer(new Retryer.Default(100,300,2))
          .encoder(new GsonEncoder())
          .decoder(new GsonDecoder())               
          .errorDecoder(AnnotationErrorDecoder.builderFor(RpcHandler.class).build())
          .target(RpcHandler.class,host);
      }
  }
  ```

- MODEL
  - RpcRequest.java : JSON_RPC 호출시 사용되는 파라미터 모델 클래스
  - Rpc.java : JSON_RPC 호출 이후 JSON_RPC 결과 정보 모델 클래스
  - RpcResponse.java : JSON_RPC 호출 이후 RESPONSE 모델 클래스

---

### SUPPORT RPC
#### Management
- Admin
  - dataDir
    - METHOD
      ```java
      dataDir();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,
          "result" : "/your/dir/ken/data",
          "error" : ""
      }
      ```
  - addPeer
    - METHOD
      ```java
      addPeer(String url);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,
          "result" : true,
          "error" : ""
      }
      ```
  - removePeer
    - METHOD
      ```java
      removePeer(String url);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,
          "result" : true,
          "error" : ""
      }
      ```
  - nodeInfo
    - METHOD
      ```
      nodeInfo();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,
          "result" : {"id":"fdea4887ed29818aec5768c3c1274f817e0c2182b1d63bf8d46d88bc1614f68e207c2a4061b879e32cb318a81e6a5a7e62af28add7e25fd3f359e0e4a98bdbe4","name":"Klaytn/v0.8.1/linux-amd64/go1.11.5","kni":"kni://fdea4887ed29818aec5768c3c1274f817e0c2182b1d63bf8d46d88bc1614f68e207c2a4061b879e32cb318a81e6a5a7e62af28add7e25fd3f359e0e4a98bdbe4@[::]:32323?ntypeu003den","ip":"::","ports":{"discovery":32323.0,"listener":32323.0},"listenAddr":":32323","protocols":{"istanbul":{"network":999.0,"blockscore":1079561.0,"genesis":"0x5b6238f8373d7eae89532386249c4e262c2522aa9b6d2a6cbc7eb401afb71473","config":{"chainId":999.0,"istanbul":{"epoch":604800.0,"policy":2.0,"sub":7.0},"unitPrice":2.5E10,"deriveShaImpl":2.0,"governance":{"governingNode":"0x180156236a7e54f3a6012d23cb3f328504763a3b","governanceMode":"single","reward":{"mintingAmount":9.6E18,"ratio":"34/54/12","useGiniCoeff":false,"deferredTxFee":true,"stakingUpdateInterval":60.0,"proposerUpdateInterval":30.0,"minimumStake":5000000.0}}},"head":"0xe72549836604161862ea90de73341284a1a3b6253a26dcde3ca7de59144c5950"}}},
          "error" : ""
      }
      ```
  - peers
    - METHOD
      ```java
      peers();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,
          "result" : [{"id":"49c5f41dd241356a8a361c3578a7fc2745d61f57e38e1b3c8b016849bb290a49224e090b83bb0d53263c816a1f67b531ccbd5ba55671a47668b1b7fb5a185d10","name":"Klaytn/v0.8.1/linux-amd64/go1.11.5","caps":["istanbul/64"],"networks":[{"localAddress":"172.31.6.108:32872","remoteAddress":"15.164.13.33:32323","inbound":false,"trusted":false,"static":true},{"localAddress":"172.31.6.108:41650","remoteAddress":"15.164.13.33:32324","inbound":false,"trusted":false,"static":true}],"protocols":{"istanbul":{"version":64.0,"blockscore":1079645.0,"head":"0xe4044a3592cb669d43e3bbb38c93cdb7116e78f3ad290beebcb31ec5617e743a"}}},{"id":"98cc93954c3d8230643a5bed208a9e74562b2bd53e3804bd31db629b1678f722012d6c2e75cad11bc1cf250e9cc9846b7edc3545c9e1a296ec26a500274b4882","name":"Klaytn/v0.8.1/linux-amd64/go1.11.5","caps":["istanbul/64"],"networks":[{"localAddress":"172.31.6.108:60972","remoteAddress":"15.164.34.122:32323","inbound":false,"trusted":false,"static":true},{"localAddress":"172.31.6.108:36244","remoteAddress":"15.164.34.122:32324","inbound":false,"trusted":false,"static":true}],"protocols":{"istanbul":{"version":64.0,"blockscore":1079645.0,"head":"0xe4044a3592cb669d43e3bbb38c93cdb7116e78f3ad290beebcb31ec5617e743a"}}},{"id":"dd1afb511200040434814f2aabeec87654a6030671c93a266c5e2c0e90a88ffc1bbfc9b038e3212aeeca61b60be7121319292b32ee83fc64202b1880f8d96d8b","name":"Klaytn/v0.8.1/linux-amd64/go1.11.5","caps":["istanbul/64"],"networks":[{"localAddress":"172.31.6.108:55368","remoteAddress":"13.209.251.200:32323","inbound":false,"trusted":false,"static":true},{"localAddress":"172.31.6.108:53576","remoteAddress":"13.209.251.200:32324","inbound":false,"trusted":false,"static":true}],"protocols":{"istanbul":{"version":64.0,"blockscore":1079645.0,"head":"0xe4044a3592cb669d43e3bbb38c93cdb7116e78f3ad290beebcb31ec5617e743a"}}}],
          "error" : ""
      }
      ```
  - startRPC
    - METHOD
      ```java
      startRPC(String host,int port,String cors,String[] apis);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,
          "result" : true,
          "error" : ""
      }
      ```
  - stopRPC
    - METHOD
      ```java
      stopRPC();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,
          "result" : true,
          "error" : ""
      }
      ```
  - startWS
    - METHOD
      ```java
      startWS(String host,int port,String cors,String[] apis);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,
          "result" : true,
          "error" : ""
      }
      ```
  - stopWS
    - METHOD
      ```java
      stopWS();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,
          "result" : true,
          "error" : ""
      }
      ```
  - importChain
    - METHOD
      ```java
      importChain(String fileName);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : true,
          "error" : ""
      }
      ```
  - exportChain
    - METHOD
      ```java
      exportChain(String fileName);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : true,
          "error" : ""
      }
      ```
  
- Debug
  - startPProf
    - METHOD
      ```java
      startPProf(String host,int port);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "",
          "error" : ""
      }
      ```
    
  - stopPProf
    - METHOD
      ```java
      stopPProf();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "",          
          "error" : ""
      }
      ```
    
  - getModifiedAccountsByHash
    - METHOD
      ```java
      getModifiedAccountsByHash(String startBlockHash,String endBlockHash);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : ["0x31b93ca83b5ad17582e886c400667c6f698b8ccd","0xb7fe15c42e66bd71835b07dc6e7daee7729f6235","0xe31a0edb11357dba71377e625fc6174da4ef4321","0x16b11cf9c2186a117b0da38315b42b1eaa03bbe5","0xd3ec3c7e4cad042dbdcb6a7e0fdbc55a92276f12","0xa4e0d726ce51572e66295756ad93206592c43a59","0xf65e07b6626ab43ecea744803fa46bd4a89bfdb6","0xaac56dfe44f9894d4f536cd17acfbc44bf81a843","0x3855407fa65c4c5104648b3a9e495072df62b585","0x61a7cbdd597848494fa85cbb76f9c63ad9c06cad","0xa4845491cb0dad5bd6707a33c02af0d9db435c15","0x026e8f70a26b6e5c8bec25e23869846edfdd6728","0x3cf3e8caea91501321feee0f0692fcd98f1c6292","0x18822790d7baf2fa6bbca6ad8baa46985abeb81b"],
          "error" : ""
      }
      ```
    
  - getModifiedAccountsByNumber
    - METHOD
      ```java
      getModifiedAccountsByNumber(BigInteger startBlockNum,BigInteger endBlockNum);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : ["0x31b93ca83b5ad17582e886c400667c6f698b8ccd","0xb7fe15c42e66bd71835b07dc6e7daee7729f6235","0xe31a0edb11357dba71377e625fc6174da4ef4321","0x16b11cf9c2186a117b0da38315b42b1eaa03bbe5","0xd3ec3c7e4cad042dbdcb6a7e0fdbc55a92276f12","0xa4e0d726ce51572e66295756ad93206592c43a59","0xf65e07b6626ab43ecea744803fa46bd4a89bfdb6","0xaac56dfe44f9894d4f536cd17acfbc44bf81a843","0x3855407fa65c4c5104648b3a9e495072df62b585","0x61a7cbdd597848494fa85cbb76f9c63ad9c06cad","0xa4845491cb0dad5bd6707a33c02af0d9db435c15","0x026e8f70a26b6e5c8bec25e23869846edfdd6728","0x3cf3e8caea91501321feee0f0692fcd98f1c6292","0x18822790d7baf2fa6bbca6ad8baa46985abeb81b"],
          "error" : ""
      }
      ```
    
  - backtraceAt
    - METHOD
      ```java
      backtraceAt(String location);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "",
          "error" : ""
      }
      ```
    
  - blockProfile
    - METHOD
      ```java
      blockProfile(String fileName, int duration);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "",
          "error" : ""
      }
      ```
    
  - cpuProfile
    - METHOD
      ```java
      cpuProfile(String fileName, int duration);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "",
          "error" : ""
      }
      ```
    
  - dumpBlock
    - METHOD
      ```java
      dumpBlock(String blockHex);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : {"root":"70383c826d1161ec2f12d799023317d8da7775dd47b8502d2d7ef646d094d3a5","accounts":{"0000000000000000000000000000000000000035":{"balance":"12800000000000000000","nonce":0,"root":"56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421","codeHash":"62b00472fac99d94ccc52f5addac43d54c129cd2c6d2357c9557abea67efdec5","code":"6080604052600436106100615763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416631a39d8ef81146100805780636353586b146100a757806370a08231146100ca578063fd6b7ef8146100f8575b3360009081526001602052604081208054349081019091558154019055005b34801561008c57600080fd5b5061009561010d565b60408051918252519081900360200190f35b6100c873ffffffffffffffffffffffffffffffffffffffff60043516610113565b005b3480156100d657600080fd5b5061009573ffffffffffffffffffffffffffffffffffffffff60043516610147565b34801561010457600080fd5b506100c8610159565b60005481565b73ffffffffffffffffffffffffffffffffffffffff1660009081526001602052604081208054349081019091558154019055565b60016020526000908152604090205481565b336000908152600160205260408120805490829055908111156101af57604051339082156108fc029083906000818181858888f193505050501561019c576101af565b3360009081526001602052604090208190555b505600a165627a7a723058201307c3756f4e627009187dcdbc0b3e286c13b98ba9279a25bfcc18dd8bcd73e40029","storage":{}},......}},
          "error" : ""
      }
      ```
    
  - gcStats
    - METHOD
      ```java
      gcStats();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : {"LastGC":"2018-10-15T00:42:08.2787037Z","NumGC":14,"PauseTotal":292805500,"Pause":[3384700,60164200,259500,354600,62331200,241700,29701500,4868200,8242800,35177700,27621100,12647400,38250100,9560800],"PauseEnd":["2018-10-15T00:42:08.2787037Z","2018-10-15T00:40:19.3302813Z","2018-10-15T00:38:41.2202755Z","2018-10-15T00:36:41.2785669Z","2018-10-15T00:36:18.3196569Z","2018-10-15T00:34:48.2073609Z","2018-10-15T00:33:01.3309817Z","2018-10-15T00:31:28.3465898Z","2018-10-15T00:30:05.4245261Z","2018-10-15T00:28:58.6377593Z","2018-10-15T00:27:55.315809Z","2018-10-15T00:27:45.075085Z","2018-10-15T00:27:44.9164574Z","2018-10-15T00:27:44.8406572Z"],"PauseQuantiles":null},
          "error" : ""
      }
      ```
    
  - getBlockRlp
    - METHOD
      ```java
      getBlockRlp(BigInteger blockNumber);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "f905ebf905e6a03ab9a91f37811c1a2e975d1f684e4f9acbfcda09ff1529369a50a0c95ed0c026a01dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347940000000000000000000000000000000000000000940000000000000000000000000000000000000000a067302a0f946f9a676f187c0bebe6a61515321ff34614af507ac2fad9edac9018a056e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421a056e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421b90100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000181c885e8d4a50fff80845bc3e058b903d8d7820302846b6c617988676f312e31302e34856c696e75780000000000000000f903b5f9011194011feb9f54d7cf0c59563d4f621a90e59fe839d9941aa5286f1ae8e183a8f8aebf2cb8425654a4979f94417a9f6e635f458be805702d85dd76d5dbb80aa8944eeabc5dbca48ff84a7fb36d67b51240ad37b01694518df54c51a355d1d961a641a1ee58a4acac62219453113c502402751deec24f6b4ca046878a74afa09459d4136ee1b30d9055b39675c86b5435d48662f9945b36da8763dce3bf84137ca79d56c468017383d2949b974f03aa0bb7d73882e3cfb80ec7a9e9ba543694af70cadbacb65d3a98352a12cc464dffb7e8181894c21dc40330bf91583c79eb58ae527487008f669d94c4a852238132f40ca085554f7bdcaf5f9ebab00f94ebd17315849780edc86f5f32081c8fbb0bd90a23b841ca7b5ac7fa0f6f228ba67544cf898005466a713bb863aaaaada20e58a838b2b468856e6277890a42ec92f8af40ca348a45438efa09d5e572ed8be4b535e016b701f9025bb841e64b232ed585759854e609c7947d19931a14e7906c5bbfe4acaa9aa7484d28f70b9dbfb7047dbd971e5d077d97306f26be258d2b016446fd41474082cc274a2b01b84195cf54ec0ac1ad4137be193f8d111f893590bd840cdb514c42ac169eb69e666a47e6cb358847364411c9cacb6261005d7fd4583e0d40216a1fa86fb083be270901b841e6caa2cb395a6e9d9c0853834a87a4c03444ec2b94035b3742c19ca09a72384c3ea9738aa66b43ad6f40269e8fd0ba015e23db36d7d7ccf666adba67c4bda4fc01b8418ad3f264ebb0dc47788f1d872423b7b067137d5d550f3311642308d5251f3e50049af533b8e16309ace3bf41ecf38a65a6e364458f8224c9efe57983c590590601b84129e147bf2666826af23027237e2894b074014ff38711ffe7168aa808a52c7e4863f2da1c5abd625567ef59b053f0b7abd36459b30a1e4d4d953784cb5f568fc500b841d58b713b28dba4bcb26ebbd274bbb12a8738a054c9cd18a2ee7298264e683a720dd633b66fa98edfe926e844d6e45f884b5e22fcd0df39d4d6f1fc84f1caceb701b8412dfaeadac01129c1ff9352c1b4b66e1c6b035b80e4b53e3ea5344a82ef607e3321fa05e57f6bdb27e4bd665af3e6c8b19575b3888f1b949c0cadbb186857b7cb01b841bb240246258aae93db40017e0ba437e0c4e517a25643282ee361f89fdb877a745f682a1f2c0441aa2d59cfa2058dc61d46f046499d4036efa02557b33a2f395200b841fdb9bb602e321d80ea09621e6ac36c01dc11152651404b4f7dba8be13466142914d97592c2625d83c27e220521d860b6128a0172c0ff39cdc23dabbfd4fdbb8100a063746963616c2062797a616e74696e65206661756c7420746f6c6572616e6365880000000000000000c0c0",
          "error" : ""
      }
      ```
    
  - goTrace
    - METHOD
      ```java
      goTrace(String fileName, int duration);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "",
          "error" : ""
      }
      ```
    
  - memStats
    - METHOD
      ```java
      memStats();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "{Alloc:7.04030136E8,TotalAlloc:6.58496346928E11,Sys:2.310561016E9,Lookups:0.0,Mallocs:6.12704116E9,Frees:6.126456527E9,HeapAlloc:7.04030136E8,HeapSys:2.213117952E9,HeapIdle:1.3535232E9,HeapInuse:8.59594752E8,HeapReleased:1.288257536E9,HeapObjects:584633.0,StackInuse:1474560.0,StackSys:1474560.0,MSpanInuse:4984840.0,MSpanSys:1.1042816E7,MCacheInuse:3456.0,MCacheSys:16384.0,BuckHashSys:3577849.0,GCSys:7.9212544E7,OtherSys:2118911.0,NextGC:1.374200096E9,LastGC:1.55592352164496717E18,PauseTotalNs:8.98712899E8,PauseNs:[51833.0,47226.0,47832.0,63178.0,69627.0,80513.0,59011.0,49124.0,55581.0,46648.0,54313.0,52587.0,61961.0,47318.0,54464.0,53885.0,54424.0,30107.0,34400.0,36950.0,37892.0,35011.0,50695.0,65986.0,52169.0,44933.0,66296.0,45356.0,74170.0,59503.0,45914.0,60591.0,50085.0,58316.0,42061.0,65249.0,57989.0,61088.0,63927.0,50584.0,33496.0,88167.0,51803.0,47558.0,55899.0,41160.0,57725.0,77657.0,56711.0,77012.0,58161.0,61878.0,42878.0,52755.0,52249.0,48062.0,70515.0,60690.0,67218.0,66768.0,81707.0,64318.0,37401.0,32902.0,40730.0,52737.0,38655.0,63949.0,53173.0,54451.0,65584.0,52808.0,60558.0,52509.0,53197.0,63940.0,60990.0,63428.0,47115.0,58154.0,52261.0,29421.0,41222.0,55770.0,54675.0,89386.0,41705.0,59585.0,49374.0,54691.0,63204.0,51771.0,51846.0,44991.0,51354.0,43903.0,59153.0,55001.0,54262.0,65107.0,36858.0,45044.0,44501.0,58208.0,54560.0,42629.0,58748.0,54108.0,54636.0,47002.0,58245.0,58830.0,67879.0,47829.0,78805.0,49130.0,58996.0,36808.0,45445.0,40578.0,56596.0,44305.0,40761.0,55524.0,51880.0,54654.0,73759.0,76532.0,59857.0,62995.0,48675.0,54018.0,45400.0,52878.0,73905.0,48626.0,76589.0,68612.0,58969.0,54250.0,34185.0,56489.0,56937.0,39517.0,53310.0,54174.0,49798.0,60844.0,51007.0,67415.0,58308.0,51585.0,50866.0,54222.0,54571.0,63217.0,81533.0,48214.0,53773.0,61994.0,49711.0,56940.0,48538.0,61824.0,52288.0,51500.0,28211.0,49437.0,41995.0,53879.0,51452.0,52529.0,76936.0,61942.0,75624.0,60149.0,62665.0,63149.0,53206.0,61154.0,64051.0,57251.0,52721.0,48316.0,63248.0,54340.0,67394.0,32627.0,52902.0,71096.0,54879.0,56479.0,67637.0,50737.0,52534.0,50762.0,47151.0,56013.0,67002.0,58702.0,58893.0,43099.0,31251.0,46797.0,47736.0,55715.0,49827.0,68513.0,58615.0,56380.0,38481.0,150565.0,83635.0,200430.0,4353527.0,48155.0,74025.0,56951.0,79309.0,83628.0,36284.0,97445.0,52672.0,58416.0,84884.0,66720.0,70441.0,55863.0,134952.0,190113.0,60330.0,157767.0,57329.0,55080.0,62942.0,53286.0,68519.0,34474.0,38417.0,60895.0,50721.0,70775.0,50778.0,66340.0,54007.0,52093.0,50863.0,68185.0,63833.0,62633.0,55620.0,69029.0,53275.0,50780.0,71628.0,53991.0],PauseEnd:[1.55592087798096794E18,1.55592099804268877E18,1.5559211182938089E18,1.5559212386046103E18,1.55592135877109555E18,1.55592147905158861E18,1.55592159922455834E18,1.55592171929716403E18,1.55592183957598438E18,1.55592196005537101E18,1.55592208010182682E18,1.55592220030368102E18,1.55592232035490534E18,1.55592244050473139E18,1.55592256055386547E18,1.55592268072105216E18,1.55592280105439206E18,1.55592292115354701E18,1.55592304130640461E18,1.55592316142384742E18,1.55592328148336461E18,1.5559234015619607E18,1.55592352164496717E18,1.55589288030570726E18,1.55589300037259571E18,1.55589312049600742E18,1.55589324066117197E18,1.55589336077943219E18,1.55589348105457894E18,1.55589360129184486E18,1.55589372133610982E18,1.55589384176324173E18,1.5558939620553152E18,1.555894082161408E18,1.55589420230324762E18,1.55589432235925402E18,1.55589444249135667E18,1.55589456258920602E18,1.55589468274224845E18,1.55589480280887731E18,1.55589492300347418E18,1.55589504305847987E18,1.55589516310909952E18,1.55589528330376499E18,1.55589540335237632E18,1.55589552347360512E18,1.55589564357137152E18,1.55589576405389696E18,1.55589588430609075E18,1.55589600446021632E18,1.55589612453464627E18,1.5558962448262935E18,1.55589636505368602E18,1.55589648512182707E18,1.55589660530651494E18,1.55589672541763098E18,1.5558968455409856E18,1.55589696562238029E18,1.55589708567611674E18,1.55589720577190579E18,1.5558973260560937E18,1.5558974461551703E18,1.55589756620667392E18,1.55589768630325274E18,1.55589780640546074E18,1.55589792665072922E18,1.55589804672100326E18,1.55589816705429555E18,1.5558982871615936E18,1.55589840728086733E18,1.55589852774243866E18,1.55589864803021978E18,1.55589876819507866E18,1.55589888830363238E18,1.55589900865228032E18,1.55589912874757197E18,1.5558992488404841E18,1.55589936901926477E18,1.55589948930492544E18,1.5558996093509632E18,1.5558997294015424E18,1.55589984949158554E18,1.55589996971839795E18,1.5559000899212009E18,1.55590021005675443E18,1.55590033019507123E18,1.55590045030371251E18,1.55590057037288704E18,1.55590069041693466E18,1.55590081050981453E18,1.55590093059497421E18,1.55590105100712499E18,1.55590117105374259E18,1.55590129130189747E18,1.55590141177128115E18,1.55590153183983974E18,1.55590165205421133E18,1.55590177209756672E18,1.55590189216208256E18,1.55590201230969395E18,1.5559021325795968E18,1.55590225274452531E18,1.55590237280518374E18,1.55590249305442458E18,1.55590261313806157E18,1.55590273318365338E18,1.5559028533073769E18,1.55590297376044032E18,1.55590309405360589E18,1.55590321423030298E18,1.55590333430696064E18,1.55590345463041766E18,1.55590357477104205E18,1.55590369492220954E18,1.55590381505521587E18,1.55590393530261939E18,1.55590405565204915E18,1.55590417571207526E18,1.55590429582121498E18,1.55590441588324762E18,1.55590453605414042E18,1.55590465630429107E18,1.5559047763753129E18,1.55590489705388186E18,1.55590501716166451E18,1.55590513730684928E18,1.55590525742609331E18,1.5559053777426007E18,1.55590549785733504E18,1.5559056179481152E18,1.5559057380047552E18,1.5559058580567424E18,1.55590597817773056E18,1.55590609824007424E18,1.5559062183072425E18,1.55590633844722637E18,1.55590645853437261E18,1.555906578652064E18,1.55590669875167232E18,1.55590681889434778E18,1.55590693894820838E18,1.55590705905443328E18,1.55590717909757133E18,1.55590729916561843E18,1.55590741926133299E18,1.55590753930769638E18,1.55590765942318234E18,1.55590777965625523E18,1.55590789986613274E18,1.5559080199175616E18,1.55590814005545754E18,1.55590826011344435E18,1.55590838016565094E18,1.55590850024619699E18,1.55590862030387814E18,1.55590874034984115E18,1.55590886051576576E18,1.55590898059257267E18,1.55590910077860301E18,1.55590922105514368E18,1.55590934113995597E18,1.55590946130652621E18,1.55590958151923251E18,1.55590970185915981E18,1.5559098220544425E18,1.5559099420974697E18,1.55591006215409869E18,1.55591018230625382E18,1.55591030274247782E18,1.55591042293420109E18,1.55591054299677414E18,1.55591066305470746E18,1.55591078327957837E18,1.55591090359729587E18,1.55591102377391949E18,1.55591114402666854E18,1.55591126414080051E18,1.55591138420839245E18,1.55591150430670797E18,1.55591162477002675E18,1.55591174494441626E18,1.55591186505467418E18,1.55591198512278502E18,1.55591210516441114E18,1.55591222529956096E18,1.55591234534897203E18,1.55591246540371763E18,1.55591258545430656E18,1.55591270562754637E18,1.55591282606058624E18,1.55591294618764262E18,1.55591306631192602E18,1.55591318677087821E18,1.555913307054112E18,1.55591342716150707E18,1.5559135472160128E18,1.55591366728194483E18,1.55591378774281472E18,1.55591390803914624E18,1.55591402821732045E18,1.55591414831372237E18,1.5559142685730537E18,1.55591438863724621E18,1.55591450905519078E18,1.55591462915634918E18,1.55591474930645811E18,1.55591486939273651E18,1.55591498977065677E18,1.55591511007285658E18,1.55591523027959782E18,1.55591535034059238E18,1.55591547045917338E18,1.55591559053679411E18,1.55591571058929024E18,1.55591583106788915E18,1.55591595113380173E18,1.55591607130274995E18,1.55591619139655142E18,1.55591631146158131E18,1.55591643154736461E18,1.55591655172405939E18,1.55591667207098573E18,1.55591679217597798E18,1.55591691229597568E18,1.55591703261007488E18,1.5559171527232832E18,1.55591727278993485E18,1.55591739306944102E18,1.55591751313071744E18,1.55591763330891392E18,1.55591775343750707E18,1.55591787367202586E18,1.55591799405733837E18,1.55591811416885094E18,1.55591823431182054E18,1.55591835435701683E18,1.5559184747322793E18,1.55591859484416512E18,1.55591871505352141E18,1.55591883523665075E18,1.55591895528313856E18,1.55591907533468314E18,1.55591919554321254E18,1.55591931577125146E18,1.55591943582660045E18,1.55591955605495245E18,1.55591967630423066E18,1.55591979650454451E18,1.55591991659086643E18,1.55592003688804966E18,1.55592015705470848E18,1.55592027716565248E18,1.55592039721033318E18,1.55592051729063142E18,1.5559206374404777E18,1.55592075774749568E18],NumGC:8215.0,NumForcedGC:0.0,GCCPUFraction:5.443898588278878E-5,EnableGC:true,DebugGC:false,BySize:[{Size:0.0,Mallocs:0.0,Frees:0.0},{Size:8.0,Mallocs:2.3083142E8,Frees:2.30802959E8},{Size:16.0,Mallocs:8.69611373E8,Frees:8.6957642E8},{Size:32.0,Mallocs:2.410630637E9,Frees:2.410442385E9},{Size:48.0,Mallocs:7.69840611E8,Frees:7.69711513E8},{Size:64.0,Mallocs:1.87194192E8,Frees:1.87163913E8},{Size:80.0,Mallocs:4.22115104E8,Frees:4.22053284E8},{Size:96.0,Mallocs:1.0562872E8,Frees:1.05623426E8},{Size:112.0,Mallocs:5.1081093E7,Frees:5.1051788E7},{Size:128.0,Mallocs:1.54988395E8,Frees:1.54983174E8},{Size:144.0,Mallocs:4.0362929E7,Frees:4.0344625E7},{Size:160.0,Mallocs:5.2751934E7,Frees:5.2749054E7},{Size:176.0,Mallocs:3068522.0,Frees:3068356.0},{Size:192.0,Mallocs:2.7370916E7,Frees:2.7369904E7},{Size:208.0,Mallocs:127352.0,Frees:125990.0},{Size:224.0,Mallocs:3903059.0,Frees:3902899.0},{Size:240.0,Mallocs:7.6324625E7,Frees:7.6323324E7},{Size:256.0,Mallocs:4722845.0,Frees:4721996.0},{Size:288.0,Mallocs:2.6958741E7,Frees:2.695698E7},{Size:320.0,Mallocs:5.882468E7,Frees:5.8822323E7},{Size:352.0,Mallocs:1.29427E7,Frees:1.2941941E7},{Size:384.0,Mallocs:550835.0,Frees:548612.0},{Size:416.0,Mallocs:6.6804399E7,Frees:6.6801707E7},{Size:448.0,Mallocs:1.48109351E8,Frees:1.48104532E8},{Size:480.0,Mallocs:85563.0,Frees:85551.0},{Size:512.0,Mallocs:2.4087897E7,Frees:2.4084737E7},{Size:576.0,Mallocs:664425.0,Frees:664392.0},{Size:640.0,Mallocs:1.09920168E8,Frees:1.09916216E8},{Size:704.0,Mallocs:1964879.0,Frees:1964798.0},{Size:768.0,Mallocs:458512.0,Frees:458500.0},{Size:896.0,Mallocs:2239717.0,Frees:2239587.0},{Size:1024.0,Mallocs:4601239.0,Frees:4601067.0},{Size:1152.0,Mallocs:800233.0,Frees:800195.0},{Size:1280.0,Mallocs:1955198.0,Frees:1955101.0},{Size:1408.0,Mallocs:4581.0,Frees:4573.0},{Size:1536.0,Mallocs:2505633.0,Frees:2505542.0},{Size:1792.0,Mallocs:5173983.0,Frees:5173264.0},{Size:2048.0,Mallocs:1839420.0,Frees:1839399.0},{Size:2304.0,Mallocs:579558.0,Frees:579541.0},{Size:2688.0,Mallocs:2097694.0,Frees:2097679.0},{Size:3072.0,Mallocs:65167.0,Frees:65146.0},{Size:3200.0,Mallocs:5606.0,Frees:5605.0},{Size:3456.0,Mallocs:35685.0,Frees:35683.0},{Size:4096.0,Mallocs:7307184.0,Frees:7307093.0},{Size:4864.0,Mallocs:8667542.0,Frees:8646206.0},{Size:5376.0,Mallocs:1752085.0,Frees:1751292.0},{Size:6144.0,Mallocs:73107.0,Frees:73087.0},{Size:6528.0,Mallocs:1014.0,Frees:1007.0},{Size:6784.0,Mallocs:3731.0,Frees:3731.0},{Size:6912.0,Mallocs:21300.0,Frees:21299.0},{Size:8192.0,Mallocs:82577.0,Frees:82553.0},{Size:9472.0,Mallocs:1282060.0,Frees:1282001.0},{Size:9728.0,Mallocs:113.0,Frees:112.0},{Size:10240.0,Mallocs:848.0,Frees:846.0},{Size:10880.0,Mallocs:1938197.0,Frees:1938123.0},{Size:12288.0,Mallocs:2582.0,Frees:2577.0},{Size:13568.0,Mallocs:676.0,Frees:675.0},{Size:14336.0,Mallocs:268.0,Frees:262.0},{Size:16384.0,Mallocs:33989.0,Frees:33986.0},{Size:18432.0,Mallocs:287.0,Frees:275.0},{Size:19072.0,Mallocs:71.0,Frees:69.0}]}",
          "error" : ""
      }
      ```
    
  - metrics
    - METHOD
      ```java
      metrics(boolean isRaw);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : {"blockchain":{"block":{"tx":{"counter":{"Overall":98307},"rate":{"AvgRate01Min":19.99999999999893,"AvgRate05Min":19.999669059400787,"AvgRate15Min":19.91097896398045,"MeanRate":16.321034565305364,"Overall":98307}}},"head":{"blocknumber":"Unknown metric type"}},"bridgeTxpool":{"refuse":{"Overall":0}},......},
          "error" : ""
      }
      ```
    
  - printBlock
    - METHOD
      ```java
      printBlock(BigInteger blockNumber);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "(*types.Block)(0xc0a1f7ed20)(Block(#1011617): Size: 827.00 B { MinerHash: c6ee45b9316e6606655e61c9c1990ec995b95889857129ec3068b18c5f1b7c15 Header(1392bc1b77207edccf6a48ef9af446dcb1c887241230a707364407d3d4a22e45): [ tParentHash:       8b54568c4fba50c99801adb13040d63da7bcc2d1adc0047a1a3e6b2c7b835024 tRewardbase:       d495668548f8724ded2a54c2ac17febbd5506867 tRoot:             001916a6a681bf7f094f206e36cd5e23e3fcfc0fdb3607d21facbd095509e11c tTxSha:            c5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470 tReceiptSha:       c5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470 tBloom:            00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000......",
          "error" : ""
      }
      ```
    
  - preImage
    - METHOD
      ```java
      preImage(String sha3hash);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0xaf953a2d01f55cfe080c0c94150a60105e8ac3d51153058a1f03dd239dd08586")
      "0xdd738d9a7d987a98798123b2322d389470328420bb3d84023a8405a5523cc532235ba325235243242cb9a4758609a8604 ...  98bbd743053d0cbadaaccd4865cc0348685460ada874506ad984506ad80458ad69038fd6f908340fd9af68faf903760",
          "error" : ""
      }
      ```
    
  - freeOSMemory
    - METHOD
      ```java
      freeOSMemory();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "",
          "error" : ""
      }
      ```
    
  - setHead
    - METHOD
      ```java
      setHead(String blockHex);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "",
          "error" : ""
      }
      ```
    
  - setBlockProfileRate
    - METHOD
      ```java
      setBlockProfileRate(int rate);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "",
          "error" : ""
      }
      ```
    
  - setVMLogTarget
    - METHOD
      ```java
      setVMLogTarget(int target);
      ```
    - RESPONSE
      ```
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "no output",
          "error" : ""
      }
      ```
    
  - stacks
    - METHOD
      ```java
      stacks();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "goroutine 409689 [running]: github.com/ground-x/klaytn/api/debug.(*HandlerT).Stacks(0xc0001a8300, 0x0, 0x0)  /rpmbuild_home/BUILD/kend-v0.8.1/build/_workspace/src/github.com/ground-x/klaytn/api/debug/api.go:288 +0x74 reflect.Value.call(0xc0a24090e0, 0xc0a23edf20, 0x13, 0x12857b4, 0x4, 0xc0a4b617c0, 0x1, 0x1, 0x30, 0xc0a5a7c630, ...)  /usr/lib/golang/src/reflect/value.go:447 +0x454 reflect.Value.Call(0xc0a24090e0, 0xc0a23edf20, 0x13, 0xc0a4b617c0, 0x1, 0x1, 0xc006a6f728, 0x41ea88, 0x30)  /usr/lib/golang/src/reflect/value.go:308 +0xa4 github.com/ground-x/klaytn/networks/rpc.(*Server).handle(0xc0a24955c0, 0x150a660, 0xc0a267f7c0, 0x1510d00, 0xc0a41d3900, 0xc0a776aba0, 0xc0000ac358, 0x0, 0xc006a6f9a0)  /rpmbuild_home/BUILD/kend-v0.8.1/build/_workspace/src/github.com/ground-x/klaytn/networks/rpc/server.go:341 +0x6cc github.com/ground-x/klaytn/networks/rpc.(*Server).exec(0xc0a24955c0, 0x150a660, 0xc0a267f7c0, 0x1510d00, 0xc0a41d3900, 0xc0a776aba0)  /rpmbuild_home/BUILD/kend-v0.8.1/build/_workspace/src/github.com/ground-x/klaytn/networks/rpc/server.go:364 +0x1ba github.com/ground-x/klaytn/networks/rpc.(*Server).serveRequest(0xc0a24955c0, 0x150a720, 0xc0a5a7c6c0, 0x1510d00, 0xc0a41d3900, 0x1291a01, 0x1, 0x0, 0x0)......",
          "error" : ""
      }
      ```
    
  - startCPUProfile
    - METHOD
      ```
      startCPUProfile(String fileName);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "",
          "error" : ""
      }
      ```
    
  - stopCPUProfile
    - METHOD
      ```java
      stopCPUProfile();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "",
          "error" : ""
      }
      ```
    
  - startGoTrace
    - METHOD
      ```java
      startGoTrace(String fileName);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "",
          "error" : ""
      }
      ```
    
  - stopGoTrace
    - METHOD
      ```java
      stopGoTrace();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "",
          "error" : ""
      }
      ```
    
  - verbosity
    - METHOD
      ```java
      verbosity(int level);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "",
          "error" : ""
      }
      ```
    
  - vmodule
    - METHOD
      ```java
      vmodule(String module);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "",
          "error" : ""
      }
      ```
    
  - writeBlockProfile
    - METHOD
      ```java
      writeBlockProfile(String fileName);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "",
          "error" : ""
      }
      ```
    
  - writeMemProfile
    - METHOD
      ```java
      writeMemProfile(String fileName);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "",
          "error" : ""
      }
      ```
    
  - setGCPercent
    - METHOD
      ```java
      setGCPercent(int percent);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : 55,
          "error" : ""
      }
      ```
    
  - traceBlock
    - METHOD
      ```java
      traceBlock(String blockRlp,String tracerName);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : [{"result":{"gas":247922,"failed":false,"returnValue":"60806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806341c0e1b514610051578063cfae321714610068575b600080fd5b34801561005d57600080fd5b506100666100f8565b005b34801561007457600080fd5b5061007d610168565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100bd5780820151818401526020810190506100a2565b50505050905090810190601f1680156100ea5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415610166573373ffffffffffffffffffffffffffffffffffffffff16ff5b565b606060018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156102005780601f106101d557610100808354040283529160200191610200565b820191906000526020600020905b8154815290600101906020018083116101e357829003601f168201915b50505050509050905600a165627a7a72305820f4e74ca2266a24aabd6a8ee6c4e54ad49014e2faa152e49e7f9d927c932c72870029","structLogs":[{"pc":0,"op":"PUSH1","gas":891344,"gasCost":3,"depth":1,"stack":[],"memory":[],"storage":{}},{"pc":2,"op":"PUSH1","gas":891341,"gasCost":3,"depth":1,"stack":["0000000000000000000000000000000000000000000000000000000000000080"],"memory":[],"storage":{}},{"pc":4,"op":"MSTORE","gas":891338,"gasCost":12,"depth":1,"stack":["0000000000000000000000000000000000000000000000000000000000000080","0000000000000000000000000000000000000000000000000000000000000040"],"memory":["0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000000"],"storage":{}},{"pc":5,"op":"CALLVALUE","gas":891326,"gasCost":2,"depth":1,"stack":[],"memory":["0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000080"],"storage":{}},...
          "error" : ""
      }
      ```
    
  - traceBlockByNumber
    - METHOD
      ```java
      traceBlockByNumber(String blockHex,String tracerName);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : [{"result":{"gas":247922,"failed":false,"returnValue":"60806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806341c0e1b514610051578063cfae321714610068575b600080fd5b34801561005d57600080fd5b506100666100f8565b005b34801561007457600080fd5b5061007d610168565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100bd5780820151818401526020810190506100a2565b50505050905090810190601f1680156100ea5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415610166573373ffffffffffffffffffffffffffffffffffffffff16ff5b565b606060018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156102005780601f106101d557610100808354040283529160200191610200565b820191906000526020600020905b8154815290600101906020018083116101e357829003601f168201915b50505050509050905600a165627a7a72305820f4e74ca2266a24aabd6a8ee6c4e54ad49014e2faa152e49e7f9d927c932c72870029","structLogs":[{"pc":0,"op":"PUSH1","gas":891344,"gasCost":3,"depth":1,"stack":[],"memory":[],"storage":{}},{"pc":2,"op":"PUSH1","gas":891341,"gasCost":3,"depth":1,"stack":["0000000000000000000000000000000000000000000000000000000000000080"],"memory":[],"storage":{}},{"pc":4,"op":"MSTORE","gas":891338,"gasCost":12,"depth":1,"stack":["0000000000000000000000000000000000000000000000000000000000000080","0000000000000000000000000000000000000000000000000000000000000040"],"memory":["0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000000"],"storage":{}},{"pc":5,"op":"CALLVALUE","gas":891326,"gasCost":2,"depth":1,"stack":[],"memory":["0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000080"],"storage":{}},...
          "error" : ""
      }
      ```
    
  - traceBlockByHash
    - METHOD
      ```java
      traceBlockByHash(String blockHash,String tracerName);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : [{"result":{"gas":247922,"failed":false,"returnValue":"60806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806341c0e1b514610051578063cfae321714610068575b600080fd5b34801561005d57600080fd5b506100666100f8565b005b34801561007457600080fd5b5061007d610168565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100bd5780820151818401526020810190506100a2565b50505050905090810190601f1680156100ea5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415610166573373ffffffffffffffffffffffffffffffffffffffff16ff5b565b606060018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156102005780601f106101d557610100808354040283529160200191610200565b820191906000526020600020905b8154815290600101906020018083116101e357829003601f168201915b50505050509050905600a165627a7a72305820f4e74ca2266a24aabd6a8ee6c4e54ad49014e2faa152e49e7f9d927c932c72870029","structLogs":[{"pc":0,"op":"PUSH1","gas":891344,"gasCost":3,"depth":1,"stack":[],"memory":[],"storage":{}},{"pc":2,"op":"PUSH1","gas":891341,"gasCost":3,"depth":1,"stack":["0000000000000000000000000000000000000000000000000000000000000080"],"memory":[],"storage":{}},{"pc":4,"op":"MSTORE","gas":891338,"gasCost":12,"depth":1,"stack":["0000000000000000000000000000000000000000000000000000000000000080","0000000000000000000000000000000000000000000000000000000000000040"],"memory":["0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000000"],"storage":{}},{"pc":5,"op":"CALLVALUE","gas":891326,"gasCost":2,"depth":1,"stack":[],"memory":["0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000080"],"storage":{}},...
          "error" : ""
      }
      ```
    
  - traceBlockFromFile
    - METHOD
      ```java
      traceBlockFromFile(String fileName,String tracerName);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : [{"result":{"gas":247922,"failed":false,"returnValue":"60806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806341c0e1b514610051578063cfae321714610068575b600080fd5b34801561005d57600080fd5b506100666100f8565b005b34801561007457600080fd5b5061007d610168565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100bd5780820151818401526020810190506100a2565b50505050905090810190601f1680156100ea5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415610166573373ffffffffffffffffffffffffffffffffffffffff16ff5b565b606060018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156102005780601f106101d557610100808354040283529160200191610200565b820191906000526020600020905b8154815290600101906020018083116101e357829003601f168201915b50505050509050905600a165627a7a72305820f4e74ca2266a24aabd6a8ee6c4e54ad49014e2faa152e49e7f9d927c932c72870029","structLogs":[{"pc":0,"op":"PUSH1","gas":891344,"gasCost":3,"depth":1,"stack":[],"memory":[],"storage":{}},{"pc":2,"op":"PUSH1","gas":891341,"gasCost":3,"depth":1,"stack":["0000000000000000000000000000000000000000000000000000000000000080"],"memory":[],"storage":{}},{"pc":4,"op":"MSTORE","gas":891338,"gasCost":12,"depth":1,"stack":["0000000000000000000000000000000000000000000000000000000000000080","0000000000000000000000000000000000000000000000000000000000000040"],"memory":["0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000000"],"storage":{}},{"pc":5,"op":"CALLVALUE","gas":891326,"gasCost":2,"depth":1,"stack":[],"memory":["0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000080"],"storage":{}},...
          "error" : ""
      }
      ```
    
  - traceTransaction
    - METHOD
      ```java
      traceTransaction(String transactionHash,String tracerName);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : [{"result":{"gas":247922,"failed":false,"returnValue":"60806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806341c0e1b514610051578063cfae321714610068575b600080fd5b34801561005d57600080fd5b506100666100f8565b005b34801561007457600080fd5b5061007d610168565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100bd5780820151818401526020810190506100a2565b50505050905090810190601f1680156100ea5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415610166573373ffffffffffffffffffffffffffffffffffffffff16ff5b565b606060018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156102005780601f106101d557610100808354040283529160200191610200565b820191906000526020600020905b8154815290600101906020018083116101e357829003601f168201915b50505050509050905600a165627a7a72305820f4e74ca2266a24aabd6a8ee6c4e54ad49014e2faa152e49e7f9d927c932c72870029","structLogs":[{"pc":0,"op":"PUSH1","gas":891344,"gasCost":3,"depth":1,"stack":[],"memory":[],"storage":{}},{"pc":2,"op":"PUSH1","gas":891341,"gasCost":3,"depth":1,"stack":["0000000000000000000000000000000000000000000000000000000000000080"],"memory":[],"storage":{}},{"pc":4,"op":"MSTORE","gas":891338,"gasCost":12,"depth":1,"stack":["0000000000000000000000000000000000000000000000000000000000000080","0000000000000000000000000000000000000000000000000000000000000040"],"memory":["0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000000"],"storage":{}},{"pc":5,"op":"CALLVALUE","gas":891326,"gasCost":2,"depth":1,"stack":[],"memory":["0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000080"],"storage":{}},...
          "error" : ""
      }
      ```
    
  - traceBadBlock
    
    - METHOD
      
      ```java
      traceBadBlock(String blockHash,String tracerName);
      ```
      
    - RESPONSE
    
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : [{"result":{"gas":247922,"failed":false,"returnValue":"60806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806341c0e1b514610051578063cfae321714610068575b600080fd5b34801561005d57600080fd5b506100666100f8565b005b34801561007457600080fd5b5061007d610168565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100bd5780820151818401526020810190506100a2565b50505050905090810190601f1680156100ea5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415610166573373ffffffffffffffffffffffffffffffffffffffff16ff5b565b606060018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156102005780601f106101d557610100808354040283529160200191610200565b820191906000526020600020905b8154815290600101906020018083116101e357829003601f168201915b50505050509050905600a165627a7a72305820f4e74ca2266a24aabd6a8ee6c4e54ad49014e2faa152e49e7f9d927c932c72870029","structLogs":[{"pc":0,"op":"PUSH1","gas":891344,"gasCost":3,"depth":1,"stack":[],"memory":[],"storage":{}},{"pc":2,"op":"PUSH1","gas":891341,"gasCost":3,"depth":1,"stack":["0000000000000000000000000000000000000000000000000000000000000080"],"memory":[],"storage":{}},{"pc":4,"op":"MSTORE","gas":891338,"gasCost":12,"depth":1,"stack":["0000000000000000000000000000000000000000000000000000000000000080","0000000000000000000000000000000000000000000000000000000000000040"],"memory":["0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000000"],"storage":{}},{"pc":5,"op":"CALLVALUE","gas":891326,"gasCost":2,"depth":1,"stack":[],"memory":["0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000000","0000000000000000000000000000000000000000000000000000000000000080"],"storage":{}},...
          "error" : ""
      }
      ```
    
  - standardTraceBadBlockToFile
  
    - METHOD
  
      ```java
      standardTraceBadBlockToFile(String blockHash,String tracerName);
      ```
  
    - RESPONSE
  
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : ["/var/folders/v9/z3vq7j4d42b2jq_vxsv0km6h0000gn/T/block_0x1d5ba00e-0-0xae6f8ed4-608268252","/var/folders/v9/z3vq7j4d42b2jq_vxsv0km6h0000gn/T/block_0x1d5ba00e-1-0x2e37321f-315574667"]
          "error" : ""
      }
      ```
  
  - standardTraceBlockToFile
  
    - METHOD
  
      ```java
      standardTraceBlockToFile(String blockHash,String tracerName);
      ```
  
    - RESPONSE
  
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : ["/var/folders/v9/z3vq7j4d42b2jq_vxsv0km6h0000gn/T/block_0x485fff44-0-0xfe8210fc-288181237"]
          "error" : ""
      }
      ```
  
- Personal
  
  - importRawKey
    - METHOD
      ```java
      importRawKey(String keyData, String passPhrase);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0x649834fcc7c......",
          "error" : ""
      }
      ```
  - listAccounts
    - METHOD
      ```
      listAccounts();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : ["0x95611200d726defd14cd1e3b29...","0xc72dc21310892573e98588f...","0x9ba0d03bc6db4c097bfdce86065a8...","0x45f2348ae2f97097c93ad614649c91...",.......],
          "error" : ""
      }
      ```
  - lockAccount
    - METHOD
      ```java
      lockAccount(String accountAddress);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : true,
          "error" : ""
      }
      ```
  - unlockAccount
    - METHOD
      ```java
      unlockAccount(String accountAddress, String passPhrase, int duration);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : true,
          "error" : ""
      }
      ```
  - sendTransaction
    - METHOD
      ```java
      sendTransaction(String fromAddress, String toAddress, String gas, String gasPrice, String value, String data, String nonce, String passPhrase);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0x26a7a8ba619a5e3e4d742c217f55f49591a5616b200c976bd58a966a05e294b7",
          "error" : ""
      }
      ```
  - sign
    - METHOD
      ```java
      sign(String message, String accountAddress, String passPhrase);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0xdab522b37b9fdf6cf5b3949499850c54a4c118291d56dc027dbf8988af3c65f120821235c897a3c75770d74bef8a2a0793d748016cde9e64d978eca8cfcb789e1b",
          "error" : ""
      }
      ```
  - ecRecover
    - METHOD
      ```java
      ecRecover(String message, String signature);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0xb49c447039f2a75d26b1834f831cb964ba8079ed",
          "error" : ""
      }
      ```
  
- TxPool
  - content
    - METHOD
      ```java
      content();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : {"pending":{},"queued":{}},
          "error" : ""
      }
      ```
  - inspect
    - METHOD
      ```java
      inspect();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : {"pending":{},"queued":{}},
          "error" : ""
      }
      ```
  - status
    - METHOD
      ```java
      status();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : {"pending":"0x0","queued":"0x0"},
          "error" : ""
      }
      ```
#### Platform
- Klay
  - protocolVersion
    - METHOD
      ```java
      protocolVersion();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0x40",
          "error" : ""
      }
      ```
    
  - syncing
    - METHOD
      ```java
      syncing();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : true,
          "error" : ""
      }
      ```
    
  - mining
    - METHOD
      ```java
      mining();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : true,
          "error" : ""
      }
      ```
    
  - gasPrice
    - METHOD
      ```java
      gasPrice();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0x5d21dba00",
          "error" : ""
      }
      ```
    
  - accounts
    - METHOD
      ```java
      accounts();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : ["0x95611200d726defd14cd1e3b29...","0xc72dc21310892573e98588f...","0x9ba0d03bc6db4c097bfdce86065a8...","0x45f2348ae2f97097c93ad614649c91...",.......],
          "error" : ""
      }
      ```
    
  - isContractAccount
    - METHOD
      ```
      isContractAccount(String accountAddress,String blockParameter);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : true,
          "error" : ""
      }
      ```
    
  - blockNumber
    - METHOD
      ```java
      blockNumber();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0xf9c7d",
          "error" : ""
      }
      ```
    
  - getBalance
    - METHOD
      ```java
      getBalance(String address, String blockParameter);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0x0",
          "error" : ""
      }
      ```
    
  - getStorageAt
    - METHOD
      ```java
      getStorageAt(String contractAddress, String position, String blockParameter);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0x0000000000000000000000000000000000000000000000000000000000000000",
          "error" : ""
      }
      ```
    
  - getTransactionCount
    - METHOD
      ```java
      getTransactionCount(String accountAddress, String blockParameter);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0x0",
          "error" : ""
      }
      ```
    
  - getBlockTransactionCountByHash
    - METHOD
      ```java
      getBlockTransactionCountByHash(String blockHash);
      ```
    - RESPONSE
      ```
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0x0",
          "error" : ""
      }
      ```
    
  - getBlockTransactionCountByNumber
    - METHOD
      ```java
      getBlockTransactionCountByNumber(String blockParameter);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0x0",
          "error" : ""
      }
      ```
    
  - getCode
    - METHOD
      ```java
      getCode(String address, String blockParameter);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0x",
          "error" : ""
      }
      ```
    
  - sign
    - METHOD
      ```java
      sign(String accountAddress, String message);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0xdab522b37b9fdf6cf5b3949499850c54a4c118291d56dc027dbf8988af3c65f120821235c897a3c75770d74bef8a2a0793d748016cde9e64d978eca8cfcb789e1b",
          "error" : ""
      }
      ```
    
  - sendTransaction
    - METHOD
      ```java
      sendTransaction(String fromAddress,String toAddress,String gas,String gasPrice,String value,String data,String nonce);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331",
          "error" : ""
      }
      ```
    
  - sendRawTransaction
    - METHOD
      ```java
      sendRawTransaction(String transaction);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331",
          "error" : ""
      }
      ```
    
  - call
    - METHOD
      ```java
      call(String fromAddress, String toAddress, String gas, String gasPrice, String value, String data,String blockParameter);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0x000000000000000000000000000000000000000000000000000000000000000a",
          "error" : ""
      }
      ```
    
  - estimateGas
    - METHOD
      ```java
      estimateGas(String fromAddress,String toAddress,String gas,String gasPrice,String value,String data,String blockParameter);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0x5208",
          "error" : ""
      }
      ```
    
  - getBlockHash
    - METHOD
      ```java
      getBlockByHash(String blockHash, boolean returnTx);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : {"blockscore":"0x1","extraData":"0xd7820801846b6c617988676f312e31312e35856c696e75780000000000000000f90164f85494180156236a7e54f3a6012d23cb3f328504763a3b9430584ed709b49b59be65bfc42820ca5235bc79ad948d729be6296b2e2ae5206dbeaa836fd11c88386094e3421757692b368972309cbae3eb7cce73b0a965b841c5b426993dd99880c34fc2224a76c6fe3371f3b356e2789c6db37648a6b0f56020fc823b76642e7c423b74ae6ece5c045688d4429aa64a7646bbfd04b1bafaa700f8c9b841b31cc856cf9dec3b300da66d8d156b391b02d3512ea50e0cb2b1cabdc8dba9854d23a1d04fe6f088d984216dccc77938f9438bc5453f6edbfeb15ae2cf38891201b84105b1e7d66f9c638e712700883c9b5239f4e348d9fe24af9b26a5fcab168e7e0e72e792bdba00b09ba6a8020f7a1dbe879984c5bd5018a5600eec2d7c4958c7ea00b84112edf861b2de3a7844f27ea814fa7a4bbf5b79e11cd4ceae22dded37c96c8d0422a032f170d121f558727d33ec7c61c0740cb751d1e4244a26f5dac279230c4000","gasUsed":"0x0","governanceData":"0x","hash":"0x0525598b412ce918655a3be76054c464e0df76b17125fbb9289850e2916aeca5","logsBloom":"0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000","number":"0xf8a50","parentHash":"0xe86d128ec6a08cded4115208c26d6388b22af84aa3a73a1cdadd784574f4fc95","receiptsRoot":"0xc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470","reward":"0xd495668548f8724ded2a54c2ac17febbd5506867","size":"0x33b","stateRoot":"0xf54034e3a00d6f7f74ddda716407d24de02a998bd88b93783b6feabe05231ce7","timestamp":"0x5ce3bc70","timestampFoS":"0x2","totalBlockScore":"0xf8a51","transactions":[],"transactionsRoot":"0xc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470","voteData":"0x"},
          "error" : ""
      }
      ```
    
  - getBlockByNumber
    - METHOD
      ```java
      getBlockByNumber(String blockParameter, boolean returnTx);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : {"blockscore":"0x1","extraData":"0xd7820801846b6c617988676f312e31312e35856c696e75780000000000000000f90164f85494180156236a7e54f3a6012d23cb3f328504763a3b9430584ed709b49b59be65bfc42820ca5235bc79ad948d729be6296b2e2ae5206dbeaa836fd11c88386094e3421757692b368972309cbae3eb7cce73b0a965b841c5b426993dd99880c34fc2224a76c6fe3371f3b356e2789c6db37648a6b0f56020fc823b76642e7c423b74ae6ece5c045688d4429aa64a7646bbfd04b1bafaa700f8c9b841b31cc856cf9dec3b300da66d8d156b391b02d3512ea50e0cb2b1cabdc8dba9854d23a1d04fe6f088d984216dccc77938f9438bc5453f6edbfeb15ae2cf38891201b84105b1e7d66f9c638e712700883c9b5239f4e348d9fe24af9b26a5fcab168e7e0e72e792bdba00b09ba6a8020f7a1dbe879984c5bd5018a5600eec2d7c4958c7ea00b84112edf861b2de3a7844f27ea814fa7a4bbf5b79e11cd4ceae22dded37c96c8d0422a032f170d121f558727d33ec7c61c0740cb751d1e4244a26f5dac279230c4000","gasUsed":"0x0","governanceData":"0x","hash":"0x0525598b412ce918655a3be76054c464e0df76b17125fbb9289850e2916aeca5","logsBloom":"0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000","number":"0xf8a50","parentHash":"0xe86d128ec6a08cded4115208c26d6388b22af84aa3a73a1cdadd784574f4fc95","receiptsRoot":"0xc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470","reward":"0xd495668548f8724ded2a54c2ac17febbd5506867","size":"0x33b","stateRoot":"0xf54034e3a00d6f7f74ddda716407d24de02a998bd88b93783b6feabe05231ce7","timestamp":"0x5ce3bc70","timestampFoS":"0x2","totalBlockScore":"0xf8a51","transactions":[],"transactionsRoot":"0xc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470","voteData":"0x"},
          "error" : ""
      }
      ```
    
  - getTransactionByHash
    - METHOD
      ```java
      getTransactionByHash(String transactionHash);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : {"blockHash":"0x7d4a2765264d7c0f3485007784e6858d157a1acc26240cd835ab7d73a1372fe3","blockNumber":"0xf97b6","from":"0xf90675a56a03f836204d66c0f923e00500ddc90a","gas":"0x419ce0","gasPrice":"0x5d21dba00","hash":"0xa35b6fd6c9510acd872dce722a87edc6a721a8312b16fc9c2a8596ccbd7d96bb","nonce":"0x5f","signatures":[{"V":"0x7f2","R":"0xef5f20f27dc3a20d72c9f809193dc8b6ad6144c11299e2b64bfc278a6f91d2a5","S":"0x4e0cb7b42e126264349c4ee82c1d9f1d1ca4a17012f19a205b3f6653468ff18d"}],"to":"0x837e29de27943e60d93b8b7e69d82c0f671f28e9","transactionIndex":"0x0","type":"TxTypeValueTransfer","typeInt":8.0,"value":"0x4563918244f40000"},
          "error" : ""
      }
      ```
    
  - getTransactionByBlockHashAndIndex
    - METHOD
      ```java
      getTransactionByBlockHashAndIndex(String blockHash, String position);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : {"blockHash":"0x0591ceb74102fc4ed54b66d27e869224d481e9f44170b25ed4a5654675043198","blockNumber":"0x27","from":"0xe02837b9d671e0848e599c374416f383f8910e45","gas":"0xf4240","gasPrice":"0x5d21dba00","hash":"0x451cafae98d61b7458b5cef54402830941432278184453e3ca490eb687317e68","input":"0x","nonce":"0x1","senderTxHash":"0x451cafae98d61b7458b5cef54402830941432278184453e3ca490eb687317e68","signatures":[{"V":"0xfea","R":"0x1924d0f36e05d368a37b8130b85067f21f0ea1d35b87bf137216cdc3c844c762","S":"0x31d61be4d5cf677e60ad0fa0214e75c3167509c8d8905d7c6f85979b5f32eead"}],"to":"0x44d827f98430784c8e3401748d8ba92c43df4546","transactionIndex":"0x0","type":"TxTypeLegacyTransaction","typeInt":0,"value":"0xde0b6b3a7640000"},
          "error" : ""
      }
      ```
    
  - getTransactionByBlockNumberAndIndex
    - METHOD
      ```java
      getTransactionByBlockNumberAndIndex(String blockParameter, String position);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : {"blockHash":"0x0591ceb74102fc4ed54b66d27e869224d481e9f44170b25ed4a5654675043198","blockNumber":"0x27","from":"0xe02837b9d671e0848e599c374416f383f8910e45","gas":"0xf4240","gasPrice":"0x5d21dba00","hash":"0x451cafae98d61b7458b5cef54402830941432278184453e3ca490eb687317e68","input":"0x","nonce":"0x1","senderTxHash":"0x451cafae98d61b7458b5cef54402830941432278184453e3ca490eb687317e68","signatures":[{"V":"0xfea","R":"0x1924d0f36e05d368a37b8130b85067f21f0ea1d35b87bf137216cdc3c844c762","S":"0x31d61be4d5cf677e60ad0fa0214e75c3167509c8d8905d7c6f85979b5f32eead"}],"to":"0x44d827f98430784c8e3401748d8ba92c43df4546","transactionIndex":"0x0","type":"TxTypeLegacyTransaction","typeInt":0,"value":"0xde0b6b3a7640000"},
          "error" : ""
      }
      ```
    
  - getTransactionReceipt
    - METHOD
      ```java
      getTransactionReceipt(String transactionHash);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : {"blockHash":"0xedd32828b677dd4f4cb169e770154b7337d98b25e48d74e9aad2401c9e76d732","blockNumber":"0xf9632","from":"0xf90675a56a03f836204d66c0f923e00500ddc90a","gas":"0x419ce0","gasPrice":"0x5d21dba00","gasUsed":"0x5208","logs":[],"logsBloom":"0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000","nonce":"0x5c","signatures":[{"V":"0x7f1","R":"0x86da1424510b55e7a98d2f26ace6658903a6055ba851e5a406b609da2d1f6e5c","S":"0x16db3b73c0cb78ff23a49afcbcfdbc100c93a641fb528e26c922d2f609bbb482"}],"status":"0x1","to":"0x837e29de27943e60d93b8b7e69d82c0f671f28e9","transactionHash":"0x7f25bb9d1fc33fd1f2b8b6c7595673f8fb5526d6edf7b36564769714e204adfa","transactionIndex":"0x0","type":"TxTypeValueTransfer","typeInt":8.0,"value":"0x4563918244f40000"},
          "error" : ""
      }
      ```
    
  - newFilter
    - METHOD
      ```java
      newFilter(String fromBlock, String toBlock, String[] address, String[] topics);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0x46192a84e834bfbff14754b201b2bc2e",
          "error" : ""
      }
      ```
    
  - newBlockFilter
    - METHOD
      ```java
      newBlockFilter();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0xdfd6eab415288a04af556d3c530967a4",
          "error" : ""
      }
      ```
    
  - newPendingTransactionFilter
    - METHOD
      ```java
      newPendingTransactionFilter();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0x2cb8b47061665c80937a8d319b0c70e8",
          "error" : ""
      }
      ```
    
  - uninstallFilter
    - METHOD
      ```java
      uninstallFilter(String filter);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : true,
          "error" : ""
      }
      ```
    
  - getFilterChanges
    - METHOD
      ```java
      getFilterChanges(String filterId);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result": [{"logIndex":"0x1","blockNumber":"0x1b4","blockHash":"0x8216c5785ac562ff41e2dcfdf5785ac562ff41e2dcfdf829c5a142f1fccd7d","transactionHash":"0xdf829c5a142f1fccd7d8216c5785ac562ff41e2dcfdf5785ac562ff41e2dcf","transactionIndex":"0x0","address":"0x16c5785ac562ff41e2dcfdf829c5a142f1fccd7d","data":"0x0000000000000000000000000000000000000000000000000000000000000000","topics":["0x59ebeb90bc63057b6515673c3ecf9438e5058bca0f92585014eced636878c9a5"]}],[....],
          "error" : ""
      }
      ```
    
  - getFilterLogs
    - METHOD
      ```java
      getFilterLogs(String filterId);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result": [{"address":"0x87ac99835e67168d4f9a40580f8f5c33550ba88b","topics":["0xd596fdad182d29130ce218f4c1590c4b5ede105bee36690727baa6592bd2bfc8"],"data":"0x0000000000000000000000000000000000000000000000000000000000000064000000000000000000000000000000000000000000000000000000000000007b","blockNumber":"0x54","transactionHash":"0xcd4703cd62bd930d4652999bce8dcb75b7ade49d922fa42dc11e568c52a5fa6f","transactionIndex":"0x0","blockHash":"0x9a49f30f1d1876ff3913bd0aa58f328822e7a369cb13e0640b82234f26e781bb","logIndex":"0x0","removed":false}],
          "error" : ""
      }
      ```
    
  - getLogs
    - METHOD
      ```java
      getLogs(String fromBlock, String toBlock, String[] address, String[] topics, String blockHash);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result": [{"address":"0x87ac99835e67168d4f9a40580f8f5c33550ba88b","topics":["0xfa9b2165fc71c1d6ffa03291c7f5d223ea363ec063d747eec9ce2d30d24855ef"],"data":"0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000060000000000000000000000000d3564e57bb5c6f4d983a493a946534f8e1e8b481000000000000000000000000000000000000000000000000000000000000001341646472657373426f6f6b436f6e747261637400000000000000000000000000","blockNumber":"0xd3b5","transactionHash":"0x57ca8ff0a0d454d4c5418694c21bc4ef3de26cf7cd18dd404d6a7189a826bfe0","transactionIndex":"0x0","blockHash":"0x279251a907c6ab1fb723595511ff401432e7c2437d54189298f53a7d33ce3a60","logIndex":"0x0","removed":false},{"address":"0x87ac99835e67168d4f9a40580f8f5c33550ba88b","topics":["0xfa3e1e272694072320aad73a3fadd8876c4bf8f40899c6c7ce2fda9f4e652cfa"],"data":"0x00000000000000000000000000000000000000000000000000000000000000400000000000000000000000000000000000000000000000000000000000000003000000000000000000000000000000000000000000000000000000000000000300000000000000000000000041383b6ee0ea5108d6b139165a9c85351aacd39800000000000000000000000057f7439898e652fa9b5654022297588532e5e0370000000000000000000000005b5b7a718a4124eb746ae00b1ce6edcaa5ab55bc","blockNumber":"0xd3b5","transactionHash":"0x57ca8ff0a0d454d4c5418694c21bc4ef3de26cf7cd18dd404d6a7189a826bfe0","transactionIndex":"0x0","blockHash":"0x279251a907c6ab1fb723595511ff401432e7c2437d54189298f53a7d33ce3a60","logIndex":"0x1","removed":false}],
          "error" : ""
      }
      ```
    
  - getWork
    - METHOD
      ```java
      getWork();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : ["0x412e8160300ad5435dc8b3630478fe223092e4ff5577e2b3441294d74b9ed7b4","0xd8787a325c6123d1085f6e48196bec7a189c90cb5bb00eb10e6e64bd7cb53c29","0x0000000000000000000000000000000000000000000000000000000000000000"],
          "error" : ""
      }
      ```
    
  - getBlockWithConsensusInfoByHash
    - METHOD
      ```java
      getBlockWithConsensusInfoByHash(String blockHash);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : {"blockscore":"0x1","committee":["0x180156236a7e54f3a6012d23cb3f328504763a3b","0x30584ed709b49b59be65bfc42820ca5235bc79ad","0x8d729be6296b2e2ae5206dbeaa836fd11c883860","0xe3421757692b368972309cbae3eb7cce73b0a965"],"extraData":"0xd7820801846b6c617988676f312e31312e35856c696e75780000000000000000f90164f85494180156236a7e54f3a6012d23cb3f328504763a3b9430584ed709b49b59be65bfc42820ca5235bc79ad948d729be6296b2e2ae5206dbeaa836fd11c88386094e3421757692b368972309cbae3eb7cce73b0a965b8410314dfa0e466f280b3bdd4cb777e8855ac4874de085814db72836f2da23e135c53bd7f34574dca806e1748459de99531b04918a603d6f34fcf5aaa7682e3565a00f8c9b8418801b17e9b59809ff7b73c8b7acb0d85f1a7a2b49fba07ae1deedceeea34cbce55fd1e899c52c599cf772a159516fc688f1682371b43ceec2c47373ee67c824500b841d718de92d40f01116833448eb3f1059029f6da02ea4f5aa1253d70c792311bac656dabcff23c5fb333f2d6e61b66bec38c7409bd2d57ec1ccd5db3660329e72400b841b731f507ffb187e69b5dc88d122000ea9f3eaf3d58401c8f867534681e5420d13402d30ab5c1308f94d85757ad016cf23e2530655d374b82b90a74406a85c3fe00","gasUsed":"0x0","governanceData":"0x","hash":"0x97407ddbe16cd50a91e5c83f0ff1ba86cdf7883b51aab59b2ab8e62726f6b563","logsBloom":"0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000","number":"0xfbff8","parentHash":"0x2d7d8719f70e1ef5de462bc684a9ecb1d4e003198d4039a991890486eccf1a64","proposer":"0x30584ed709b49b59be65bfc42820ca5235bc79ad","receiptsRoot":"0xc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470","reward":"0x778195b13cf1d21e284de1aae22aed7033d1a5a9","size":"0x33b","stateRoot":"0x1ea29f7d207511970d1c47ac4ef3a432702eec19253a9c53c15301a06b5391d3","timestamp":"0x5ce3f218","timestampFoS":"0x2","totalBlockScore":"0xfbff9","transactions":[],"transactionsRoot":"0xc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470","voteData":"0x"},
          "error" : ""
      }
      ```
    
  - getBlockWithConsensusInfoByNumber
    - METHOD
      ```java
      getBlockWithConsensusInfoByNumber(String blockParameter);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : {"blockscore":"0x1","committee":["0x180156236a7e54f3a6012d23cb3f328504763a3b","0x30584ed709b49b59be65bfc42820ca5235bc79ad","0x8d729be6296b2e2ae5206dbeaa836fd11c883860","0xe3421757692b368972309cbae3eb7cce73b0a965"],"extraData":"0xd7820801846b6c617988676f312e31312e35856c696e75780000000000000000f90164f85494180156236a7e54f3a6012d23cb3f328504763a3b9430584ed709b49b59be65bfc42820ca5235bc79ad948d729be6296b2e2ae5206dbeaa836fd11c88386094e3421757692b368972309cbae3eb7cce73b0a965b8411cd0cfbecfda9ec852b311c480e4907d63bfe08a784fb3b5c10fe5fa628f6d3a05b668d4cc4916cb08c1a5771a6882d5eed01d390cdbac402910ed138d63699301f8c9b841774ba9f8fcb10d8c6c4e8585d68d7e683bc9c777529624055970555205d17ab00fb2c83039f4b8fbeec15fc0784ca494a64e06255813e21a2dc853ceea6b4d1700b84168de0a9394545a59ae670b3cab6194f5b67d53fe4e84068ddbde23757a780c5538b74051dc1f1b2260035c92e57dc897a1ff64cc9b7120668c24223a94db1ecc00b841b13ab52a861b3ac319c8c61639d2d343ddbb61073d8896dfd8883ec76d9da9b1247f61816942f5fc5ebb8e59d7c400423a370342d92777f5e974bcec5f94924100","gasUsed":"0x0","governanceData":"0x","hash":"0x741642d3c88725b883b51848b23bca0fece9964236841418024cb12f1fb33526","logsBloom":"0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000","number":"0xfc060","parentHash":"0x7720f336de05834a305f1fdbc5f5cb2dc34b566fc42839800f933ce2a32d8c18","proposer":"0xe3421757692b368972309cbae3eb7cce73b0a965","receiptsRoot":"0xc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470","reward":"0xd495668548f8724ded2a54c2ac17febbd5506867","size":"0x33b","stateRoot":"0xfc0fc7e6c57f4043df0c1fadc9a8957dc2f081d409a4c06f12ae52ec3f82e16a","timestamp":"0x5ce3f280","timestampFoS":"0x2","totalBlockScore":"0xfc061","transactions":[],"transactionsRoot":"0xc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470","voteData":"0x"},
          "error" : ""
      }
      ```
    
  - chainID
    - METHOD
      ```
      chainID();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0x3e7",
          "error" : ""
      }
      ```
    
  - rewardbase
    - METHOD
      ```java
      rewardbase();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0x6f19c2f3c9694612a5c3e9f4341c243a26687110",
          "error" : ""
      }
      ```
    
  - isParallelDBWrite
    - METHOD
      ```
      isParallelDBWrite();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : true,
          "error" : ""
      }
      ```
    
  - writeThroughCaching
    - METHOD
      ```java
      writeThroughCaching();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : true,
          "error" : ""
      }
      ```
    
  - signTransaction
    - METHOD
      ```java
      signTransaction(String from,String to,String gas,String gasPrice,String value,String data,String nonce);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : {"raw":"0xf86c0286025000000000840100000094cd6bfdb523a4d030890d28bf1eb6ef36307c9aaa8301000080820fe8a056d2ddd231c3c111687ab351d339240db18cd721e5aa33c601dd4fc3927fb4d1a03443443392517aa7da082aa0a00b9ee5e3e1ee007d22e57cd9ff55b5ddbf4a64","tx":{"nonce":"0x2","gasPrice":"0x25000000000","gas":"0x1000000","to":"0xcd6bfdb523a4d030890d28bf1eb6ef36307c9aaa","value":"0x10000","input":"0x","v":"0xfe8","r":"0x56d2ddd231c3c111687ab351d339240db18cd721e5aa33c601dd4fc3927fb4d1","s":"0x3443443392517aa7da082aa0a00b9ee5e3e1ee007d22e57cd9ff55b5ddbf4a64","hash":"0xb53cc9128a19c3916c0de1914725b7337bba84666c2556d8682c72ca34c6874c"}},
          "error" : ""
      }
      ```
    
  - getBlockReceipts
    - METHOD
      ```java
      getBlockReceipts(String blockHash);
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : [{"blockHash":"0xdc762ed0274496e2a42278e2648d910d82468687b5415bb5eb058a96a0b93c30","blockNumber":"0x3ba38","contractAddress":null,"from":"0x16b11cf9c2186a117b0da38315b42b1eaa03bbe5","gas":"0x30d40","gasPrice":"0x5d21dba00","gasUsed":"0x1886c","input":"0x772e5c61000000000000000000000000bf756e27c5342a1249aa4475866e9e4d7eef9b2e0000000000000000000000000000000000000000000000004563918244f40000","logs":[{"address":"0xdbb98c72e9818ad2c93a09e35ad43ada0d4223f0","topics":["0x011e8596961d1f358aff58fc5c89276f9b1f554536b7bfe20139bbc1f230e693"],"data":"0x000000000000000000000000bf756e27c5342a1249aa4475866e9e4d7eef9b2e0000000000000000000000000000000000000000000000004563918244f40000000000000000000000000000000000000000000000000000000000000003ba380000000000000000000000000000000000000000000000000000000000050bb8"}]}],
          "error" : ""
      }
      ```
      
  - accountCreated
      - METHOD
        ```java
        accountCreated(String accountAddress,String blockParameter);
        ```
        
      - RESPONSE
        ```json
        {
          "id": 1,
          "jsonrpc": "2.0",
          "result": true,
          "error" : ""
        }
        ```
    
  - getAccount
    - METHOD
      
      ```java
      getAccount(String accountAddress,String blockParameter);
      ```
      
    - RESPONSE
    
      ```json
      {
        "id": 1,
        "jsonrpc": "2.0",
        "result": {
          "accType": 2,
          "account": {
            "balance": "0x0",
            "codeFormat": 0,
            "codeHash": "80NXvdOay02rYC/JgQ7RfF7yoxY1N7W8P7BiPvkIeF8=",
            "key": {
              "key": {},
              "keyType": 3
            },
            "nonce": 1,
            "storageRoot": "0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421"
          }
        }
      }
      ```
    
  - getAccountKey
    - METHOD
      ```
      getAccountKey(String accountAddress,String blockParameter);
      ```
  
    - RESPONSE
  
      ```json
      {
        "jsonrpc": "2.0",
        "id":1,
        "result": {
          key: {
            x: "0x230037a99462acd829f317d0ce5c8e2321ac2951de1c1b1a18f9af5cff66f0d7",
            y: "0x18a7fb1b9012d2ac87bc291cbf1b3b2339356f1ce7669ae68405389be7f8b3b6"
          },
          keyType: 2
        },
        "error" : ""
      }
      ```
  
  - getCommittee
  
    - METHOD
  
      ```java
      getCommittee(String blockParameter);
      ```
  
    - RESPONSE
  
      ```json
      {
          "jsonrpc":"2.0",
          "id":73,
          "result":[
              "0x207e38864b45a538733741dc1ff92eff9d1a6159",
              "0x6d64bc82b93368a7f963d6c34483ca3893f405f6",
              "0xbc9c19f91878369776812039e4ebcdfa3c646716",
              "0xe3ed6fa287752b992f936b42360770c59731d9eb"
          ],
          "error" : ""
      }
      ```
  
  - getCommitteeSize
  
    - METHOD
  
      ```java
      getCommitteeSize(String blockParameter);
      ```
  
    - RESPONSE
  
      ```json
      {
          "jsonrpc":"2.0",
          "id":73,
          "result":4,
          "error" : ""
      }
      ```
  
  - getCouncil
  
    - METHOD
  
      ```
      getCouncil(String blockParameter);
      ```
  
    - RESPONSE
  
      ```json
      {
          "jsonrpc":"2.0",
          "id":73,
          "result":[
              "0x207e38864b45a538733741dc1ff92eff9d1a6159",
              "0x6d64bc82b93368a7f963d6c34483ca3893f405f6",
              "0xbc9c19f91878369776812039e4ebcdfa3c646716",
              "0xe3ed6fa287752b992f936b42360770c59731d9eb"
          ],
          "error" : ""
      }
      ```
  
  - getCouncilSize
  
    - METHOD
  
      ```java
      getCouncilSize(String blockParameter);
      ```
  
    - RESPONSE
  
      ```json
      {
          "jsonrpc":"2.0",
          "id":73,
          "result": 4,
          "error" : ""
      }
      ```
  
  - gasPriceAt
  
    - METHOD
  
      ```java
      gasPriceAt(String blockParameter);
      ```
  
    - RESPONSE
  
      ```json
      {
        "jsonrpc": "2.0",
        "id":1,
        "result": "0x5d21dba00",
        "error" : ""
      }
      ```
  
  - isSenderTxHashIndexingEnabled
  
    - METHOD
  
      ```
      isSenderTxHashIndexingEnabled();
      ```
  
    - RESPONSE
  
      ```json
      {
          "jsonrpc":"2.0",
          "id":1,
          "result":true,
          "error" : ""
      }
      ```
  
  - sha3
  
    - METHOD
  
      ```java
      sha3(String sha3hash);
      ```
  
    - RESPONSE
  
      ```json
      {
        "jsonrpc":"2.0",
        "id":1,
        "result":"0x36712aa4d0dd2f64a9ae6ac09555133a157c74ddf7c079a70c33e8b4bf70dd73",
        "error" : ""
      }
      ```
  
  - getTransactionBySenderTxHash
    - METHOD
       ```java
       getTransactionBySenderTxHash(String transactionHash);
       ```
       
    - RESPONSE
      ```json
      {
        "jsonrpc":"2.0",
        "id":1,
        "result":"0x36712aa4d0dd2f64a9ae6ac09555133a157c74ddf7c079a70c33e8b4bf70dd73",
        "error" : ""
      }
      ```
  
  
- Net
  
  - networkID
    - METHOD
      ```java
      networkID();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "999.0",
          "error" : ""
      }
      ```
    
  - listening
    - METHOD
      ```java
      listening();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : true,
          "error" : ""
      }
      ```
    
  - peerCount
    - METHOD
      ```java
      peerCount();
      ```
    - RESPONSE
      ```json
      {
          "jsonrpc" : "2.0",
          "id" : 1,   
          "result" : "0x3",
          "error" : ""
      }
      ```
    
  - peerCountByType
  
    - METHOD
  
      ```java
      peerCountByType();
      ```
  
    - RESPONSE
  
      ```json
      {
          "jsonrpc": "2.0",
          "id":74,
          "result": {"en":3,"pn":2,"total":5},
          "error" : ""
      }
      ```
#### RpcModules
- rpcModules
  - METHOD
    ```java
    rpcModules();
    ```
  - RESPONSE
    ```json
    {
        "jsonrpc" : "2.0",
        "id" : 1,   
        "result" : {
          "admin":"1.0",
          "debug":"1.0",
          "klay":"1.0",
          "miner":"1.0",
          "net":"1.0",
          "personal":"1.0",
          "rpc":"1.0",
          "txpool":"1.0",
          "web3":"1.0"
       },
        "error" : ""
    }
    ```

---

### RPC RESPONSE ERROR
- RpcResponse 에러 정보 유무에 따라 핸들링 하시면 됩니다.
  - ApiUtils.java에서 처리 하는 방법 예시
    ```java
    public static ApiResponse onRpcResponse(RpcResponse rpcResponse) {
        if (ObjectUtil.isEmpty(rpcResponse)) {
            return onError(
                ApiResponseTarget.RPC,
                ApiResponseCode.EMPTY_DATA,
                ApiResponseCode.EMPTY_DATA.getMessage()
            );
        }
    
        if (ObjectUtil.isNotEmpty(rpcResponse.getError())) {
            return onError(
                ApiResponseTarget.RPC,
                ApiResponseCode.RPC_CALL_ERROR,
                rpcResponse.getError()
            );
        }
    
        return onSuccess(rpcResponse.getResult());
    }
    ```
