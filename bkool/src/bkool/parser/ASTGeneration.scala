package bkool.parser
import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.ParserRuleContext
import java.io.{PrintWriter,File}
import org.antlr.v4.runtime.ANTLRFileStream
import bkool.utils._
import scala.collection.JavaConverters._
import org.antlr.v4.runtime.tree._

class ASTGeneration extends BKOOLBaseVisitor[Object] {

  override def visitProgram(ctx:BKOOLParser.ProgramContext) = {
    val classes = ctx.classes().asScala.map(visit).toList
    Program(classes.asInstanceOf[List[ClassDecl]])
  }
  
  override def visitClasses(ctx:BKOOLParser.ClassesContext) = {
    val name = Id(ctx.ID(0).getText)
    val parent = if (ctx.ID().size > 1) Id(ctx.ID(1).getText) else Id("")
    val decl = ctx.members().asScala.toList.foldRight(List[MemDecl]())((a,b)=>visit(a).asInstanceOf[List[MemDecl]] ++ b)
    
    ClassDecl(name, parent, decl)
  }
  
  override def visitMembers(ctx:BKOOLParser.MembersContext) = {
    if (ctx.methods() != null) visit(ctx.methods())
    else {
      val kind = if (ctx.STATIC() == null) Instance else Static
      val decl = visit(ctx.attributes()).asInstanceOf[List[Decl]]
      
      decl.map((x:Decl) => AttributeDecl(kind, x)).toList
    }
  }
  
  override def visitAttributes(ctx:BKOOLParser.AttributesContext) = {
    if (ctx.mutable_atrs() == null) visit(ctx.immutable_atrs())
    else visit(ctx.mutable_atrs())
  }
  
  override def visitMutable_atrs(ctx:BKOOLParser.Mutable_atrsContext) = {
    visit(ctx.para_list())
  }
  
  override def visitImmutable_atrs(ctx:BKOOLParser.Immutable_atrsContext) = {
    List(ConstDecl(Id(ctx.ID().getText), ctx.getChild(1).accept(this).asInstanceOf[Type],
        ctx.expression().accept(this).asInstanceOf[Expr]))
  }
  
  override def visitId_list(ctx:BKOOLParser.Id_listContext) = {
    ctx.ID().asScala.map((x:TerminalNode) => x.getText).toList
  }
  
  override def visitPrimitiveType(ctx:BKOOLParser.PrimitiveTypeContext) = {
    ctx.primitive_types().accept(this)
  }
  
  override def visitArrayType(ctx:BKOOLParser.ArrayTypeContext) = {
   ctx.array_types().accept(this) 
  }
  
  override def visitClassType(ctx:BKOOLParser.ClassTypeContext) = {
    ctx.class_types().accept(this)
  }
  
  override def visitPrimitive_types(ctx:BKOOLParser.Primitive_typesContext) = {
    if (ctx.INTEGER() != null) IntType
    else if (ctx.FLOAT() != null) FloatType
    else if (ctx.BOOL() != null) BoolType
    else StringType
  }
  
  override def visitArray_types(ctx:BKOOLParser.Array_typesContext) = {
    ArrayType(IntLiteral(ctx.INTLIT().getText.toInt), 
        ctx.getChild(0).accept(this).asInstanceOf[Type])
  }
  
  override def visitClass_types(ctx:BKOOLParser.Class_typesContext) = ClassType(ctx.ID().getText)
  
  override def visitMethods(ctx:BKOOLParser.MethodsContext) = {
    if (ctx.functions() != null) visit(ctx.functions())
    else visit(ctx.constructor())
  }
  
  override def visitFunctions(ctx:BKOOLParser.FunctionsContext) = {
    val funcType = if (ctx.`type`() == null) VoidType else visit(ctx.`type`()).asInstanceOf[Type]
    val kind = if (ctx.STATIC() == null) Instance else Static
    val name = Id(ctx.ID().getText)
    val param = visit(ctx.parameter_list()).asInstanceOf[List[VarDecl]]
    val paramDelc = param.map((x:VarDecl ) => ParamDecl(x.variable,x.varType))
    val body = visit(ctx.block_statement()).asInstanceOf[Stmt]
    
    List(MethodDecl(kind, name, paramDelc, funcType, body))
    
  }
  
  override def visitConstructor(ctx:BKOOLParser.ConstructorContext) = {
    val name = Id(ctx.ID().getText)
    val param = visit(ctx.parameter_list()).asInstanceOf[List[VarDecl]]
    val paramDelc = param.map((x:VarDecl ) => ParamDecl(x.variable,x.varType))
    val body = visit(ctx.block_statement()).asInstanceOf[Stmt]
    val kind = if (ctx.STATIC() == null) Instance else Static
    
    List(MethodDecl(kind, name, paramDelc, null, body))
  }
  
