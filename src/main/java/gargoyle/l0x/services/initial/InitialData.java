package gargoyle.l0x.services.initial;

import gargoyle.l0x.entities.app.Creation;
import gargoyle.l0x.entities.app.News;
import gargoyle.l0x.entities.app.Quote;
import gargoyle.l0x.entities.user.User;
import gargoyle.l0x.services.app.menu.MenuBuilder;
import gargoyle.l0x.services.users.Users;
import gargoyle.l0x.util.IOUtil;
import gargoyle.l0x.util.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Map;

import static gargoyle.l0x.data.Roles.ADMIN;
import static gargoyle.l0x.data.Roles.USER;

@Profile("development")
@Service
@RequiredArgsConstructor
@Slf4j
public class InitialData {
    private static final String USERNAME = "Gargoyle";
    private static final String PASSWORD = "44114411";
    private static final String EMAIL = "lure.of.chaos@gmail.com";

    private final Users users;
    private final JdbcTemplate jdbcTemplate;
    private final MenuBuilder menuBuilder;

    @PostConstruct
    @Transactional
    public void init() {
        log.info("Initial data possibly go");
        users.find(USERNAME).ifPresentOrElse((user) -> log.info("Initial data already exists"), () -> {
            try (Timer ignored = new Timer("Initial data")) {
                //noinspection unused
                User user = users.registerWithRoles(USERNAME, PASSWORD, EMAIL, USER, ADMIN);
                Map.of(
                        Creation.TABLE_CREATIONS, "/L0X_PUBLIC_CREATIONS.sql",
                        News.TABLE_NEWS, "/L0X_PUBLIC_NEWS.sql",
                        Quote.TABLE_QUOTES, "/L0X_PUBLIC_QUOTES.sql"
                ).forEach((key, value) -> {
                    if (!IOUtil.checkTable(key, jdbcTemplate, log))
                        IOUtil.execScript(value, jdbcTemplate, log);
                });
            }
            try (Timer ignored = new Timer("Build Menu")) {
                log.info(menuBuilder.buildMenu(null).toString());
            }
        });
    }

}
