package ua.com.shyrkov.service.factory;

import org.reflections.Reflections;
import ua.com.shyrkov.service.StringReverserService;

import java.util.Set;

public class StringReverserFactory {

    private static StringReverserFactory instance;
    private Reflections reflections;
    private Set<Class<? extends StringReverserService>> reverseServices;

    private StringReverserFactory() {
        reflections = new Reflections("ua.com.shyrkov");
        reverseServices = reflections.getSubTypesOf(StringReverserService.class);
    }

    public static StringReverserFactory getInstance() {
        if (instance == null) {
            instance = new StringReverserFactory();
        }
        return instance;
    }

    public StringReverserService getReverserService() {
        for (Class<? extends StringReverserService> service : reverseServices) {
            if (!service.isAnnotationPresent(Deprecated.class)) {
                try {
                    return service.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("Couldn't return new instance of service");
                }
            }
        }
        throw new RuntimeException("There are no actual services");
    }

}
