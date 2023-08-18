package SeAH.savg.dto;

import SeAH.savg.entity.SpecialFile;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Getter @Setter @ToString
public class SpecialFileFormDTO {
    private String speFileName;       // 파일명
    private String speFileOriName;    // 원본 파일명
    private String speFileUrl;        // 파일 조회 경로

    private static ModelMapper modelMapper = new ModelMapper();

    public static SpecialFileFormDTO of (SpecialFile specialFile) { return modelMapper.map(specialFile, SpecialFileFormDTO.class); }
}
