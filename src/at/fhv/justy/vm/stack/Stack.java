package at.fhv.justy.vm.stack;

import java.util.LinkedList;

public class Stack {
	private int[] values;

	private LinkedList<StackFrame> stackFrames = new LinkedList<>();

	private int constantPoolStart;
	private int constantPoolSize;

	public Stack(int constantPoolSize) {
		this.values = new int[100];

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

	public void addConstant(int address, int value) {
		this.put(constantPoolStart+address, value);
	}

	public void createStackFrame(int paramSize, int localVarSize, int stackSize) {
		StackFrame stackFrame = new StackFrame(this, this.getHighest()
				- paramSize, localVarSize, stackSize);
		this.stackFrames.add(stackFrame);
	}

	public int get(int address) {
		int value = this.values[address];
		return value;
	}

	public void put(int address, int value) {
		this.values[address] = value;
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
		return this.getString(0, this.getHighest());
	}
}
