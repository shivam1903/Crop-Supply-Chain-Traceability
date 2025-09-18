package com.example.Backend.Service;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.springframework.stereotype.Service;
import io.github.cdimascio.dotenv.Dotenv;

@Service
public class Web3jService {
    private final Web3j web3j;
    Dotenv dotenv = Dotenv.load();

    public Web3jService() {
        String INFURA_URL = dotenv.get("INFURA_URL");
        this.web3j = Web3j.build(new HttpService(INFURA_URL));
    }

    public Web3j getWeb3j() {
        return web3j;
    }
}
