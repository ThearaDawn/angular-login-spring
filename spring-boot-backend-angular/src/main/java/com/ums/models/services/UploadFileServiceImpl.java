package com.ums.models.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    private final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);

    private final static String DIRECTORIES_UPLOAD = "uploads";

    @Override
    public Resource load(String photoName) throws MalformedURLException {

        Path pathArchive = getPath(photoName);
        log.info(pathArchive.toString());

        Resource recurso = new UrlResource(pathArchive.toUri());

        if (!recurso.exists() && !recurso.isReadable()) {
            pathArchive = Paths.get("src/main/resources/static/images").resolve("no-usuario.png").toAbsolutePath();

            recurso = new UrlResource(pathArchive.toUri());

            log.error("Error image could not be loaded: " + photoName);

        }
        return recurso;
    }

    @Override
    public String load(MultipartFile archive) throws IOException {

        String nameArchive = UUID.randomUUID().toString() + "_" + archive.getOriginalFilename().replace(" ", "");

        Path pathArchive = getPath(nameArchive);
        log.info(pathArchive.toString());

        Files.copy(archive.getInputStream(), pathArchive);

        return nameArchive;
    }

    @Override
    public boolean remove(String namePhoto) {

        if (namePhoto != null && namePhoto.length() > 0) {
            Path pathPhotoArchive = Paths.get("uploads").resolve(namePhoto).toAbsolutePath();
            File fileArchivePhoto = pathPhotoArchive.toFile();
            if (fileArchivePhoto.exists() && fileArchivePhoto.canRead()) {
                fileArchivePhoto.delete();
                return true;
            }
        }

        return false;
    }

    @Override
    public Path getPath(String namePhoto) {
        return Paths.get(DIRECTORIES_UPLOAD).resolve(namePhoto).toAbsolutePath();
    }

}
