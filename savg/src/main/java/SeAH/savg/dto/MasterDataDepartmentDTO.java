package SeAH.savg.dto;

import SeAH.savg.entity.MasterDataDepartment;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data

public class MasterDataDepartmentDTO {


    private String secondDepartment; //부서2
    private String firstDepartment; //부서1

    private static ModelMapper modelMapper = new ModelMapper();

    public MasterDataDepartment toEntity(){
        return modelMapper.map(this, MasterDataDepartment.class);}
}
