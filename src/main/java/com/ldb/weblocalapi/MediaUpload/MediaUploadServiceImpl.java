package com.ldb.weblocalapi.MediaUpload;

import com.ldb.weblocalapi.entities.Document;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class MediaUploadServiceImpl implements MediaUploadService{
    @Value("${media.upload.url}")
    private String uploadURL;
    @Value("${media.upload.path}")
    private String uploadPath;
    @Value("${media.upload.url_pdf}")
    private String uploadURLPDF;
    @Override
    public String uploadMedia(MultipartFile file, Document document,String originFile) {
        try {
            log.info("originFile:"+originFile);
            log.info("getDocNo:"+document.getDocNo());
            log.info("getSaveDate:"+document.getSaveDate());
            String docNo = document.getDocNo();
            String originFiles = originFile.trim();
            LocalDateTime now = LocalDateTime.now();
            // Define the desired format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String formattedDateTime = now.format(formatter);
            log.info("Begin Convert MultiPart File To Base64String");
            String base64String = new String(Base64.encodeBase64(file.getBytes()));
            log.info("Convert To Base64 String Completed");

            log.info("Get File Extension");
            String[] filePattern = (file.getOriginalFilename()).split("\\.");
            String extension = filePattern[filePattern.length-1];
            log.info("Media File Extension Is {}",extension);
            log.info("Generate Random Media Name");
            UUID uuid =  UUID.randomUUID();
            String fileName = docNo  + "-"+formattedDateTime+ "-"+originFiles;
            //String fileName = docNo +  "." + extension;
            log.info("New Generate File Name {}" + fileName);
            log.info("Begin To Calling Http Request Image Upload URL: {} ", uploadURL);
            HttpClient client = HttpClients.createDefault();
            HttpPost httpPost;
            if(extension.toLowerCase().equals("pdf")){
                httpPost = new HttpPost(uploadURLPDF);
            }else{
                httpPost = new HttpPost(uploadURL);
            }
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("BASE64", base64String));
            params.add(new BasicNameValuePair("filename", fileName));
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            log.info("Start To Post Upload Image ...");
            HttpResponse rest = client.execute(httpPost);
            log.info("Finish Image Upload");
            return uploadPath + fileName;
        }catch (Exception ex){
            ex.printStackTrace();
            return "";
        }

    }
    @Override
    public String uploadMedia(MultipartFile file) {
        try {

            log.info("Begin Convert MultiPart File To Base64String");
            String base64String = new String(Base64.encodeBase64(file.getBytes()));
            log.info("Convert To Base64 String Completed");

            log.info("Get File Extension");
            String[] filePattern = (file.getOriginalFilename()).split("\\.");
            String extension = filePattern[filePattern.length-1];
            log.info("Media File Extension Is {}",extension);

            log.info("Generate Random Media Name");
            UUID uuid =  UUID.randomUUID();
            String fileName = uuid.toString() + "-"+uuid.toString() + "." + extension;
            log.info("New Generate File Name {}" + fileName);

            log.info("Begin To Calling Http Request Image Upload URL: {} ", uploadURL);
            HttpClient client = HttpClients.createDefault();
            HttpPost httpPost;
            if(extension.toLowerCase().equals("pdf")){
                httpPost = new HttpPost(uploadURLPDF);
            }else{
                httpPost = new HttpPost(uploadURL);
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("BASE64", base64String));
            params.add(new BasicNameValuePair("filename", fileName));

            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            log.info("Start To Post Upload Image ...");
            HttpResponse rest = client.execute(httpPost);
            log.info("Finish Image Upload");
            return uploadPath + fileName;
        }catch (Exception ex){
            ex.printStackTrace();
            return "";
        }

    }

}
