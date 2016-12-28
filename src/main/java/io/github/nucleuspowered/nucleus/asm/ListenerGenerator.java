/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.asm;

import io.github.nucleuspowered.nucleus.Nucleus;
import io.github.nucleuspowered.nucleus.internal.annotations.ConditionalListener;
import io.github.nucleuspowered.nucleus.internal.listeners.ListenerBase;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;

/**
 * Class to generate listener {@link java.util.function.BiConsumer} objects to allow for
 * method level conditional listeners.
 */
public class ListenerGenerator implements Opcodes {

    private static final Random r = new Random();

    public static byte[] create(ListenerBase listenerBase, Method method, Listener annotation) throws Exception {

        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;
        AnnotationVisitor av0;

        String thisPackage = ListenerGenerator.class.getPackage().getName().replace('.','/');
        String className = thisPackage + "/listeners/gen_"
                + listenerBase.getClass().getSimpleName()
                + "_" + method.getName() + "_" + r.nextInt();
        String desc = "L" + className + ";";

        String nucleusName = Type.getInternalName(Nucleus.class);
        String nucleusDescriptor = Type.getDescriptor(Nucleus.class);

        String listenerBaseDescriptor = Type.getType(listenerBase.getClass()).getDescriptor();

        String methodDescriptor = Type.getMethodDescriptor(method);
        String eventDescriptor = Type.getDescriptor(method.getParameterTypes()[0]);

        String predicateNucleusDescriptor = "Ljava/util/function/Predicate<Lio/github/nucleuspowered/nucleus/Nucleus;>;";

        cw.visit(52, ACC_PUBLIC + ACC_SUPER, className, "Ljava/lang/Object;Lio/github/nucleuspowered/nucleus/internal/listeners/NucleusSingleEventListener<Lorg/spongepowered/api/event/Event;>;", "java/lang/Object", new String[]{"io/github/nucleuspowered/nucleus/internal/listeners/NucleusSingleEventListener"});

        {
            fv = cw.visitField(ACC_PRIVATE + ACC_FINAL, "base", listenerBaseDescriptor, null, null);
            fv.visitEnd();
        }
        {
            fv = cw.visitField(ACC_PRIVATE + ACC_FINAL, "condition", "Ljava/util/function/Predicate;", "", null);
            fv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "(" + listenerBaseDescriptor + "Ljava/util/function/Predicate;)V", "(" + listenerBaseDescriptor + predicateNucleusDescriptor + ">;)V", null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(17, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(18, l1);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitFieldInsn(PUTFIELD, className, "base", listenerBaseDescriptor);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLineNumber(19, l2);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 2);
            mv.visitFieldInsn(PUTFIELD, className, "condition", "Ljava/util/function/Predicate;");
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitLineNumber(20, l3);
            mv.visitInsn(RETURN);
            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitLocalVariable("this", desc, null, l0, l4, 0);
            mv.visitLocalVariable("base", desc, null, l0, l4, 1);
            mv.visitLocalVariable("condition", "Ljava/util/function/Predicate;", predicateNucleusDescriptor, l0, l4, 2);
            mv.visitMaxs(2, 3);
            mv.visitEnd();
        }

        {
            mv = cw.visitMethod(ACC_PUBLIC, "handle", methodDescriptor, null, new String[]{"java/lang/Exception"});

            {
                av0 = mv.visitAnnotation(Type.getDescriptor(Listener.class), true);
                av0.visitEnum("order", Type.getDescriptor(Order.class), annotation.order().name());
                av0.visit("beforeModifications", annotation.beforeModifications());
                av0.visitEnd();
            }

            {
                final MethodVisitor currentVisitor = mv;
                Arrays.stream(method.getDeclaredAnnotations())
                    .filter(x -> !(ConditionalListener.class.isAssignableFrom(x.annotationType())))
                    .filter(x -> {
                        Retention ret = x.annotationType().getAnnotation(Retention.class);
                        return ret != null && ret.value() == RetentionPolicy.RUNTIME;
                    }).forEach(anno -> {
                        AnnotationVisitor visitor =
                            currentVisitor.visitAnnotation(Type.getDescriptor(anno.annotationType()), true);

                    });
            }

            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(31, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, className, "base", listenerBaseDescriptor);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL, listenerBaseDescriptor, method.getName(), "(" + eventDescriptor + ")V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", desc, null, l0, l1, 0);
            mv.visitLocalVariable("event", eventDescriptor, null, l0, l1, 1);
            mv.visitMaxs(0, 2);
            mv.visitEnd();
        }

        {
            mv = cw.visitMethod(ACC_PUBLIC, "test", "(" + nucleusDescriptor + ")Z", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(34, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, className, "condition", "Ljava/util/function/Predicate;");
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/function/Predicate", "test", "(Ljava/lang/Object;)Z", true);
            mv.visitInsn(IRETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", desc, null, l0, l1, 0);
            mv.visitLocalVariable("nucleus", nucleusDescriptor, null, l0, l1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_BRIDGE + ACC_SYNTHETIC, "test", "(Ljava/lang/Object;)Z", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(18, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitTypeInsn(CHECKCAST, nucleusName);
            mv.visitMethodInsn(INVOKEVIRTUAL, className, "test",
                "(" + nucleusDescriptor + ")Z", false);
            mv.visitInsn(IRETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", desc, null, l0, l1, 0);
            mv.visitEnd();
        }


        cw.visitEnd();

        return cw.toByteArray();
    }
}
