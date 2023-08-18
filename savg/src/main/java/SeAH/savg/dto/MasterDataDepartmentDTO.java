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

    private String firstDepartment; //부서1
    private String secondDepartment; //부서2

    private static ModelMapper modelMapper = new ModelMapper();


    public static MasterDataDepartmentDTO of(MasterDataDepartment masterDataDepartment){
        return modelMapper.map(masterDataDepartment, MasterDataDepartmentDTO.class);
    }

    public MasterDataDepartment toEntity() {
        return modelMapper.map(this, MasterDataDepartment.class);
    }



    public static MasterDataDepartment createMasterDepart(MasterDataDepartmentDTO departmentDTO) {

        return MasterDataDepartment.builder()
                .firstDepartment(departmentDTO.getFirstDepartment())
                .secondDepartment(departmentDTO.getSecondDepartment())
                .build();
    }

}
