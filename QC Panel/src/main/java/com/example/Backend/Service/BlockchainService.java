package com.example.Backend.Service;

import com.example.Backend.Service.DataStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import io.github.cdimascio.dotenv.Dotenv;

@Service
public class BlockchainService {
    private final DataStorage contract;
    private final Web3j web3j;
    Dotenv dotenv = Dotenv.load();

    public BlockchainService(Web3j web3j) {
        this.web3j = web3j;
        String privateKey = dotenv.get("PRIVATE_KEY");
        String contractAddress = dotenv.get("CONTRACT_ADDRESS");

        Credentials credentials = Credentials.create(privateKey);
        TransactionManager transactionManager = new org.web3j.tx.RawTransactionManager(web3j, credentials);

        // Load deployed contract
        this.contract = DataStorage.load(contractAddress, web3j, transactionManager, new DefaultGasProvider());
    }

    // Function to store data
    public CompletableFuture<String> addData(BigInteger id, String data) {
        return contract.addData(id, data).sendAsync()
                .thenApply(transactionReceipt -> transactionReceipt.getTransactionHash());
    }

    // Function to retrieve data
    public CompletableFuture<String> getData(BigInteger id) {
        return contract.getData(id).sendAsync()
                .thenApply(result -> "Data: " + result.component1() +
                        ", Sender: " + result.component2() +
                        ", Timestamp: " + result.component3());
    }
}
