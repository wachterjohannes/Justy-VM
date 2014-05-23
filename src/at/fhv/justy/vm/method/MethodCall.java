package at.fhv.justy.vm.method;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import at.fhv.justy.vm.VirtualMachine;
import at.fhv.justy.vm.stack.Stack;

public class MethodCall {
	private VirtualMachine vm;
	private Stack stack;
	private Method method;

	public MethodCall(VirtualMachine vm, Stack stack, Method method) {
		this.vm = vm;
		this.stack = stack;
		this.method = method;
	}

	public void invoke() {
		this.stack.createStackFrame(method.getParamSize(),
				method.getMaxLocals(), method.getMaxStack());

		this.run();
	}

	private void run() {
		int line = 0;
		while (line < method.getCode().length) {
			String codeLine = method.getCode(line);
			String[] parts = codeLine.split(" ");
			System.out.println(codeLine);
			switch (parts[0]) {
			case "LDC_W":
				line = this.ldc_w(line, Integer.parseInt(parts[1]));
				break;
			case "ILOAD":
				line = this.iload(line, Integer.parseInt(parts[1]));
				break;
			case "GETSTATIC":
				line = this.getstatic(line, Integer.parseInt(parts[1]));
				break;
			case "IADD":
				line = this.iadd(line);
				break;
			case "IMUL":
				line = this.imul(line);
				break;
			case "ISTORE":
				line = this.istore(line, Integer.parseInt(parts[1]));
				break;
			case "PUTSTATIC":
				line = this.putstatic(line, Integer.parseInt(parts[1]));
				break;
			case "INVOKESTATIC":
				line = this.invokestatic(line, Integer.parseInt(parts[1]));
				break;
			case "RETURN":
				line = this.simpleReturn(line);
				break;
			case "IRETURN":
				line = this.ireturn(line);
				break;
			case "IF_ICMPEQ":
				line = this.if_icmpeq(line, parts[1]);
				break;
			case "IF_ICMPNE":
				line = this.if_icmpne(line, parts[1]);
				break;
			case "GOTO":
				line = this.simpleGoto(line, parts[1]);
				break;
			case "NOP":
				line++;
				break;
			default:
				throw new NotImplementedException();
			}
			System.out.println(stack.toString() + "\n");
		}
	}

	// goto
	private int simpleGoto(int line, String label) {
		return this.method.getCodeLineByLabel(label);
	}

	// comparison
	private int if_icmpne(int line, String label) {
		int value1 = stack.pop();
		int value2 = stack.pop();

		// different value jump to label
		if (value1 != value2) {
			return this.method.getCodeLineByLabel(label);
		} else {
			return line + 1;
		}
	}

	private int if_icmpeq(int line, String label) {
		int value1 = stack.pop();
		int value2 = stack.pop();

		// same value jump to label
		if (value1 == value2) {
			return this.method.getCodeLineByLabel(label);
		} else {
			return line + 1;
		}
	}

	// constants
	private int ldc_w(int line, int address) {
		int value = stack.getConstant(address);
		stack.push(value);
		return line + 1;
	}

	// global vars
	private int putstatic(int line, int address) {
		int value = stack.pop();
		this.stack.setGlobalVar(address, value);
		return line + 1;
	}

	private int getstatic(int line, int address) {
		int value = stack.getGlobalVar(address);
		this.stack.push(value);
		return line + 1;
	}

	// arithmetic
	private int iadd(int line) {
		int value1 = stack.pop();
		int value2 = stack.pop();
		int result = value1 + value2;
		stack.push(result);
		return line + 1;
	}

	private int imul(int line) {
		int value1 = stack.pop();
		int value2 = stack.pop();
		int result = value1 * value2;
		stack.push(result);
		return line + 1;
	}

	// local var
	private int istore(int line, int address) {
		int value = stack.pop();
		stack.setLocalVar(address, value);
		return line + 1;
	}

	private int iload(int line, int address) {
		int value = stack.getLocalVar(address);
		stack.push(value);
		return line + 1;
	}

	// call function
	private int invokestatic(int line, int address) {
		vm.invoke(address);
		return line + 1;
	}

	// returns
	private int simpleReturn(int line) {
		// break this method
		this.stack.destroyStackFrame();
		return Integer.MAX_VALUE;
	}

	private int ireturn(int line) {
		int value = stack.pop();

		// break this method
		this.stack.destroyStackFrame();
		this.stack.push(value);
		return Integer.MAX_VALUE;
	}
}
