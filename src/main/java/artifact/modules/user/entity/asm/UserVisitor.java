package artifact.modules.user.entity.asm;

import artifact.modules.user.entity.User;

import org.objectweb.asm.*;

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

        ClassReader classReader = new ClassReader(User.class.getName());
        ClassWriter classWriter = new ClassWriter(classReader,ClassWriter.COMPUTE_MAXS);

        UserVisitor userVisitor = new UserVisitor(classWriter);
        classReader.accept(userVisitor,ClassReader.SKIP_DEBUG);

        classWriter.visitEnd();
    }

}


