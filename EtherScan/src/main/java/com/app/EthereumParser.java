package com.app;

import java.io.IOException;

import com.ethereum.accounts.EOA;
import com.ethereum.accounts.SmartContract;
import com.ethereum.transactions.EtherScan;
import com.ethereum.transactions.EthereumTransactions;
import com.ethereum.transactions.EtherScan.Account;

public class EthereumParser {
	public static final String API_KEY ="8BPTQD7WQ9NK9IVRZHQEDIQMX4C6MDBQ3C";
	
	
	public static void main(String[] args) throws IOException {
		EtherScan s = new EtherScan(API_KEY);
		EthereumTransactions t = s.getEthereumTransactions(args[0].toLowerCase());
		if (t.getResult().size() > 1) {
			if (s.getAccountType(t.getResult().get(0)).equals(EtherScan.Account.EOA)) {
				EOA eoa = new EOA(args[0], t.getResult());
				System.out.println(eoa.getAllFeatures()+",EOA");

			}

			if (s.getAccountType(t.getResult().get(0)).equals(Account.SMARTCONTRACT)) {
				SmartContract sc = new SmartContract(args[0], t.getResult());
				System.out.println(sc.getAllFeatures()+",SmartContract");
			}

		}
	}
}
