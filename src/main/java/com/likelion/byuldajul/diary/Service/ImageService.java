package com.likelion.byuldajul.diary.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.likelion.byuldajul.diary.Entity.Idea;
import com.likelion.byuldajul.diary.Entity.Image;
import com.likelion.byuldajul.diary.Repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private static String bucketName = "byuldajulclone";

    private final AmazonS3Client amazonS3Client;
    private final ImageRepository imageRepository;

    @Transactional
    public List<String> saveImages(List<MultipartFile> multipartFiles, Idea idea) {
        List<String> resultList = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            String value = saveImage(multipartFile, idea);
            resultList.add(value);
        }

        return resultList;
    }


    private String saveImage(MultipartFile multipartFile, Idea idea) {
        String originalName = multipartFile.getOriginalFilename();
        Image image = new Image(originalName, idea);
        String filename = image.getStoredName();

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getInputStream().available());

            amazonS3Client.putObject(bucketName, filename, multipartFile.getInputStream(), objectMetadata);

            String accessUrl = amazonS3Client.getUrl(bucketName, filename).toString();
            image.setAccessUrl(accessUrl);
            String imgUrl = accessUrl + filename;
            image.setAccessUrl(imgUrl);
        } catch (IOException e) {

        }

        imageRepository.save(image);

        return image.getAccessUrl();
    }

    @Transactional
    public void deleteImage(String filename) {
        amazonS3Client.deleteObject(bucketName, filename);
    }

//    @Transactional
//    public String getImage(PathVariable Long id) {
//
//
//    }


}
