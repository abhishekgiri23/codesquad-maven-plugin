package com.knoldus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;

@Mojo(name = "reports")
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(force = true)
public class CodeSquadMojo extends AbstractMojo {
    
    public void execute() throws MojoExecutionException {
        uploadReports(filePaths, projectName, moduleName, registrationKey, organisation);
    }
    
    @Parameter(property = "filePath", required = true)
    private String[] filePaths;
    
    @Parameter(property = "projectName", required = true)
    private String projectName;
    
    @Parameter(property = "moduleName", required = true)
    private String moduleName;
    
    @Parameter(property = "registrationKey", required = true)
    private String registrationKey;
    
    @Parameter(property = "organisation", required = true)
    private String organisation;
    
    private void uploadReports(String[] filePaths, String projectName,
                               String moduleName, String registrationKey, String organisation) {
        
        CloseableHttpClient httpclient = HttpClients.createDefault();
        for (String filePath : filePaths) {
            File file = new File(filePath);
            
            HttpEntity entity = MultipartEntityBuilder.create()
                    .addTextBody("projectName", projectName)
                    .addTextBody("moduleName", moduleName)
                    .addTextBody("registrationKey", registrationKey)
                    .addTextBody("organisation", organisation)
                    .addBinaryBody("file", file)
                    .build();
            HttpPut httpPut = new HttpPut("http://34.214.155.246:8080/add/reports");
            httpPut.setEntity(entity);
            
            HttpResponse response = null;
            try {
                response = httpclient.execute(httpPut);
                
                HttpEntity result = response.getEntity();
                getLog().info("-----------------------------------");
                getLog().info(response.getStatusLine().toString());
                getLog().info("-----------------------------------");
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}