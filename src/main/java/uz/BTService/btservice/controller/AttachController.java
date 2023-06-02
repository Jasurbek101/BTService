package uz.BTService.btservice.controller;

import org.springframework.http.HttpStatus;
import uz.BTService.btservice.controller.convert.AttachConvert;
import uz.BTService.btservice.controller.dto.response.AttachDownloadDTO;
import uz.BTService.btservice.controller.dto.dtoUtil.HttpResponse;
import uz.BTService.btservice.controller.dto.response.AttachResponseDto;
import uz.BTService.btservice.service.AttachService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/attach")
@RequiredArgsConstructor
public class AttachController {

    private final AttachService service;

    @PostMapping("/upload")
    public HttpResponse<Object> upload(@RequestParam MultipartFile file){

        HttpResponse<Object> response = new HttpResponse<>(true);
        AttachResponseDto attach = AttachConvert.from(service.saveAttach(file));
        return response
                .code(HttpResponse.Status.OK)
                .message(HttpStatus.OK.name())
                .body(attach);

    }


    @GetMapping(value = "/public/open/{fileName}", produces = MediaType.ALL_VALUE)
    public byte[] open(@PathVariable("fileName") String fileName) {
        return service.open(fileName);
    }


    @GetMapping("/download/{fineName}")
    public ResponseEntity<Resource> download(@PathVariable("fineName") String fileName) {
        AttachDownloadDTO result = service.download(fileName);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(result.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + result.getResource().getFilename() + "\"").body(result.getResource());
    }



    @GetMapping("/get")
    public ResponseEntity<?> getWithPage(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Page<AttachResponseDto> result = service.getWithPage(page, size);
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<?> deleteById(@PathVariable("fileName") String fileName) {
        String result = service.deleteById(fileName);
        return ResponseEntity.ok(result);
    }

}
