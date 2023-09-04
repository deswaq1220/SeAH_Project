package SeAH.savg.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
public class RegularFileDTO {
    List<String> id;
    List<MultipartFile> file;
}
