package artifact;

import artifact.modules.user.entity.User;
import artifact.modules.user.entity.asm.UserVisitor;

public class AsmTest {

    public static void main(String[] args) throws Exception{

        UserVisitor.execute();

        User user=new User();
        int i=20;
        while (i-->0){
            user.setName("admin".concat(String.valueOf(i)));
        }

        System.out.println(user.getName());

    }
}
