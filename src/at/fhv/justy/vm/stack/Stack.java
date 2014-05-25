package at.fhv.justy.vm.stack;

import java.nio.ByteBuffer;

import at.fhv.justy.vm.stack.StackEntry.Type;

public class Stack {
	private StackEntry[] entries = new StackEntry[100];

	private int highestAddress = 0;

	private void createStackEntry(int address, byte[] bytes, Type type) {
		if (this.entries[address] != null) {
			StackEntry entry = this.entries[address];
			entry.setBytes(bytes);
		} else {
			this.entries[address] = new StackEntry(highestAddress,
					bytes.length, type, bytes);
			this.highestAddress += bytes.length;
		}
		System.out.println("HA: " + this.highestAddress);
	}

	public void free(int address) {
		this.entries[address] = null;
	}

	public void putInteger(int address, int value) {
		byte[] bytes = ByteBuffer.allocate(4).putInt(value).array();
		this.createStackEntry(address, bytes, Type.integerType);
	}

	public int getInteger(int address) {
		StackEntry entry = this.entries[address];
		if (entry == null) {
			return 0;
		}

		byte[] bytes = entry.getBytes();
		int result = ByteBuffer.wrap(bytes).getInt();
		return result;
	}
}
