import model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class MainReflection {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume();
        Class resumeClass = resume.getClass();
        Field field = resumeClass.getDeclaredField("uuid");
        field.setAccessible(true);
        System.out.println("UUID before abuse: " + field.get(resume));
        field.set(resume, "new-uuid");
        System.out.println("Sad UUID after: " + field.get(resume));
        // HW4 \u2193
        System.out.println("toString method invocation: " + resumeClass.getMethod("toString").invoke(resume));
    }
}
