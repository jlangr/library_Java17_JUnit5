package com.langrsoft.slides;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

class ProxyTest {
    ActionProxyFactory actionProxyFactory = new ActionProxyFactory();

    @Test
    void testMessageKey() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("foo", "200");
        HashMap<String, Map<String, Object>> extraContext = new HashMap<>();
        extraContext.put(ActionContext.PARAMETERS, params);
        try {
            ActionProxy proxy = actionProxyFactory.createActionProxy("",
               MockConfigurationProvider.VALIDATION_ACTION_NAME, extraContext);
            ValueStack stack = ActionContext.getContext().getValueStack();
            ActionContext.setContext(new ActionContext(stack.getContext()));
            ActionContext.getContext().setLocale(Locale.US);
            proxy.execute();
            assertThat(proxy.getAction().hasFieldErrors()).isTrue();
            Map<String, List<String>> errors = proxy.getAction().getFieldErrors();
            List<String> fooErrors = errors.get("foo");
            assertThat(fooErrors).hasSize(1);
            String errorMessage = fooErrors.get(0);
            assertThat(errorMessage).isNotNull();
            assertThat(errorMessage).isEqualTo("Foo Range Message");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}