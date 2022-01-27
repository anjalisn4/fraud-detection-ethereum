package com.ethereum.nonfelonious;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ethereum.transactions.EthereumTransaction;

public class NonFelonious {

	private static final String COMMA_DELIMITER = ",";
	private static final String INVAID_CSV_PATH = "/home/hdoop/Documents/Project/DSFinalProject/data/invalid/invalid.csv";

	public static List<String> getInvalidAccounts() throws IOException {
		List<String> records = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(INVAID_CSV_PATH))) {
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

	//Returns true if the account is a valid non felonious account
	public static boolean validateNonFeloniousAccount(List<String> invalidAccounts,
			List<EthereumTransaction> transactions) {
		List<String> accountsInteractedWith = new ArrayList<String>();

		for (EthereumTransaction tx : transactions) {
			accountsInteractedWith.add(tx.getFrom().toLowerCase());
			accountsInteractedWith.add(tx.getTo().toLowerCase());
		}

		Set<String> uniqueAccountsInteractedWith = new HashSet<String>(accountsInteractedWith);
		return Collections.disjoint(uniqueAccountsInteractedWith, invalidAccounts);

	}

}
