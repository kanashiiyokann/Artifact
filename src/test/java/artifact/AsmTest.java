package artifact;

import artifact.modules.user.entity.User;
import artifact.modules.user.entity.asm.UserVisitor;

public class AsmTest {

    public static void main(String[] args) throws Exception{

        UserVisitor.execute();

        User user=new User();
        user.setName("admin");

        System.out.println(user.getName());

    }
}
