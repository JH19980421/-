package com.example.demo.api.service;

import com.example.demo.api.entity.File;
import com.example.demo.api.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final String projectPath = System.getProperty("user.dir");
    private final String fileDir = projectPath + "\\src\\main\\resources\\static\\files\\";

    public String getFullPath(String filename) { return fileDir + filename; }

    public File storeFile(MultipartFile multipartFile) throws IOException {

        if(multipartFile.isEmpty()) {
            return null;
        }

        String originalName = multipartFile.getOriginalFilename();
        String saveName = UUID.randomUUID() + "." + extractExt(originalName);

        multipartFile.transferTo(new java.io.File(getFullPath(saveName)));
        File file = new File(originalName, saveName);
        fileRepository.save(file);

        return file;
    }

    public List<File> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<File> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    private File getFile(Long id) {
        return fileRepository.findById(id).orElse(null);
    }
}
