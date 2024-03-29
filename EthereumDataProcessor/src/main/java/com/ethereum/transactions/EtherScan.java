package com.ethereum.transactions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;

import com.google.gson.Gson;

public class EtherScan {

	public static String ENDPOINT = "http://api.etherscan.io/api?module=account&action=txlist&address=";
	public static String API_QUERY_STRING = "&sort=asc&apikey=";
	
	private String ApiKey;
	

	public EtherScan(String apiKey) {
		super();
		ApiKey = apiKey;
	}

	public enum Account {
		EOA, SMARTCONTRACT, NONE
	}

//	public static void main(String[] args) throws Exception {
//		EtherScan s = new EtherScan();
//		String address = "0x2CcD34285fDa10fa37eFBCC8B5B11530c982a30A";
//		EthereumTransactions t = s.getEthereumTransactions(address);
//		if (s.getAccountType(t.getResult().get(0)).equals(Account.EOA)) {
//			EOA eoa = new EOA(address, t.getResult());
//			System.out.println(eoa.printAllFeatures());
//		}
//
//		if (s.getAccountType(t.getResult().get(0)).equals(Account.SMARTCONTRACT)) {
//			SmartContract sm = new SmartContract(address, t.getResult());
//			System.out.println(sm.printAllFeatures());
//		}
//	}

	public EthereumTransactions getEthereumTransactions(String address) throws IOException {
		EthereumTransactions data = new EthereumTransactions();
		StringBuffer content = null;
		String endpoint = ENDPOINT + address.toLowerCase() + API_QUERY_STRING+this.ApiKey;
		URL url = new URL(endpoint);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		try {
			con.setRequestMethod("GET");
			int status = con.getResponseCode();
			if (status == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				content = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					content.append(inputLine);
				}
				in.close();
			}
			data = new Gson().fromJson(content.toString(), EthereumTransactions.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error occured for this address: "+address+"\n"+ content.toString());
			return data;
		} finally {
			con.disconnect();
		}

		if (data.getResult()!=null && data.getResult().size() != 0) {
			Account type = getAccountType(data.getResult().get(0));
			data.setType(type);
		} else {
			data.setType(Account.NONE);
		}
		return data;

	}

	public Account getAccountType(EthereumTransaction firstTx) {
		if (firstTx.getInput().equals("0x") && firstTx.getContractAddress().length() == 0) {
			return Account.EOA;
		}

		if (firstTx.getTo().length() == 0 && firstTx.getContractAddress().length() != 0) {
			return Account.SMARTCONTRACT;
		}

		return Account.NONE;
	}

}
