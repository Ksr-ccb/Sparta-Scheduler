package com.example.scheduler.service.scheduleServ;

import com.example.scheduler.dto.scheduleDto.ScheduleResponseDto;
import com.example.scheduler.dto.scheduleDto.ScheduleRequestDto;
import com.example.scheduler.entity.Schedule;
import com.example.scheduler.repository.scheduleRepo.ScheduleRepository;
import com.example.scheduler.repository.userManagementRepo.UserManagementRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

/**
 * Annotation @Service는 명시적으로  Service Layer 라는것을 나타내며 비지니스 로직을 수행합니다.
 * ScheduleServiceImpl에서는 {@link ScheduleService} 인터페이스를 구현합니다.
 * 한 함수에서 repository를 여러번 호출할 경우 @Transactional 으로 일관성을 지켜줍니다.
 */
@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;
    private final UserManagementRepository userManagementRepository;

    /**
     * 생성자 주입
     * 클래스가 필요로 하는 의존성을 생성자를 통해 전달하는 방식
     * @param scheduleRepository @Repository으로 등록된 scheduleRepository 구현체인 Impl
     */
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, UserManagementRepository userManagementRepository){
        this.scheduleRepository = scheduleRepository;
        this.userManagementRepository = userManagementRepository;
    }

    /**
     * [service] 새로운 스케줄을 만드는 기능을 하는 함수입니다.
     * 입력받은 dto의 값을 확인하고,
     * 조건을 만족하면 {@link Schedule}객체를 생성하고
     * scheduleRepository.saveSchedule 의 매개변수로 넘겨준다..
     * @param dto
     * @return
     */
    @Transactional
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        //들어온 데잍터가 일단 필요한게 다 있는지 확인함
        if(dto.getUserId() == null || dto.getPassword() == null || dto.getThingTodo() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are no essential values.");
        }//없으면 바로 BAD REQUEST

        try{
            //존재하는 userId값이 있어야 함.
            userManagementRepository.getUserNameById(dto.getUserId());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용자 id값이 올바르지 않습니다.");
        }


        //객체 수정 완료 후에 INSERT 날림
        ScheduleResponseDto responseDto = scheduleRepository.saveSchedule(dto);
        responseDto.setUserName(userManagementRepository.getUserNameById(dto.getUserId()));

        return responseDto;
    }

    /**
     * [service] 스케줄 리스트를 반환하는 함수입니다.
     * @param userName 특정 작성자의 이름값(동명이인도 모두 탐색)
     * @param updateDate 최종 수정된 날짜
     * @return
     */
    @Override
    public List<ScheduleResponseDto> finaAllSchedules(String userName, String updateDate) {

        LocalDate date = null;

        if(userManagementRepository.findUsersByName(userName) == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, userName + " 의 기록을 찾을 수 없습니다.");
        }

        if(updateDate != null){
            // "yyyy-MM-dd" 형식으로 날짜 넣었는지 확인
            if (!updateDate.matches("\\d{4}-\\d{2}-\\d{2}")) { 
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "검색 날짜 형식이 올바르지 않습니다.");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                date = LocalDate.parse(updateDate, formatter);
            } catch (DateTimeParseException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "날짜 형식으로 바꿀 수 없습니다." + date);
            }
        }

        return scheduleRepository.findAllSchedules(userName, date);
    }

    /**
     * 모든 스케줄을 페이지 별로 읽어옵니다.
     * @param pageNum 페이지 넘버
     * @param pageSize 페이지 사이즈
     * @return 리스트가 비었다면 204으로 반환하고, 내용이 있으면 해당 리스트를 추가합니다.
     */
    @Override
    public List<ScheduleResponseDto> findAllScheduleByPages(Long pageNum, Long pageSize) {
        if(pageNum == null || pageSize == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "페이지 번호와 사이즈 값을 확인해주세요");
        }

        List<ScheduleResponseDto> schedules = scheduleRepository.findAllScheduleByPages((pageNum-1)*pageSize, pageSize);

        if(schedules.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "해당 페이지에 데이터가 없습니다");
        }
        return schedules;
    }

    /**
     * [service] id값에 맞는 데이터 하나를 반환하는 함수입니다.
     * 매개변수로 받은 id값이 테이블에 잇는지 확인하고
     * 있다면 해당 row만 읽어와서 MemoResponseDto으로 반환하는 함수입니다.
     * @param id 찾아야 할 id값
     * @return
     */
    @Transactional
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {

        Schedule responseSchedule = scheduleRepository.findScheduleById(id);
        Long userID = responseSchedule.getUserId();

        ScheduleResponseDto responseDto = new ScheduleResponseDto(responseSchedule);
        responseDto.setUserName(userManagementRepository.getUserNameById(userID));
        return responseDto;
    }

    /**
     * [service]일정을 변경하는 함수입니다.
     * 먼저, 입력받은 값에서 필요한 값들이 다 있는지 확인합니다.
     * 그 다음 scheduleRepository.checkPassword을 통해 비밀번호가 맞는지 검사한 후,
     * 통과가 되면 row의 값을 update합니다.
     * @param id 수정할 id값
     * @param updateBody 업데이트 할 맵 내용들
     * @return
     */
    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, Map<String, String> updateBody) {
        //들어온 requestBody가 정상적인 형태가 맞는지 확인
        if(updateBody.get("password") == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호 값이 유효하지 않습니다.");
        }

        // 3. 둘다 NULL => BAD REQEUST
        if(updateBody.get("userName") == null && updateBody.get("thingTodo") == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 할 값이 없습니다.");
        }

        Schedule responseSchedule = scheduleRepository.findScheduleById(id);
        int updatedRow;

        //비밀번호 꺼내서 비밀번호 확인함
        if(updateBody.get("password").equals(responseSchedule.getPassword())){
            // 내용 + 갱신일 수정.......
            if( updateBody.get("thingTodo") != null){
                updatedRow = scheduleRepository.updateSchedule(id, updateBody.get("thingTodo"));
                if( updatedRow != 1 ){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "수정할 row를 찾지 못했어요/");
                }
                responseSchedule.setThingTodo(updateBody.get("thingTodo"));
            }
            // 작성자 + 갱신일 수정
            if( updateBody.get("userName") != null){
                updatedRow = userManagementRepository.updateUserNameById(responseSchedule.getUserId(), updateBody.get("userName"));
                //작성자 이름 변경
                if( updatedRow != 1 ){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제할 row를 찾지 못했어요/");
                }
            }
        }else{
            // 틀리면 BAD REQEUST
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 다릅니다.");
        }

        ScheduleResponseDto responseDto = new ScheduleResponseDto(responseSchedule);
        responseDto.setUserName(userManagementRepository.getUserNameById(responseSchedule.getUserId()));

        return responseDto;
    }

    /**
     * [service]해당하는 일정을 삭제하는 함수입니다.
     * 주어진 id값과 password를 비교하고, 조건이 충족되면 해당 row를 찾아 삭제합니다.
     * @param id 삭제할 id값
     * @param password 해당 id에 있는 password랑 비교해야함. (같아야 삭제가능)
     */
    @Transactional
    @Override
    public void deleteSchedule(Long id, String password) {
        if(password == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호 값이 유효하지 않습니다.");
        }

        Schedule responseSchedule = scheduleRepository.findScheduleById(id);
        //비밀번호 꺼내서 비밀번호 확인함
        if(password.equals(responseSchedule.getPassword())){
            if( scheduleRepository.deleteSchedule(id) != 1){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제할 row를 찾지 못했어요/");
            }

        }else{
            // 틀리면 BAD REQEUST
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 다릅니다.");
        }
    }
}
