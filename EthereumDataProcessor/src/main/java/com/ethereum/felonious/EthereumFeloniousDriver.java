package com.ethereum.felonious;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class EthereumFeloniousDriver {
	
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Please specify the input and output path");
			System.exit(-1);
		}
		FileUtils.deleteDirectory(new File(args[1]));

		Configuration conf = new Configuration();

		Job job = Job.getInstance(conf, "Invalid Accounts Mapper");
		job.setJarByClass(EthereumFeloniousDriver.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setMapperClass(EthereumFeloniousMapper.class);
		job.setReducerClass(EthereumFeloniousReducer.class);
		job.setNumReduceTasks(1);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DoubleWritable.class);

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
