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
        uploadCheckstyle(coverage);
        
    }
    
    @Parameter(property = "coverage", defaultValue = "plugin default value")
    private String coverage;
    
    private void uploadCheckstyle(String filePath) {
        
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        File file = new File(filePath);
        
        HttpEntity entity = MultipartEntityBuilder.create()
                .addTextBody("projectName", "guest-accounts")
                .addTextBody("moduleName", "guest-accounts-forgerock")
                .addTextBody("registrationKey", "14d6c944-4b8b-4437-a888-5fa07efeb269")
                .addTextBody("organisation", "RCCL")
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
