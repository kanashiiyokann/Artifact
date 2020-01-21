package artifact.modules.user.entity.asm;

import artifact.modules.user.entity.User;

import org.objectweb.asm.*;

import java.io.File;
import java.io.FileOutputStream;

public class UserVisitor extends ClassVisitor {

    public UserVisitor( ClassVisitor classVisitor) {
        super(Opcodes.ASM7, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {

        if("setName".equals(name)){
            return new MethodVisitor(Opcodes.ASM7) {
                @Override
                public void visitCode() {
                    this.visitFieldInsn(Opcodes.GETSTATIC,"java/lang/System","out","Ljava/io/PrintStream;");
                    this.visitLdcInsn("user name setter invoke");
                    this.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/io/PrintStream","println","(Ljava/lang/String;)V",false);
                    super.visitCode();
                }
            };
        }

        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    public static void execute() throws Exception{

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        UserVisitor userVisitor = new UserVisitor(classWriter);
        ClassReader classReader=new ClassReader("artifact.modules.user.entity.User");
        classReader.accept(userVisitor,0);

        File file=new File("artifact/modules/user/entity/User.class");
        String path=file.getParent();
        File parent=new File(path);
        parent.mkdirs();
        file.createNewFile();
        FileOutputStream fileOutputStream=new FileOutputStream(file);
        fileOutputStream.write(classWriter.toByteArray());

    }

}


