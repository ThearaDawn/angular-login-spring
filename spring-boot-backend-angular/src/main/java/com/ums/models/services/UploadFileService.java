package com.ums.models.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {

    public Resource load(String photoName) throws MalformedURLException;

    public String load(MultipartFile archive) throws IOException;

    public boolean remove(String photoName);

    public Path getPath(String photoName);
}
