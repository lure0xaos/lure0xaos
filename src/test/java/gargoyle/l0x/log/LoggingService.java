package gargoyle.l0x.log;

import gargoyle.l0x.annotations.log.Log;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {


    @Log
    public LogResult returnMethod(LogParam param1, LogParam param2) {
        return new LogResult(param1, param2);
    }

    @Log
    public LogResult throwMethod(LogParam param1,LogParam param2) {
        throw new RuntimeException();
    }
}
