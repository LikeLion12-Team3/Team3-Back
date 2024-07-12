package com.likelion.byuldajul.diary.Controller;

import com.likelion.byuldajul.diary.Service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<String> saveImage(@RequestPart("images") List<MultipartFile> images) {
        return imageService.saveImages(images);
    }

    // S3에 저장된 이미지를 삭제하는 로직, 이미지 파일의 확장자까지 정확하게 입력해야 삭제 가능
    // S3에 저장되지 않은 이미지 파일의 이름으로 요청하여도 오류 발생하지 않음
    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public String deleteImage(@RequestParam("name") String fileName) { imageService.deleteImage(fileName);
        return "이미지가 삭제되었습니다.";
    }





}

