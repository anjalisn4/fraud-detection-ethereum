package com.ethereum.accounts;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ethereum.transactions.EthereumTransaction;

public class SmartContract {
	private String address;
	private List<EthereumTransaction> transactions;

	public SmartContract(String address, List<EthereumTransaction> transactions) {
		super();
		this.address = address.toLowerCase();
		this.transactions = transactions;
	}

	public String getAddress() {
		return address;
	}

	public List<EthereumTransaction> getTransactions() {
		return transactions;
	}

	private EthereumTransaction getFirstTransaction() {
		return this.transactions.get(0);
	}

	private EthereumTransaction getLastTransaction() {
		return this.transactions.get(this.transactions.size() - 1);
	}

	public long f1_contract_creation_time() {
		return Long.parseLong(getFirstTransaction().getTimeStamp());
	}

	public long f2_transaction_fee_spent_contract_creation() {
		return Long.parseLong(getFirstTransaction().getGas());
	}

	public double f3_percentage_gas_used_contract_creation() {
		return 100 * (Double.parseDouble(this.getFirstTransaction().getGasUsed())
				/ Double.parseDouble(this.getFirstTransaction().getGas()));
	}

	public long f4_gas_price_contract_creation() {
		return Long.parseLong(getFirstTransaction().getGasPrice());
	}

	public long f5_first_contract_invoke_time() {
		return Long.parseLong(this.getFirstTransaction().getTimeStamp());
	}

	public long f6_last_contract_invoke_time() {
		return Long.parseLong(this.getLastTransaction().getTimeStamp());
	}

	public long f7_active_duration() {
		return Duration.between(Instant.ofEpochSecond(this.f5_first_contract_invoke_time()),
				Instant.ofEpochSecond(this.f6_last_contract_invoke_time())).toSeconds();
	}

	public int f8_total_invocations() {
		return this.transactions.size() - 1;
	}

	public int f9_total_unique_invocations() {
		List<String> gasList = new ArrayList<String>();
		for (EthereumTransaction tx : this.transactions) {
			gasList.add(tx.getFrom() + "-" + tx.getTo());
		}
		Set<String> uniqueGas = new HashSet<String>(gasList);
		return uniqueGas.size();
	}

	public double f10_avg_gas_used_contract_invocations() {
		long gasUsed = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getContractAddress().isEmpty()) {
				gasUsed = gasUsed + Long.parseLong(tx.getGasUsed());
			}
		}

		if (this.f8_total_invocations() != 0) {
			return gasUsed / this.f8_total_invocations();
		} else {
			return 0;
		}
	}

	public long f11_total_gas_price_contract_invocations() {
		long gasPrice = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getContractAddress().isEmpty()) {
				gasPrice = gasPrice + Long.parseLong(tx.getGasPrice());
			}
		}
		return gasPrice;
	}

	public double f12_avg_gas_price_contract_invocations() {
		long gasPrice = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getContractAddress().isEmpty()) {
				gasPrice = gasPrice + Long.parseLong(tx.getGasPrice());
			}
		}
		if (this.f8_total_invocations() != 0) {
			return gasPrice / this.f8_total_invocations();
		} else {
			return 0;
		}
	}

	public long f13_total_tx_fee_contract_invocations() {
		long gas = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getContractAddress().isEmpty()) {
				gas = gas + Long.parseLong(tx.getGas());
			}
		}
		return gas;
	}

	public double f14_avg_tx_fee_contract_invocations() {
		long gas = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getContractAddress().isEmpty()) {
				gas = gas + Long.parseLong(tx.getGas());
			}
		}
		if (this.f8_total_invocations() != 0) {
			return gas / this.f8_total_invocations();
		} else {
			return 0;
		}
	}

	public BigInteger f15_total_ether_contract_invocations() {
		BigInteger value = new BigInteger("0");
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getContractAddress().isEmpty()) {
				BigInteger t = new BigInteger(tx.getValue());
				value = value.add(t);
			}
		}
		return value;
	}

	public BigInteger f16_average_ether_contract_invocations() {
		BigInteger value = new BigInteger("0");
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getContractAddress().isEmpty()) {
				BigInteger t = new BigInteger(tx.getValue());
				value = value.add(t);
			}
		}
		BigInteger t = new BigInteger(String.valueOf(this.f8_total_invocations()));
		if (this.f8_total_invocations() != 0) {
			return value.divide(t);
		} else {
			return new BigInteger("0");
		}
	}

	public long f17_total_gas_used_contract_invocations() {
		long gasUsed = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getContractAddress().isEmpty()) {
				gasUsed = gasUsed + Long.parseLong(tx.getGasUsed());
			}
		}
		return gasUsed;
	}

	public double f18_avg_gas_used_contract_invocations() {
		long gasUsed = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getContractAddress().isEmpty()) {
				gasUsed = gasUsed + Long.parseLong(tx.getGasUsed());
			}
		}
		if (this.f8_total_invocations() != 0) {
			return gasUsed / this.f8_total_invocations();
		} else {
			return 0;
		}

	}

	public String printAllFeatures() {
		return "F1: " + this.f1_contract_creation_time() + "\n" + "F2: "
				+ this.f2_transaction_fee_spent_contract_creation() + "\n" + "F3: "
				+ this.f3_percentage_gas_used_contract_creation() + "\n" + "F4: "
				+ this.f4_gas_price_contract_creation() + "\n" + "F5: " + this.f5_first_contract_invoke_time() + "\n"
				+ "F6: " + this.f6_last_contract_invoke_time() + "\n" + "F7: " + this.f7_active_duration() + "\n"
				+ "F8: " + this.f8_total_invocations() + "\n" + "F9: " + this.f9_total_unique_invocations() + "\n"
				+ "F10: " + this.f10_avg_gas_used_contract_invocations() + "\n" + "F11: "
				+ this.f11_total_gas_price_contract_invocations() + "\n" + "F12: "
				+ this.f12_avg_gas_price_contract_invocations() + "\n" + "F13: "
				+ this.f13_total_tx_fee_contract_invocations() + "\n" + "F14: "
				+ this.f14_avg_tx_fee_contract_invocations() + "\n" + "F15: "
				+ this.f15_total_ether_contract_invocations() + "\n" + "F16: "
				+ this.f16_average_ether_contract_invocations() + "\n" + "F17: "
				+ this.f17_total_gas_used_contract_invocations() + "\n" + "F18: "
				+ this.f18_avg_gas_used_contract_invocations() + "\n";
	}

	public String getAllFeatures() {
		return this.f1_contract_creation_time() + "," + this.f2_transaction_fee_spent_contract_creation() + ","
				+ this.f3_percentage_gas_used_contract_creation() + "," + this.f4_gas_price_contract_creation() + ","
				+ this.f5_first_contract_invoke_time() + "," + this.f6_last_contract_invoke_time() + ","
				+ this.f7_active_duration() + "," + this.f8_total_invocations() + ","
				+ this.f9_total_unique_invocations() + "," + this.f10_avg_gas_used_contract_invocations() + ","
				+ this.f11_total_gas_price_contract_invocations() + "," + this.f12_avg_gas_price_contract_invocations()
				+ "," + this.f13_total_tx_fee_contract_invocations() + "," + this.f14_avg_tx_fee_contract_invocations()
				+ "," + this.f15_total_ether_contract_invocations() + ","
				+ this.f16_average_ether_contract_invocations() + "," + this.f17_total_gas_used_contract_invocations()
				+ "," + this.f18_avg_gas_used_contract_invocations();
	}
}
