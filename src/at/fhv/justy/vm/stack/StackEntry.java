package at.fhv.justy.vm.stack;

public class StackEntry {
	public enum Type {
		integerType, stringType, byteType, charType
	};

	private int startAddress;
	private int length;
	private Type type;

	public StackEntry(int startAddress, int length, Type type) {
		this.startAddress = startAddress;
		this.length = length;
		this.type = type;
	}

	public int getStartAddress() {
		return startAddress;
	}

	public int getLength() {
		return length;
	}

	public Type getType() {
		return type;
	}
}
