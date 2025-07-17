package com.rentalsbackend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryPictureService {

    private final Cloudinary cloudinary;

    public CloudinaryPictureService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadFile (MultipartFile file, String folderName) {
        try {
            Map<String, Object> options = ObjectUtils.asMap("folder", folderName);
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
            return (String)  uploadResult.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload to Cloudinay", e);
        }
    }
}