package at.fhv.justy.vm.stack;

import java.util.LinkedList;

public class ApplicationStack {

	private Stack stack;

	private LinkedList<StackFrame> stackFrames = new LinkedList<>();

	private int constantPoolStart;
	private int constantPoolSize;

	public ApplicationStack(int constantPoolSize) {
		this.stack = new Stack();

		this.constantPoolStart = 0;
		this.constantPoolSize = constantPoolSize;
	}

	private int getHighest() {
		if (stackFrames.size() == 0) {
			return this.constantPoolSize + constantPoolStart;
		}
		StackFrame stackFrame = this.stackFrames.getLast();
		return stackFrame.getEnd();
	}

	public void createStackFrame(int paramSize, int localVarSize, int stackSize) {
		int highest = this.getHighest();

		if (this.stackFrames.size() > 0) {
			highest = this.stackFrames.getLast().getStackPointer();
		}
		for (int i = 0; i < paramSize; i++) {
			this.pop();
		}

		StackFrame stackFrame = new StackFrame(this, highest - paramSize,
				localVarSize, stackSize);
		this.stackFrames.addLast(stackFrame);
	}

	public void destroyStackFrame() {
		this.stackFrames.removeLast();
	}

	public int get(int address) {
		int value = (int) this.stack.get(address);
		return value;
	}

	public void put(int address, int value) {
		this.stack.put(address, value);
	}

	public void addConstant(int address, int value) {
		this.put(constantPoolStart + address, value);
	}

	public void setConstant(int address, int value) {
		this.put(this.constantPoolStart + address, value);
	}

	public int getConstant(int address) {
		return this.get(this.constantPoolStart + address);
	}

	public void push(int value) {
		this.stackFrames.getLast().push(value);
	}

	public int pop() {
		return this.stackFrames.getLast().pop();
	}

	public void setLocalVar(int address, int value) {
		this.stackFrames.getLast().setLocalVar(address, value);
	}

	public int getLocalVar(int address) {
		return this.stackFrames.getLast().getLocalVar(address);
	}

	public void setGlobalVar(int address, int value) {
		this.setConstant(address, value);
	}

	public int getGlobalVar(int address) {
		return this.getConstant(address);
	}

	public String getString(int start, int size) {
		StringBuilder sb = new StringBuilder();
		for (int i = start; i < start + size; i++) {
			sb.append(i);
			sb.append(": ");
			sb.append(this.get(i));
			sb.append("\n");
		}
		return sb.toString();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (StackFrame stackFrame : this.stackFrames) {
			sb.append(stackFrame.toString());
		}
		return sb.toString();
	}
}
