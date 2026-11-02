package com.codewithraj.shoppingcart.controller;

import com.codewithraj.shoppingcart.dto.ImageDTO;
import com.codewithraj.shoppingcart.exceptions.InvalidFileException;
import com.codewithraj.shoppingcart.exceptions.ResourceNotFoundException;
import com.codewithraj.shoppingcart.model.Image;
import com.codewithraj.shoppingcart.response.ApiResponse;
import com.codewithraj.shoppingcart.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}")
@RequiredArgsConstructor
public class ImageController {

    private final IImageService imageService;

    @PostMapping(value = "/products/{productId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> uploadImage(@PathVariable Long productId, @RequestParam("files") List<MultipartFile> files) {
        try{
            List<Image> savedImages = imageService.saveImages(productId, files);
            List<ImageDTO> dtos = savedImages.stream().map(imageService::convertToDTO).toList();
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("Uploaded "+dtos.size()+" images", dtos));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }catch (InvalidFileException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<?> getImage(@PathVariable Long id){
        try{
            Image image = imageService.getImageById(id);
            ByteArrayResource resource = new ByteArrayResource(image.getData());
            ImageDTO dto = imageService.convertToDTO(image);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(image.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + image.getFileName() + "\"")
                    .body(resource);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

}
