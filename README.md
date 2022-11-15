# Branch name will be changed

We will change the `master` branch to `main` on Dec 15, 2022.
After the branch policy change, please check your local or forked repository settings.

# Enterprise Proxy Lite

Compact version of Enterprise proxy that uses no DB connection. Please find more details in [Official documentation of EP.](https://docs.klaytn.com/klaytn/enterprise_proxy)

---

### EP ARCHITECTURE
![EP_ARCHITECTURE](./img/EP.png)

---

### GETTING STARTED
- maven compile
  - The EP-API is compiled into a WAR file for distribution.
  ```shell
  mvn clean package
  cd epi-api/target
  * ep-api-0.0.1-SNAPSHOT.war file will be created.
  ```
- application.properties (ep-api/src/main/resources)
  ```shell
  klaytn.rpc.server.host=<EN NODE RPC HOST>
  klaytn.rpc.server.port=<EN NODE RPC PORT>
  * The registered host is called by default if targetHost information is missing in the API.
  ```
  ```shell
  logging.level.root=log level (debug,info,error...)
  * The logging level is set as info by default.
  * You can change the level according to your dev or prod environment.
  ```
  ```shell
  feepayer.address=ENC(...)
  feepayer.password=ENC(...)
  feepayer.keyStoreFilePath=ENC(...)
  * You must create the delegated fee payer account in advance to use fee delegation functions.
  * The fee payer account information is designed to use Jasypt encryption for security reasons.
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
- tomcat
  - Set tomcat settings according to your dev or prod environments.
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

---

### API CALL
- Examples of Management / Admin / datadir calls
  ```http
  http://<EP_HOST>/management/admin/datadir
  ```
  ```json
  {
    "code": 0,
    "target": "api",
    "result": "SUCCESS",
    "data": "/data/kend/data"
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
  * Target can be one of these: api, rpc, transaction, router, fee delegated.
  ```

---


### License

Enterprise Proxy Lite is provided under Apache License version 2.0. See [LICENSE](./LICENSE) for more details.

---


### Contributing

As an open source project, Enterprise Proxy Lite is always welcoming your contribution. Please read our [CONTTIBUTING.md](./CONTRIBUTING.md) for a walk-through of the contribution process.
