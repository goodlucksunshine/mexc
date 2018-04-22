package com.mexc.vcoin.eth;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.exception.ResultCode;
import com.laile.esf.common.exception.SystemException;
import com.mexc.common.util.ThressDescUtil;
import org.apache.commons.lang3.StringUtils;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.methods.response.BooleanResponse;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.parity.Parity;
import org.web3j.tx.Contract;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.fail;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2017/12/28
 * Time: 下午6:03
 */
public class SmartCallTest {
    //
//
    public static void main(String[] args) throws IOException, CipherException {
//        String path = "/Users/sunshine/Library/Application Support/io.parity.ethereum/keys/ethereum/UTC--2017-12-13T06-15-54.300855788Z--ded03e6482b07e3330872b3d24dfaade1689a2e0";
//        Credentials credentials = WalletUtils.loadCredentials("yg0110010", new File(path));
//        System.out.println(credentials.getEcKeyPair().getPublicKey());
//        System.out.println(credentials.getEcKeyPair().getPrivateKey());
//        String from = "0x0076f41D870540B47D835CC5C56F0f78835F55A9";
//        String to = "0xDEd03e6482B07E3330872b3D24dFAAde1689a2E0";
//        String contract = "0xE41d2489571d322189246DaFA5ebDe1F4699F498";
        String parityAddress = "http://192.168.1.64:8588/";
        Parity parity = Parity.build(new HttpService(parityAddress));
//        BigInteger gasprice = parity.ethGasPrice().send().getGasPrice();
//        Transaction transaction = new Transaction(from, BigInteger.ZERO, gasprice, Contract.GAS_LIMIT.multiply(gasprice), contract, BigInteger.valueOf(1000000000000000000L), null);
//        EthEstimateGas gas = parity.ethEstimateGas(transaction).send();
//        System.out.println(gas.getAmountUsed());
//        BooleanResponse booleanResponse=parity.parityTestPassword("0x68436d464e428308cc29af8247f25aa4df0f7aa6","OYq1h6QGv9Wvo9NLktb9ai2q").send();
//        System.out.println(booleanResponse.success());


//        PersonalUnlockAccount personalUnlockAccount=parity.personalUnlockAccount(from,"yg0110010").send();
//        System.out.println(personalUnlockAccount.accountUnlocked());
//        Function function = transfer(to, BigInteger.valueOf(1000000000000000000l));
//        String encodedFunction = FunctionEncoder.encode(function);
//        Web3j web3j = Web3j.build(new HttpService(parityAddress));
//        BigInteger gasprice = web3j.ethGasPrice().send().getGasPrice();
//        Transaction transaction = Transaction.createFunctionCallTransaction(from, BigInteger.valueOf(0), gasprice, BigInteger.valueOf(21000), contract, encodedFunction);
//        BigInteger gasUse=parity.ethEstimateGas(transaction).send().getAmountUsed();
//        BigInteger count=parity.ethGetTransactionCount(from,DefaultBlockParameterName.LATEST).send().getTransactionCount();
//        Transaction transaction1 = Transaction.createFunctionCallTransaction(from,count, gasprice, gasUse, contract, encodedFunction);
//        EthSendTransaction ethSendTransaction = web3j.ethSendTransaction(transaction1).send();
//        System.out.println(ethSendTransaction.getTransactionHash());

        System.out.println(ThressDescUtil.decodeAssetPwd("QRAqufInSWkvGzrvuEN7DaeBx43YQMDYWWgsWnLqfYQ=","CjMw8xni"));






//        NewAccountIdentifier identifier=parity.personalNewAccount("yg0110010").send();

//        System.out.println(parity.ethBlockNumber().send().getBlockNumber());
//        EthGetBlockTransactionCountByNumber count=parity.ethGetBlockTransactionCountByNumber(new DefaultBlockParameterNumber(4875614)).send();
//        System.out.println(count.getTransactionCount());
//        String hash=Numeric.toHexStringWithPrefix(BigInteger.valueOf(4875614));
//        System.out.println("hash-->"+hash);
//        System.out.println("hash-->"+JSON.toJSONString(null));
//        EthTransaction ethTransaction=parity.ethGetTransactionByBlockNumberAndIndex(new DefaultBlockParameterNumber(4875614), BigInteger.valueOf(0)).send();
//        System.out.println(JSON.toJSONString(ethTransaction.getTransaction()));
//        EthGetStorageAt storageAt=parity.ethGetStorageAt("0x0000aF15E7a2443EAB6Acfb070c486878C711dF6",BigInteger.ZERO,DefaultBlockParameterName.LATEST).send();
////        System.out.println(JSON.toJSON(storageAt));
//        Credentials credentials=WalletUtils.loadCredentials("yg0110010",new File(path));
//        System.out.println(credentials.getEcKeyPair().getPrivateKey());
//        System.out.println(credentials.getEcKeyPair().getPublicKey());
    }


