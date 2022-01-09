package com.ethereum.felonious;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
	
 */
public class EthereumFeloniousReducer extends Reducer<Text, DoubleWritable, Text, Text> {

	public void reduce(Text t_key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
		Text key = t_key;
		double unit_price = (double) 0;
		int count = 0;
		for (DoubleWritable value : values) {
			unit_price += value.get();
			count++;
		}
		Double avg = (double) unit_price / count;
		context.write(key, new Text(avg.toString()));
	}

}