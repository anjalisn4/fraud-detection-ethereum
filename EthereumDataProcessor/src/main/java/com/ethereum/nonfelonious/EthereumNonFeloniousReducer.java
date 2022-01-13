package com.ethereum.nonfelonious;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


/**
 * This is a Reducer class which generates the features for each unique key
 * 
 */
public class EthereumNonFeloniousReducer extends Reducer<Text, Text, Text, Text> {

	public void reduce(Text t_key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		Text output = new Text();
		Text mapKey = new Text();
		String address = t_key.toString().split(",")[0];

		for (Text tx : values) {
			output.set(tx+","+"0");
		}
		mapKey.set(address);
		context.write(mapKey, new Text(output));
	}

}