  override def visitParameter_list(ctx:BKOOLParser.Parameter_listContext) = {
    if (ctx.para_list() == null) List()
    else {
      ctx.para_list().asScala.toList.foldRight(List[VarDecl]())((a,b)=>visit(a).asInstanceOf[List[VarDecl]] ++ b)
    }
  }
  
  override def visitPara_list(ctx:BKOOLParser.Para_listContext) = {
    val idList = visit(ctx.id_list()).asInstanceOf[List[String]]
    val idType = visit(ctx.`type`()).asInstanceOf[Type]
    
    idList.map((x:String) => VarDecl(Id(x), idType))
  }
  
  override def visitBlock_statement(ctx:BKOOLParser.Block_statementContext) = {
    val statements = if (ctx.statements() != null) ctx.statements().asScala.map(visit).toList.asInstanceOf[List[Stmt]] else List()
    val attributes = if (ctx.attributes() != null) {
      ctx.attributes().asScala.toList.foldRight(List[Decl]())((a,b)=>visit(a).asInstanceOf[List[Decl]] ++ b)
    } else List()
    
    Block(attributes, statements)
  }
  
  override def visitAssignStm(ctx:BKOOLParser.AssignStmContext) = {
    ctx.assign_statement().accept(this)
  }
  
  override def visitIfStm(ctx:BKOOLParser.IfStmContext) = {
    ctx.if_statement().accept(this)
  }
  
  override def visitWhileStm(ctx:BKOOLParser.WhileStmContext) = {
    ctx.while_statement().accept(this)
  }
  
  override def visitBreakStm(ctx:BKOOLParser.BreakStmContext) = {
    ctx.break_statement().accept(this)
  }
  
  override def visitContinueStm(ctx:BKOOLParser.ContinueStmContext) = {
    ctx.continue_statement().accept(this)
  }
  
  override def visitReturnStm(ctx:BKOOLParser.ReturnStmContext) = {
    ctx.return_statement().accept(this)
  }
  
  override def visitMethodInvoStm(ctx:BKOOLParser.MethodInvoStmContext) = {
    ctx.method_invo_statement().accept(this)
  }
  
  override def visitBlockStm(ctx:BKOOLParser.BlockStmContext) = {
    ctx.block_statement().accept(this)
  }
  
  override def visitAssign_statement(ctx:BKOOLParser.Assign_statementContext) = {
    val lhs = visit(ctx.expression(0)).asInstanceOf[LHS]
    val expr = visit(ctx.expression(1)).asInstanceOf[Expr]
    
    Assign(lhs, expr)
  }
  
  override def visitIf_statement(ctx:BKOOLParser.If_statementContext) = {
    if (ctx.statements().size() > 1)
      If(ctx.expression().accept(this).asInstanceOf[Expr],
        ctx.statements(0).accept(this).asInstanceOf[Stmt],
        Some(ctx.statements(1).accept(this).asInstanceOf[Stmt]))
    else
      If(ctx.expression().accept(this).asInstanceOf[Expr],
          ctx.statements(0).accept(this).asInstanceOf[Stmt], None)
  }
  
  override def visitWhile_statement(ctx:BKOOLParser.While_statementContext) = {
    While(ctx.expression().accept(this).asInstanceOf[Expr],
        ctx.statements().accept(this).asInstanceOf[Stmt])
  }
  
  override def visitBreak_statement(ctx:BKOOLParser.Break_statementContext) = {
    Break
  }
  
  override def visitContinue_statement(ctx:BKOOLParser.Continue_statementContext) = {
    Continue
  }
  
  override def visitReturn_statement(ctx:BKOOLParser.Return_statementContext) = {
    Return(ctx.expression().accept(this).asInstanceOf[Expr])
  }
  
  override def visitMethod_invo_statement(ctx:BKOOLParser.Method_invo_statementContext) = {
    Call(ctx.expression().accept(this).asInstanceOf[Expr],
        Id(ctx.ID().getText), ctx.list_expr().accept(this).asInstanceOf[List[Expr]])
  }
  
  override def visitEqualExpr(ctx:BKOOLParser.EqualExprContext) = {
    BinaryOp(ctx.getChild(1).getText, ctx.expression(0).accept(this).asInstanceOf[Expr],
        ctx.expression(1).accept(this).asInstanceOf[Expr])
  }
  
