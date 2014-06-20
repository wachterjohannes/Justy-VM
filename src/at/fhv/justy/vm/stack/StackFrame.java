package at.fhv.justy.vm.stack;

/**
 * Created by johannes on 20.06.14.
 */
public class StackFrame {
    private int start;
    private int paramCount;
    private int localsCount;
    private OperandStack operandStack;
    private CallStack callStack;

    public StackFrame(int start, int paramCount, int localsCount, int stackSize, CallStack callStack) {
        this.start = start;
        this.paramCount = paramCount;
        this.localsCount = localsCount;
        this.callStack = callStack;

        this.operandStack = new OperandStack(this, stackSize);
    }

    public void push(int value) {
        operandStack.push(value);
    }

    public int pop() {
        return operandStack.pop();
    }

    public void setLocalVar(int address, int value) {
        set(address, value);
    }

    public int getLocalVar(int address) {
        return get(address);
    }

    public void set(int address, int value) {
        callStack.set(this.start + address, value);
    }

    public int get(int address) {
        return callStack.get(this.start + address);
    }

    public void setStack(int address, int value) {
        set(address + paramCount + localsCount, value);
    }

    public int getStack(int address) {
        return get(address + paramCount + localsCount);
    }
}
