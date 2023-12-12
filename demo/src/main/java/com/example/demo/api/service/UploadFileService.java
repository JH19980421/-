package com.example.demo.api.service;

import com.example.demo.api.entity.UploadFile;
import com.example.demo.api.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class UploadFileService {

    private final UploadFileRepository uploadFileRepository;
    private final String projectPath = System.getProperty("user.dir");
    private final String fileDir = projectPath + "\\src\\main\\resources\\static\\files\\";

    public String getFullPath(String filename) { return fileDir + filename; }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {

        if(multipartFile.isEmpty()) {
            return null;
        }

        String originalName = multipartFile.getOriginalFilename();
        String saveName = UUID.randomUUID() + "." + extractExt(originalName);

        multipartFile.transferTo(new File(getFullPath(saveName)));
        UploadFile uploadFile = new UploadFile(originalName, saveName,fileDir + saveName);
        uploadFileRepository.save(uploadFile);
        return uploadFile;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeUploadFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) {
                storeUploadFileResult.add(storeFile(multipartFile));
            }
        }
        return storeUploadFileResult;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    private UploadFile getFile(Long id) {
        return uploadFileRepository.findById(id).orElse(null);
    }
}
