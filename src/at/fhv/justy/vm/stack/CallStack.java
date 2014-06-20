package at.fhv.justy.vm.stack;

import java.util.LinkedList;

/**
 * Created by johannes on 20.06.14.
 */
public class CallStack {
    private int[] values = new int[1000];

    private LinkedList<StackFrame> frames = new LinkedList<>();

    public void set(int address, int value) {
        this.values[address] = value;
    }

    public int get(int address) {
        return this.values[address];
    }

    public void push(int value) {
        frames.getLast().push(value);
    }

    public int pop() {
        return frames.getLast().pop();
    }

    public void setLocalVar(int address, int value) {
        frames.getLast().setLocalVar(address, value);
    }

    public int getLocalVar(int address) {
        return frames.getLast().getLocalVar(address);
    }
}
