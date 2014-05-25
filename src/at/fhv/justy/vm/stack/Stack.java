package at.fhv.justy.vm.stack;

import java.nio.ByteBuffer;

import at.fhv.justy.vm.stack.StackEntry.Type;

public class Stack {
	private byte[] values = new byte[100000];
	private StackEntry[] entries = new StackEntry[100];

	private int highestAddress = 0;

	public void putInteger(int address, int value) {
		StackEntry entry;
		byte[] bytes = ByteBuffer.allocate(4).putInt(value).array();

		if (this.entries[address] != null
				&& entries[address].getType() == Type.integerType) {
			entry = this.entries[address];
		} else {
			entry = new StackEntry(highestAddress, bytes.length,
					Type.integerType);
			this.entries[address] = entry;
			this.highestAddress += bytes.length;
		}

		for (int i = 0; i < bytes.length; i++) {
			this.values[entry.getStartAddress() + i] = bytes[i];
		}
	}

	public int getInteger(int address) {
		StackEntry entry = this.entries[address];
		if (entry == null) {
			return 0;
		}

		byte[] bytes = new byte[4];
		for (int i = 0; i < 4; i++) {
			bytes[i] = this.values[entry.getStartAddress() + i];
		}

		return ByteBuffer.wrap(bytes).getInt();
	}
}
