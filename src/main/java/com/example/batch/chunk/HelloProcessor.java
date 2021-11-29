package com.example.batch.chunk;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@StepScope
@Slf4j

public class HelloProcessor implements ItemProcessor<String, String>{

	//↓↓Readerが格納したExecutionContext内の変数を取得する処理を追記
	@Value("#{JobExecutionContext['jobKey']}")
	private String jobValue;
	
	@Value("#{StepExecutionContext['stepKey']}")
	private String stepValue;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		log.info("jobKey={}",jobValue);
		log.info("stepKey={}",stepValue);
	}
	//↑↑Readerが格納したExecutionContext内の変数を取得する処理を追記
	
	@Override
	public String process(String item) throws Exception{
		
		item = item + "★";
		log.info("Processor:{}", item);
		return item;
		
	}

}
