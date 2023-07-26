package SeAH.savg.service;

import SeAH.savg.entity.EduFile;
import SeAH.savg.repository.EduFileRepository;
import SeAH.savg.service.EduFileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DataJpaTest
public class EduFileServiceTest {

   /* @Autowired
    private EduFileService eduFileService;

    @Test
    public void testUploadFile() throws Exception {
        // 가짜 파일 생성
        byte[] fileContent = "Test file content".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", fileContent);

        // 파일 업로드
        String fileName = eduFileService.uploadFile(file);

        // 파일이 존재하는지 확인
        String filePath = "C:\\seah\\edu" + File.separator + fileName;
        assertTrue(Files.exists(Path.of(filePath)));

        // 업로드한 파일 삭제
        deleteFile(filePath);
    }

    @Test
    public void testUpdateFile() throws Exception {
        // 가짜 파일 생성
        byte[] fileContent = "Test 파일".getBytes();
        MockMultipartFile initialFile = new MockMultipartFile("file", "initial.txt", "text/plain", fileContent);

        // 파일 업로드
        String fileName = eduFileService.uploadFile(initialFile);

        // 새로운 가짜 파일 생성
        byte[] updatedFileContent = "Test 파일 2".getBytes();
        MockMultipartFile updatedFile = new MockMultipartFile("file", "updated.txt", "text/plain", updatedFileContent);

        // 파일 업데이트
        eduFileService.updateFile(fileName, updatedFile);

        // 업데이트한 파일이 존재하는지 확인
        String updatedFilePath = getFilePath(fileName);
        assertTrue(Files.exists(Path.of(updatedFilePath)));

        // 초기 파일이 삭제되었는지 확인
        String initialFilePath = getFilePath("initial.txt");
        assertFalse(Files.exists(Path.of(initialFilePath)));

        // 업데이트한 파일 삭제
        deleteFile(updatedFilePath);
    }

    @Test
    public void testDeleteFile() throws Exception {
        // 가짜 파일 생성
        byte[] fileContent = "Test file content".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", fileContent);

        // 파일 업로드
        String fileName = eduFileService.uploadFile(file);

        // 파일 삭제
        eduFileService.deleteFile(fileName);

        // 파일이 삭제되었는지 확인
        String filePath = getFilePath(fileName);
        assertFalse(Files.exists(Path.of(filePath)));
    }

    private String getFilePath(String fileName) {
        String eduFileLocation = "C:\\seah\\edu"; // 파일 업로드 위치
        return eduFileLocation + File.separator + fileName;
    }

    private void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }


    @Autowired
    private EduFileRepository eduFileRepository;
*/



}
