package com.example.plhistory.config.dataImportConfig.storageImportConfig;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.specialized.BlockBlobClient;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class CloudManagement {

    private final BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
            .endpoint("https://plprovider.blob.core.windows.net")
            .sasToken("sv=2022-11-02&ss=bfqt&srt=sco&sp=rwdlacupiytfx&se=2024-06-01T16:16:07Z&st=2024-03-20T09:16:07Z&spr=https,http&sig=BtbivvL7XoWhpRYOtkxY1QQ7iYeOxpiKtBmzWdVqIO4%3D")
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
