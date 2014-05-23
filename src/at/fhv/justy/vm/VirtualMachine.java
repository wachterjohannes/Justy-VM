package at.fhv.justy.vm;

import java.util.HashMap;

import at.fhv.justy.vm.method.Method;
import at.fhv.justy.vm.stack.*;

public class VirtualMachine {
	public static void main(String[] args) {
		VirtualMachine vm = new VirtualMachine();
		vm.init();
	}

	private Stack stack;

	private HashMap<Integer, Method> methods = new HashMap<>();

	public VirtualMachine() {
	}

	public void init() {
		stack = new Stack(2);
		
		stack.setConstant(0, 10);
		stack.setConstant(1, 20);

		Method mainMethod = new Method("main", "()V", 2, 1, new String[] {
				"LDC_W 0", "LDC_W 1", "IADD", "ISTORE 0", "ILOAD 0",
				"PUTSTATIC 2" });
		this.methods.put(10, mainMethod);
		
		this.invoke(10);
	}

	public void invoke(int address) {
		Method method = this.methods.get(address);

		this.stack.createStackFrame(method.getParamSize(),
				method.getMaxLocals(), method.getMaxStack());

		this.run(method);
	}

	void run(Method method) {
		for (String c : method.getCode()) {
			String[] parts = c.split(" ");
			switch (parts[0]) {
			case "LDC_W":
				this.ldc_w(Integer.parseInt(parts[1]));
				break;
			case "ILOAD":
				this.iload(Integer.parseInt(parts[1]));
				break;
			case "GETSTATIC":
				this.getstatic(Integer.parseInt(parts[1]));
				break;
			case "IADD":
				this.iadd();
				break;
			case "ISTORE":
				this.istore(Integer.parseInt(parts[1]));
				break;
			case "PUTSTATIC":
				this.putstatic(Integer.parseInt(parts[1]));
				break;
			}
			System.out.println(stack.toString() + "\n");
		}
		System.out.println(stack.toString());
	}

	// constants
	private void ldc_w(int address) {
		int value = stack.getConstant(address);
		stack.push(value);
	}

	// global vars
	private void putstatic(int address) {
		int value = stack.pop();
		this.stack.setGlobalVar(address, value);
	}

	private void getstatic(int address) {
		int value = stack.getGlobalVar(address);
		this.stack.push(value);
	}

	// arithmetic
	private void iadd() {
		int value1 = stack.pop();
		int value2 = stack.pop();
		int result = value1 + value2;
		stack.push(result);
	}

	// local var
	private void istore(int address) {
		int value = stack.pop();
		stack.setLocalVar(address, value);
	}

	private void iload(int address) {
		int value = stack.getLocalVar(address);
		stack.push(value);
	}

	// call function
	private void invoke_static(int address) {
	}
}
