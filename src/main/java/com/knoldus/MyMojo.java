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
public class MyMojo extends AbstractMojo {
    
    public void execute() throws MojoExecutionException {
        getLog().info("Hello " + coverage);
        getLog().info("Hello " + projectName);
        getLog().info("Hello " + moduleName);
        getLog().info("Hello " + registrationKey);
        getLog().info("Hello " + organisation);
        
        uploadCoverage(coverage, projectName, moduleName, registrationKey, organisation);
        
    }
    
    @Parameter(property = "coverage", defaultValue = "plugin default value")
    private String coverage;
    
    @Parameter(property = "projectName", defaultValue = "plugin default value")
    private String projectName;
    
    @Parameter(property = "moduleName", defaultValue = "plugin default value")
    private String moduleName;
    
    @Parameter(property = "registrationKey", defaultValue = "plugin default value")
    private String registrationKey;
    
    @Parameter(property = "organisation", defaultValue = "plugin default value")
    private String organisation;
    
    private void uploadCoverage(String filePath, String projectName,
                                String moduleName, String registrationKey, String organisation) {
        
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity result = response.getEntity();
        System.out.println(">>>>>>>>>>>>>>> ???" + response);
    }
    
}
