package SeAH.savg.dto;


import SeAH.savg.entity.MasterDataDepartment;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@Log4j2
public class MasterDataDepartmentDTO {

    private Long departmentId; //부서1
    private String departmentName; //부서2

    private static ModelMapper modelMapper = new ModelMapper();


    public static MasterDataDepartmentDTO of(MasterDataDepartment masterDataDepartment){
        return modelMapper.map(masterDataDepartment, MasterDataDepartmentDTO.class);
    }

    public MasterDataDepartment toEntity() {
        return MasterDataDepartment.builder()
                .departmentId(this.departmentId)
                .departmentName(this.departmentName) // 직접 기본키 값을 설정
                .build();
    }


    public static MasterDataDepartment createMasterDepart(MasterDataDepartmentDTO departmentDTO) {

        return MasterDataDepartment.builder()
                .departmentId(departmentDTO.getDepartmentId())
                .departmentName(departmentDTO.getDepartmentName())
                .build();
    }

}
