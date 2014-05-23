package at.fhv.justy.vm.method;

public class Method {
	private int paramSize;
	private int maxStack;
	private int maxLocals;

	private String name;
	private String[] code;

	private Character retunValue;

	public Method(String name, String descriptor, int maxStack, int maxLocals,
			String[] code) {
		this.name = name;
		this.retunValue = descriptor.charAt(descriptor.length() - 1);
		this.paramSize = descriptor.length() - 3;

		this.maxStack = maxStack;
		this.maxLocals = maxLocals;

		this.code = code;
	}

	public String getName() {
		return name;
	}

	public int getParamSize() {
		return paramSize;
	}

	public int getMaxLocals() {
		return maxLocals;
	}

	public int getMaxStack() {
		return maxStack;
	}

	public Character getRetunValue() {
		return retunValue;
	}

	public String getCode(int line) {
		return this.code[line];
	}

	public String[] getCode() {
		return this.code;
	}
}
