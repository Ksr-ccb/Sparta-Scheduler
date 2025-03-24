package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.dto.ScheduleRequestDto;
import com.example.scheduler.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Annotation @Service는 명시적으로  Service Layer 라는것을 나타내며 비지니스 로직을 수행합니다.
 * ScheduleServiceImpl에서는 {@link ScheduleService} 인터페이스를 구현합니다.
 */
@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;

    /**
     * 생성자 주입
     * 클래스가 필요로 하는 의존성을 생성자를 통해 전달하는 방식
     * @param scheduleRepository @Repository으로 등록된 scheduleRepository 구현체인 Impl
     */
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }

    /**
     * 새로운 스케줄을 만드는 기능을 하는 함수입니다.
     * 입력받은 dto의 값을 확인하고,
     * 조건을 만족하면 {@link com.example.scheduler.entity.Schedule}객체를 생성하고
     * scheduleRepository.saveSchedule 의 매개변수로 넘겨준다..
     * @param dto
     * @return
     */
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        //들어온 데잍터가 일단 필요한게 다 있는지 확인함
        //없으면 바로 BAD REQUEST

        // 다 있으면 생성날짜, 업데이트 날짜를 지금시간으로 설정해서 객체를 수정해중(세터)

        //이제 들어온 이름값에 맞는 아이디로 치환해야하는데
        // ??? 동명이인 저장어떻게하지 (데이터 저장은 둘째고 뭐로 구분해서 id를 받아올건디....?)

        //객체 수정 완료 후에 INSERT 날림 (이게 리턴문)
        //return scheduleRepository.saveSchedule(schedule);
        return null;
    }

    /**
     * 스케줄 리스트를 반환하는 함수입니다.
     * @param userName 특정 작성자의 0
     * @param updateDate
     * @return
     */
    @Override
    public List<ScheduleResponseDto> finaAllSchedules(String userName, String updateDate) {
        //return scheduleRepository.finaAllSchedules(userName, updateDate)l
        // scheduleRepository. finaAllSchedules 에서 유효값에 따라서 쿼리문 다르게 적용해줘야 함.
        return List.of();
    }

    /**
     * 매개변수로 받은 id값이 테이블에 잇는지 확인하고
     * 있다면 해당 row만 읽어와서 MemoResponseDto으로 반환하는 함수입니다.
     * @param id 찾아야 할 id값
     * @return
     */
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        // return new ScheduleResponseDto(scheduleRepository.findScheduleById(id));
        return null;
    }

    /**
     * 일정을 변경하는 함수입니다.
     * 먼저, 입력받은 값에서 필요한 값들이 다 있는지 확인합니다.
     * 그 다음 scheduleRepository.checkPassword을 통해 비밀번호가 맞는지 검사한 후,
     * 통과가 되면 row의 값을 update합니다.
     * @param id 수정할 id값
     * @param dto
     * @return
     */
    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto) {
        //들어온 requestBody가 정상적인 형태가 맞는지 확인
        // 아니면 BAD REQUEST

        //다 있으면 비밀번호 꺼내서 비밀번호 확인함
        // 틀리면 BAD REQEUST

        //맞으면 레포지토리 일시킴. 받아오는 것:수정한 row개수
        // 수정한 후에 , 수정된 값을 scheduleRepository.findScheduleById(id)으로 받아와서 스케줄 객체만듬

        //return new ScheduleResponseDto(schedule);
        return null;
    }

    /**
     * 해당하는 일정을 삭제하는 함수입니다.
     * 주어진 id값과 password를 비교하고, 조건이 충족되면 해당 row를 찾아 삭제합니다.
     * @param id 삭제할 id값
     * @param password 해당 id에 있는 password랑 비교해야함. (같아야 삭제가능)
     */
    @Override
    public void deleteSchedule(Long id, String password) {

    }
}
