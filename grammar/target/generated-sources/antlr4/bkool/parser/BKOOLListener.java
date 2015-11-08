// Generated from BKOOL.g4 by ANTLR 4.5.1

	package bkool.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

import bkool.parser.BKOOLParser;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link BKOOLParser}.
 */
public interface BKOOLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(BKOOLParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(BKOOLParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#classes}.
	 * @param ctx the parse tree
	 */
	void enterClasses(BKOOLParser.ClassesContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#classes}.
	 * @param ctx the parse tree
	 */
	void exitClasses(BKOOLParser.ClassesContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#members}.
	 * @param ctx the parse tree
	 */
	void enterMembers(BKOOLParser.MembersContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#members}.
	 * @param ctx the parse tree
	 */
	void exitMembers(BKOOLParser.MembersContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#attributes}.
	 * @param ctx the parse tree
	 */
	void enterAttributes(BKOOLParser.AttributesContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#attributes}.
	 * @param ctx the parse tree
	 */
	void exitAttributes(BKOOLParser.AttributesContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#mutable_atrs}.
	 * @param ctx the parse tree
	 */
	void enterMutable_atrs(BKOOLParser.Mutable_atrsContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#mutable_atrs}.
	 * @param ctx the parse tree
	 */
	void exitMutable_atrs(BKOOLParser.Mutable_atrsContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#immutable_atrs}.
	 * @param ctx the parse tree
	 */
	void enterImmutable_atrs(BKOOLParser.Immutable_atrsContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#immutable_atrs}.
	 * @param ctx the parse tree
	 */
	void exitImmutable_atrs(BKOOLParser.Immutable_atrsContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#id_list}.
	 * @param ctx the parse tree
	 */
	void enterId_list(BKOOLParser.Id_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#id_list}.
	 * @param ctx the parse tree
	 */
	void exitId_list(BKOOLParser.Id_listContext ctx);
	/**
	 * Enter a parse tree produced by the {@code primitiveType}
	 * labeled alternative in {@link BKOOLParser#type}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(BKOOLParser.PrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code primitiveType}
	 * labeled alternative in {@link BKOOLParser#type}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(BKOOLParser.PrimitiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link BKOOLParser#type}.
	 * @param ctx the parse tree
	 */
	void enterArrayType(BKOOLParser.ArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link BKOOLParser#type}.
	 * @param ctx the parse tree
	 */
	void exitArrayType(BKOOLParser.ArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code classType}
	 * labeled alternative in {@link BKOOLParser#type}.
	 * @param ctx the parse tree
	 */
	void enterClassType(BKOOLParser.ClassTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code classType}
	 * labeled alternative in {@link BKOOLParser#type}.
	 * @param ctx the parse tree
	 */
	void exitClassType(BKOOLParser.ClassTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#primitive_types}.
	 * @param ctx the parse tree
	 */
	void enterPrimitive_types(BKOOLParser.Primitive_typesContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#primitive_types}.
	 * @param ctx the parse tree
	 */
	void exitPrimitive_types(BKOOLParser.Primitive_typesContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#array_types}.
	 * @param ctx the parse tree
	 */
	void enterArray_types(BKOOLParser.Array_typesContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#array_types}.
	 * @param ctx the parse tree
	 */
	void exitArray_types(BKOOLParser.Array_typesContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#class_types}.
	 * @param ctx the parse tree
	 */
	void enterClass_types(BKOOLParser.Class_typesContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#class_types}.
	 * @param ctx the parse tree
	 */
	void exitClass_types(BKOOLParser.Class_typesContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#methods}.
	 * @param ctx the parse tree
	 */
	void enterMethods(BKOOLParser.MethodsContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#methods}.
	 * @param ctx the parse tree
	 */
	void exitMethods(BKOOLParser.MethodsContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#functions}.
	 * @param ctx the parse tree
	 */
	void enterFunctions(BKOOLParser.FunctionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#functions}.
	 * @param ctx the parse tree
	 */
	void exitFunctions(BKOOLParser.FunctionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#constructor}.
	 * @param ctx the parse tree
	 */
	void enterConstructor(BKOOLParser.ConstructorContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#constructor}.
	 * @param ctx the parse tree
	 */
	void exitConstructor(BKOOLParser.ConstructorContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#parameter_list}.
	 * @param ctx the parse tree
	 */
	void enterParameter_list(BKOOLParser.Parameter_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#parameter_list}.
	 * @param ctx the parse tree
	 */
	void exitParameter_list(BKOOLParser.Parameter_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#para_list}.
	 * @param ctx the parse tree
	 */
	void enterPara_list(BKOOLParser.Para_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#para_list}.
	 * @param ctx the parse tree
	 */
	void exitPara_list(BKOOLParser.Para_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#block_statement}.
	 * @param ctx the parse tree
	 */
	void enterBlock_statement(BKOOLParser.Block_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#block_statement}.
	 * @param ctx the parse tree
	 */
	void exitBlock_statement(BKOOLParser.Block_statementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterAssignStm(BKOOLParser.AssignStmContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitAssignStm(BKOOLParser.AssignStmContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterIfStm(BKOOLParser.IfStmContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitIfStm(BKOOLParser.IfStmContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterWhileStm(BKOOLParser.WhileStmContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitWhileStm(BKOOLParser.WhileStmContext ctx);
	/**
	 * Enter a parse tree produced by the {@code breakStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterBreakStm(BKOOLParser.BreakStmContext ctx);
	/**
	 * Exit a parse tree produced by the {@code breakStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitBreakStm(BKOOLParser.BreakStmContext ctx);
	/**
	 * Enter a parse tree produced by the {@code continueStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterContinueStm(BKOOLParser.ContinueStmContext ctx);
	/**
	 * Exit a parse tree produced by the {@code continueStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitContinueStm(BKOOLParser.ContinueStmContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterReturnStm(BKOOLParser.ReturnStmContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitReturnStm(BKOOLParser.ReturnStmContext ctx);
	/**
	 * Enter a parse tree produced by the {@code methodInvoStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterMethodInvoStm(BKOOLParser.MethodInvoStmContext ctx);
	/**
	 * Exit a parse tree produced by the {@code methodInvoStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitMethodInvoStm(BKOOLParser.MethodInvoStmContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blockStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterBlockStm(BKOOLParser.BlockStmContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blockStm}
	 * labeled alternative in {@link BKOOLParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitBlockStm(BKOOLParser.BlockStmContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#assign_statement}.
	 * @param ctx the parse tree
	 */
	void enterAssign_statement(BKOOLParser.Assign_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#assign_statement}.
	 * @param ctx the parse tree
	 */
	void exitAssign_statement(BKOOLParser.Assign_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#if_statement}.
	 * @param ctx the parse tree
	 */
	void enterIf_statement(BKOOLParser.If_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#if_statement}.
	 * @param ctx the parse tree
	 */
	void exitIf_statement(BKOOLParser.If_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#while_statement}.
	 * @param ctx the parse tree
	 */
	void enterWhile_statement(BKOOLParser.While_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#while_statement}.
	 * @param ctx the parse tree
	 */
	void exitWhile_statement(BKOOLParser.While_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#break_statement}.
	 * @param ctx the parse tree
	 */
	void enterBreak_statement(BKOOLParser.Break_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#break_statement}.
	 * @param ctx the parse tree
	 */
	void exitBreak_statement(BKOOLParser.Break_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#continue_statement}.
	 * @param ctx the parse tree
	 */
	void enterContinue_statement(BKOOLParser.Continue_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#continue_statement}.
	 * @param ctx the parse tree
	 */
	void exitContinue_statement(BKOOLParser.Continue_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#return_statement}.
	 * @param ctx the parse tree
	 */
	void enterReturn_statement(BKOOLParser.Return_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#return_statement}.
	 * @param ctx the parse tree
	 */
	void exitReturn_statement(BKOOLParser.Return_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#method_invo_statement}.
	 * @param ctx the parse tree
	 */
	void enterMethod_invo_statement(BKOOLParser.Method_invo_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#method_invo_statement}.
	 * @param ctx the parse tree
	 */
	void exitMethod_invo_statement(BKOOLParser.Method_invo_statementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code operandLit}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOperandLit(BKOOLParser.OperandLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code operandLit}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOperandLit(BKOOLParser.OperandLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addMathExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddMathExpr(BKOOLParser.AddMathExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addMathExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddMathExpr(BKOOLParser.AddMathExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaLogExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaLogExpr(BKOOLParser.UnaLogExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaLogExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaLogExpr(BKOOLParser.UnaLogExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLogExpr(BKOOLParser.LogExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLogExpr(BKOOLParser.LogExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binStrExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinStrExpr(BKOOLParser.BinStrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binStrExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinStrExpr(BKOOLParser.BinStrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code indexExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIndexExpr(BKOOLParser.IndexExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code indexExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIndexExpr(BKOOLParser.IndexExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mulMathExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMulMathExpr(BKOOLParser.MulMathExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mulMathExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMulMathExpr(BKOOLParser.MulMathExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaExpr(BKOOLParser.UnaExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaExpr(BKOOLParser.UnaExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code createObjExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCreateObjExpr(BKOOLParser.CreateObjExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code createObjExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCreateObjExpr(BKOOLParser.CreateObjExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterRelExpr(BKOOLParser.RelExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitRelExpr(BKOOLParser.RelExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code braceExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBraceExpr(BKOOLParser.BraceExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code braceExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBraceExpr(BKOOLParser.BraceExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code accessExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAccessExpr(BKOOLParser.AccessExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code accessExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAccessExpr(BKOOLParser.AccessExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equalExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEqualExpr(BKOOLParser.EqualExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equalExpr}
	 * labeled alternative in {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEqualExpr(BKOOLParser.EqualExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intLit}
	 * labeled alternative in {@link BKOOLParser#operands}.
	 * @param ctx the parse tree
	 */
	void enterIntLit(BKOOLParser.IntLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intLit}
	 * labeled alternative in {@link BKOOLParser#operands}.
	 * @param ctx the parse tree
	 */
	void exitIntLit(BKOOLParser.IntLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code floatLit}
	 * labeled alternative in {@link BKOOLParser#operands}.
	 * @param ctx the parse tree
	 */
	void enterFloatLit(BKOOLParser.FloatLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code floatLit}
	 * labeled alternative in {@link BKOOLParser#operands}.
	 * @param ctx the parse tree
	 */
	void exitFloatLit(BKOOLParser.FloatLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolLit}
	 * labeled alternative in {@link BKOOLParser#operands}.
	 * @param ctx the parse tree
	 */
	void enterBoolLit(BKOOLParser.BoolLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolLit}
	 * labeled alternative in {@link BKOOLParser#operands}.
	 * @param ctx the parse tree
	 */
	void exitBoolLit(BKOOLParser.BoolLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringLit}
	 * labeled alternative in {@link BKOOLParser#operands}.
	 * @param ctx the parse tree
	 */
	void enterStringLit(BKOOLParser.StringLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringLit}
	 * labeled alternative in {@link BKOOLParser#operands}.
	 * @param ctx the parse tree
	 */
	void exitStringLit(BKOOLParser.StringLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code selfLit}
	 * labeled alternative in {@link BKOOLParser#operands}.
	 * @param ctx the parse tree
	 */
	void enterSelfLit(BKOOLParser.SelfLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code selfLit}
	 * labeled alternative in {@link BKOOLParser#operands}.
	 * @param ctx the parse tree
	 */
	void exitSelfLit(BKOOLParser.SelfLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullLit}
	 * labeled alternative in {@link BKOOLParser#operands}.
	 * @param ctx the parse tree
	 */
	void enterNullLit(BKOOLParser.NullLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullLit}
	 * labeled alternative in {@link BKOOLParser#operands}.
	 * @param ctx the parse tree
	 */
	void exitNullLit(BKOOLParser.NullLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idLit}
	 * labeled alternative in {@link BKOOLParser#operands}.
	 * @param ctx the parse tree
	 */
	void enterIdLit(BKOOLParser.IdLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idLit}
	 * labeled alternative in {@link BKOOLParser#operands}.
	 * @param ctx the parse tree
	 */
	void exitIdLit(BKOOLParser.IdLitContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#list_expr}.
	 * @param ctx the parse tree
	 */
	void enterList_expr(BKOOLParser.List_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#list_expr}.
	 * @param ctx the parse tree
	 */
	void exitList_expr(BKOOLParser.List_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link BKOOLParser#expr_list}.
	 * @param ctx the parse tree
	 */
	void enterExpr_list(BKOOLParser.Expr_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link BKOOLParser#expr_list}.
	 * @param ctx the parse tree
	 */
	void exitExpr_list(BKOOLParser.Expr_listContext ctx);
}