package com.maia.cursomc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

/*
 * Class de Configuração para Acesso do Amazon S3*/

@Configuration
public class S3Config {

	// Chamo os valores que estão no Properties com as chaves de entrada da Amazon
	@Value("${aws.access_key_id}")
	private String awsId;

	@Value("${aws.secret_access_key}")
	private String awsKey;

	@Value("${s3.region}")
	private String region;

	@Bean
	public AmazonS3 s3client() {
		BasicAWSCredentials awsCred = new BasicAWSCredentials(awsId, awsKey);
		AmazonS3 s3client = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region))
				.withCredentials(new AWSStaticCredentialsProvider(awsCred)).build();
		return s3client;

	}
}
