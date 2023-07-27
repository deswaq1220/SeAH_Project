package SeAH.savg.service;

import SeAH.savg.repository.SpecialInspectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.AopInvocationException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class MakeIdService {
    public final SpecialInspectionRepository specialInspectionRepository;

    private String makingId = "";   // id 저장할 변수
    private String previousYearAndMonth = "";      // 이전 todayYearAndMonth 저장할 변수
    private int seqNumber = 0;
    private String cateType = "";       // 교육, 수시, 정기 카테고리 저장할 변수

    // id 생성 함수
    public String makeId(String categoryType){
        cateType = categoryType;
        String todayYearAndMonth = new SimpleDateFormat("yyMM").format(new Date());

        // DB값 가져와서 비교 후 seqNumber 세팅
        try {
            seqNumber = specialInspectionRepository.findAllByMaxSeq(todayYearAndMonth);
            seqNumber++;
        } catch (AopInvocationException e){
            // 초기화
            seqNumber = 0;
        }

        // 이전 todayYearAndMonth 현재 비교해서 바뀌었을 경우 sequenceNumber를 0으로 초기화
//        if(!(todayYearAndMonth.equals(previousYearAndMonth))) { seqNumber = 0; }

        // seqNumber가 10 이하이면 00, 01 .... : 앞에 0 붙여주기
        if(seqNumber<10) { makingId = cateType + todayYearAndMonth + "-0" + seqNumber;  }
        else { makingId = cateType + todayYearAndMonth + "-" + seqNumber; }

        previousYearAndMonth = todayYearAndMonth;
        return makingId;
    }
}