package com.ethereum.nonfelonious;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ethereum.transactions.EtherScan;
import com.ethereum.transactions.EthereumTransaction;
import com.ethereum.transactions.EthereumTransactions;

public class NonFelonious {

	private static final String COMMA_DELIMITER = ",";

	public static void main(String[] args) throws IOException {
		EtherScan s = new EtherScan();
		String address = "0x9f26aE5cd245bFEeb5926D61497550f79D9C6C1c";
		EthereumTransactions t = s.getEthereumTransactions(address);
		List<String> invalidAccounts = getInvalidAccounts();
		System.out.println(validateNonFeloniousAccount(invalidAccounts, t.getResult()));

	}

	public static List<String> getInvalidAccounts() throws IOException {
		List<String> records = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(
				new FileReader("/home/hdoop/Documents/Project/DSFinalProject/data/invalid/invalid.csv"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(COMMA_DELIMITER);
				if (!values[0].equals("Address")) {
					records.add(values[0]);
				}
			}
		}
		return records;
	}

	public static boolean validateNonFeloniousAccount(List<String> invalidAccounts,
			List<EthereumTransaction> transactions) {
		List<String> accountsInteractedWith = new ArrayList<String>();

		for (EthereumTransaction tx : transactions) {
			accountsInteractedWith.add(tx.getFrom().toLowerCase());
			accountsInteractedWith.add(tx.getTo().toLowerCase());
		}

		Set<String> uniqueAccountsInteractedWith = new HashSet<String>(accountsInteractedWith);

		return !Collections.disjoint(uniqueAccountsInteractedWith, invalidAccounts);

	}

}