    public static String tokenCode = "60606040526b033b2e3c9fd0803ce8000000600355341561001c57fe5b5b600354600060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055505b5b610b82806100746000396000f30060606040523615610097576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806306fdde0314610099578063095ea7b31461013257806318160ddd1461018957806323b872dd146101af578063313ce5671461022557806370a082311461025157806395d89b411461029b578063a9059cbb14610334578063dd62ed3e1461038b575bfe5b34156100a157fe5b6100a96103f4565b60405180806020018281038252838181518152602001915080519060200190808383600083146100f8575b8051825260208311156100f8576020820191506020810190506020830392506100d4565b505050905090810190601f1680156101245780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561013a57fe5b61016f600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803590602001909190505061042e565b604051808215151515815260200191505060405180910390f35b341561019157fe5b610199610521565b6040518082815260200191505060405180910390f35b34156101b757fe5b61020b600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff16906020019091908035906020019091905050610527565b604051808215151515815260200191505060405180910390f35b341561022d57fe5b610235610857565b604051808260ff1660ff16815260200191505060405180910390f35b341561025957fe5b610285600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190505061085c565b6040518082815260200191505060405180910390f35b34156102a357fe5b6102ab6108a6565b60405180806020018281038252838181518152602001915080519060200190808383600083146102fa575b8051825260208311156102fa576020820191506020810190506020830392506102d6565b505050905090810190601f1680156103265780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561033c57fe5b610371600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919080359060200190919050506108e0565b604051808215151515815260200191505060405180910390f35b341561039357fe5b6103de600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610ace565b6040518082815260200191505060405180910390f35b604060405190810160405280601181526020017f30782050726f746f636f6c20546f6b656e00000000000000000000000000000081525081565b600081600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925846040518082815260200191505060405180910390a3600190505b92915050565b60035481565b60006000600160008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054905082600060008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054101580156105f95750828110155b80156106855750600060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205483600060008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020540110155b156108455782600060008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254019250508190555082600060008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825403925050819055507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8110156107d75782600160008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825403925050819055505b8373ffffffffffffffffffffffffffffffffffffffff168573ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef856040518082815260200191505060405180910390a36001915061084f565b6000915061084f565b5b509392505050565b601281565b6000600060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205490505b919050565b604060405190810160405280600381526020017f5a5258000000000000000000000000000000000000000000000000000000000081525081565b600081600060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054101580156109b15750600060008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205482600060008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020540110155b15610abe5781600060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254039250508190555081600060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055508273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef846040518082815260200191505060405180910390a360019050610ac8565b60009050610ac8565b5b92915050565b6000600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205490505b929150505600a165627a7a72305820d31ec12b6fe4e3204b5bdd3a3cafc924f0101471e7aa3e9460b81496e9067ec70029";
    public static String zrx = "0xDEd03e6482B07E3330872b3D24dFAAde1689a2E0";
    public static String contractAddress = "0xE41d2489571d322189246DaFA5ebDe1F4699F498";
    public static String ZRX_FROM = "0xDEd03e6482B07E3330872b3D24dFAAde1689a2E0";
    public static String ZRX_TO = "0x0076f41D870540B47D835CC5C56F0f78835F55A9";
    //    static final BigInteger GAS_PRICE = BigInteger.valueOf(22_000_000_000L);
//    static final BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);
    private static final int SLEEP_DURATION = 15000;
    private static final int ATTEMPTS = 40;

//
//    public static BigInteger getGasPrice1() {
//        EthGasPrice ethGasPrice = null;
//        try {
//            ethGasPrice = Web3JClient.getClient().ethGasPrice().send();
//            return ethGasPrice.getGasPrice();
//        } catch (IOException e) {
//            throw new SystemException(ResultCode.COMMON_ERROR, "查询钱包余额异常");
//        }
//    }
////
//    public static void main(String[] args) throws Exception {
//        String path = "/Users/sunshine/Library/Application Support/io.parity.ethereum/keys/ethereum/UTC--2017-12-13T06-15-54.300855788Z--ded03e6482b07e3330872b3d24dfaade1689a2e0";
//        Credentials credentials = WalletUtils.loadCredentials("yg0110010", new File(path));
//        getTotalSupply(contractAddress);
//        BigInteger aliceQty = BigInteger.valueOf(1);
//        confirmBalance(ZRX_FROM,contractAddress,aliceQty);
//        BigInteger aliceQty = BigInteger.valueOf(1_000_000);
//            createContract(credentials,aliceQty);
//        transZrx();
//        System.out.println(Convert.fromWei("94600000000000000", Convert.Unit.ETHER));
//        Web3JClient.getClient().ethEstimateGas()
//    }
////
//    public static String createContract(
//            Credentials credentials, BigInteger initialSupply) throws Exception {
//        String createTransactionHash = sendCreateContractTransaction(credentials, initialSupply);
//        assertFalse(createTransactionHash.isEmpty());
//
//        TransactionReceipt createTransactionReceipt =
//                waitForTransactionReceipt(createTransactionHash);
//
//        assertThat(createTransactionReceipt.getTransactionHash(), is(createTransactionHash));
//
//        assertFalse("Contract execution ran out of gas",
//                createTransactionReceipt.getGasUsed().equals(BigInteger.ZERO));
//
//        String contractAddress = createTransactionReceipt.getContractAddress();
//
//        assertNotNull(contractAddress);
//        return contractAddress;
//    }
//
//    public static String sendCreateContractTransaction(
//            Credentials credentials, BigInteger initialSupply) throws Exception {
//        BigInteger nonce = getNonce(credentials.getAddress());
//
//        String encodedConstructor =
//                FunctionEncoder.encodeConstructor(
//                        Arrays.asList(
//                                new Uint256(initialSupply),
//                                new Utf8String("web3j tokens"),
//                                new Uint8(BigInteger.TEN),
//                                new Utf8String("w3j$")));
//
//        RawTransaction rawTransaction = RawTransaction.createContractTransaction(
//                nonce,
//                BigInteger.ZERO,
//                BigInteger.ZERO,
//                BigInteger.ZERO,
//                getHumanStandardTokenBinary() + encodedConstructor);
//
//        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
//        String hexValue = Numeric.toHexString(signedMessage);
//
//        EthSendTransaction transactionResponse = Web3JClient.getClient().ethSendRawTransaction(hexValue)
//                .sendAsync().get();
//
//        return transactionResponse.getTransactionHash();
//    }
//
//    public static String getHumanStandardTokenBinary() throws Exception {
//        return load("/solidity/contracts/build/HumanStandardToken.bin");
//    }
//
//    public static String load(String filePath) throws URISyntaxException, IOException {
//        byte[] bytes = tokenCode.getBytes();
//        return new String(bytes);
//    }
////
////
//    public static BigInteger getTotalSupply(String contractAddress) throws Exception {
//        Function function = totalSupply();
//        String responseValue = callSmartContractFunction(function, contractAddress);
//
//        List<Type> response = FunctionReturnDecoder.decode(
//                responseValue, function.getOutputParameters());
//
//        assertThat(response.size(), is(1));
//        return (BigInteger) response.get(0).getValue();
//    }
//
//
//    public static void transZrx() throws Exception {
//        String path = "/Users/sunshine/Library/Application Support/io.parity.ethereum/keys/ethereum/UTC--2017-12-13T06-15-54.300855788Z--ded03e6482b07e3330872b3d24dfaade1689a2e0";
//        Credentials credentials = WalletUtils.loadCredentials("yg0110010", new File(path));
//        Function function = transfer(ZRX_TO, BigInteger.valueOf(7));
//        String functionHash = execute(credentials, function, contractAddress, Contract.GAS_LIMIT);
//        TransactionReceipt transferTransactionReceipt =
//                waitForTransactionReceipt(functionHash);
//        List<Log> logs = transferTransactionReceipt.getLogs();
//        Log log = logs.get(0);
//        List<String> topics = log.getTopics();
//        Event transferEvent = transferEvent();
//        String encodedEventSignature = EventEncoder.encode(transferEvent);
//        List<Type> results = FunctionReturnDecoder.decode(
//                log.getData(), transferEvent.getNonIndexedParameters());
//        System.out.println(JSON.toJSON(results));
//    }
//
//
//    private static void sendTransferTokensTransaction(
//            Credentials credentials, String to, String contractAddress, BigInteger qty)
//            throws Exception {
//
//        Function function = transfer(to, qty);
//        String functionHash = execute(credentials, function, contractAddress);
//
//        TransactionReceipt transferTransactionReceipt =
//                waitForTransactionReceipt(functionHash);
//
//        List<Log> logs = transferTransactionReceipt.getLogs();
//        Log log = logs.get(0);
//
//        // verify the event was called with the function parameters
//        List<String> topics = log.getTopics();
//
//        Event transferEvent = transferEvent();
//
//        // check function signature - we only have a single topic our event signature,
//        // there are no indexed parameters in this example
//        String encodedEventSignature = EventEncoder.encode(transferEvent);
//
//        // verify qty transferred
//        List<Type> results = FunctionReturnDecoder.decode(
//                log.getData(), transferEvent.getNonIndexedParameters());
//    }
//
//    private static String execute(
//            Credentials credentials, Function function, String contractAddress) throws Exception {
//        BigInteger nonce = getNonce(credentials.getAddress());
//
//        String encodedFunction = FunctionEncoder.encode(function);
//        RawTransaction rawTransaction = RawTransaction.createTransaction(
//                nonce,
//                BigInteger.ZERO,
//                BigInteger.ZERO,
//                contractAddress,
//                encodedFunction);
//
//        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
//        String hexValue = Numeric.toHexString(signedMessage);
//
//        EthSendTransaction transactionResponse = Web3JClient.getClient().ethSendRawTransaction(hexValue)
//                .sendAsync().get();
//
//        return transactionResponse.getTransactionHash();
//    }
//
//
//    private static String execute(
//            Credentials credentials, Function function, String contractAddress,BigInteger gas) throws Exception {
//        BigInteger nonce = getNonce(credentials.getAddress());
//
//        String encodedFunction = FunctionEncoder.encode(function);
//        RawTransaction rawTransaction = RawTransaction.createTransaction(
//                nonce,
//                getGasPrice1(),
//                gas,
//                contractAddress,
//                encodedFunction);
//
//        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
//        String hexValue = Numeric.toHexString(signedMessage);
//
//        EthSendTransaction transactionResponse = Web3JClient.getClient().ethSendRawTransaction(hexValue)
//                .sendAsync().get();
//
//        return transactionResponse.getTransactionHash();
//    }
//
//
//
//    public static BigInteger getNonce(String address) throws Exception {
//        EthGetTransactionCount ethGetTransactionCount = Web3JClient.getClient().ethGetTransactionCount(
//                address, DefaultBlockParameterName.LATEST).sendAsync().get();
//
//        return ethGetTransactionCount.getTransactionCount();
//    }
//
//    public static TransactionReceipt waitForTransactionReceipt(
//            String transactionHash) throws Exception {
//
//        Optional<TransactionReceipt> transactionReceiptOptional =
//                getTransactionReceipt(transactionHash, SLEEP_DURATION, ATTEMPTS);
//
//        if (!transactionReceiptOptional.isPresent()) {
//            fail("Transaction receipt not generated after " + ATTEMPTS + " attempts");
//        }
//
//        return transactionReceiptOptional.get();
//    }
//
//    private static Optional<TransactionReceipt> getTransactionReceipt(
//            String transactionHash, int sleepDuration, int attempts) throws Exception {
//
//        Optional<TransactionReceipt> receiptOptional =
//                sendTransactionReceiptRequest(transactionHash);
//        for (int i = 0; i < attempts; i++) {
//            System.out.println("i++======>" + i);
//            if (!receiptOptional.isPresent()) {
//                Thread.sleep(sleepDuration);
//                receiptOptional = sendTransactionReceiptRequest(transactionHash);
//            } else {
//                break;
//            }
//        }
//
//        return receiptOptional;
//    }
//
//    private static Optional<TransactionReceipt> sendTransactionReceiptRequest(
//            String transactionHash) throws Exception {
//        EthGetTransactionReceipt transactionReceipt =
//                Web3JClient.getClient().ethGetTransactionReceipt(transactionHash).sendAsync().get();
//
//        return transactionReceipt.getTransactionReceipt();
//    }
//
//
//    public static void getOwnerBalance() throws ExecutionException, InterruptedException {
//        Function function = balanceOf(zrx);
//        String encodedFunction = FunctionEncoder.encode(function);
//        org.web3j.protocol.core.methods.response.EthCall response = Web3JClient.getClient().ethCall(
//                Transaction.createEthCallTransaction(
//                        zrx, contractAddress, encodedFunction),
//                DefaultBlockParameterName.LATEST)
//                .sendAsync().get();
//        String responseValue = response.getValue();
//
//        List<Type> responseDecode = FunctionReturnDecoder.decode(
//                responseValue, function.getOutputParameters());
//        System.out.println(JSON.toJSON(responseDecode));
//    }
//
//
//    private static void confirmBalance(
//            String address, String contractAddress, BigInteger expected) throws Exception {
//        Function function = balanceOf(address);
//        String responseValue = callSmartContractFunction(function, contractAddress);
//
//        List<Type> response = FunctionReturnDecoder.decode(
//                responseValue, function.getOutputParameters());
//        System.out.println("response====>" + JSON.toJSON(response));
//        assertThat(response.size(), is(1));
//        assertThat(response.get(0), equalTo(new Uint256(expected)));
//    }