  override def visitLogExpr(ctx:BKOOLParser.LogExprContext) = {
    BinaryOp(ctx.getChild(1).getText, ctx.expression(0).accept(this).asInstanceOf[Expr],
        ctx.expression(1).accept(this).asInstanceOf[Expr])
  }
  
  override def visitAddMathExpr(ctx:BKOOLParser.AddMathExprContext) = {
    BinaryOp(ctx.getChild(1).getText, ctx.expression(0).accept(this).asInstanceOf[Expr],
        ctx.expression(1).accept(this).asInstanceOf[Expr])
  }
  
  override def visitBraceExpr(ctx:BKOOLParser.BraceExprContext) = {
    ctx.expression().accept(this)
  }
  
  override def visitRelExpr(ctx:BKOOLParser.RelExprContext) = {
    BinaryOp(ctx.getChild(1).getText, ctx.expression(0).accept(this).asInstanceOf[Expr],
        ctx.expression(1).accept(this).asInstanceOf[Expr])
  }
  
  override def visitUnaLogExpr(ctx:BKOOLParser.UnaLogExprContext) = {
    UnaryOp(ctx.getChild(0).getText, ctx.expression().accept(this).asInstanceOf[Expr])
  }
  
  override def visitUnaExpr(ctx:BKOOLParser.UnaExprContext) = {
    UnaryOp(ctx.getChild(0).getText, ctx.expression().accept(this).asInstanceOf[Expr])
  }
  
  override def visitMulMathExpr(ctx:BKOOLParser.MulMathExprContext) = {
    BinaryOp(ctx.getChild(1).getText, ctx.expression(0).accept(this).asInstanceOf[Expr],
        ctx.expression(1).accept(this).asInstanceOf[Expr])
  }
  
  override def visitCreateObjExpr(ctx:BKOOLParser.CreateObjExprContext) = {
    val name = Id(ctx.ID().getText)
    val exprs = if(ctx.list_expr() == null) List() else visit(ctx.list_expr()).asInstanceOf[List[Expr]]
    
    NewExpr(name, exprs)
  }
  
  override def visitAccessExpr(ctx:BKOOLParser.AccessExprContext) = {
    if (ctx.list_expr() == null) FieldAccess(ctx.expression().accept(this).asInstanceOf[Expr],
                                              Id(ctx.ID().getText))
    else CallExpr(ctx.expression().accept(this).asInstanceOf[Expr], Id(ctx.ID().getText),
                                              ctx.list_expr().accept(this).asInstanceOf[List[Expr]])
  }
  
  override def visitIndexExpr(ctx:BKOOLParser.IndexExprContext) = {
    ArrayCell(ctx.expression(0).accept(this).asInstanceOf[Expr],
        ctx.expression(1).accept(this).asInstanceOf[Expr])
  }
  
  override def visitOperandLit(ctx:BKOOLParser.OperandLitContext) = {
    ctx.operands().accept(this)
  }
  
  override def visitBinStrExpr(ctx:BKOOLParser.BinStrExprContext) = {
    BinaryOp(ctx.getChild(1).getText, ctx.expression(0).accept(this).asInstanceOf[Expr],
        ctx.expression(1).accept(this).asInstanceOf[Expr])
  }
  
  override def visitIntLit(ctx:BKOOLParser.IntLitContext) = {
    IntLiteral(ctx.INTLIT().getText.toInt)
  }
  
  override def visitFloatLit(ctx:BKOOLParser.FloatLitContext) = {
    FloatLiteral(ctx.FLOATLIT().getText.toFloat)
  }
  
  override def visitBoolLit(ctx:BKOOLParser.BoolLitContext) = {
    BooleanLiteral(ctx.BOOLEANLIT().getText.toBoolean)
  }
  
  override def visitStringLit(ctx:BKOOLParser.StringLitContext) = {
    StringLiteral(ctx.STRINGLIT().getText)
  }
  
  override def visitIdLit(ctx:BKOOLParser.IdLitContext) = {
    Id(ctx.ID().getText)
  }
  
  override def visitNullLit(ctx:BKOOLParser.NullLitContext) = {
    NullLiteral
  }
  
  override def visitSelfLit(ctx:BKOOLParser.SelfLitContext) = {
    SelfLiteral
  }
  
  override def visitList_expr(ctx:BKOOLParser.List_exprContext) = {
    if (ctx.expr_list() == null) List()
    else ctx.expr_list().accept(this)
  }
  
  override def visitExpr_list(ctx:BKOOLParser.Expr_listContext) = {
    ctx.expression().asScala.map(visit).toList
  } 
}