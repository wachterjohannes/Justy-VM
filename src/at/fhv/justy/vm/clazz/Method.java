package at.fhv.justy.vm.clazz;

/**
 * Created by johannes on 20.06.14.
 */
public class Method {
    private Integer localsCount;
    private Integer stackCount;
    private String type;
    private String name;
    private Integer start;
    private Integer length;

    public Method(String name, String type, Integer stackCount, Integer localsCount, String code, int start, int length) {
        this.name = name;
        this.type = type;
        this.stackCount = stackCount;
        this.localsCount = localsCount;
        this.start = start;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public Integer getStart() {
        return start;
    }
}
