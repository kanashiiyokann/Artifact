package artifact.modules.initializer;

public class SmallAccountInitializer extends AccountInitializer {
    @Override
    void initSubject() {
        System.out.println("init small account subject");
    }

    @Override
    protected void initPeriod() {
        System.out.println("init small account period");
    }
}
