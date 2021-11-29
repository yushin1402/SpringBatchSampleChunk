package com.example.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing

public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private ItemReader<String> reader;

	@Autowired
	private ItemProcessor<String, String> processor;

	@Autowired
	private ItemWriter<String> writer;
	
	//ListenerのDI追記
	@Autowired
	private JobExecutionListener jobListener;

	//ListenerのDI追記
	@Autowired
	private StepExecutionListener stepListener;

	@Bean
	public Step chunkStep() {	
		return stepBuilderFactory.get("HelloChunkStep")
				.<String, String>chunk(3)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.listener(stepListener) //StepLisnter追記
				.build();
	}
	
	@Bean
	public Job chunkJob() throws Exception{
		return jobBuilderFactory.get("HelloWorldChunkjob")
				.incrementer(new RunIdIncrementer())
				.start(chunkStep())
				.listener(jobListener) //JobListner追記
				.build();
	}
}
