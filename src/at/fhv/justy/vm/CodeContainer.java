package at.fhv.justy.vm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by johannes on 20.06.14.
 */
public class CodeContainer {
    private List<String> codeLines = new ArrayList<>();

    public Integer append( String[] codeLines) {
        Integer start = this.codeLines.size();
        this.codeLines.addAll(new ArrayList<>(Arrays.asList(codeLines)));

        return start;
    }

    public String getLine(Integer pc) {
        return this.codeLines.get(pc);
    }
}
