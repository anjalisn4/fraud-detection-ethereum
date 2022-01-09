package com.ethereum.felonious;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
/**

 */
public class EthereumFeloniousMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {

	@Override
	public void map(LongWritable key, Text values, Context context) throws IOException, InterruptedException {
		Text country = new Text();
		DoubleWritable unitPrice = new DoubleWritable();
		if (key.get() == 0 && values.toString().contains("index"))
			return;
		else {
			String line = values.toString();
			String[] data = line.split(",");
			String year = "";
			try {
				Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data[6]);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				year = String.valueOf(cal.get(Calendar.YEAR));

			} catch (ParseException e) {
				e.printStackTrace();
			}
			country.set(data[2].concat(",").concat(data[3]).concat(",").concat(year));
			if (data[10].matches("\\d+\\.\\d+")) {
				double i = Double.parseDouble(data[10]);
				unitPrice.set(i);
			}

			context.write(country, unitPrice);
		}

	}

}