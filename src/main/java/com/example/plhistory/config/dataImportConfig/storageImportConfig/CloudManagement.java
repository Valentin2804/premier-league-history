package com.example.plhistory.config.dataImportConfig.storageImportConfig;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.specialized.BlockBlobClient;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class CloudManagement {

    private final Dotenv dotenv = Dotenv.load();

    private final BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
            .endpoint("https://plprovider.blob.core.windows.net")
            .sasToken(dotenv.get("SAS_TOKEN"))
            .buildClient();

    public void upload(String name, byte[] image, String containerName){

        if (name.contains(" ")){
            name = name.replace(" ", "_");
        }

        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);

        BlockBlobClient blockBlobClient = blobContainerClient.getBlobClient(name).getBlockBlobClient();

        try (ByteArrayInputStream dataStream = new ByteArrayInputStream(image)) {
            blockBlobClient.upload(dataStream, image.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String download(String name, String containerName){

        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);

        BlobClient blobClient =  blobContainerClient.getBlobClient(name);

        BinaryData image = blobClient.downloadContent();

        return Base64.getEncoder().encodeToString(image.toBytes());
    }

}
