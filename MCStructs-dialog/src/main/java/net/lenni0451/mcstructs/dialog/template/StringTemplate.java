package net.lenni0451.mcstructs.dialog.template;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class StringTemplate {

    private static final int MAX_LENGTH = 2_000_000;

    public static StringTemplate parse(final String input) {
        List<String> segments = new ArrayList<>();
        List<String> variables = new ArrayList<>();
        int length = input.length();
        int current = 0;
        int variableStart = input.indexOf('$');
        while (variableStart != -1) {
            if (variableStart != length - 1 && input.charAt(variableStart + 1) == '(') {
                segments.add(input.substring(current, variableStart));
                int variableEnd = input.indexOf(')', variableStart + 1);
                if (variableEnd == -1) throw new IllegalArgumentException("Unterminated macro variable");

                String variable = input.substring(variableStart + 2, variableEnd);
                if (isValidVariableName(variable)) throw new IllegalArgumentException("Invalid macro variable name: " + variable);

                variables.add(variable);
                current = variableEnd + 1;
                variableStart = input.indexOf('$', current);
            } else {
                variableStart = input.indexOf('$', variableStart + 1);
            }
        }

        if (current == 0) throw new IllegalArgumentException("No variables in macro");
        if (current != length) segments.add(input.substring(current));
        return new StringTemplate(segments, variables);
    }

    public static boolean isValidVariableName(final String string) {
        char[] chars = string.toCharArray();
        for (char c : chars) {
            if (!Character.isLetterOrDigit(c) && c != '_') return false;
        }
        return true;
    }


    private final List<String> segments;
    private final List<String> variables;

    public String substitute(final List<String> input) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < this.variables.size(); i++) {
            out.append(this.segments.get(i)).append(input.get(i));
            if (out.length() > MAX_LENGTH) throw new IllegalArgumentException("Output too long (> " + MAX_LENGTH + ")");
        }
        if (this.segments.size() > this.variables.size()) {
            out.append(this.segments.get(this.segments.size() - 1));
            if (out.length() > MAX_LENGTH) throw new IllegalArgumentException("Output too long (> " + MAX_LENGTH + ")");
        }
        return out.toString();
    }

}
