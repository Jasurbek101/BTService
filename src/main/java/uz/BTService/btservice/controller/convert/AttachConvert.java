package uz.BTService.btservice.controller.convert;

import lombok.experimental.UtilityClass;
import uz.BTService.btservice.controller.dto.CategoryDto;
import uz.BTService.btservice.controller.dto.response.AttachResponseDto;
import uz.BTService.btservice.entity.AttachEntity;
import uz.BTService.btservice.entity.CategoryEntity;
import uz.BTService.btservice.exceptions.OriginalFileNameNullException;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class AttachConvert {


    private static final String ATTACH_UPLOAD_FOLDER = "src/main/resources/images/";

    private static final String ATTACH_PATH = "http://localhost:8080/images/";

    public List<AttachResponseDto> from(List<AttachEntity> categoryList) {
        return categoryList.stream().map(AttachConvert::from).toList();
    }

    public AttachResponseDto from(AttachEntity attachEntity) {
        AttachResponseDto dto = new AttachResponseDto();
        dto.setId(attachEntity.getId());
        dto.setOriginalName(attachEntity.getOriginName());
        dto.setDuration(attachEntity.getDuration());
        dto.setPath(attachEntity.getPath());
        dto.setType(attachEntity.getType());
        dto.setSize(attachEntity.getSize());
        dto.setCreatedData(attachEntity.getCreatedDate());
        dto.setUrl(ATTACH_PATH + attachEntity.getPath() + "/" + attachEntity.getId() + "." + attachEntity.getType());
        return dto;
    }

    public AttachEntity generateAttachEntity(String fileOriginalFilename, long fileSize, String fileContentType) {
        String pathFolder = getYmDString();
        File folder = new File(ATTACH_UPLOAD_FOLDER + pathFolder);

        if (!folder.exists()) folder.mkdirs();

        String fileName = UUID.randomUUID().toString();
        String extension = getExtension(fileOriginalFilename);

        return attachSetProperty(fileName, fileOriginalFilename, extension, pathFolder, fileSize, fileContentType);
    }

    private AttachEntity attachSetProperty(String fileName, String originalFilename, String extension, String pathFolder, long size, String contentType) {
        AttachEntity entity = new AttachEntity();
        entity.setId(fileName);
        entity.setOriginName(originalFilename);
        entity.setType(extension);
        entity.setPath(pathFolder);
        entity.setSize(size);
        entity.setContentType(contentType);
        return entity;
    }

    private String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/04/23
    }

    private String getExtension(String fileName) {
        // mp3/jpg/npg/mp4.....
        if (fileName == null) {
            throw new OriginalFileNameNullException("File name null");
        }
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }
}
