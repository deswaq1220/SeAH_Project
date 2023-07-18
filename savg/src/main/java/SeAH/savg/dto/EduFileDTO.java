package SeAH.savg.dto;

import SeAH.savg.entity.EduFile;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class EduFileDTO {
    private String eduFileName;       // 파일명
    private String eduFileOriName;    // 원본 파일명
    private String eduFileUrl;        // 파일 조회 경로

    private static ModelMapper modelMapper = new ModelMapper();

    public static EduFileDTO of (EduFile eduFile) {
        return modelMapper.map(eduFile, EduFileDTO.class);
    }
}
