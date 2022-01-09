package com.ethereum.eoa;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import com.ethereum.transactions.EthereumTransaction;

public class EOA {
	private String address;
	private List<EthereumTransaction> transactions;

	private EthereumTransaction getFirstTransaction() {
		return this.transactions.get(0);
	}

	private EthereumTransaction getLastTransaction() {
		return this.transactions.get(this.transactions.size() - 1);
	}

	public EOA(String address, List<EthereumTransaction> transactions) {
		super();
		this.address = address.toLowerCase();
		this.transactions = transactions;
	}

	public int f1_total_transactions_sent() {
		int count = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getFrom().equals(this.address)) {
				count += 1;
			}
		}
		return count;
	}

	public int f2_total_transactions_received() {
		int count = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getTo().equals(this.address)) {
				count += 1;
			}
		}
		return count;
	}

	public BigInteger f3_value_out() {
		BigInteger value = new BigInteger("0");
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getFrom().equals(this.address)) {
				BigInteger t = new BigInteger(tx.getValue());
				value = value.add(t);
			}
		}
		return value;
	}

	public BigInteger f4_value_in() {
		BigInteger value = new BigInteger("0");
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getTo().equals(this.address)) {
				BigInteger t = new BigInteger(tx.getValue());
				value = value.add(t);
			}
		}
		return value;
	}

	public BigInteger f5_value_difference() {
		return (this.f3_value_out().subtract(this.f4_value_in())).abs();
	}

	public int f6_number_of_distinct_address_contacted() {
		Set<String> seen = new HashSet<String>();
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getFrom().equals(this.address)) {
				seen.add(tx.getTo());
			}

			if (tx.getTo().equals(this.address)) {
				seen.add(tx.getFrom());
			}
		}
		return seen.size();
	}

	public int f7_total_transactions_sent_received() {
		return this.f1_total_transactions_sent() + this.f2_total_transactions_received();
	}

	public int f8_total_transactions_sent_to_unique_address() {
		Set<String> seen = new HashSet<String>();
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getFrom().equals(this.address)) {
				seen.add(tx.getTo());
			}
		}
		return seen.size();
	}

	public int f9_total_transactions_received_from_unique_address() {
		Set<String> seen = new HashSet<String>();
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getTo().equals(this.address)) {
				seen.add(tx.getFrom());
			}
		}
		return seen.size();
	}

	public Long f10_first_transaction_time() {
		return Long.parseLong(this.getFirstTransaction().getTimeStamp());
	}

	public Long f11_last_transaction_time() {
		return Long.parseLong(this.getLastTransaction().getTimeStamp());
	}

	public Long f12_transaction_active_duration() {
		return Math.abs(this.f11_last_transaction_time() - this.f10_first_transaction_time()) / 60;
	}

	public String f13_last_txn_bit() {
		return this.getLastTransaction().getTxreceipt_status();
	}

	public String f14_last_transaction_value() {
		return this.getLastTransaction().getValue();
	}

	public BigInteger f15_average_incoming_ether() {
		BigInteger value = new BigInteger("0");
		BigInteger count = new BigInteger("0");
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getTo().equals(this.address)) {
				BigInteger t = new BigInteger(tx.getValue());
				value = value.add(t);
				count = count.add(new BigInteger("1"));
			}
		}
		return value.divide(count);
	}

	public BigInteger f16_average_outgoing_ether() {
		BigInteger value = new BigInteger("0");
		BigInteger count = new BigInteger("0");
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getFrom().equals(this.address)) {
				BigInteger t = new BigInteger(tx.getValue());
				value = value.add(t);
				count = count.add(new BigInteger("1"));
			}
		}
		return value.divide(count);
	}

	public double f17_average_percentage_gas_incoming() {
		long gas = 0;
		long total_gas = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getTo().equals(this.address)) {
				gas = gas + Long.parseLong(tx.getGas());

			}
			total_gas = total_gas + Long.parseLong(tx.getGas());
		}
		return ((double) gas / total_gas) * 100;
	}

	public double f18_average_percentage_gas_outgoing() {
		long gas = 0;
		long total_gas = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getFrom().equals(this.address)) {
				gas = gas + Long.parseLong(tx.getGas());

			}
			total_gas = total_gas + Long.parseLong(tx.getGas());
		}
		return ((double) gas / total_gas) * 100;
	}

	public double f19_outgoing_gas_price() {
		long gas = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getFrom().equals(this.address)) {
				gas = gas + Long.parseLong(tx.getGasPrice());

			}
		}
		return gas;
	}

	public double f20_incoming_gas_price() {
		long gas = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getTo().equals(this.address)) {
				gas = gas + Long.parseLong(tx.getGasPrice());

			}
		}
		return gas;
	}

	public double f21_average_incoming_gas_price() {
		long gas = 0;
		long total = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getTo().equals(this.address)) {
				gas = gas + Long.parseLong(tx.getGasPrice());
				total = total + 1;
			}
		}
		return ((double) gas / total);
	}

	public double f22_average_outgoing_gas_price() {
		long gas = 0;
		long total = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getFrom().equals(this.address)) {
				gas = gas + Long.parseLong(tx.getGasPrice());
				total = total + 1;
			}
		}
		return ((double) gas / total);
	}

	public double f23_total_failed_transactions_incoming() {
		int count = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getTo().equals(this.address) && tx.getTxreceipt_status().equals("0")) {
				count = count + 1;
			}
		}
		return count;
	}

	public double f24_total_failed_transactions_outgoing() {
		int count = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getFrom().equals(this.address) && tx.getTxreceipt_status().equals("0")) {
				count = count + 1;
			}
		}
		return count;
	}

	public double f25_total_failed_transactions() {
		int count = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getTxreceipt_status().equals("0")) {
				count = count + 1;
			}
		}
		return count;
	}

	public double f26_total_success_transactions_incoming() {
		int count = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getTxreceipt_status().equals("1") && tx.getTo().equals(this.address)) {
				count = count + 1;
			}
		}
		return count;
	}

	public double f27_total_success_transactions_outgoing() {
		int count = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getTxreceipt_status().equals("1") && tx.getFrom().equals(this.address)) {
				count = count + 1;
			}
		}
		return count;
	}

	public double f28_total_success_transactions() {
		int count = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getTxreceipt_status().equals("1")) {
				count = count + 1;
			}
		}
		return count;
	}

	public long f29_gas_used_incoming_transaction() {
		long gasUsed = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getTo().equals(this.address)) {
				gasUsed = gasUsed + Long.parseLong(tx.getGasUsed());
			}
		}
		return gasUsed;
	}

	public long f30_gas_used_outgoing_transaction() {
		long gasUsed = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getFrom().equals(this.address)) {
				gasUsed = gasUsed + Long.parseLong(tx.getGasUsed());
			}
		}
		return gasUsed;
	}

	public double f31_percentage_transaction_sent() {
		return ((this.f1_total_transactions_sent())
				/ (this.f1_total_transactions_sent() + this.f2_total_transactions_received()) * 100);
	}

	public double f32_percentage_transaction_received() {
		return ((this.f2_total_transactions_received())
				/ (this.f1_total_transactions_sent() + this.f2_total_transactions_received()) * 100);
	}

	public double f33_standard_deviation_ether_incoming() {
		int max = this.transactions.size();
		double[] values = new double[max];
		int index = 0;
		for (EthereumTransaction tx : this.transactions) {

			if (tx.getTo().equals(this.address)) {
				values[index] = Double.parseDouble(tx.getValue());
				index++;
			}
		}
		StandardDeviation sd = new StandardDeviation(false);
		return sd.evaluate(values);
	}

	public double f34_standard_deviation_ether_outgoing() {
		int max = this.transactions.size();
		double[] values = new double[max];
		int index = 0;
		for (EthereumTransaction tx : this.transactions) {

			if (tx.getFrom().equals(this.address)) {
				values[index] = Double.parseDouble(tx.getValue());
				index++;
			}
		}
		StandardDeviation sd = new StandardDeviation(false);
		return sd.evaluate(values);
	}

	public double f35_standard_deviation_gas_price_incoming() {
		int max = this.transactions.size();
		double[] values = new double[max];
		int index = 0;
		for (EthereumTransaction tx : this.transactions) {

			if (tx.getTo().equals(this.address)) {
				values[index] = Double.parseDouble(tx.getGasPrice());
				index++;
			}
		}
		StandardDeviation sd = new StandardDeviation(false);
		return sd.evaluate(values);
	}

	public double f36_standard_deviation_gas_price_outgoing() {
		int max = this.transactions.size();
		double[] values = new double[max];
		int index = 0;
		for (EthereumTransaction tx : this.transactions) {

			if (tx.getFrom().equals(this.address)) {
				values[index] = Double.parseDouble(tx.getGasPrice());
				index++;
			}
		}
		StandardDeviation sd = new StandardDeviation(false);
		return sd.evaluate(values);
	}

	public String f37_first_transaction_bit() {
		return getFirstTransaction().getTxreceipt_status();
	}

	public String f38_first_transaction_value() {
		return getFirstTransaction().getValue();
	}

