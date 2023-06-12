package uz.BTService.btservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.BTService.btservice.controller.dto.dtoUtil.HttpResponse;
import uz.BTService.btservice.entity.MessageEntity;
import uz.BTService.btservice.service.MessageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/message")
public class MessageController {
    private final MessageService service;

    @GetMapping("/service")
    public HttpResponse<Object> getOrderForService() {

        HttpResponse<Object> response = HttpResponse.build(true);
        List<MessageEntity> allMessageForService = service.getAllMessageForService();

        return response
                .code(HttpResponse.Status.OK)
                .body(allMessageForService)
                .message(HttpResponse.Status.OK.name());
    }

    @GetMapping("/product")
    public HttpResponse<Object> getOrderForProduct() {

        HttpResponse<Object> response = HttpResponse.build(true);
        List<MessageEntity> allMessageForService = service.getAllMessageForProduct();

        return response
                .code(HttpResponse.Status.OK)
                .body(allMessageForService)
                .message(HttpResponse.Status.OK.name());
    }
}
