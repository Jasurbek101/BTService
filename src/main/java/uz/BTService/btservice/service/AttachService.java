package uz.BTService.btservice.service;

import uz.BTService.btservice.controller.convert.AttachConvert;
import uz.BTService.btservice.controller.dto.response.AttachDownloadDTO;
import uz.BTService.btservice.controller.dto.response.AttachResponseDto;
import uz.BTService.btservice.entity.AttachEntity;
import uz.BTService.btservice.exceptions.*;
import uz.BTService.btservice.repository.AttachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Service
@RequiredArgsConstructor
public class AttachService {

    @Value("${attach.upload.folder}")
    private String attachUploadFolder;

    @Value("${attach.download.url}")
    private String attachDownloadUrl;

    private final AttachRepository repository;

    public AttachEntity saveAttach(MultipartFile file) {

        AttachEntity attachEntity = AttachConvert.generateAttachEntity(file.getOriginalFilename(), file.getSize(), file.getContentType());

        fileSaveToSystem(file, attachEntity.getPath(), attachEntity.getId(), attachEntity.getType());

        return repository.save(attachEntity);

    }

    public byte[] open(String fileName) {
        try {
            AttachEntity entity = getAttach(fileName);

            Path file = Paths.get(attachUploadFolder + entity.getPath() + "/" + fileName);
            return Files.readAllBytes(file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public AttachDownloadDTO download(String fileName) {
        try {
            AttachEntity entity = getAttach(fileName);

            File file = new File(attachUploadFolder + entity.getPath() + "/" + fileName);

            File dir = file.getParentFile();
            File rFile = new File(dir, entity.getId() + "." + entity.getType());

            Resource resource = new UrlResource(rFile.toURI());

            if (resource.exists() || resource.isReadable()) {
                return new AttachDownloadDTO(resource, entity.getContentType());
            } else {
                throw new CouldNotRead("Could not read");
            }
        } catch (MalformedURLException e) {
            throw new SomethingWentWrong("Something went wrong");
        }
    }

    public Page<AttachResponseDto> getWithPage(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AttachEntity> pageObj = repository.findAll(pageable);

        List<AttachEntity> entityList = pageObj.getContent();
        List<AttachResponseDto> dtoList = AttachConvert.from(entityList);

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }

    public String deleteById(String fileName) {
        try {
            AttachEntity entity = getAttach(fileName);
            Path file = Paths.get(attachUploadFolder + entity.getPath() + "/" + fileName + "." + entity.getType());

            Files.delete(file);
            repository.delete(entity);

            return "deleted";
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private AttachEntity getAttach(String fileName) {
        String id = fileName.split("\\.")[0];
        Optional<AttachEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new FileNotFoundException("File Not Found");
        }
        return optional.get();
    }

    private void fileSaveToSystem(MultipartFile file, String pathFolder, String fileName, String extension) {

        try {

            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachUploadFolder + pathFolder + "/" + fileName + "." + extension);
            File newFile = Files.write(path, bytes).toFile();
            contentCheck(extension, newFile);

        } catch (IOException e) {
            throw new FileUploadException("File could not upload");
        }

    }

    private void contentCheck(String extension, File newFile) {
        try {

            if (extension.equalsIgnoreCase("mp4")

                    || extension.equalsIgnoreCase("wmv")
                    || extension.equalsIgnoreCase("avi")
                    || extension.equalsIgnoreCase("avchd")
                    || extension.equalsIgnoreCase("flv")
                    || extension.equalsIgnoreCase("mkv")) {

                MultimediaObject instance = new MultimediaObject(newFile);
                MultimediaInfo result = instance.getInfo();
            }
        } catch (EncoderException e) {
            throw new RuntimeException(e);
        }
    }


}

