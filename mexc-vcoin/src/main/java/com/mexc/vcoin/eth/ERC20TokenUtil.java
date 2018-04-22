package com.mexc.vcoin.eth;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.exception.ResultCode;
import com.laile.esf.common.exception.SystemException;
import com.mexc.dao.util.mail.MailSender;
import com.mexc.vcoin.WalletHolder;
import com.mexc.vcoin.api.model.WalletAccount;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.admin.methods.response.BooleanResponse;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.tx.Contract;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static junit.framework.TestCase.fail;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2017/12/25
 * Time: 下午4:26
 */
public class ERC20TokenUtil {

    private static Logger logger = LoggerFactory.getLogger(ERC20TokenUtil.class);


    public static String getNewAddress() {
        return "";
    }


    public static String createWalletAccountBySecret(String pwd) {
        WalletAccount walletAccount = new WalletAccount();
        walletAccount.setPwd(pwd);
        return createWalletAccountBySecret(walletAccount);
    }


    public static String createWalletAccountBySecret(WalletAccount account) {
        try {
            ParityClientWrapper parityClientWrapper = WalletHolder.getParityClient();
            NewAccountIdentifier newAccountIdentifier = parityClientWrapper.getParity().personalNewAccount(account.getPwd()).send();
            if (newAccountIdentifier != null) {
                String address = newAccountIdentifier.getAccountId();
                logger.error("创建ETH钱包返回地址" + address);
                return address;
            } else {
                logger.error("创建ETH钱包地址异常");
                return "";
            }
        } catch (Exception e) {
            logger.error("秘钥创建用户平台钱包异常", e);
            return null;
        }
    }

    public static WalletAccount createWalletAccountByPhrase(WalletAccount account) {
        try {
            ParityClientWrapper parityClientWrapper = WalletHolder.getParityClient();
            NewAccountIdentifier newAccountIdentifier = parityClientWrapper.getParity().parityNewAccountFromPhrase(account.getPhrase(), account.getSecret()).send();
            if (newAccountIdentifier != null) {
                return null;
            }
            account.setAddress(newAccountIdentifier.getAccountId());
        } catch (IOException e) {
            logger.error("短语创建用户平台钱包异常", e);
            return null;
        }
        return null;
    }

    public static boolean setAccountName(String name, String walletAddress) {
        try {
            ParityClientWrapper parityClientWrapper = WalletHolder.getParityClient();
            BooleanResponse booleanResponse = parityClientWrapper.getParity().paritySetAccountName(name, walletAddress).send();
            return booleanResponse.success();
        } catch (Exception e) {
            logger.error("修改账号名字异常", e);
            return false;
        }
    }


    public static boolean testPwd(String pwd, String address) {
        try {
            WalletHolder.reload();
            ParityClientWrapper parityClientWrapper = WalletHolder.getParityClient();
            BooleanResponse booleanResponse = parityClientWrapper.getParity().parityTestPassword(address, pwd).send();
            return booleanResponse.success();
        } catch (Exception e) {
            logger.error("测试密码异常", e);
            return false;
        }
    }


    public static BigInteger queryBalance(String address) {
        try {
            ParityClientWrapper parityClientWrapper = WalletHolder.getParityClient();
            EthGetBalance balance = parityClientWrapper.getParity().ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
            return balance.getBalance();
        } catch (IOException e) {
            logger.error("查询钱包余额异常", e);
            throw new SystemException(ResultCode.COMMON_ERROR, "查询钱包余额异常");
        }
    }

