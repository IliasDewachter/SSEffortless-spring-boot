package sseffortless.register.util;

import org.springframework.stereotype.Component;
import sseffortless.SSEPayload;
import sseffortless.annotations.SSEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ActionConverter {

    private Pattern pattern = Pattern.compile("[a-z][A-Z]");

    public String convertToAction(Class<? extends SSEPayload> payloadClass) {
        if (payloadClass.isAnnotationPresent(SSEvent.class)) {
            SSEvent ssEvent = payloadClass.getAnnotation(SSEvent.class);
            if (!ssEvent.action().equals("")) {
                return ssEvent.action();
            }
        }

        String action = payloadClass.getSimpleName();
        Matcher matcher = pattern.matcher(action);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String[] match = matcher.group().split("");
            matcher.appendReplacement(sb, match[0] + "_" + match[1]);
        }
        matcher.appendTail(sb);
        action = sb.toString().toUpperCase();
        return action;
    }

}
