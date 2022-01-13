package com.ethereum.nonfelonious;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.ethereum.accounts.EOA;
import com.ethereum.accounts.SmartContract;
import com.ethereum.transactions.EtherScan;
import com.ethereum.transactions.EtherScan.Account;
import com.ethereum.transactions.EthereumTransactions;

/**
 * We use the invalid data set with about 5k records to extract the features
 * 
 * This class is a Mapper class which takes the input in the .csv format, and
 * maps the records as key-value pair.
 * 
 * The key is address,account_type The value is the list of transactions for the
 * address
 * 
 * 
 */
public class EthereumNonFeloniousMapper extends Mapper<LongWritable, Text, Text, Text> {
	public static final String API_KEY = "8KNHFB7J3U5WX8AGKN2WPXHRB63V5IBSRT";

	@Override
	public void map(LongWritable key, Text values, Context context) throws IOException, InterruptedException {
		Text mapKey = new Text();
		Text accountType = new Text();
		Text output = new Text();
		if (key.get() == 0 && values.toString().contains("Address"))
			return;
		else {
			String line = values.toString();
			String[] data = line.split(",");

			EtherScan s = new EtherScan(API_KEY);
			EthereumTransactions t = s.getEthereumTransactions(data[0].toLowerCase());
			List<String> invalidAccounts = NonFelonious.getInvalidAccounts();

			if (t.getResult().size() > 0 && NonFelonious.validateNonFeloniousAccount(invalidAccounts, t.getResult())) {
				if (s.getAccountType(t.getResult().get(0)).equals(EtherScan.Account.EOA)) {
					accountType.set(EtherScan.Account.EOA.toString());
					EOA eoa = new EOA(data[0], t.getResult());
					output.set(eoa.getAllFeatures());

				}

				if (s.getAccountType(t.getResult().get(0)).equals(Account.SMARTCONTRACT)) {
					accountType.set(EtherScan.Account.SMARTCONTRACT.toString());
					SmartContract sc = new SmartContract(data[0], t.getResult());
					output.set(sc.getAllFeatures());
				}

				mapKey.set(data[0] + "," + accountType.toString());

				context.write(mapKey, output);
			}
		}

	}

}