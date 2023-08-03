/*
import SeAH.savg.SavgApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SavgApplication.class)
@AutoConfigureMockMvc
public class EduControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHandleEduModify() throws Exception {
        // 입력할 데이터 값
        Long eduId = 3L;
        String eduCategory = "MANAGE";
        String eduTitle = "테스트";
        String eduInstructor = "변경원";
        String eduPlace = "우리집";
        LocalDateTime eduStartTime = LocalDateTime.parse("2023-07-27T11:15:00");
        String eduSumTime = "120";
        String eduTarget = "O";
        String eduContent = "[관리감독]";
        String eduWriter = "작성자";

        // 폼 데이터 생성
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedStartTime = eduStartTime.format(formatter);
        formData.add("eduId", String.valueOf(eduId));
        formData.add("eduCategory", eduCategory);
        formData.add("eduTitle", eduTitle + "_수정");
        formData.add("eduInstructor", eduInstructor + "_수정");
        formData.add("eduPlace", eduPlace + "_수정");
        formData.add("eduStartTime", eduStartTime.toString());
        formData.add("eduSumTime", eduSumTime + "_수정");
        formData.add("eduTarget", eduTarget);
        formData.add("eduContent", eduContent + "_수정");
        formData.add("eduWriter", eduWriter + "_수정");

        // MockMvc를 사용하여 PUT 요청 전송
        mockMvc.perform(MockMvcRequestBuilders.put("/edumodify/{eduId}", eduId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(formData))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));

    }
}
*/
