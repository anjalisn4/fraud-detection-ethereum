package com.ethereum.transactions;

import java.util.List;

import com.ethereum.transactions.EtherScan.Account;

public class EthereumTransactions {
	
	private Account type;

	private String status;
	private String message;
	private List<EthereumTransaction> result;
	
	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public List<EthereumTransaction> getResult() {
		return result;
	}

	public Account getType() {
		return type;
	}

	public void setType(Account type) {
		this.type = type;
	}

}
