package at.fhv.justy.vm.stack;

public class StackEntry {
	public enum Type {
		integerType, stringType, charType, byteType
	};

	private int length;
	private Type type;
	private byte[] bytes;

	public StackEntry(byte[] bytes, Type type) {
		this.length = bytes.length;
		this.type = type;
		this.bytes = bytes;
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

	public void setType(Type type) {
		this.type = type;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
		this.length = bytes.length;
	}
}
