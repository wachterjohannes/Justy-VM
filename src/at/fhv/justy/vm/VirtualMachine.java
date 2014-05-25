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

	private ApplicationStack stack;

	private HashMap<Integer, Method> methods = new HashMap<>();

	public VirtualMachine() {
	}

	public void init() {
		stack = new ApplicationStack(3);

		stack.setConstant(0, 1);
		stack.setConstant(1, 0);
		stack.setConstant(2, 30);
		stack.setConstant(3, 8);

		Method mainMethod = new Method("main", "()V", 2, 1, new String[] {
				"LDC_W 3", "INVOKESTATIC 12", "ISTORE 0" });
		this.methods.put(10, mainMethod);

		String codeLines = "    LDC_W 1\n" + "    ISTORE 1\n"
				+ "    LDC_W 0\n" + "    ISTORE 2\n" + "    LDC_W 1\n"
				+ "    ISTORE 3\n" + "    LDC_W 0\n" + "    ISTORE 4\n"
				+ "L1: NOP\n" + "    ILOAD 2\n" + "    ILOAD 0\n"
				+ "    IF_ICMPEQ L2\n" + "    ILOAD 3\n" + "    ILOAD 4\n"
				+ "    IADD\n" + "    ISTORE 5\n" + "    ILOAD 4\n"
				+ "    ISTORE 3\n" + "    ILOAD 5\n" + "    ISTORE 4\n"
				+ "    ILOAD 2\n" + "    LDC_W 0\n" + "    IADD\n"
				+ "    ISTORE 2\n" + "    GOTO L1\n" + "L2: NOP\n"
				+ "    ILOAD 4\n" + "    IRETURN";

		Method fibonacciMethod = new Method("fibonacci", "(I)I", 10, 6,
				codeLines.split("\n"));
		this.methods.put(12, fibonacciMethod);

		this.invoke(10);
	}

	public void invoke(int address) {
		Method method = this.methods.get(address);
		MethodCall call = new MethodCall(this, this.stack, method);
		call.invoke();
	}
}
