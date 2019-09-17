package gargoyle.l0x.services.sessions;

import gargoyle.l0x.model.session.SpringSessionModel;
import gargoyle.l0x.model.user.UserModel;
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
    public static final String SELECT = "select distinct s.* from SPRING_SESSION s left join SPRING_SESSION_ATTRIBUTES a on s.PRIMARY_ID = a.SESSION_PRIMARY_ID";
    public static final String DELETE_SESSION_ATTRIBUTES = "delete from SPRING_SESSION_ATTRIBUTES where SESSION_PRIMARY_ID in (select PRIMARY_ID from SPRING_SESSION where PRINCIPAL_NAME = ?)";
    public static final String DELETE_SESSION = "delete from SPRING_SESSION where PRINCIPAL_NAME = ?";

    private final JdbcTemplate jdbc;
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public Map<UserModel, List<SpringSessionModel>> getSessions() {
        Map<UserModel, List<SpringSessionModel>> map = new HashMap<>();
        List<SpringSessionModel> sessions = jdbc.query(SELECT, (rs, rowNum) -> SpringSessionModel.builder()
                .sessionId(rs.getString(SpringSessionModel.COLUMN_SESSION_ID))
                .primaryId(rs.getString(SpringSessionModel.COLUMN_PRIMARY_ID))
                .creationTime(rs.getLong(SpringSessionModel.COLUMN_CREATION_TIME))
                .expiryTime(rs.getLong(SpringSessionModel.COLUMN_EXPIRY_TIME))
                .lastAccessTime(rs.getLong(SpringSessionModel.COLUMN_LAST_ACCESS_TIME))
                .maxInactiveInterval(rs.getInt(SpringSessionModel.COLUMN_MAX_INACTIVE_INTERVAL))
                .principalName(rs.getString(SpringSessionModel.COLUMN_PRINCIPAL_NAME))
                .build());
        sessions.stream()
                .map(SpringSessionModel::getPrincipalName)
                .map(userRepository::findById)
                .forEach(optionalUser -> optionalUser.ifPresent((user) ->
                        map.putIfAbsent(userConverter.toModel(user), sessions)));
        return map;
    }

    @Override
    public long online() {
        Long count = jdbc.queryForObject(COUNT, Long.class);
        return null == count ? 0 : count;
    }

    @Override
    @Transactional
    public void killByUsername(String username) {
        jdbc.update(DELETE_SESSION_ATTRIBUTES, username);
        jdbc.update(DELETE_SESSION, username);
    }
}
