package artifact.modules.initializer;

/**
 * @author DGG-S27-D-20
 */
public abstract class AccountInitializer {


    public void init() throws Exception {
        if (checkExist()) {
            throw new Exception("account already exsit!");
        }
        initPeriod();
        initSubject();
        initTemplate();
    }

    protected boolean checkExist() {
        System.out.println("base check exist.");
        return false;
    }

    protected void initPeriod() {
        System.out.println("base init period.");
    }

    abstract void initSubject();

    protected void initTemplate() {
        System.out.println("base init template.");
    }

}
