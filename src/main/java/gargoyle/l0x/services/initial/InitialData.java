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
    private static final String LOCATION = "/scripts/L0X_PUBLIC_%s.sql";

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
                checkAndExec(Map.of(
                        Creation.TABLE_CREATIONS, String.format(LOCATION, Creation.TABLE_CREATIONS),
                        News.TABLE_NEWS, String.format(LOCATION, News.TABLE_NEWS),
                        Quote.TABLE_QUOTES, String.format(LOCATION, Quote.TABLE_QUOTES)
                ));
            }
            try (Timer ignored = new Timer("Build Menu")) {
                log.info(menuBuilder.buildMenu(null).toString());
            }
        });
    }

    private void checkAndExec(Map<String, String> tableScipts) {
        tableScipts.forEach((table, script) -> {
            if (!IOUtil.checkTable(table, jdbcTemplate, log))
                IOUtil.execScript(script, jdbcTemplate, log);
        });
    }

}
