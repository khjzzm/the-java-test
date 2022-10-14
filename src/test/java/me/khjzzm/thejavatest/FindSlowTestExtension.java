package me.khjzzm.thejavatest;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;

public class FindSlowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final long THRESHOLD = 1000L;

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        ExtensionContext.Store store = store(context);
        store.put("START_TIME", System.currentTimeMillis());
    }


    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Method requiredTestMethod = context.getRequiredTestMethod();
        String testMethodName = requiredTestMethod.getName();
        SlowTest annotation = requiredTestMethod.getAnnotation(SlowTest.class);
        ExtensionContext.Store store = store(context);
        long start_time = store.remove("START_TIME", long.class);
        long duration = System.currentTimeMillis() - start_time;
        if(duration > THRESHOLD && annotation == null){
            System.out.printf("Please consider mark method [%s] with @SlowTest.\n", testMethodName);
        }
    }


    private static ExtensionContext.Store store(ExtensionContext context) {
        String testClassName = context.getRequiredTestClass().getName();
        String testMethodName = context.getRequiredTestMethod().getName();
        ExtensionContext.Store store = context.getStore(ExtensionContext.Namespace.create(testClassName, testMethodName));
        return store;
    }

}
