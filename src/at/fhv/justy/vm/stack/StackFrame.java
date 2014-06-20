package at.fhv.justy.vm.stack;

public class StackFrame {
	private ApplicationStack stack;

	private int stackStart;
	private int stackSize;

	private int stackPointer;

	private int localVarStart;
	private int localVarSize;

	private int start;
	private int size;

	public StackFrame(ApplicationStack stack, int start, int localVarSize,
			int stackSize) {
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

	public int getStackPointer()  {
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
		if (this.isStackFull()) {
			throw new RuntimeException("Stack is full");
		}
		int sp = this.increaseStackPointer();
		this.stack.put(sp, value);
	}

	public int pop() {
		if (this.isStackEmpty()) {
			throw new RuntimeException("Stack is empty");
		}
		int sp = this.decreaseStackPointer();
		int value = this.stack.get(sp);
		return value;
	}

	public boolean isStackEmpty() {
		return !(this.getStackPointer() > this.stackStart);
	}

	public boolean isStackFull() {
		return !(this.getStackPointer() < this.stackStart + this.stackSize);
	}

	public void setLocalVar(int address, int value) {
		if (address >= this.localVarSize) {
			throw new RuntimeException("No local var");
		}
		this.stack.put(this.localVarStart + address, value);
	}

	public int getLocalVar(int address) {
		if (address >= this.localVarSize) {
			throw new RuntimeException("No local var");
		}
		return this.stack.get(this.localVarStart + address);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Start StackFrame\n");
		sb.append("LocalVars:\n");
		sb.append(this.stack.getString(this.start, localVarSize));

		sb.append("StackPointer:\n");
		sb.append(this.stack.getString(this.stackPointer, 1));

		sb.append("Stack:\n");
		sb.append(this.stack.getString(this.stackStart, stackSize));
		sb.append("End StackFrame\n-------\n");

		return sb.toString();
	}
}
