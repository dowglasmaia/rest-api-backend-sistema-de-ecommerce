package com.maia.cursomc.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class S3Service {

	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3client;

	// nome do Bucket do S3
	@Value("${s3.bucket}")
	private String bucketName;

	// Fazendo Envio de arquivo para o S3 por endpoint
	public URI uploadFile(MultipartFile multipartFile) {
		try {
			String fileName = multipartFile.getOriginalFilename(); // Estraindo o nome do arquivo passado por paramentro
			InputStream is = multipartFile.getInputStream(); // Encapsula o arquivo de leitura apartir de uma origem
			String contentType = multipartFile.getContentType(); // pega o tipo do arquivo enviado
			return uploadFile(is, fileName, contentType);
		} catch (IOException e) {			
			throw new RuntimeException("Erro do OI: "+ e.getMessage());
		} 
	}

	// Sobrecarga do metodo que Faz o Envio de arquivo para o S3
	public URI uploadFile(InputStream is, String fileName, String contentType) {
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(contentType);

			LOG.info("Iniciando Upload ");

			s3client.putObject(bucketName, fileName, is, metadata);

			LOG.info("Upload Finlazidado");

			return s3client.getUrl(bucketName, fileName).toURI();
		} catch (URISyntaxException e) {
			throw new RuntimeException("Erro ao converter URL para URI !");
		}
	}
}
