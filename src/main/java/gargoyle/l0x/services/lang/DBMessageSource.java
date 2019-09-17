package gargoyle.l0x.services.lang;

import gargoyle.l0x.entities.lang.Message;
import gargoyle.l0x.repositories.lang.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;

@Component("messageSource")
@RequiredArgsConstructor
public class DBMessageSource extends AbstractMessageSource {

    private static final String DEFAULT_LOCALE_CODE = "en";

    private final MessageRepository repository;

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        Message message = repository.findByKeyAndLocale(code, locale.getLanguage());
        if (null == message) message = repository.findByKeyAndLocale(code, DEFAULT_LOCALE_CODE);
        if (null == message) return new MessageFormat(code, locale);
        return new MessageFormat(message.getContent(), locale);
    }

}
