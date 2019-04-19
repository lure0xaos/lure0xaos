package gargoyle.l0x.services.sessions;

import gargoyle.l0x.dto.session.SpringSessionDto;
import gargoyle.l0x.dto.user.UserDto;
import gargoyle.l0x.repositories.user.UserRepository;
import gargoyle.l0x.services.users.convert.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(SessionsImpl.BEAN_ID)
@RequiredArgsConstructor
public class SessionsImpl implements Sessions {
    public static final String BEAN_ID = "sessions";
    public static final String COUNT = "select count(*) from SPRING_SESSION";
    public static final String SELECT = "select * from SPRING_SESSION s left join SPRING_SESSION_ATTRIBUTES a on s.PRIMARY_ID = a.SESSION_PRIMARY_ID";
    public static final String DELETE_SESSION_ATTRIBUTES = "delete from SPRING_SESSION_ATTRIBUTES where SESSION_PRIMARY_ID in (select PRIMARY_ID from SPRING_SESSION where PRINCIPAL_NAME = ?)";
    public static final String DELETE_SESSION = "delete from SPRING_SESSION where PRINCIPAL_NAME = ?";

    private final JdbcTemplate jdbc;
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public Map<UserDto, List<SpringSessionDto>> getSessions() {
        Map<UserDto, List<SpringSessionDto>> map = new HashMap<>();
        List<SpringSessionDto> sessions = jdbc.query(SELECT, (rs, rowNum) -> SpringSessionDto.builder()
                .sessionId(rs.getString(SpringSessionDto.COLUMN_SESSION_ID))
                .primaryId(rs.getString(SpringSessionDto.COLUMN_PRIMARY_ID))
                .creationTime(rs.getLong(SpringSessionDto.COLUMN_CREATION_TIME))
                .expiryTime(rs.getLong(SpringSessionDto.COLUMN_EXPIRY_TIME))
                .lastAccessTime(rs.getLong(SpringSessionDto.COLUMN_LAST_ACCESS_TIME))
                .maxInactiveInterval(rs.getInt(SpringSessionDto.COLUMN_MAX_INACTIVE_INTERVAL))
                .principalName(rs.getString(SpringSessionDto.COLUMN_PRINCIPAL_NAME))
                .build());
        sessions.stream()
                .map(SpringSessionDto::getPrincipalName)
                .map(userRepository::findById)
                .forEach(optionalUser -> optionalUser.ifPresent((user) -> map.putIfAbsent(userConverter.toDto(user), sessions)));
        return map;
    }

    @Override
    public long online() {
        Long count = jdbc.queryForObject(COUNT, Long.class);
        return count == null ? 0 : count;
    }

    @Override
    @Transactional
    public void killByUsername(String username) {
        jdbc.update(DELETE_SESSION_ATTRIBUTES, username);
        jdbc.update(DELETE_SESSION, username);
    }
}
