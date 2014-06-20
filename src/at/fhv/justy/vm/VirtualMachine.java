package at.fhv.justy.vm;

import at.fhv.justy.vm.clazz.Clazz;
import at.fhv.justy.vm.clazz.Loader;
import at.fhv.justy.vm.constant.ConstantPool;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VirtualMachine {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        VirtualMachine vm = new VirtualMachine();
        vm.init();

        vm.run();
    }

    private CodeContainer codeContainer = new CodeContainer();
    private ConstantPool constantPool = new ConstantPool();
    private List<Clazz> clazzes = new ArrayList<>();
    private List<Thread> threads = new ArrayList<>();

	public VirtualMachine() {
	}

    public void init() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        // init loader
        Loader clazzLoader = new Loader();

        // init class
        Clazz clazz = clazzLoader.load("just/fibonacci.jl", codeContainer, constantPool);
        clazzes.add(clazz);

        // init thread
        threads.add(new Thread(clazz.getMainMethod().getStart(), this));
    }

    public void run() {
        while (true) {
            threads.get(0).run();
        }
    }

    public Integer execute(Integer pc, Thread thread) {
        String codeLine = codeContainer.getLine(pc);
        String[] parts = codeLine.split(" ");

        switch (parts[0]) {
            case "LDC_W":
                pc = this.ldc_w(thread, pc, Integer.parseInt(parts[1]));
                break;
        }

        return pc;
    }

    // constants
    private int ldc_w(Thread thread, int pc, int address) {
        int value = this.constantPool.getIntegerConstant(address);
        thread.push(value);
        return pc + 1;
    }
}
