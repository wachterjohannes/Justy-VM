package at.fhv.justy.vm.constant;

/**
 * Created by johannes on 20.06.14.
 */
public class Constant {
    private final String type;
    private final byte[] value;
    private final int id;

    public Constant(int id, byte[] value, String type) {
        this.id = id;
        this.value = value;
        this.type = type;
    }

    public byte[] getValue() {
        return value;
    }
}