    public static String queryTokenBalance(String address, String contractAddress) {
        try {
            ParityClientWrapper parityClientWrapper = WalletHolder.getParityClient();
            Function function = ContractFunction.balanceOf(address);
            return callSmartContractFunction(function, address, contractAddress, parityClientWrapper);
        } catch (IOException e) {
            logger.error("查询钱包余额异常", e);
            throw new SystemException(ResultCode.COMMON_ERROR, "查询钱包余额异常");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static BigInteger transactionCount(String address, String walletId) {
        try {
            ParityClientWrapper parityClientWrapper = WalletHolder.getParityClient();
            EthGetTransactionCount ethGetTransactionCount = parityClientWrapper.getWeb3j().ethGetTransactionCount(
                    address, DefaultBlockParameterName.LATEST).send();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();
            return nonce;
        } catch (Exception e) {
            logger.error("查询钱包交易总笔数", e);
            throw new SystemException(ResultCode.COMMON_ERROR, "查询钱包交易总笔数异常");
        }
    }

    private static String callSmartContractFunction(
            Function function, String fromaddress, String contractAddress, ParityClientWrapper parityClientWrapper) throws Exception {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall response = parityClientWrapper.getWeb3j().ethCall(
                Transaction.createEthCallTransaction(
                        fromaddress, contractAddress, encodedFunction),
                DefaultBlockParameterName.LATEST).send();
        return response.getValue();
    }

    public static String postTransaction(String from, String to, BigInteger gasLimit, BigInteger value, String pwd) {
        ParityClientWrapper parityClientWrapper = WalletHolder.getParityClient();
        try {
            parityClientWrapper.getParity().personalUnlockAccount(from, pwd);
            Transaction transaction = new Transaction(from, getNonce(from, parityClientWrapper), getGasPrice(), gasLimit, to, value, null);
            EthSendTransaction response = parityClientWrapper.getParity().personalSendTransaction(transaction, pwd).send();
            if (StringUtils.isEmpty(response.getTransactionHash())) {
                return "";
            }
            return response.getTransactionHash();
        } catch (Exception e) {
            logger.error("发送交易失败", e);
            throw new SystemException(ResultCode.COMMON_ERROR, "发送交易失败");
        } finally {
            parityClientWrapper.getParity().personalUnlockAccount(from, pwd);
        }
    }


    /**
     * 获取当前gas价格
     *
     * @return
     *
     * @throws IOException
     */
    public static BigInteger getGasPrice() {
        EthGasPrice ethGasPrice;
        try {
            ParityClientWrapper parityClientWrapper = WalletHolder.getParityClient();
            ethGasPrice = parityClientWrapper.getWeb3j().ethGasPrice().send();
            return ethGasPrice.getGasPrice();
        } catch (IOException e) {
            logger.error("查询gasPrice失败", e);
            throw new SystemException(ResultCode.COMMON_ERROR, "查询钱包余额异常");
        }
    }


    public static Long getEthLastBlock() {
        ParityClientWrapper parityClientWrapper = WalletHolder.getParityClient();
        try {
            EthBlockNumber last = parityClientWrapper.getParity().ethBlockNumber().send();
            return last.getBlockNumber().longValue();
        } catch (IOException e) {
            logger.error("获取eth区块数异常", e);
            MailSender.sendWarningMail("获取eth区块数异常" + e.getMessage());
            throw new SystemException(ResultCode.COMMON_ERROR, "获取eth区块数异常");
        }
    }


    public static org.web3j.protocol.core.methods.response.Transaction getBlockTransByNumberValue(Long block, long index) {
        ParityClientWrapper parityClientWrapper = WalletHolder.getParityClient();
        try {
            EthTransaction ethTransaction = parityClientWrapper.getParity().ethGetTransactionByBlockNumberAndIndex(new DefaultBlockParameterNumber(block), BigInteger.valueOf(index)).send();
            ethTransaction.getResult().getInput();
            return ethTransaction.getTransaction().get();
        } catch (IOException e) {
            logger.error("根据区块及区块索引获取交易信息失败", e);
            throw new SystemException(ResultCode.COMMON_ERROR, "根据区块及区块索引获取交易信息失败");
        }
    }

    //区块交易数量
    public static BigInteger eth_getBlockTransactionCountByNumber(long id) {
        ParityClientWrapper parityClientWrapper = WalletHolder.getParityClient();
        try {
            EthGetBlockTransactionCountByNumber ethTransaction = parityClientWrapper.getParity().ethGetBlockTransactionCountByNumber(new DefaultBlockParameterNumber(id)).send();
            return ethTransaction.getTransactionCount();
        } catch (IOException e) {
            logger.error("根据区块获取交易笔数异常", e);
            throw new SystemException(ResultCode.COMMON_ERROR, "根据区块获取交易笔数异常");
        }
    }

    public static BigInteger getEstimateGas(String from, String to, BigInteger value) {
        ParityClientWrapper parityClientWrapper = WalletHolder.getParityClient();
        try {
            Transaction transaction = new Transaction(from, getNonce(from, parityClientWrapper), getGasPrice(), Contract.GAS_LIMIT, to, value, null);
            EthEstimateGas gas = parityClientWrapper.getParity().ethEstimateGas(transaction).send();
            return gas.getAmountUsed();
        } catch (Exception e) {
            logger.error("getEstimateGas error", e);
            throw new SystemException(ResultCode.COMMON_ERROR, "获取交易手续费估值异常");
        }
    }


    public static BigInteger getContractTransEstimateGas(String from, String to, String contract, BigInteger value) {
        ParityClientWrapper parityClientWrapper = WalletHolder.getParityClient();
        try {
            Function function = ContractFunction.transfer(to, value);
            String encodedFunction = FunctionEncoder.encode(function);
            Transaction transaction = Transaction.createFunctionCallTransaction(from, getNonce(from, parityClientWrapper), getGasPrice(), BigInteger.valueOf(21000), contract, encodedFunction);
            EthEstimateGas gas = parityClientWrapper.getParity().ethEstimateGas(transaction).send();
            return gas.getAmountUsed().multiply(BigInteger.valueOf(2));
        } catch (Exception e) {
            logger.error("getEstimateGas error", e);
            throw new SystemException(ResultCode.COMMON_ERROR, "获取交易手续费估值异常");
        }
    }

    public static BigInteger getEstimateGas(String from, String to, BigInteger value, ParityClientWrapper parity) {
        Transaction transaction = new Transaction(from, BigInteger.ZERO, getGasPrice(), Contract.GAS_LIMIT, to, value, null);
        try {
            EthEstimateGas gas = parity.getParity().ethEstimateGas(transaction).send();
            return gas.getAmountUsed();
        } catch (Exception e) {
            logger.error("getEstimateGas error", e);
            throw new SystemException(ResultCode.COMMON_ERROR, "获取交易手续费估值异常");
        }
    }

    public static BigInteger getNonce(String address, ParityClientWrapper parityClientWrapper) throws Exception {
        EthGetTransactionCount ethGetTransactionCount = parityClientWrapper.getWeb3j().ethGetTransactionCount(
                address, DefaultBlockParameterName.LATEST).send();
        return ethGetTransactionCount.getTransactionCount();
    }

    private static final int SLEEP_DURATION = 15000;
    private static final int ATTEMPTS = 40;

    public static String load(String filePath) throws URISyntaxException, IOException {
        String zrxcontract = "60606040526b033b2e3c9fd0803ce8000000600355341561001c57fe5b5b600354600060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055505b5b610b82806100746000396000f30060606040523615610097576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806306fdde0314610099578063095ea7b31461013257806318160ddd1461018957806323b872dd146101af578063313ce5671461022557806370a082311461025157806395d89b411461029b578063a9059cbb14610334578063dd62ed3e1461038b575bfe5b34156100a157fe5b6100a96103f4565b60405180806020018281038252838181518152602001915080519060200190808383600083146100f8575b8051825260208311156100f8576020820191506020810190506020830392506100d4565b505050905090810190601f1680156101245780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561013a57fe5b61016f600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803590602001909190505061042e565b604051808215151515815260200191505060405180910390f35b341561019157fe5b610199610521565b6040518082815260200191505060405180910390f35b34156101b757fe5b61020b600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff16906020019091908035906020019091905050610527565b604051808215151515815260200191505060405180910390f35b341561022d57fe5b610235610857565b604051808260ff1660ff16815260200191505060405180910390f35b341561025957fe5b610285600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190505061085c565b6040518082815260200191505060405180910390f35b34156102a357fe5b6102ab6108a6565b60405180806020018281038252838181518152602001915080519060200190808383600083146102fa575b8051825260208311156102fa576020820191506020810190506020830392506102d6565b505050905090810190601f1680156103265780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561033c57fe5b610371600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919080359060200190919050506108e0565b604051808215151515815260200191505060405180910390f35b341561039357fe5b6103de600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610ace565b6040518082815260200191505060405180910390f35b604060405190810160405280601181526020017f30782050726f746f636f6c20546f6b656e00000000000000000000000000000081525081565b600081600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925846040518082815260200191505060405180910390a3600190505b92915050565b60035481565b60006000600160008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054905082600060008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054101580156105f95750828110155b80156106855750600060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205483600060008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020540110155b156108455782600060008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254019250508190555082600060008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825403925050819055507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8110156107d75782600160008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825403925050819055505b8373ffffffffffffffffffffffffffffffffffffffff168573ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef856040518082815260200191505060405180910390a36001915061084f565b6000915061084f565b5b509392505050565b601281565b6000600060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205490505b919050565b604060405190810160405280600381526020017f5a5258000000000000000000000000000000000000000000000000000000000081525081565b600081600060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054101580156109b15750600060008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205482600060008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020540110155b15610abe5781600060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254039250508190555081600060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055508273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef846040518082815260200191505060405180910390a360019050610ac8565b60009050610ac8565b5b92915050565b6000600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205490505b929150505600a165627a7a72305820d31ec12b6fe4e3204b5bdd3a3cafc924f0101471e7aa3e9460b81496e9067ec70029";
        byte[] bytes = zrxcontract.getBytes();
        return new String(bytes);
    }

    public static TransactionReceipt waitForTransactionReceipt(
            String transactionHash, ParityClientWrapper parityClientWrapper) throws Exception {
        Optional<TransactionReceipt> transactionReceiptOptional =
                getTransactionReceipt(transactionHash, SLEEP_DURATION, ATTEMPTS, parityClientWrapper);
        if (!transactionReceiptOptional.isPresent()) {
            fail("Transaction receipt not generated after " + ATTEMPTS + " attempts");
        }
        return transactionReceiptOptional.get();
    }

    public static TransactionReceipt waitForTransactionReceipt(
            String transactionHash) throws Exception {
        Optional<TransactionReceipt> transactionReceiptOptional =
                getTransactionReceipt(transactionHash, SLEEP_DURATION, ATTEMPTS, WalletHolder.getParityClient());
        if (!transactionReceiptOptional.isPresent()) {
            fail("Transaction receipt not generated after " + ATTEMPTS + " attempts");
        }
        return transactionReceiptOptional.get();
    }


    public static TransactionReceipt getTransReceipt(String transHash, ParityClientWrapper parityClientWrapper) {
        try {
            EthGetTransactionReceipt transactionReceipt =
                    parityClientWrapper.getWeb3j().ethGetTransactionReceipt(transHash).send();
            return transactionReceipt.getResult();
        } catch (Exception e) {
            logger.error("获取交易凭证异常");
            throw new SystemException(ResultCode.COMMON_ERROR, "获取凭证异常");
        }
    }

    public static TransactionReceipt getTransReceipt(String transHash) {
        try {
            EthGetTransactionReceipt transactionReceipt =
                    WalletHolder.getParityClient().getWeb3j().ethGetTransactionReceipt(transHash).send();
            return transactionReceipt.getResult();
        } catch (Exception e) {
            logger.error("获取交易凭证异常");
            throw new SystemException(ResultCode.COMMON_ERROR, "获取凭证异常");
        }
    }


    public static boolean unlockAccount(String pwd, String account) {
        try {
            PersonalUnlockAccount result = WalletHolder.getParityClient().getParity().personalUnlockAccount(account, pwd).send();
            return result.getResult();
        } catch (Exception e) {
            logger.error("获取交易凭证异常");
            throw new SystemException(ResultCode.COMMON_ERROR, "获取凭证异常");
        }
    }


    private static Optional<TransactionReceipt> sendTransactionReceiptRequest(
            String transactionHash, ParityClientWrapper parityClientWrapper) throws Exception {
        EthGetTransactionReceipt transactionReceipt =
                parityClientWrapper.getWeb3j().ethGetTransactionReceipt(transactionHash).sendAsync().get();
        return transactionReceipt.getTransactionReceipt();
    }

    private static Optional<TransactionReceipt> getTransactionReceipt(
            String transactionHash, int sleepDuration, int attempts, ParityClientWrapper parityClientWrapper) throws Exception {
        Optional<TransactionReceipt> receiptOptional =
                sendTransactionReceiptRequest(transactionHash, parityClientWrapper);
        for (int i = 0; i < attempts; i++) {
            if (!receiptOptional.isPresent()) {
                Thread.sleep(sleepDuration);
                receiptOptional = sendTransactionReceiptRequest(transactionHash, parityClientWrapper);
            } else {
                break;
            }
        }

        return receiptOptional;
    }


    private static String sendCreateContractTransaction(
            Credentials credentials, BigInteger initialSupply, ParityClientWrapper parityClientWrapper) throws Exception {
        System.out.println("address----->" + credentials.getAddress());
        BigInteger nonce = getNonce(credentials.getAddress(), parityClientWrapper);

        String encodedConstructor =
                FunctionEncoder.encodeConstructor(
                        Arrays.asList(
                                new Uint256(initialSupply),
                                new Utf8String("web3j tokens"),
                                new Uint8(BigInteger.ZERO),
                                new Utf8String("w3j$")));

        RawTransaction rawTransaction = RawTransaction.createContractTransaction(
                nonce,
                getGasPrice(),
                Contract.GAS_LIMIT,
                Contract.GAS_LIMIT,
                load("") + encodedConstructor);

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        EthSendTransaction transactionResponse = parityClientWrapper.getWeb3j().ethSendRawTransaction(hexValue)
                .sendAsync().get();

        return transactionResponse.getTransactionHash();
    }


    private static String execute(
            Credentials credentials, Function function, String contractAddress, ParityClientWrapper parityClientWrapper) throws Exception {
        BigInteger nonce = getNonce(credentials.getAddress(), parityClientWrapper);

        String encodedFunction = FunctionEncoder.encode(function);

        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                Contract.GAS_PRICE,
                Contract.GAS_LIMIT,
                contractAddress,
                encodedFunction);

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        EthSendTransaction transactionResponse = parityClientWrapper.getWeb3j().ethSendRawTransaction(hexValue).send();
        logger.info("token Trans response:{}", JSON.toJSONString(transactionResponse));
        return transactionResponse.getTransactionHash();
    }


    public static BigDecimal parseAmount(Integer scale, String transValue) {
        BigInteger decodeValue=null;
        if(transValue.indexOf("0x")>-1){
            decodeValue = Numeric.decodeQuantity(transValue);
        }else{
            decodeValue = new BigInteger(transValue);
        }
        String divid = String.valueOf(Math.pow(10, scale));
        BigDecimal transDecimal = new BigDecimal(decodeValue);
        return transDecimal.divide(new BigDecimal(divid));
    }


    public static BigDecimal parseunit(Integer scale, String transValue) {
        String divid = String.valueOf(Math.pow(10, scale));
        BigDecimal transDecimal = new BigDecimal(transValue);
        return transDecimal.multiply(new BigDecimal(divid));
    }


    public static String sendTransferTokensTransaction(String from, String to, String contractAddress, String pwd, BigInteger qty, ParityClientWrapper parityClientWrapper)
            throws Exception {
        if (!parityClientWrapper.getParity().personalUnlockAccount(from, pwd).send().getResult()) {
            throw new SystemException(ResultCode.COMMON_ERROR, "账号密码错误");
        }
        Function function = ContractFunction.transfer(to, qty);
        Transaction transaction = Transaction.createFunctionCallTransaction(from, getNonce(from, parityClientWrapper), getGasPrice(), BigInteger.valueOf(21000), contractAddress, FunctionEncoder.encode(function));
        BigInteger gasUse = parityClientWrapper.getParity().ethEstimateGas(transaction).send().getAmountUsed();
        Transaction transaction1 = Transaction.createFunctionCallTransaction(from, getNonce(from, parityClientWrapper), getGasPrice(), gasUse, contractAddress, FunctionEncoder.encode(function));
        EthSendTransaction ethSendTransaction = parityClientWrapper.getParity().ethSendTransaction(transaction1).send();
        if (StringUtils.isNotEmpty(ethSendTransaction.getTransactionHash())) {
            return ethSendTransaction.getTransactionHash();
        } else {
            throw new SystemException(ResultCode.COMMON_ERROR, "token Trans error");
        }
    }


    public static boolean checkEthAddress(String address, String password) {
        ParityClientWrapper parityClientWrapper = WalletHolder.getParityClient();
        try {
            BooleanResponse booleanResponse = parityClientWrapper.getParity().parityTestPassword(address, password).send();
            return booleanResponse.success();
        } catch (Exception e) {
            logger.error("测试eth密码异常", e);
            return false;
        }
    }


    private static class ContractFunction {
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
    }


    public static void main(String[] args) {
        System.out.println(Numeric.decodeQuantity("0x0000000000000000000000000000000000000000000000004563918244f40000"));
    }
}
