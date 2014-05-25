package at.fhv.justy.vm.stack;

public class StackEntry {
	public enum Type {
		integerType, stringType, byteType, charType
	};

	private int startAddress;
	private int length;
	private Type type;
	private byte[] bytes;

	public StackEntry(int startAddress, int length, Type type, byte[] bytes) {
		this.startAddress = startAddress;
		this.length = length;
		this.type = type;
		this.bytes = bytes;
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

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
}
