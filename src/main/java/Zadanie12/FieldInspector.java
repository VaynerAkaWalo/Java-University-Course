package Zadanie12;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FieldInspector implements FieldInspectorInterface{
    @Override
    public Collection<FieldInfo> inspect(String className) {
        try {
            List<FieldInfo> result = new ArrayList<>();
            Class inspected = Class.forName(className);
            Field[] fields = inspected.getFields();
            for (Field field : fields) {
                if(field.getDeclaringClass().equals(inspected)) {
                    int annotation = -1;
                    if(field.getAnnotation(FieldVersion.class) != null) {
                        annotation = field.getAnnotation(FieldVersion.class).version();
                    }

                    FieldInfo info;
                    if (annotation == -1) {
                        info = new FieldInfo(checkField(field), field.getName());
                    }
                    else {
                        info = new FieldInfo(checkField(field), field.getName(), annotation);
                    }
                    result.add(info);
                }
            }
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Type checkField(Field field) {
        try {
            if(field.getType() == int.class || field.getType() == Integer.class) {
                return Type.INT;
            }
            if(field.getType() == long.class || field.getType() == Long.class) {
                return Type.LONG;
            }
            if(field.getType() == float.class|| field.getType() == Float.class) {
                return Type.FLOAT;
            }
            if(field.getType() == double.class || field.getType() == Double.class) {
                return Type.DOUBLE;
            }
        }
        catch (Exception e) {

        }

        return Type.OTHER;
    }

}
