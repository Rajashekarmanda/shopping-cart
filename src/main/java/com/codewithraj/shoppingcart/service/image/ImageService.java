package com.codewithraj.shoppingcart.service.image;

import com.codewithraj.shoppingcart.dto.ImageDTO;
import com.codewithraj.shoppingcart.exceptions.InvalidFileException;
import com.codewithraj.shoppingcart.model.Image;
import com.codewithraj.shoppingcart.model.Product;
import com.codewithraj.shoppingcart.repository.ImageRepository;
import com.codewithraj.shoppingcart.service.product.IProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Override
    @Transactional
    public List<Image> saveImages(Long productId, List<MultipartFile> files){
        Product product = productService.getProductById(productId); // 404 case if missing

        files.forEach(this::validateFile); // check all files before saving

        List<Image> savedImages = new ArrayList<>();

        for (MultipartFile file : files){
            Image image = new Image();
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setData(readBytes(file));
            image.setProduct(product);

            Image saved = imageRepository.save(image);   // now it has an id
            saved.setDownloadUrl(apiPrefix +"/images/"+saved.getId()); // build the URL with it
        }

        return savedImages;
    }

    @Override
    public ImageDTO convertToDTO(Image image) {
        return new ImageDTO(image.getId(), image.getFileName(), image.getDownloadUrl());
    }

    // Used by upload now, and by update later
    private void validateFile(MultipartFile file){
        if(file.isEmpty()){
            throw new InvalidFileException("File is empty");
        }
        String type = file.getContentType();
        if (type == null || !type.startsWith("image/")){
            throw new InvalidFileException(file.getOriginalFilename()+" is not an image");
        }
    }

    private byte[] readBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Could not read file "+file.getOriginalFilename(), e);
        }
    }
}
