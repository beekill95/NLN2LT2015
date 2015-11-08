.source a.java
.class public a
.super java/lang/Object

.method public static main([Ljava/lang/String;)V
.var 0 is args [Ljava/lang/String; from Label0 to Label1
Label0:
	iconst_5
	i2f
	iconst_5
	ineg
	i2f
	ldc 4.0
	fmul
	ldc 5.0
	fdiv
	fadd
	iconst_5
	i2f
	fadd
	invokestatic io/writeFloatLn(F)V
Label1:
	return
.limit stack 3
.limit locals 1
.end method

.method public <init>()V
.var 0 is this La; from Label0 to Label1
Label0:
	aload_0
	invokespecial java/lang/Object/<init>()V
Label1:
	return
.limit stack 1
.limit locals 1
.end method
