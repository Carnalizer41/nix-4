package ua.com.shyrkov.service.factory;

import org.reflections.Reflections;
import ua.com.shyrkov.service.CalculatorService;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CalculatorFactory {

    private static CalculatorFactory instance;
    private Reflections reflections;
    private Set<Class<? extends CalculatorService>> calcServices;
    private Map<Class<? extends CalculatorService>, Object> maps = new ConcurrentHashMap<>();

    private CalculatorFactory() {
        reflections = new Reflections("ua.com.shyrkov");
        calcServices = reflections.getSubTypesOf(CalculatorService.class);
    }

    public static CalculatorFactory getInstance() {
        if (instance == null) {
            instance = new CalculatorFactory();
        }
        return instance;
    }

    public CalculatorService getCalcService() {
        for (Class<? extends CalculatorService> calcService : calcServices) {
            if (!calcService.isAnnotationPresent(Deprecated.class)) {
                try {
                    return calcService.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("Idiot");
                }
            }
        }
        throw new RuntimeException("Idiot");
    }

}
