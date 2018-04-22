package com.mexc.vcoin.bitcoin;

import com.laile.esf.common.exception.ResultCode;
import com.laile.esf.common.exception.SystemException;
import com.mexc.common.util.ThressDescUtil;
import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;
import com.neemre.btcdcli4j.core.client.BtcdClientImpl;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.math.BigDecimal;
import java.util.Properties;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/1/5
 * Time: 下午5:02
 */
public class BitClientTest {


//    public static void main(String[] args) {
//        BtcdClientImpl client = init();
//        try {
////            Map<String, BigDecimal> account = client.listAccounts();
//////            System.out.println("=====>>>>>>account-->"+JSON.toJSONString(account));
////            Iterator iterator = account.keySet().iterator();
////            while (iterator.hasNext()) {
////                String key = (String) iterator.next();
////                System.out.println("key--->:" + key);
////                BigDecimal value = account.get(key);
////                System.out.println("value--->:" + value.toPlainString());
////            }
////            List<Address> addressList = client.listReceivedByAddress();
////            for (Address address : addressList) {
////                System.out.println("address--->"+JSON.toJSONString(address));
////            }
////            String bestBlockHash=client.getBestBlockHash();
//            boolean addressInfo=client.checkAddress("37KStcJFFrhTPxEuVZpzaBiphHLZogZzyr");
//            System.out.println(addressInfo);
////            client.walletPassphrase("3F4DN9QGqWrB4DCdfYMXp8xdDYL4HDFzpaS9r76DbNhw",30);
////            String address=client.getNewAddress();
////            client.setAccount(address,"AAA");
//        } catch (BitcoindException e) {
//            e.printStackTrace();
//        } catch (CommunicationException e) {
//            e.printStackTrace();
//        }
//
//        String  aa="curl --user user --data-binary '{\"jsonrpc\": \"1.0\", \"id\":\"curltest\", \"method\": \"getaccount\", \"params\": [] }' \n" +
//                "    -H 'content-type: text/plain;' http://127.0.0.1:8332/";
//    }

    public static BtcdClientImpl init() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        CloseableHttpClient httpProvider = HttpClients.custom().setConnectionManager(cm).build();
        Properties nodeConfig = new Properties();
        nodeConfig.setProperty("node.bitcoind.rpc.protocol", "http");
        nodeConfig.setProperty("node.bitcoind.rpc.host", "192.168.1.233");
        nodeConfig.setProperty("node.bitcoind.rpc.user", "mexc_user");
        nodeConfig.setProperty("node.bitcoind.rpc.password", "1q2w3e4r$1");
        //nodeConfig.setProperty("node.bitcoind.rpc.password", "3F4DN9QGqWrB4DCdfYMXp8xdDYL4HDFzpaS9r76DbNhw");
        nodeConfig.setProperty("node.bitcoind.notification.block.port", "192.168.1.233");
        nodeConfig.setProperty("node.bitcoind.notification.wallet.port", "5160");
        nodeConfig.setProperty("node.bitcoind.notification.alert.port", "5158");
        try {
            BtcdClientImpl btcdClient = new BtcdClientImpl(httpProvider, nodeConfig);
            return btcdClient;
        } catch (Exception e) {
            throw new SystemException(ResultCode.COMMON_ERROR, "链接比特币客户端异常");
        }
    }

}
