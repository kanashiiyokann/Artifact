package artifact.modules.user.entity.asm;

import artifact.modules.user.entity.User;

import org.objectweb.asm.*;

public class UserVisitor extends ClassVisitor {

    public UserVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    public static void execute() throws Exception{

        ClassReader classReader = new ClassReader(User.class.getName());
        ClassWriter classWriter = new ClassWriter(classReader,ClassWriter.COMPUTE_MAXS);
        UserVisitor userVisitor = new UserVisitor( Opcodes.ASM7,classWriter);
        classReader.accept(userVisitor,ClassReader.SKIP_DEBUG);
    }


    public static void  test() throws Exception{

        ClassReader classReader = new ClassReader(User.class.getName());
        ClassWriter classWriter = new ClassWriter(classReader,ClassWriter.COMPUTE_MAXS);
        UserVisitor userVisitor = new UserVisitor( Opcodes.ASM7,classWriter);
       // userVisitor.visitSource();
        MethodVisitor methodVisitor=    userVisitor.visitMethod(Opcodes.ACC_PUBLIC,"setName","(Ljava/lang/String;)V",null,null);
            methodVisitor.visitParameter();


        methodVisitor.visitEnd();
        userVisitor.visitEnd();
        classReader.accept(userVisitor,ClassReader.SKIP_DEBUG);

    }
}


