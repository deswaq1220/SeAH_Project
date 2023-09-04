package SeAH.savg.service;

import SeAH.savg.repository.RegularInspectionRepository;
import SeAH.savg.repository.SpecialInspectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class InspectionService {

    private final RegularInspectionRepository regularInspectionRepository;
    private final SpecialInspectionRepository specialInspectionRepository;

}
