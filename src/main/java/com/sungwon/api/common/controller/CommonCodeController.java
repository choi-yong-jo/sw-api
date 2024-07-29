package com.sungwon.api.common.controller;

import com.sungwon.api.common.service.CommonCodeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "commonCode")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/common/")
public class CommonCodeController {

    @Autowired
    CommonCodeService commonCodeService;

}
