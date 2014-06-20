package at.fhv.justy.vm;

import at.fhv.justy.vm.stack.CallStack;

/**
 * Created by johannes on 20.06.14.
 */
public class Thread {

    private Integer pc;

    private VirtualMachine virtualMachine;

    private CallStack callStack;

    public Thread(Integer start, VirtualMachine virtualMachine) {
        this.pc = start;
        this.virtualMachine = virtualMachine;
        this.callStack = new CallStack();
    }

    public void run() {
        pc = virtualMachine.execute(pc, this);
    }

    public void push(int value) {
        callStack.push(value);
    }

    public int pop() {
        return callStack.pop();
    }

    public void setLocalVar(int address, int value) {
        callStack.setLocalVar(address, value);
    }

    public int getLocalVar(int address) {
        return callStack.getLocalVar(address);
    }
}
