package com.ethereum.transactions;

import java.util.List;

public class EthereumTransactions {

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

}
