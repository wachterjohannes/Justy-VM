package at.fhv.justy.vm.constant;

import java.nio.ByteBuffer;
import java.util.HashMap;

/**
 * Created by johannes on 20.06.14.
 */
public class ConstantPool {
    private HashMap<Integer, Constant> constants = new HashMap<>();

    public Integer add(byte[] value, String type) {
        // TODO search for value and return the index if exists
        int id = constants.size();

        constants.put(id, new Constant(id, value, type));

        return id;
    }

    public int getIntegerConstant(int address) {
        return ByteBuffer.wrap(this.constants.get(address).getValue()).getInt();
    }
}
