package gargoyle.l0x.util;

import gargoyle.l0x.config.SymbolConfig;
import gargoyle.l0x.entities.app.Creation;
import gargoyle.l0x.services.app.App;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AliasesTest {
    @MockBean
    private SymbolConfig symbolConfig;
    @Autowired
    private App app;
    private List<String> strings;

    @Before
    public void setUp() {
        strings = app.findCreations(Sort.unsorted()).stream()
                .map(Creation::getHead)
                .collect(Collectors.toList());
    }

    @Test
    public void toAlias() {
        try (Timer ignored = new Timer("toAlias")) {
            for (String s : strings) {
                Aliases.toAlias(s);
            }
        }
    }

}
