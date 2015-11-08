// Generated from BKOOL.g4 by ANTLR 4.5.1

	package bkool.parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link BKOOLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface BKOOLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(BKOOLParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#classes}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClasses(BKOOLParser.ClassesContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#members}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMembers(BKOOLParser.MembersContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#attributes}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributes(BKOOLParser.AttributesContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#mutable_atrs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMutable_atrs(BKOOLParser.Mutable_atrsContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#immutable_atrs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImmutable_atrs(BKOOLParser.Immutable_atrsContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#id_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId_list(BKOOLParser.Id_listContext ctx);
	/**
	 * Visit a parse tree produced by the {@code primitiveType}
	 * labeled alternative in {@link BKOOLParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(BKOOLParser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link BKOOLParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(BKOOLParser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code classType}
	 * labeled alternative in {@link BKOOLParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassType(BKOOLParser.ClassTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#primitive_types}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitive_types(BKOOLParser.Primitive_typesContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#array_types}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_types(BKOOLParser.Array_typesContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#class_types}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClass_types(BKOOLParser.Class_typesContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#methods}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethods(BKOOLParser.MethodsContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#functions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctions(BKOOLParser.FunctionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#constructor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructor(BKOOLParser.ConstructorContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#parameter_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter_list(BKOOLParser.Parameter_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#para_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPara_list(BKOOLParser.Para_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#block_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock_statement(BKOOLParser.Block_statementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignStm(BKOOLParser.AssignStmContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStm(BKOOLParser.IfStmContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whileStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStm(BKOOLParser.WhileStmContext ctx);
	/**
	 * Visit a parse tree produced by the {@code breakStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStm(BKOOLParser.BreakStmContext ctx);
	/**
	 * Visit a parse tree produced by the {@code continueStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStm(BKOOLParser.ContinueStmContext ctx);
	/**
	 * Visit a parse tree produced by the {@code returnStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStm(BKOOLParser.ReturnStmContext ctx);
	/**
	 * Visit a parse tree produced by the {@code methodInvoStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodInvoStm(BKOOLParser.MethodInvoStmContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blockStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStm(BKOOLParser.BlockStmContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#assign_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign_statement(BKOOLParser.Assign_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#if_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_statement(BKOOLParser.If_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#while_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_statement(BKOOLParser.While_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#break_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreak_statement(BKOOLParser.Break_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#continue_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinue_statement(BKOOLParser.Continue_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#return_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_statement(BKOOLParser.Return_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#method_invo_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethod_invo_statement(BKOOLParser.Method_invo_statementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operandLit}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperandLit(BKOOLParser.OperandLitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addMathExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddMathExpr(BKOOLParser.AddMathExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaLogExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaLogExpr(BKOOLParser.UnaLogExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogExpr(BKOOLParser.LogExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binStrExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinStrExpr(BKOOLParser.BinStrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code indexExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexExpr(BKOOLParser.IndexExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mulMathExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulMathExpr(BKOOLParser.MulMathExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaExpr(BKOOLParser.UnaExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code createObjExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateObjExpr(BKOOLParser.CreateObjExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code relExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelExpr(BKOOLParser.RelExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code braceExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBraceExpr(BKOOLParser.BraceExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code accessExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccessExpr(BKOOLParser.AccessExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code equalExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualExpr(BKOOLParser.EqualExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intLit}
	 * labeled alternative in {@link BKOOLParser#operands}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntLit(BKOOLParser.IntLitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code floatLit}
	 * labeled alternative in {@link BKOOLParser#operands}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatLit(BKOOLParser.FloatLitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolLit}
	 * labeled alternative in {@link BKOOLParser#operands}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolLit(BKOOLParser.BoolLitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringLit}
	 * labeled alternative in {@link BKOOLParser#operands}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLit(BKOOLParser.StringLitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code selfLit}
	 * labeled alternative in {@link BKOOLParser#operands}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelfLit(BKOOLParser.SelfLitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nullLit}
	 * labeled alternative in {@link BKOOLParser#operands}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullLit(BKOOLParser.NullLitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idLit}
	 * labeled alternative in {@link BKOOLParser#operands}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdLit(BKOOLParser.IdLitContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#list_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList_expr(BKOOLParser.List_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#expr_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_list(BKOOLParser.Expr_listContext ctx);
}