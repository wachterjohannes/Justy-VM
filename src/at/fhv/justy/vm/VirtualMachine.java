package at.fhv.justy.vm;

import java.util.HashMap;

import at.fhv.justy.vm.method.Method;
import at.fhv.justy.vm.method.MethodCall;
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
		stack = new Stack(3);

		stack.setConstant(0, 10);
		stack.setConstant(1, 20);
		stack.setConstant(2, 30);

		Method mainMethod = new Method("main", "()V", 2, 1, new String[] {
				"LDC_W 0", "LDC_W 1", "IADD", "ISTORE 0", "ILOAD 0",
				"PUTSTATIC 2", "GETSTATIC 2", "INVOKESTATIC 11", "ISTORE 0" });
		this.methods.put(10, mainMethod);

		Method doItMethod = new Method("doIt", "(I)I", 2, 1, new String[] {
				"LDC_W 2", "ILOAD 0", "IMUL", "IRETURN" });
		this.methods.put(11, doItMethod);

		this.invoke(10);
	}

	public void invoke(int address) {
		Method method = this.methods.get(address);
		MethodCall call = new MethodCall(this, this.stack, method);
		call.invoke();
	}
}
