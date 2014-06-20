package at.fhv.justy.vm.clazz;

/**
 * Created by johannes on 20.06.14.
 */
public class Clazz {
    private Field[] fields;

    private Method[] methods;

    private String name;

    public Clazz(String name, Method[] methods, Field[] fields) {
        this.fields = fields;
        this.name = name;
        this.methods = methods;
    }

    public Method getMainMethod() {
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals("main")) {
                return methods[i];
            }
        }

        return null;
    }
}
