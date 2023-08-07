package SeAH.savg.service;

import SeAH.savg.repository.EduRepository;
import SeAH.savg.repository.SpecialInspectionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.AopInvocationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MakeIdServiceTest {

    @Mock
    private SpecialInspectionRepository specialInspectionRepository;

    @Mock
    private EduRepository eduRepository;

    @InjectMocks
    private MakeIdService makeIdService;

    @Test
    public void testMakeIdWithSpecialInspection() {
        // Arrange
        String categoryType = "S";
        String todayYearAndMonth = "2308"; // "2309월"로 가정
        int categoryRepository = 99;

        // Assume that the next sequence will be 100
        when(specialInspectionRepository.findAllByMaxSeq(todayYearAndMonth)).thenReturn(categoryRepository);

        // Act
        String result = makeIdService.makeId(categoryType);

        // Assert
        assertEquals("S2308-100", result);
    }

    @Test
    public void testMakeIdWithEdu() {
        // Arrange
        String categoryType = "E";
        String todayYearAndMonth = "2308";
        int categoryRepository = 0;

        when(eduRepository.findAllByMaxSeq(todayYearAndMonth)).thenReturn(categoryRepository);

        // Act
        String result = makeIdService.makeId(categoryType);

        // Assert
        assertEquals("E2308-01", result);
    }



    @Test
    public void testMakeIdWithNoData() {
        // Arrange
        String categoryType = "S";
        String todayYearAndMonth = "2308";

        when(specialInspectionRepository.findAllByMaxSeq(todayYearAndMonth)).thenThrow(new AopInvocationException(""));

        // Act
        String result = makeIdService.makeId(categoryType);

        // Assert
        assertEquals("S2308-00", result);
    }
}
