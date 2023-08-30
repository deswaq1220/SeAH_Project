package SeAH.savg.controller;

import SeAH.savg.constant.RegStatus;
import SeAH.savg.dto.RegularDTO;
import SeAH.savg.dto.RegularDetailDTO;
import SeAH.savg.service.RegularInspectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/*public class RegularTest {

    @Mock
    private RegularInspectionService regularInspectionService;

    @InjectMocks
    private RegularController regularController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createRegularTest() throws Exception {
        RegularDetailDTO regularDetailDTO = new RegularDetailDTO();
        regularDetailDTO.setRegularCheck(RegStatus.BAD);
        regularDetailDTO.setRegularActContent("개선대책");
        regularDetailDTO.setRegularActPerson("담당:정수인");
        regularDetailDTO.setRegularActEmail("suin@example.com");
        regularDetailDTO.setRegularDate(LocalDateTime.now());
        regularDetailDTO.setRegularActDate(LocalDateTime.now());

        RegularDTO regularDTO = new RegularDTO();
        regularDTO.setRegularDate(LocalDateTime.now());
        regularDTO.setRegularPart("주조");
        regularDTO.setRegularPerson("담당:정수인");


        doNothing().when(regularInspectionService).createRegular(any(), any());

        // 실행 및 검증
        ResponseEntity<String> response = regularController.createRegularInspection(regularDetailDTO, regularDTO);

        // 서비스 메소드가 제대로 호출되었는지 확인
        verify(regularInspectionService, times(1)).createRegular(any(), any());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("정기점검 등록 성공", response.getBody());
    }
}*/
