package com.example.scheduler.repository.scheduleRepo;

import com.example.scheduler.dto.scheduleDto.ScheduleRequestDto;
import com.example.scheduler.dto.scheduleDto.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JdbcTemplateScheduleRepository는
 * ScheduleRepository 인터페이스를 구현한 클래스입니다.
 * 주로 scheduleService에서 필요한 테이블인 schedule을 접근하는 기능이 있습니다.
 */
@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;
    /**
     * 생성자
     */
    public JdbcTemplateScheduleRepository(DataSource dataSource) { //사용자 지정 데이터 형태에도 JdbcTemplate 생성을 하기 위함
        this.jdbcTemplate = new JdbcTemplate((dataSource));
    }

    /**
     * saveSchedule 함수는 새로운 "할 일"을 추가합ㄴ디ㅏ.
     * SimpleJdbcInsert 를 사용하여 insert를 진행합니다.
     * @param dto 할일 등록에 사용자의 정보가 필요한 변수들이 있습니다.
     * ThigTodo, Password, UserId가 포함이 됩니다.
     * @return 삽입 완료 후에 해당 열을 다시 읽어와서 리턴합니다.
     */
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {

        // insert작업을 하겠따
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("SCHEDULE_ID")
                .usingColumns("SCHEDULE_CONTENT", "PASSWORD", "USER_ID");

        //매개변수 넣어주기
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("SCHEDULE_CONTENT", dto.getThingTodo());
        parameters.put("PASSWORD", dto.getPassword());
        parameters.put("USER_ID", dto.getUserId());

        //삽입후, id값을 가져오겠다
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return new ScheduleResponseDto(findScheduleById(key.longValue()));

    }

    /**
     * 특정 id값에 맞는 스케줄 row 한줄을 불러오는 함수입니다.
     * @param id 해당 id값은 PK 이므로 테이블에 단 하나가 존재합니다.
     * @return id에 맞는 row가 있으면 해당 row를 ScheduleResponseDto형태로 반환하고 그렇지 않으면 HttpStatus.NOT_FOUND를 반환합니다.
     */
    @Override
    public Schedule findScheduleById(Long id){
        String sql = "SELECT * FROM schedule WHERE SCHEDULE_ID = ?";

        try{
            Map<String, Object> row = jdbcTemplate.queryForMap(sql, id);

            // DATETIME 형식을 Timestamp로 읽어옴
            return new Schedule(
                Long.parseLong(row.get("SCHEDULE_ID").toString()),
                row.get("SCHEDULE_CONTENT").toString(),
                row.get("PASSWORD").toString(),
                Long.parseLong(row.get("USER_ID").toString()),
                (LocalDateTime) row.get("CREATE_DATE"),
                (LocalDateTime) row.get("UPDATE_DATE")
            );
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }

    /**
     * 조건을 만족하는 모든 row를 출력합니다.
     * 이때 스케줄 테이블의 userID값에 맞는 이름을 출력해야하기 때문에 sql조인을 사용했습니다.
     * @param userName 탐색하고싶은 작성자의 이름입니다.
     * @param updateDate 최종적으로 수정된 할일의 날짜를 필터링합니다. 해당 날짜에 최종 수정된 할일만 남깁니다.
     * @return 조건이 있을수도 없을수도 있는데, 경우에 맞게 출력할 row가 있다면 List<ScheduleResponseDto> 형태로 출력합니다.
     * 만약 결과로 나온 row가 하나도없다면 NOT_FOUND 를 반환합니다.
     */
    @Override
    public List<ScheduleResponseDto> findAllSchedules(String userName, LocalDate updateDate) {
        String sql = "SELECT SCHEDULE_ID, SCHEDULE_CONTENT, USER_NAME, CREATE_DATE, S.UPDATE_DATE " +
                "FROM SCHEDULE AS S " +
                "JOIN USER AS U " +
                "ON S.USER_ID = U.USER_ID ";

        // 조건을 저장할 리스트
        List<Object> parameters = new ArrayList<>();

        if(userName != null){ //이름 O, 날짜 O/X
            sql += "WHERE USER_NAME = ? ";
            parameters.add(userName);

            if(updateDate != null){
                sql += "AND DATE(S.UPDATE_DATE) = ? ";
                parameters.add(updateDate);
            }
        }else {//이름X, 날짜 O/X
            if(updateDate != null){
                sql += "WHERE DATE(S.UPDATE_DATE) = ? ";
                parameters.add(updateDate);
            }
        }

        sql += "ORDER BY S.UPDATE_DATE";

        try{
            List<ScheduleResponseDto> result = jdbcTemplate.query(sql, parameters.toArray(), scheduleDtoRowMapper());

            if(result.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당되는 컬럼이 없습니다. ");
            }

            return result;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "불러오기 실패 : 입력값을 다시 확인해주세요." + e.getMessage());
        }
    }

    /**
     * 할 일의 내용을 변경합니다.
     * @param id 스케줄 아이디 값입니다.
     * @param thingTodo 바꿀 할일의 내용입니다.
     * @return 업데이트를 한 row의 수를 반환합니다.
     */
    @Override
    public int updateSchedule(Long id, String thingTodo) {
        return jdbcTemplate.update("UPDATE SCHEDULE SET SCHEDULE_CONTENT = ? WHERE SCHEDULE_ID = ?", thingTodo, id);
    }
    /**
     * 주어진 id값에 맞는 row를 삭제하는 함수입니다.
     * @param id 는 PK 값으로 테이블에 딱 하나만 존재합니다.
     *           비밀번호는 이미 서비스 레이어에서 검증했기 때문에 바로 삭제만 합니다.
     * @return 삭제를 진행한 row의 수를 반환합니다.
     */
    @Override
    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("DELETE FROM SCHEDULE WHERE SCHEDULE_ID = ?", id);
    }

    /**
     * 쿼리 실행문을 {@link ScheduleResponseDto} 형태로 바꿔주는 함수입니다.
     * @return 각 row에 col과 `ScheduleResponseDto` 의 변수를 서로 매핑하여 순서대로 저장합니다.
     */
    private RowMapper<ScheduleResponseDto> scheduleDtoRowMapper(){
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                //여기서 행 하나씪 읽어서 MemoResponseDto객체 만들어줌
                return new ScheduleResponseDto(
                    rs.getLong("SCHEDULE_ID"),
                    rs.getString("SCHEDULE_CONTENT"),
                    rs.getString("USER_NAME"),

                    rs.getObject("CREATE_DATE", LocalDateTime.class),
                    rs.getObject("UPDATE_DATE", LocalDateTime.class)
                );
            }
        };
    }

    /**
     * 쿼리 실행문을 {@link Schedule} 형태로 바꿔주는 함수입니다.
     * @return 각 row에 col과 `Schedule` 의 변수를 서로 매핑하여 순서대로 저장합니다.
     */
    private RowMapper<Schedule> scheduleRowMapper() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                    rs.getLong("SCHEDULE_ID"),
                    rs.getString("SCHEDULE_CONTENT"),
                    rs.getString("PASSWORD"),
                    rs.getLong("USER_ID"),
                    rs.getObject("CREATE_DATE", LocalDateTime.class),
                    rs.getObject("UPDATE_DATE", LocalDateTime.class)
                );
            }
        };
    }
}
