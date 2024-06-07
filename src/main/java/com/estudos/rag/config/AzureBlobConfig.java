package com.estudos.rag.config;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@Configuration
public class AzureBlobConfig {
  @Value("${spring.cloud.azure.storage.blob.connection-string}")
  private String connectionString;

  @Value("${spring.cloud.azure.storage.blob.endpoint}")
  private String endpoint;

  @Bean
  public BlobServiceClient blobServiceClient() {
    return new BlobServiceClientBuilder()
        .connectionString(connectionString)
        .endpoint(endpoint)
        .buildClient();
  }
}