    private static Function totalSupply() {
        return new Function(
                "totalSupply",
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
    }

    private static Function balanceOf(String owner) {
        return new Function(
                "balanceOf",
                Collections.singletonList(new Address(owner)),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
    }

    private static Function transfer(String to, BigInteger value) {
        return new Function(
                "transfer",
                Arrays.asList(new Address(to), new Uint256(value)),
                Collections.singletonList(new TypeReference<Bool>() {
                }));
    }

    private static Function allowance(String owner, String spender) {
        return new Function(
                "allowance",
                Arrays.asList(new Address(owner), new Address(spender)),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
    }

    private static Function approve(String spender, BigInteger value) {
        return new Function(
                "approve",
                Arrays.asList(new Address(spender), new Uint256(value)),
                Collections.singletonList(new TypeReference<Bool>() {
                }));
    }

    private static Function transferFrom(String from, String to, BigInteger value) {
        return new Function(
                "transferFrom",
                Arrays.asList(new Address(from), new Address(to), new Uint256(value)),
                Collections.singletonList(new TypeReference<Bool>() {
                }));
    }

    private static Event transferEvent() {
        return new Event(
                "Transfer",
                Arrays.asList(new TypeReference<Address>() {
                }, new TypeReference<Address>() {
                }),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
    }

    private static Event approvalEvent() {
        return new Event(
                "Approval",
                Arrays.asList(new TypeReference<Address>() {
                }, new TypeReference<Address>() {
                }),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
    }
////
////
}
