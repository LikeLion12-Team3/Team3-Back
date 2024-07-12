package com.likelion.byuldajul.diary.Controller;

import com.likelion.byuldajul.diary.Dto.reponse.IdeaResponseDto;
import com.likelion.byuldajul.diary.Dto.request.CreateIdeaRequestDto;
import com.likelion.byuldajul.diary.Dto.request.UpdateIdeaRequestDto;
import com.likelion.byuldajul.diary.Service.IdeaService;
import com.likelion.byuldajul.diary.Service.ImageService;
import com.likelion.byuldajul.user.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/idea")
@RequiredArgsConstructor
public class IdeaController {

    private final IdeaService ideaService;
    private final ImageService imageService;


    @Operation(summary = "아이디어 생성", description = "아이디어를 생성합니다. \n (Header) Content-Type : multipart/form-data;boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
    @PostMapping("")
    public ResponseEntity<?> createIdea(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                        @RequestPart CreateIdeaRequestDto createIdeaRequestDto,
                                        @RequestPart("images") List<MultipartFile> images) {
        log.info("제목: {}", createIdeaRequestDto.getTitle());
        log.info("아이디어내용: {}", createIdeaRequestDto.getMainText());

        return ResponseEntity.ok(ideaService.saveIdea(customUserDetails.getUsername(),
                createIdeaRequestDto, images));

    }

    @Operation(summary = "아이디어 조회", description = "아이디어 단건 조회 (ID)")
    @GetMapping("/{id}")
    public IdeaResponseDto getIdea(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                   @PathVariable Long id) {
        return ideaService.getIdea(customUserDetails.getUsername(), id);
    }

    @Operation(summary = "아이디어 전체 조회", description = "아이디어 전체 조회")
    @GetMapping("")
    public ResponseEntity<?> getIdeaList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(ideaService.getIdeaList(customUserDetails.getUsername()));
    }

    @Operation(summary = "아이디어 업데이트", description = "아이디어 업데이트")
    @PatchMapping("/{id}")
    public void updateIdea(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                           @PathVariable Long id,
                           @RequestPart UpdateIdeaRequestDto updateIdeaRequestDto,
                           @RequestPart("images") List<MultipartFile> images) {
        ideaService.updateIdea(customUserDetails.getUsername(), id, updateIdeaRequestDto, images);
    }

    @Operation(summary = "아이디어 삭제", description = "아이디어 삭제")
    @DeleteMapping("/{id}")
    public String deleteIdea(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                           @PathVariable Long id,
                           @RequestParam("name") String fileName) {
        ideaService.deleteIdea(customUserDetails.getUsername(), id);
        imageService.deleteImage(fileName);

        return "아이디어가 삭제되었습니다.";
    }
}
