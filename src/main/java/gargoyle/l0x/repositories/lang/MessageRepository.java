package gargoyle.l0x.repositories.lang;

import gargoyle.l0x.entities.lang.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Message findByKeyAndLocale(String key, String locale);

}
