public class HelperClass {


    public static void checkPermission(Class... expectedCallerClass) {
        StackTraceElement callerTrace = Thread.currentThread().getStackTrace()[3];
        for (Class expectedClass : expectedCallerClass) {
            if (callerTrace.getClassName().equals(expectedClass.getName())) {
                System.out.println("This employee can perform this action");
                return;
            }
        }
        throw new RuntimeException("This employee cannot perform this action.");
    }
}
