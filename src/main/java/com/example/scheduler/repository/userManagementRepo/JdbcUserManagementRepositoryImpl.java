package com.example.scheduler.repository.userManagementRepo;

import com.example.scheduler.dto.scheduleDto.ScheduleResponseDto;
import com.example.scheduler.dto.userManagementDto.UserResponseDto;
import com.example.scheduler.entity.Schedule;
import com.example.scheduler.entity.User;
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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JdbcUserManagementRepositoryImpl
 * {@link UserManagementRepository} 인터페이스를 구현한 클래스입니다.
 * 주로 UserManagementService 필요한 테이블인 user 테이블을 접근하는 기능이 있습니다.
 */
@Repository
public class JdbcUserManagementRepositoryImpl implements UserManagementRepository{

    private final JdbcTemplate jdbcTemplate;
    /**
     * 생성자
     */
    public JdbcUserManagementRepositoryImpl(DataSource dataSource) { //사용자 지정 데이터 형태에도 JdbcTemplate 생성을 하기 위함
        this.jdbcTemplate = new JdbcTemplate((dataSource));
    }

    /**
     * registerUser 함수는 새로운 "사용자"를 추가합니다.
     * SimpleJdbcInsert 를 사용하여 insert를 진행합니다.
     * @param user 등록에 필요한 사용자의 정보가 있습니다.
     * USER_NAME, EMAIL 가 포함이 됩니다.
     * @return 삽입 완료 후에 해당 열을 다시 읽어와서 리턴합니다.
     */
    @Override
    public UserResponseDto registerUser(User user) {
        // insert작업을 하겠따
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("USER_ID")
                .usingColumns("USER_NAME", "EMAIL");

        //매개변수 넣어주기
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("USER_NAME", user.getUserName());
        parameters.put("EMAIL", user.getEmail());

        //삽입후, id값을 가져오겠다
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return findUserById(key.longValue());
    }

    /**
     * 특정 id값에 맞는 사용자 row 한줄을 불러오는 함수입니다.
     * @param id 해당 id값은 PK 이므로 테이블에 단 하나가 존재합니다.
     * @return id에 맞는 row가 있으면 해당 row를 UserResponseDto 형태로 반환합니다.
     * @throws ResponseStatusException 결과로 나온 row가 없다면 404를 반환합니다.
     */
    private UserResponseDto findUserById(Long id){
        String sql = "SELECT * FROM user WHERE USER_ID = ?";
        try{
            Map<String, Object> row = jdbcTemplate.queryForMap(sql, id);


            // DATETIME 형식을 Timestamp로 읽어옴
            return new UserResponseDto(
                    Long.parseLong(row.get("USER_ID").toString()),
                    row.get("USER_NAME").toString(),
                    row.get("EMAIL").toString(),
                    (LocalDateTime) row.get("UPDATE_DATE")// DATETIME 형식을 Timestamp로 읽어옴
            );
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }

    /**
     * 동명이인을 포함하여 이름이 같은 사용자의 수를 반환합니다.
     * @param userName 탐색대상이 되는 이름입니다.
     * @return 해당 이름을 가진 사람의 수를 반환합니다.
     * @throws ResponseStatusException 결과로 나온 row가 없다면 404를 반환합니다.
     */
    @Override
    public int findUsersByName(String userName){
        String sql = "SELECT COUNT(*) FROM USER WHERE USER_NAME = ?";

        try {
            int countUser = jdbcTemplate.queryForObject(sql, Integer.class, userName);
            return countUser;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    " 사용자 정보 검색 중 오류가 발생 했습니다.  " + e.getMessage());
        }
    }

    /**
     * 이미 똑같은 정보로 등록이 되어있는지 확인하는 함수입니다.
     * @param userName 탐색대상이 되는 이름입니다.
     * @param email 가입시 기재하는 이메일 정보입니다.
     * @return 해당 이름을 가진 사람의 수를 반환합니다.
     * @throws ResponseStatusException 결과로 나온 row가 없다면 404를 반환합니다.
     */
    @Override
    public int checkForDuplicate (String userName, String email){
        String sql = "SELECT COUNT(*) FROM USER WHERE USER_NAME = ? AND EMAIL = ?";

        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, userName, email);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "중복 체크 중 오류가 발생했습니다: " + e.getMessage());
        }
    }


    /**
     * 특정 id값에 맞는 사용자 "이름"을 반환해주는 함수입니다.
     * @param id 해당 id 값은 PK 이므로 테이블에 단 하나가 존재합니다.
     * @return  id에 맞는 row가 있으면 거기서 이름값만 가져와 String 형태로 반환합니다.
     * @throws ResponseStatusException 결과로 나온 row가 없다면 404를 반환합니다.
     */
    @Override
    public String getUserNameById(Long id){
        String sql = "SELECT * FROM USER WHERE USER_ID = ?";

        try {
            User name = jdbcTemplate.queryForObject(sql, userRowMapper(), id);
            assert name != null;
            return name.getUserName();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "해당 아이디 값이 존재하지 않습니다.  id = "+id);
        }
    }

    /**
     * [Repository] 작성자 이름을 수정하는 함수입니다.
     * @param id 해당되는 row를 찾습니다.
     * @param userName row의 바꿀 작성자 이름입니다.
     * @return 업데이트된 row수를 반환합니다.
     */
    @Override
    public int updateUserNameById(Long id, String userName) {
        return jdbcTemplate.update("UPDATE USER SET USER_NAME = ? WHERE USER_ID = ?", userName, id);
    }

    /**
     * 쿼리 실행문을 {@link User} 형태로 바꿔주는 함수입니다.
     * @return 각 row에 col과 `User` 의 변수를 서로 매핑하여 순서대로 저장합니다.
     */
    private RowMapper<User> userRowMapper() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(
                    rs.getLong("USER_ID"),
                    rs.getString("USER_NAME"),
                    rs.getString("EMAIL")
                );
            }
        };
    }

    /**
     * 쿼리 실행문을 {@link String} 형태로 바꿔주는 함수입니다.
     */
    private RowMapper<String> namesRowMapper() {
        return new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("USER_NAME");
            }
        };
    }

}
