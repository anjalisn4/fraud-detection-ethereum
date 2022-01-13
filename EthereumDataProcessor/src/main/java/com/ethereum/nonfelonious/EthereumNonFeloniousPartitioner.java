package com.ethereum.nonfelonious;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import com.ethereum.transactions.EtherScan.Account;

/**
 * 
 * 
 * This class is a Partitioner class. It partitions the data into 3 categories
 * 
 * 1. Data belonging to EOA 2. Data belonging to Smart Contract
 * 
 * This partitioning specifies that all the values for each key are grouped
 * together based on order_date year and make sure that all the values of a
 * single key go to the same reducer, thus allows even distribution of the map
 * output over the reducer.
 */

public class EthereumNonFeloniousPartitioner extends Partitioner<Text, Text> {

	@Override
	public int getPartition(Text key, Text arg1, int numReduceTasks) {
		String[] str = key.toString().split(",");
		Account accountType = null;
		if (str.length > 1) {
			accountType = Account.valueOf(str[1]);
		} else {
			accountType = Account.NONE;
		}
		if (numReduceTasks == 0) {
			return 0;
		}
		if (accountType.equals(Account.EOA)) {
			return 0;
		} else if (accountType.equals(Account.SMARTCONTRACT)) {
			return 1 % numReduceTasks;
		} else {
			return 2 % numReduceTasks;
		}
	}

}