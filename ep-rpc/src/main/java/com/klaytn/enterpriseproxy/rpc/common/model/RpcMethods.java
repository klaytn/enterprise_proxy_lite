/**
 * Copyright 2019 Enterprise Proxy Authors
 *
 * Licensed under the Apache License, Version 2.0 (the “License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an “AS IS” BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.klaytn.enterpriseproxy.rpc.common.model;

public enum RpcMethods {
    RPC_MODULES("rpc_modules"),


    ADMIN_SERVICENAME("admin"),
    ADMIN_ADDPEER("admin_addPeer"),
    ADMIN_REMOVEPEER("admin_removePeer"),
    ADMIN_DATADIR("admin_datadir"),
    ADMIN_NODEINFO("admin_nodeInfo"),
    ADMIN_PEERS("admin_peers"),
    ADMIN_STARTRPC("admin_startRPC"),
    ADMIN_STOPRPC("admin_stopRPC"),
    ADMIN_STARTWS("admin_startWS"),
    ADMIN_STOPWS("admin_stopWS"),
    ADMIN_IMPORTCHAIN("admin_importChain"),
    ADMIN_EXPORTCHAIN("admin_exportChain"),


    DEBUG_SERVICENAME("debug"),
    DEBUG_STARTPPROF("debug_startPProf"),
    DEBUG_STOPPPROF("debug_stopPProf"),
    DEBUG_ISPPROFRUNNING("debug_isPProfRunning"),
    DEBUG_GETMODIFIEDACCOUNTSBYHASH("debug_getModifiedAccountsByHash"),
    DEBUG_GETMODIFIEDACCOUNTSBYNUMBER("debug_getModifiedAccountsByNumber"),
    DEBUG_BACKTRACEAT("debug_backtraceAt"),
    DEBUG_BLOCKPROFILE("debug_blockProfile"),
    DEBUG_CPUPROFILE("debug_cpuProfile"),
    DEBUG_DUMPBLOCK("debug_dumpBlock"),
    DEBUG_GCSTATS("debug_gcStats"),
    DEBUG_GETBLOCKRLP("debug_getBlockRlp"),
    DEBUG_GOTRACE("debug_goTrace"),
    DEBUG_MEMSTATS("debug_memStats"),
    DEBUG_METRICS("debug_metrics"),
    DEBUG_PRINTBLOCK("debug_printBlock"),
    DEBUG_PREIMAGE("debug_preimage"),
    DEBUG_FREEOSMEMOEY("debug_freeOSMemory"),
    DEBUG_SETHEAD("debug_setHead"),
    DEBUG_SETBLOCKPROFILERATE("debug_setBlockProfileRate"),
    DEBUG_SETVMLOGTARGET("debug_setVMLogTarget"),
    DEBUG_STACKS("debug_stacks"),
    DEBUG_STARTCPUPROFILE("debug_startCPUProfile"),
    DEBUG_STOPCPUPROFILE("debug_stopCPUProfile"),
    DEBUG_STARTGOTRACE("debug_startGoTrace"),
    DEBUG_STOPGOTRACE("debug_stopGoTrace"),
    DEBUG_VERBOSITY("debug_verbosity"),
    DEBUG_VMODULE("debug_vmodule"),
    DEBUG_WRITEBLOCKPROFILE("debug_writeBlockProfile"),
    DEBUG_WRITEMEMPROFILE("debug_writeMemProfile"),
    DEBUG_SETGCPERCENT("debug_setGCPercent"),
    DEBUG_TRACEBLOCK("debug_traceBlock"),
    DEBUG_TRACEBLOCKBYNUMBER("debug_traceBlockByNumber"),
    DEBUG_TRACEBLOCKBYHASH("debug_traceBlockByHash"),
    DEBUG_TRACEBLOCKFROMFILE("debug_traceBlockFromFile"),
    DEBUG_TRACETRANSACTION("debug_traceTransaction"),
    DEBUG_TRACEBADBLOCK("debug_traceBadBlock"),
    DEBUG_STANDARDTRACEBADBLOCKTOFILE("debug_standardTraceBadBlockToFile"),
    DEBUG_STANDARDTRACEBLOCKTOFILE("debug_standardTraceBlockToFile"),


    PERSONAL_SERVICENAME("personal"),
    PERSONAL_IMPORTRAWKEY("personal_importRawKey"),
    PERSONAL_LISTACCOUNTS("personal_listAccounts"),
    PERSONAL_LOCKACCOUNT("personal_lockAccount"),
    PERSONAL_UNLOCKACCOUNT("personal_unlockAccount"),
    PERSONAL_SENDTRANSACTION("personal_sendTransaction"),
    PERSONAL_SIGN("personal_sign"),
    PERSONAL_ECRECOVER("personal_ecRecover"),


    TXPOOL_SERVICENAME("txpool"),
    TXPOOL_CONTENT("txpool_content"),
    TXPOOL_INSPECT("txpool_inspect"),
    TXPOOL_STATUS("txpool_status"),


    NET_SERVICENAME("net"),
    NET_NETWORKID("net_networkID"),
    NET_LISTENING("net_listening"),
    NET_PEERCOUNT("net_peerCount"),
    NET_PEERCOUNTBYTYPE("net_peerCountByType"),


    WEB3_SERVICENAME("web3"),
    WEB3_CLIENTVERSION("web3_clientVersion"),
    WEB3_SHA3("web3_sha3"),


    KLAY_SERVICENAME("klay"),
    KLAY_PROTOCOLVERSION("klay_protocolVersion"),
    KLAY_SYNCING("klay_syncing"),
    KLAY_MINING("klay_mining"),
    KLAY_GASPRICE("klay_gasPrice"),
    KLAY_GASPRICEAT("klay_gasPriceAt"),
    KLAY_ACCOUNTS("klay_accounts"),
    KLAY_GETACCOUNT("klay_getAccount"),
    KLAY_GETACCOUNTKEY("klay_getAccountKey"),
    KLAY_ACCOUNTCREATED("klay_accountCreated"),
    KLAY_BLOCKNUMBER("klay_blockNumber"),
    KLAY_CHAINID("klay_chainID"),
    KLAY_CLIENTVERSION("klay_clientVersion"),
    KLAY_REWARDBASE("klay_rewardbase"),
    KLAY_ISCONTRACTADDRESS("klay_isContractAccount"),
    KLAY_ISPARALLELDBWRITE("klay_isParallelDBWrite"),
    KLAY_ISSENDERTXHASHINDEXINGENABLED("klay_isSenderTxHashIndexingEnabled"),
    KLAY_WRITETHROUGHCACHING("klay_writeThroughCaching"),
    KLAY_GETBALANCE("klay_getBalance"),
    KLAY_GETSTORAGEAT("klay_getStorageAt"),
    KLAY_GETTRANSACTIONCOUNT("klay_getTransactionCount"),
    KLAY_GETTRANSACTIONCOUNTBYHASH("klay_getBlockTransactionCountByHash"),
    KLAY_GETTRANSACTIONCOUNTBYNUMBER("klay_getBlockTransactionCountByNumber"),
    KLAY_GETCODE("klay_getCode"),
    KLAY_SIGN("klay_sign"),
    KLAY_SIGNTRANSACTION("klay_signTransaction"),
    KLAY_SENDTRANSACTION("klay_sendTransaction"),
    KLAY_SENDRAWTRANSACTION("klay_sendRawTransaction"),
    KLAY_CALL("klay_call"),
    KLAY_ESTIMATEGAS("klay_estimateGas"),
    KLAY_GETBLOAKBYHASH("klay_getBlockByHash"),
    KLAY_GETBLOCKBYNUMBER("klay_getBlockByNumber"),
    KLAY_GETBLOCKRECEIPTS("klay_getBlockReceipts"),
    KLAY_SHA3("klay_sha3"),
    KLAY_GETTRANSACTIONBYHASH("klay_getTransactionByHash"),
    KLAY_GETTRANSACTIONBYSENDERTXHASH("klay_getTransactionBySenderTxHash"),
    KLAY_GETTRANSACTIONBYBLOCKHASHANDINDEX("klay_getTransactionByBlockHashAndIndex"),
    KLAY_GETTRANSACTIONBYBLOCKNUMBERANDINDEX("klay_getTransactionByBlockNumberAndIndex"),
    KLAY_GETTRANSACTIONRECEIPT("klay_getTransactionReceipt"),
    KLAY_GETTRANSACTIONRECEIPTBYSENDERTXHASH("klay_getTransactionReceiptBySenderTxHash"),
    KLAY_NEWFILTER("klay_newFilter"),
    KLAY_NEWBLOCKFILTER("klay_newBlockFilter"),
    KLAY_NEWPENDINGTRANSACTIONFILTER("klay_newPendingTransactionFilter"),
    KLAY_UNINSTALLFILTER("klay_uninstallFilter"),
    KLAY_GETFILTERCHANGES("klay_getFilterChanges"),
    KLAY_GETFILTERLOGS("klay_getFilterLogs"),
    KLAY_GETLOGS("klay_getLogs"),
    KLAY_GETWORK("klay_getWork"),
    KLAY_GETVALIDATORS("klay_getValidators"),
    KLAY_GETBLOCKWITHCONSENSUSINFOBYHASH("klay_getBlockWithConsensusInfoByHash"),
    KLAY_GETBLOCKWITHCONSENSUSINFOBYNUMBER("klay_getBlockWithConsensusInfoByNumber"),
    KLAY_GETCOMMITTEE("klay_getCommittee"),
    KLAY_GETCOMMITTEESIZE("klay_getCommitteeSize"),
    KLAY_GETCOUNCIL("klay_getCouncil"),
    KLAY_GETCOUNCILSIZE("klay_getCouncilSize");




    /**
     * METHOD NAME
     * @type string
     */
    private String value;


    private RpcMethods(String value) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }
}
