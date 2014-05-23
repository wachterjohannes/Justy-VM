package at.fhv.justy.vm.stack;

public class StackFrame {
	private Stack stack;

	private int stackStart;
	private int stackSize;

	private int stackPointer;

	private int localVarStart;
	private int localVarSize;

	private int start;
	private int size;

	public StackFrame(Stack stack, int start, int localVarSize, int stackSize) {
		this.stack = stack;

		this.start = start;
		this.size = localVarSize + 1 + stackSize;
		
		this.localVarStart = start;
		this.localVarSize = localVarSize;
		
		this.stackPointer = localVarStart + localVarSize;
		
		this.stackStart = stackPointer + 1;
		this.stackSize = stackSize;
		
		this.stack.put(stackPointer, this.stackStart);
	}

	private int getStackPointer() {
		return this.stack.get(stackPointer);
	}

	private int increaseStackPointer() {
		int sp = this.getStackPointer();
		this.stack.put(stackPointer, sp + 1);
		return sp;
	}

	private int decreaseStackPointer() {
		int sp = this.getStackPointer() - 1;
		this.stack.put(stackPointer, sp);
		return sp;
	}

	public int getEnd() {
		return this.start + this.size;
	}

	public int getStart() {
		return start;
	}

	public int getSize() {
		return size;
	}

	public void push(int value) {
		int sp = this.increaseStackPointer();
		this.stack.put(sp, value);
	}

	public int pop() {
		int sp = this.decreaseStackPointer();
		int value = this.stack.get(sp);
		this.stack.put(sp, -1);
		return value;
	}

	public void setLocalVar(int address, int value) {
		this.stack.put(this.localVarStart + address, value);
	}

	public int getLocalVar(int address) {
		return this.stack.get(this.localVarStart + address);
	}
}
