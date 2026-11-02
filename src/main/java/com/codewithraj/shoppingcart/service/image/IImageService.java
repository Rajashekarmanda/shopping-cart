package com.codewithraj.shoppingcart.service.image;

import com.codewithraj.shoppingcart.dto.ImageDTO;
import com.codewithraj.shoppingcart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    Image getImageById(Long id);

    List<Image> saveImages(Long productId, List<MultipartFile> files);

    ImageDTO convertToDTO(Image image);
}
