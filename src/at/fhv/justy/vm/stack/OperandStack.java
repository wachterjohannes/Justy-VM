package at.fhv.justy.vm.stack;

/**
 * Created by johannes on 20.06.14.
 */
public class OperandStack {
    StackFrame frame;
    int size;
    int topOfStack = 0;

    public OperandStack(StackFrame frame, int size) {
        this.frame = frame;
        this.size = size;
    }

    public void push(int value) {
        frame.setStack(topOfStack++, value);
    }

    public int pop() {
        return frame.getStack(topOfStack--);
    }
}
