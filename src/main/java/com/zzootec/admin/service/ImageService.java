package com.zzootec.admin.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String upload(MultipartFile file);

}
