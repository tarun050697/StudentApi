package student.utils;

import play.Logger;

public class Log {
    public static final play.Logger.ALogger Logger = play.Logger.of("studentProject");

    public static play.Logger.ALogger getLogger(Class type) {
        play.Logger.ALogger logger = play.Logger.of(type);
        return logger;
    }
}
