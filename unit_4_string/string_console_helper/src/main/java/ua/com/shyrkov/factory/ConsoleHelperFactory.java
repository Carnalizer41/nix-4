package ua.com.shyrkov.factory;

import org.reflections.Reflections;
import ua.com.shyrkov.service.ConsoleHelperService;

import java.util.Set;

public class ConsoleHelperFactory {

    private static ConsoleHelperFactory instance;
    private Reflections reflections;
    private Set<Class<? extends ConsoleHelperService>> helperServices;

    private ConsoleHelperFactory() {
        reflections = new Reflections("ua.com.shyrkov");
        helperServices = reflections.getSubTypesOf(ConsoleHelperService.class);
    }

    public static ConsoleHelperFactory getInstance() {
        if (instance == null) {
            instance = new ConsoleHelperFactory();
        }
        return instance;
    }

    public ConsoleHelperService getHelperService() {
        for (Class<? extends ConsoleHelperService> helperService : helperServices) {
            if (!helperService.isAnnotationPresent(Deprecated.class)) {
                try {
                    return helperService.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("Idiot");
                }
            }
        }
        throw new RuntimeException("Idiot");
    }
}