//    public void  f39_mean_in_time(self):
//        times = []
//        all_time = [x['timeStamp'] for x in self.transactions if x['to'] == self.eoa_address]
//        for index,value in enumerate(all_time):
//            if(index + 1 < len(all_time)):
//                a = datetime.datetime.fromtimestamp(int(all_time[index]))
//                b = datetime.datetime.fromtimestamp(int(all_time[index + 1]))
//                times.append((b-a).total_seconds()) 
//        if(len(times) == 0):
//            return 0
//        else:
//            return statistics.mean(times)
//        
//    public void  f40_mean_out_time(self):
//        times = []
//        all_time = [x['timeStamp'] for x in self.transactions if x['from'] == self.eoa_address]
//        for index,value in enumerate(all_time):
//            if(index + 1 < len(all_time)):
//                a = datetime.datetime.fromtimestamp(int(all_time[index]))
//                b = datetime.datetime.fromtimestamp(int(all_time[index + 1]))
//                times.append((b-a).total_seconds()) 
//        if(len(times) == 0):
//            return 0
//        else:
//            return statistics.mean(times)
//        
//    public void  f41_mean_time(self):
//        times = []
//        all_time = [x['timeStamp'] for x in self.transactions]
//        for index,value in enumerate(all_time):
//            if(index + 1 < len(all_time)):
//                a = datetime.datetime.fromtimestamp(int(all_time[index]))
//                b = datetime.datetime.fromtimestamp(int(all_time[index + 1]))
//                times.append((b-a).total_seconds()) 
//        if(len(times) == 0):
//            return 0
//        else:
//            return statistics.mean(times)
//        

	public long f42_transaction_fee_spent_incoming() {
		long gas = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getTo().equals(this.address)) {
				gas = gas + Long.parseLong(tx.getGas());
			}
		}
		return gas;
	}

	public long f43_transaction_fee_spent_outgoing() {
		long gas = 0;
		for (EthereumTransaction tx : this.transactions) {
			if (tx.getFrom().equals(this.address)) {
				gas = gas + Long.parseLong(tx.getGas());
			}
		}
		return gas;
	}

	public long f44_transaction_fee_spent() {
		long gas = 0;
		for (EthereumTransaction tx : this.transactions) {
			gas = gas + Long.parseLong(tx.getGas());

		}
		return gas;
	}

	public String printAllFeatures() {
		return 
				"F1: "+ this.f1_total_transactions_sent() 
				+"\n"
				+ "F2: "
				+this.f2_total_transactions_received() 
				+"\n"
				+ "F3: "
				+this.f3_value_out()
				+"\n"
				+ "F4: "
				+this.f4_value_in()
				+"\n"
				+ "F5: "
				+this.f5_value_difference();
				
	}

}
