package at.fhv.justy.vm.stack;

import java.nio.ByteBuffer;

import at.fhv.justy.vm.stack.StackEntry.Type;

public class Stack {
	private StackEntry[] entries = new StackEntry[100];

	private void createStackEntry(int address, byte[] bytes, Type type) {
		if (this.entries[address] != null) {
			StackEntry entry = this.entries[address];
			entry.setType(type);
			entry.setBytes(bytes);
		} else {
			this.entries[address] = new StackEntry(bytes, type);
		}
	}

	public void put(int address, byte value) {
		byte[] bytes = new byte[] { value };
		this.createStackEntry(address, bytes, Type.byteType);
	}

	public void put(int address, char value) {
		byte[] bytes = new byte[] { (byte) value };
		this.createStackEntry(address, bytes, Type.charType);
	}

	public void put(int address, String value) {
		byte[] bytes = value.getBytes();
		this.createStackEntry(address, bytes, Type.stringType);
	}

	public void put(int address, int value) {
		byte[] bytes = ByteBuffer.allocate(4).putInt(value).array();
		this.createStackEntry(address, bytes, Type.integerType);
	}

	public Object get(int address) {
		StackEntry entry = this.entries[address];
		if (entry != null) {
			switch (entry.getType()) {
			case stringType:
				return this.getString(address);
			case integerType:
				return this.getInteger(address);
			case byteType:
				return this.getByte(address);
			case charType:
				return this.getChar(address);
			}
		}

		// default value of stack is 0
		return 0;
	}

	public byte getByte(int address) {
		StackEntry entry = this.entries[address];
		if (entry == null) {
			return 0;
		}

		byte[] bytes = entry.getBytes();
		return (byte) bytes[0];
	}

	public char getChar(int address) {
		StackEntry entry = this.entries[address];
		if (entry == null) {
			return 0;
		}

		byte[] bytes = entry.getBytes();
		return (char) bytes[0];
	}

	public String getString(int address) {
		StackEntry entry = this.entries[address];
		if (entry == null) {
			return "";
		}

		byte[] bytes = entry.getBytes();
		return new String(bytes);
	}

	public int getInteger(int address) {
		StackEntry entry = this.entries[address];
		if (entry == null) {
			return 0;
		}

		byte[] bytes = entry.getBytes();
		return ByteBuffer.wrap(bytes).getInt();
	}
}
