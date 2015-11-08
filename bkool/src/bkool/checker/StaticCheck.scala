package bkool.checker

/**
 * @author nhphung
 */
/**
 * Student Name : Nguyen Mai Bao Quan
 * Student ID : 51303225
 * */

import bkool.parser._
import bkool.utils._
import java.io.{PrintWriter, File}
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree._
import scala.collection.JavaConverters._

// Data structure for storing global variables, including class and class members
case class GlobalEnv(classList:List[ClassCon]) extends Context
case class ClassCon(name:String, parent:String, memberList: MemberList) extends Context
case class MemberDecl(name:String, memType:MemType, mKind: SIKind) extends Context

// for local variables
case class LocalEnv(className:String, localPara:ParaListSC, prevBlk:DeclListSC, curBlk:DeclListSC) extends Context // note: in localEnv: every new element
                                                                                                  // should be add in the first of the list

trait ContextList extends Context
case class MemberList(members:List[MemberDecl]) extends ContextList
case class ParaListSC(paraList:List[ParaSC]) extends ContextList
case class DeclListSC(declList:List[DeclSC]) extends ContextList
case class ArgumentList(list:List[MemType]) extends ContextList

// Boolean if for static or instance
trait MemType extends Context
case object IntTypeSC extends MemType {
  //override def equals(that:Any) = that.isInstanceOf[IntTypeSC.type] || that.isInstanceOf[IntLitSC.type]
}
case object FloatTypeSC extends MemType {
  //override def equals(that:Any) = that.isInstanceOf[FloatTypeSC.type] || that.isInstanceOf[FloatLitSC.type]
}
case object BoolTypeSC extends MemType {
  //override def equals(that:Any) = that.isInstanceOf[BoolTypeSC.type] || that.isInstanceOf[BoolLitSC.type]
}
case object StrTypeSC extends MemType {
  //override def equals(that:Any) = that.isInstanceOf[StrTypeSC.type] || that.isInstanceOf[StringLitSC.type]
}
case object VoidTypeSC extends MemType
case class ArrTypeSC(elemType:MemType, dim:Int) extends MemType
case class ClassTypeSC(name:String) extends MemType {
  override def equals(that:Any) = that.isInstanceOf[ClassTypeSC] && this.hashCode() == that.asInstanceOf[ClassTypeSC].hashCode()
  override def hashCode() = name.hashCode()
}
case class ClassSC(name:String) extends MemType // for access static attribute
case class MethodTypeSC(paras:ParaListSC, retType:MemType) extends MemType

case class ParaSC(name:String, paraType:MemType) extends Context
case class AttTypeSC(cType:MemType, expr:Expr) extends MemType

case class DeclSC(name:String, decl:AttTypeSC) extends MemType
case object SelfSC extends MemType
case object NullSC extends MemType

trait LiteralSC extends MemType
case object IntLitSC extends LiteralSC {
  //override def equals(that:Any) = that.isInstanceOf[IntTypeSC.type] || that.isInstanceOf[IntLitSC.type]
}
case object FloatLitSC extends LiteralSC {
  //override def equals(that:Any) = that.isInstanceOf[FloatTypeSC.type] || that.isInstanceOf[FloatLitSC.type]
}
case object BoolLitSC extends LiteralSC {
  //override def equals(that:Any) = that.isInstanceOf[BoolTypeSC.type] || that.isInstanceOf[BoolLitSC.type]
}
case object StringLitSC extends LiteralSC {
  //override def equals(that:Any) = that.isInstanceOf[StrTypeSC.type] || that.isInstanceOf[StringLitSC.type]
}

class StaticChecker(ast:AST) {
    def check() = {
        val bldecls = new GlobalEnvVisitor()
        val defaultEnv = GlobalEnv(List(ClassCon("io","",MemberList(List(
            MemberDecl("readInt",MethodTypeSC(ParaListSC(List[ParaSC]()),IntTypeSC),Static),
            MemberDecl("writeInt",MethodTypeSC(ParaListSC(List(ParaSC("anArg", IntTypeSC))),VoidTypeSC),Static),
            MemberDecl("writeIntLn",MethodTypeSC(ParaListSC(List(ParaSC("anArg", IntTypeSC))),VoidTypeSC),Static),
            MemberDecl("readFloat",MethodTypeSC(ParaListSC(List[ParaSC]()),FloatTypeSC),Static),
            MemberDecl("writeFloat",MethodTypeSC(ParaListSC(List(ParaSC("anArg", FloatTypeSC))),VoidTypeSC),Static),
            MemberDecl("writeFloatLn",MethodTypeSC(ParaListSC(List(ParaSC("anArg", FloatTypeSC))),VoidTypeSC),Static),
            MemberDecl("readBool",MethodTypeSC(ParaListSC(List[ParaSC]()),BoolTypeSC),Static),
            MemberDecl("writeBool",MethodTypeSC(ParaListSC(List(ParaSC("anArg", BoolTypeSC))),VoidTypeSC),Static),
            MemberDecl("writeBoolLn",MethodTypeSC(ParaListSC(List(ParaSC("anArg", BoolTypeSC))),VoidTypeSC),Static),
            MemberDecl("readStr",MethodTypeSC(ParaListSC(List[ParaSC]()),StrTypeSC),Static),
            MemberDecl("writeStr",MethodTypeSC(ParaListSC(List(ParaSC("anArg", StrTypeSC))),VoidTypeSC),Static),
            MemberDecl("writeStrLn",MethodTypeSC(ParaListSC(List(ParaSC("anArg", StrTypeSC))),VoidTypeSC),Static))))))
            
        val env = bldecls.visit(ast,defaultEnv).asInstanceOf[GlobalEnv]
        
        val localVisitor = new LocalEnvVisitor(env)
        localVisitor.visit(ast, env)
    }
}

// Visitor for creating global environment
// As the same time, this visitor will check whether an element is redeclared
class GlobalEnvVisitor extends Visitor {
  override def visitProgram(ast:Program, envObj:Context) = {
    ast.decl.foldLeft(envObj.asInstanceOf[GlobalEnv])((a,b) => visit(b,a).asInstanceOf[GlobalEnv])
  }
    
  override def visitClassDecl(ast:ClassDecl,envObj:Context) = {
    val env = envObj.asInstanceOf[GlobalEnv]
    
    // Check for redeclare class
    if (env.classList.exists(x => (x.name == ast.name.name))) throw Redeclared(Class, ast.name.name)
    else {
      // visit its members
      val memList = ast.decl.foldLeft(MemberList(List[MemberDecl]()))((a,b) => visit(b,a).asInstanceOf[MemberList])
      
      // return a list
      val list = env.classList ++ List(ClassCon(ast.name.name, ast.parent.name, memList.asInstanceOf[MemberList]))
      GlobalEnv(list)
    }
  }
  
  override def visitAttributeDecl(ast:AttributeDecl, classEnvObj:Context) = {
    val env = classEnvObj.asInstanceOf[MemberList]
    
    // Check for redeclare attribute
    val decl = visit(ast.decl,classEnvObj).asInstanceOf[DeclSC]
    if (env.members.exists(x => x.name == decl.name)) {
      if (decl.decl.expr != null) throw /*Redeclared(Constant,decl.name)*/ Redeclared(Attribute,decl.name) // is modified by me at 09:42 18-10-2015
      else throw Redeclared(Attribute,decl.name)
    }
    else {
      val list = env.members ++ List(MemberDecl(decl.name,AttTypeSC(decl.decl.cType,decl.decl.expr) ,ast.kind))
      MemberList(list)
    }
  }
  
  override def visitMethodDecl(ast:MethodDecl, classEnvObj:Context) = {
    val env = classEnvObj.asInstanceOf[MemberList]
    
    // Check for redeclare method
    if (env.members.exists(x => x.name == ast.name.name)) {
      if (ast.returnType != null)
        throw Redeclared(Method,ast.name.name)
      else throw Redeclared(SpecialMethod,ast.name.name)
    }
    else {
      //val para = ast.param.map(visit(_,classEnvObj)).asInstanceOf[List[MemType]]
      val retType = if (ast.returnType != null) visit(ast.returnType,classEnvObj).asInstanceOf[MemType] else null
      val para = ast.param.foldLeft(ParaListSC(List[ParaSC]()))((a,b) => visit(b,a).asInstanceOf[ParaListSC])
      
      val list = env.members ++ List(MemberDecl(ast.name.name, MethodTypeSC(para,retType),ast.kind))
      MemberList(list)
    }
  }
  
  override def visitParamDecl(ast: ParamDecl, c:Context) = {
    val methodLocalEnv = c.asInstanceOf[ParaListSC]
    
    if (methodLocalEnv.paraList.exists(x => x.name == ast.id.name)) throw Redeclared(Parameter,ast.id.name)
    else {
      val paraType = visit(ast.paramType,methodLocalEnv).asInstanceOf[MemType]
      
      val list = methodLocalEnv.paraList ++ List(ParaSC(ast.id.name,paraType))
      ParaListSC(list)
    }
  }
  
  override def visitVarDecl(ast:VarDecl, classEnvObj:Context) = DeclSC(ast.variable.name,AttTypeSC(visit(ast.varType, classEnvObj).asInstanceOf[MemType], null))
  override def visitConstDecl(ast:ConstDecl, classEnvObj:Context) = {
    val _constType = visit(ast.constType, classEnvObj)
    val constType = _constType match {
      case IntTypeSC => IntLitSC
      case FloatTypeSC => FloatLitSC
      case BoolTypeSC => BoolLitSC
      case StrTypeSC => StringLitSC
      case a => a.asInstanceOf[MemType]
    }
    DeclSC(ast.id.name,AttTypeSC(constType, ast.const))
  }
  override def visitIntType(ast: IntType.type, classEnvObj: Context) = IntTypeSC
  override def visitFloatType(ast:FloatType.type,classEnvObj:Context) = FloatTypeSC
  override def visitBoolType(ast:BoolType.type, classEnvObj:Context) = BoolTypeSC
  override def visitStringType(ast:StringType.type, classEnvObj:Context) = StrTypeSC
  override def visitVoidType(ast:VoidType.type, classEnvObj:Context) = VoidTypeSC
  override def visitArrayType(ast:ArrayType, classEnvObj:Context) = ArrTypeSC(visit(ast.eleType,classEnvObj).asInstanceOf[MemType],ast.dimen.value)
  override def visitClassType(ast:ClassType, classEnvObj:Context) = ClassTypeSC(ast.classType)
  
  // not use
  override def visitInstance(ast: Instance.type, c: Context) = c
  override def visitStatic(ast: Static.type, c: Context) = c
  override def visitBinaryOp(ast: BinaryOp, c: Context) = c
  override def visitUnaryOp(ast: UnaryOp, c: Context) = c
  override def visitNewExpr(ast: NewExpr, c: Context) = c
  override def visitCallExpr(ast: CallExpr, c: Context) = c
  override def visitId(ast: Id, c: Context) = c
  override def visitArrayCell(ast: ArrayCell, c: Context) = c
  override def visitFieldAccess(ast: FieldAccess, c: Context) = c
  override def visitBlock(ast: Block, c: Context) = c
  override def visitAssign(ast: Assign, c: Context) = c
  override def visitIf(ast: If, c: Context) = c
  override def visitCall(ast: Call, c: Context) = c
  override def visitWhile(ast: While, c: Context) = c
  override def visitBreak(ast: Break.type, c: Context) = c
  override def visitContinue(ast: Continue.type, c: Context) = c
  override def visitReturn(ast: Return, c: Context) = c
  override def visitIntLiteral(ast: IntLiteral, c: Context) = c
  override def visitFloatLiteral(ast: FloatLiteral, c: Context) = c
  override def visitStringLiteral(ast: StringLiteral, c: Context) = c
  override def visitBooleanLiteral(ast: BooleanLiteral, c: Context) = c
  override def visitNullLiteral(ast: NullLiteral.type, c: Context) = c
  override def visitSelfLiteral(ast: SelfLiteral.type, c: Context) = c
}

// Visitor for creating local environment for each method and block
// As the same time, this visitor will check whether an element is redeclared, including Variable and Constancy
class LocalEnvVisitor(globalEnv:GlobalEnv) extends Visitor with Utils {
  var loopScope = 0
  val line = 0
  
  override def visitProgram(ast: Program, c: Context) = {
    //println(ast)
    //System.out.println(c.toString())
    ast.decl.map(a => visit(a,globalEnv))
  }
  
  override def visitClassDecl(ast: ClassDecl, c: Context) = {
    // in case of a class has a parent, check whether the parent is declared 
    if (ast.parent.name != "")
      if (!globalEnv.classList.exists(x => x.name == ast.parent.name)) throw Undeclared(Class, ast.parent.name)
    
    // if not, create class's environment
    val classEnv = globalEnv.classList.filter(a => a.name == ast.name.name).head
    
    // create environment for this class
    val localEnv = LocalEnv(classEnv.name, null, null, null)
    
    // visit each member of this class
    ast.decl.map(a => visit(a, localEnv))
  }
  
  override def visitAttributeDecl(ast: AttributeDecl, c: Context) = {
    // workaround to prevent bug that might occur
    visit(ast.decl,c)
  }
  
  override def visitVarDecl(ast: VarDecl, c: Context) = {
    // it's not necessary be block environment, because attributeDecl also visit this
    val localEnv = c.asInstanceOf[LocalEnv]
    val decl = DeclSC(ast.variable.name, AttTypeSC(visit(ast.varType, c).asInstanceOf[MemType], null))
    if (localEnv.localPara != null) {// this should return, because it's from visitor of AttributeDecl
      //System.out.println(localEnv.toString())
      val paraList = localEnv.localPara.paraList
      
      if (localEnv.prevBlk == null) {// is visited from method
        if (paraList.exists(p => p.name == decl.name)) throw Redeclared(Variable, decl.name)
      }
      
      val localList = localEnv.curBlk.declList
      if (localList.exists(p => p.name == decl.name)) throw Redeclared(Variable, decl.name)
        
      LocalEnv(localEnv.className, localEnv.localPara, localEnv.prevBlk, DeclListSC(List(decl) ++ localList))
    } else c
  }
  
  override def visitConstDecl(ast: ConstDecl, c: Context) = {
    // it's very similar to the above method
    val localEnv = c.asInstanceOf[LocalEnv]
    val _constType = visit(ast.constType, c).asInstanceOf[MemType]
    val constType = _constType match {
      case IntTypeSC => IntLitSC
      case FloatTypeSC => FloatLitSC
      case StrTypeSC => StringLitSC
      case BoolTypeSC => BoolLitSC
      case a => a
    }
    val _constExprType = visit(ast.const, c)
    
    
    val constExprType = if (_constExprType.isInstanceOf[AttTypeSC]) _constExprType.asInstanceOf[AttTypeSC].cType else _constExprType.asInstanceOf[MemType]
    
    if (!constExprType.isInstanceOf[LiteralSC]) throw NotConstantExpression(ast.const)
    if (!checkCompatible(constType, constExprType, globalEnv)) throw TypeMismatchInConstant(ast)
    
    val decl = DeclSC(ast.id.name, AttTypeSC(constType, ast.const))
    
    
    if (localEnv.localPara != null) { // this should return, because it's from visitor of AttributeDecl
      val paraList = localEnv.localPara.paraList
      
      if (localEnv.prevBlk == null) {// is visited from method
        if (paraList.exists(p => p.name == decl.name)) throw Redeclared(Constant, decl.name)
      }
      
      val localList = localEnv.curBlk.declList
      if (localList.exists(p => p.name == decl.name)) throw Redeclared(Constant, decl.name)
     
        
      LocalEnv(localEnv.className, localEnv.localPara, localEnv.prevBlk, DeclListSC(List(decl) ++ localList))
    } else c
  }
  
  override def visitParamDecl(ast: ParamDecl, c: Context) = visit(ast.paramType, c) // its main job is to visit paramType
  
  override def visitMethodDecl(ast: MethodDecl, c: Context) = {
    // to visit parameters to check whether a parameter has illegal type (mainly class type)
    val paraTmp = ast.param.map(a => visit(a,c))
    
    val classEnv = c.asInstanceOf[LocalEnv]
    val func = locateTypeInClass(ast.name.name, classEnv.className, globalEnv).get
    val paraList = func.asInstanceOf[MethodTypeSC].paras
    
    // visit function's body
    // create environment to visit
    val env = LocalEnv(classEnv.className, paraList, null, null)
    val tmp = visit(ast.body, env).asInstanceOf[List[(Stmt, MemType)]]
    val returnStmt = tmp.filter(a => a._1 != null) // filter list of return statement
    val returnMethodType = if (ast.returnType != null) visit(ast.returnType, c).asInstanceOf[MemType] else null
    
    if (returnMethodType != null && returnMethodType != VoidTypeSC) {
      if (returnStmt.size == 0) throw MethodNotReturn(ast.name.name)
      else {
        returnStmt.foreach(x => if(!checkCompatible(returnMethodType, x._2, globalEnv)) throw TypeMismatchInStatement(x._1) else null)
        null
      }
    } else null
  }
  
  override def visitIntType(ast: IntType.type, c: Context) = IntTypeSC
  override def visitFloatType(ast: FloatType.type, c: Context) = FloatTypeSC
  override def visitBoolType(ast: BoolType.type, c: Context) = BoolTypeSC
  override def visitStringType(ast: StringType.type, c: Context) = StrTypeSC
  override def visitVoidType(ast: VoidType.type, c: Context) = VoidTypeSC
  override def visitArrayType(ast: ArrayType, c: Context) = {
    val elemType = visit(ast.eleType, c).asInstanceOf[MemType]
    val dimen = ast.dimen.value;
    
    ArrTypeSC(elemType, dimen)
  }
  
  override def visitClassType(ast: ClassType, c: Context) = {    
    if (!globalEnv.classList.exists(p => p.name == ast.classType)) throw Undeclared(Class, ast.classType)
    else ClassTypeSC(ast.classType)
  }
  
  override def visitBlock(ast: Block, c: Context) = {
    val env = c.asInstanceOf[LocalEnv]
    
    // this is wrong, you should create new local environment with empty local-declaration list
    val newEnv = if (env.prevBlk == null && env.curBlk == null) LocalEnv(env.className, env.localPara, null, DeclListSC(List[DeclSC]()))
                 else if (env.prevBlk == null) LocalEnv(env.className, env.localPara, env.curBlk, DeclListSC(List[DeclSC]()))
                 else LocalEnv(env.className, env.localPara, DeclListSC(env.curBlk.declList ++ env.prevBlk.declList), DeclListSC(List[DeclSC]()))
    
    // create list of declarations in block statement
    val localEnv = ast.decl.foldLeft(newEnv)((a,b) => visit(b,a).asInstanceOf[LocalEnv])
    
    // new environment after visit all declarations inside a block
    val blockEnv = LocalEnv(env.className, env.localPara, newEnv.prevBlk, localEnv.curBlk)
    
    // begin visit all statements inside this block also
    val tmp = ast.stmt.foldLeft(List[(Stmt, MemType)]())((a,b) => visit(b,blockEnv).asInstanceOf[List[(Stmt, MemType)]] ++ a)
    //println(tmp)
    
    tmp
  }
  
  // these expressions should return a type such as IntTypeSC, ArrTypeSC
  override def visitBinaryOp(ast: BinaryOp, c: Context) = {
    //System.out.println(ast.toString)
    val _lhType = visit(ast.left, c)
    val lhType = if (_lhType.isInstanceOf[AttTypeSC]) _lhType.asInstanceOf[AttTypeSC].cType else _lhType.asInstanceOf[MemType]
    val _rhType = visit(ast.right, c)
    val rhType = if (_rhType.isInstanceOf[AttTypeSC]) _rhType.asInstanceOf[AttTypeSC].cType else _rhType.asInstanceOf[MemType]
    
   ast.op match {
      case "^" => if (typeCheck(lhType, rhType)) {
        val retType = typeOfTwoStrings(lhType, rhType)
        if (retType == None) throw TypeMismatchInExpression(ast)
        else retType.get
      } else throw TypeMismatchInExpression(ast)
      
      case ("&&" | "||") => if (typeCheck(lhType, rhType)) {
        val retType = typeOfTwoBools(lhType, rhType)
        if (retType == None) throw TypeMismatchInExpression(ast)
        else retType.get
      } else throw TypeMismatchInExpression(ast)
      
      case ("==" | "<>") => if (typeCheck(lhType, rhType)) {
        //println("hai lhType va rhType lan luot la : " + lhType + " " + rhType)
        val retType = typeOfEqualOperator(lhType, rhType)
        //println(ast + " tra ve gia tri la " + retType)
        if (retType == None) throw TypeMismatchInExpression(ast)
        else retType.get
      } else throw TypeMismatchInExpression(ast)
      
      case ("<" | "<=" | ">" | ">=") => {
        val retType = typeOfRelationOperator(lhType, rhType)
        if (retType == None) throw TypeMismatchInExpression(ast)
        else retType.get
      }
      
      case ("\\" | "%") => if (typeCheck(lhType, rhType)) {
        val retType = typeOfTwoInts(lhType, rhType)
        if (retType == None) throw TypeMismatchInExpression(ast)
        else retType.get
      } else throw TypeMismatchInExpression(ast)
      
      case ("+" | "-" | "*") => {
        val retType = typeOfAddOperator(lhType, rhType)
        if (retType == None) throw TypeMismatchInExpression(ast)
        else retType.get
      }
      
      case "/" => {
        val retType = typeOfFloatDiv(lhType, rhType)
        if (retType == None) throw TypeMismatchInExpression(ast)
        else retType.get
      }
    } 
  }
  
  override def visitUnaryOp(ast: UnaryOp, c: Context) = {
    val _exprType = visit(ast.body, c)
    val exprType = if (_exprType.isInstanceOf[AttTypeSC]) _exprType.asInstanceOf[AttTypeSC].cType else _exprType.asInstanceOf[MemType]
    
    ast.op match {
      case "!" => if (exprType.isInstanceOf[BoolTypeSC.type] || exprType.isInstanceOf[BoolLitSC.type]) exprType else throw TypeMismatchInExpression(ast)
      case ("-" | "+") => if (exprType.isInstanceOf[IntTypeSC.type] || exprType.isInstanceOf[FloatTypeSC.type]
                              || exprType.isInstanceOf[FloatLitSC.type] || exprType.isInstanceOf[IntLitSC.type]) exprType else throw TypeMismatchInExpression(ast)
    }
  }
  
  override def visitNewExpr(ast: NewExpr, c: Context) = {    
    val className = ast.name.name
    if (!globalEnv.classList.exists(a => a.name == className)) throw Undeclared(Class, className)
    // this should return class type
    else {
      val paraList = ast.exprs.map(a => {val para = visit(a,c); if (para.isInstanceOf[AttTypeSC]) para.asInstanceOf[AttTypeSC].cType else para}).asInstanceOf[List[MemType]]
      println("tham so truyen vao : " + paraList)
      
      val method = locateConstructorOfClass(className, globalEnv)
      println("constructor tim duoc " + method)
      val arguList = if (method == None) List[MemType]() else {
        if (method.get.isInstanceOf[MethodTypeSC]) {
            method.get.asInstanceOf[MethodTypeSC].paras.paraList.foldLeft(List[MemType]())((a,b) => (b.paraType)::a)
        } else List[MemType]()
      }
      println("tham so truyen vao : " + arguList)
      
      if (!checkCompatible(arguList, paraList, globalEnv)) throw TypeMismatchInExpression(ast)
      ClassTypeSC(className)
    }
  }
  
  override def visitCallExpr(ast: CallExpr, c: Context) = {
    // environment
    val env = c.asInstanceOf[LocalEnv]
    val _methodType = visit(ast.cName, c)
    val methodType = if (_methodType.isInstanceOf[AttTypeSC]) _methodType.asInstanceOf[AttTypeSC].cType else _methodType
    System.out.println(methodType)
    // visit expression, if it doesn't return a class type, it's considered an error??
    val classEnv = methodType match {
      case SelfSC => env.className
      case ClassTypeSC(name) => name
      case ClassSC(name) => name
      case _ => throw TypeMismatchInExpression(ast)
    }
    
    // if it does, check the class to see if it contains our method
    val method = locateTypeInClass(ast.method.name, classEnv, globalEnv)
    val paraList = ast.params.map(a => {val para = visit(a,c); if (para.isInstanceOf[AttTypeSC]) para.asInstanceOf[AttTypeSC].cType else para}).asInstanceOf[List[MemType]]
    println("call expr paraList : " + paraList)
    
    if (method == None) throw Undeclared(Method, ast.method.name)
    else if (method.get.isInstanceOf[MethodTypeSC]) {
      val methodType = method.get.asInstanceOf[MethodTypeSC]
      if (methodType.retType == VoidTypeSC /*|| methodType.retType == null*/) throw TypeMismatchInExpression(ast)
      else {
        //println(methodType.retType)
        val arguList = methodType.paras.paraList.foldLeft(List[MemType]())((a,b) => (b.paraType)::a)
        println("call expr arguList : " + arguList)
        if (!checkCompatible(arguList, paraList, globalEnv)) throw TypeMismatchInExpression(ast)
        method.get.asInstanceOf[MethodTypeSC].retType
      }
    }
    else throw TypeMismatchInExpression(ast)
  }
  
  override def visitFieldAccess(ast: FieldAccess, c: Context) = {
    // environment
    val env = c.asInstanceOf[LocalEnv]
    val _clType = visit(ast.name, c)
    val clType = if (_clType.isInstanceOf[AttTypeSC]) _clType.asInstanceOf[AttTypeSC].cType else _clType
    
    // visit expression, if it doesn't return a class type, it's considered an error??
    clType match {
      case SelfSC => {
        val field = locateTypeInClass(ast.field.name, env.className, globalEnv)
    
        if (field == None) throw Undeclared(Attribute, ast.field.name)
        else {
          if (field.get.isInstanceOf[AttTypeSC]) {
            //println("field access duoc ket qua la " + field)
            field.get.asInstanceOf[AttTypeSC]
          }
          else throw TypeMismatchInExpression(ast)
        }
      }
      case ClassTypeSC(name) => {
        val field = locateTypeInClass(ast.field.name, name, globalEnv)
        //System.out.println(field)
      
        if (field == None) throw Undeclared(Attribute, ast.field.name)
        else {
          if (field.get.isInstanceOf[AttTypeSC]) {
            if (name == env.className || isChild(name, env.className, globalEnv)) field.get
            else {
              if (!isStatic(ast.field.name, name, globalEnv)) throw CannotAccessPrivateAttribute(name, ast.field.name)
              else field.get
            }
          }
          else throw TypeMismatchInExpression(ast)
        }
      }
      case ClassSC(name) => {// access static attribute
        val field = locateTypeInClass(ast.field.name, name, globalEnv)
        //System.out.println(field)
      
        if (field == None) throw Undeclared(Attribute, ast.field.name)
        else {
          if (field.get.isInstanceOf[AttTypeSC])
            if (!isStatic(ast.field.name, name, globalEnv)) throw CannotAccessPrivateAttribute(name, ast.field.name) else field.get
          else throw TypeMismatchInExpression(ast)
        } 
      }
      case _ => throw TypeMismatchInExpression(ast)
    }
  }
  
  override def visitArrayCell(ast: ArrayCell, c: Context) = {
    // environment
    val env = c.asInstanceOf[LocalEnv]
    
    // visit first expression to check whether it's a array type
    // if not, throw something???
    val _firstExpr = visit(ast.arr, c)
    val firstExpr = if (_firstExpr.isInstanceOf[AttTypeSC]) _firstExpr.asInstanceOf[AttTypeSC].cType else _firstExpr
    if (!firstExpr.isInstanceOf[ArrTypeSC]) throw TypeMismatchInExpression(ast)
    
    // if it is, start checking the second expression
    // if it returns an integer type, return array cell??
    val _idx = visit(ast.idx, c)
    val idx = if (_idx.isInstanceOf[AttTypeSC]) _idx.asInstanceOf[AttTypeSC].cType else _idx
    if (!(idx.isInstanceOf[IntTypeSC.type] || idx.isInstanceOf[IntLitSC.type])) throw TypeMismatchInExpression(ast)
    
    // if not, throw something??
    firstExpr.asInstanceOf[ArrTypeSC].elemType
  }
  
  override def visitInstance(ast: Instance.type, c: Context) = c  
  
  override def visitId(ast: Id, c: Context) = {
    val env = c.asInstanceOf[LocalEnv]
    
    if (env.localPara == null) {// is visited from expression of constant attribute
      val att = locateTypeInClass(ast.name, env.className, globalEnv)
      if (att == None) throw Undeclared(Attribute, ast.name)
      else {
        att.get.asInstanceOf[AttTypeSC]
      }
    } else {
    
      // is visited from everywhere else
      if (_locateClass(ast.name, globalEnv.classList) == None) {
        val idType = locateType(ast.name, c, globalEnv)
        if (idType == None) throw Undeclared(Identifier, ast.name)
        else idType.get
      } else ClassSC(ast.name) // access static attribute/method
    }
  }
  
  override def visitStatic(ast: Static.type, c: Context) = c
  override def visitAssign(ast: Assign, c: Context) = {
    val lhs = visit(ast.leftHandSide, c)
    //println(lhs)
    val lhsType = lhs match {
      /*case MemberDecl(_,memType,_) => if (memType.asInstanceOf[AttTypeSC].expr != null) throw CannotAssignToConstant(ast) else memType.asInstanceOf[AttTypeSC].cType
      case DeclSC(_,decl) => if (decl.expr != null) throw CannotAssignToConstant(ast) else decl.cType*/
      case AttTypeSC(memType, expr) => if (expr != null) throw CannotAssignToConstant(ast) else memType 
      case VoidTypeSC => throw TypeMismatchInStatement(ast)
      case a:MemType => a
      case _ => throw TypeMismatchInStatement(ast)
    }
    val rhs = visit(ast.expr, c).asInstanceOf[MemType]
    val rhsType = if (rhs.isInstanceOf[AttTypeSC]) rhs.asInstanceOf[AttTypeSC].cType else rhs
    
    //println(lhsType + " " + rhsType)
    
    if (!checkCompatible(lhsType, rhsType, globalEnv)) throw TypeMismatchInStatement(ast) else List((null, null))
  }
  
  override def visitIf(ast: If, c: Context) = {
    val _boolExp = visit(ast.expr, c).asInstanceOf[MemType]
    val boolExp = if (_boolExp.isInstanceOf[AttTypeSC]) _boolExp.asInstanceOf[AttTypeSC].cType else _boolExp
    if (!(boolExp.isInstanceOf[BoolTypeSC.type] || boolExp.isInstanceOf[BoolLitSC.type])) throw TypeMismatchInStatement(ast)
    val thenStm = visit(ast.thenStmt, c).asInstanceOf[List[(Stmt, MemType)]]
    val elseStm = if (ast.elseStmt != None) visit(ast.elseStmt.get, c).asInstanceOf[List[(Stmt, MemType)]] else List[(Stmt,MemType)]((null, null))
    
    thenStm ++ elseStm
  }
  
  override def visitCall(ast: Call, c: Context) = {
    // environment
    val env = c.asInstanceOf[LocalEnv]
    val _methodType = visit(ast.parent, c)
    val methodType = if (_methodType.isInstanceOf[AttTypeSC]) _methodType.asInstanceOf[AttTypeSC].cType else _methodType
    
    // visit expression, if it doesn't return a class type, it's considered an error??
    val classEnv = methodType match {
      case SelfSC => env.className
      case ClassTypeSC(name) => name
      case ClassSC(name) => name
      case _ => throw TypeMismatchInStatement(ast)
    }
    
    // if it does, check the class to see if it contains our method
    val method = locateTypeInClass(ast.method.name, classEnv, globalEnv)
    val paraList = ast.params.map(a => {val para = visit(a,c); if (para.isInstanceOf[AttTypeSC]) para.asInstanceOf[AttTypeSC].cType else para}).asInstanceOf[List[MemType]]
    
    if (method == None) throw Undeclared(Method, ast.method.name)
    else if (method.get.isInstanceOf[MethodTypeSC]) {
      val methodType = method.get.asInstanceOf[MethodTypeSC]
      if (methodType.retType != VoidTypeSC) throw TypeMismatchInStatement(ast)
      else {
        if (!checkCompatible(methodType.paras.paraList.foldLeft(List[MemType]())((a,b) => (b.paraType)::a), paraList, globalEnv)) throw TypeMismatchInStatement(ast)
        List[(Stmt, MemType)]((null, null))
      }
    }
    else throw TypeMismatchInStatement(ast)
  }
  
  override def visitWhile(ast: While, c: Context) = {
    loopScope = loopScope + 1;
    val _boolExp = visit(ast.expr, c)
    val boolExp = if (_boolExp.isInstanceOf[AttTypeSC]) _boolExp.asInstanceOf[AttTypeSC].cType else _boolExp
    if (!(boolExp.isInstanceOf[BoolTypeSC.type] || boolExp.isInstanceOf[BoolLitSC.type])) throw TypeMismatchInStatement(ast)
    val loopStm = visit(ast.loop, c)
    loopScope = loopScope - 1;
    
    
    loopStm
  }
  override def visitBreak(ast: Break.type, c: Context) = {
    if (loopScope == 0) throw BreakNotInLoop(0) else List[(Stmt, MemType)]((null, null))
  }
  override def visitContinue(ast: Continue.type, c: Context) = {
    if (loopScope == 0) throw ContinueNotInLoop(0) else List[(Stmt, MemType)]((null, null))
  }
  override def visitReturn(ast: Return, c: Context) = {
    //println(ast)
    val _retExpr = visit(ast.expr, c)
    val retExpr = if (_retExpr.isInstanceOf[AttTypeSC]) _retExpr.asInstanceOf[AttTypeSC].cType else _retExpr
    // only return statement should return type of expression
    List((ast, retExpr))
  }
  
  override def visitIntLiteral(ast: IntLiteral, c: Context) = IntLitSC
  override def visitFloatLiteral(ast: FloatLiteral, c: Context) = FloatLitSC
  override def visitStringLiteral(ast: StringLiteral, c: Context) = StringLitSC
  override def visitBooleanLiteral(ast: BooleanLiteral, c: Context) = BoolLitSC
  override def visitNullLiteral(ast: NullLiteral.type, c: Context) = NullSC
  override def visitSelfLiteral(ast: SelfLiteral.type, c: Context) = SelfSC
}

trait Utils {
  def typeCheck(typeOne:MemType, typeTwo:MemType):Boolean = typeOne match {
    case (IntTypeSC | IntLitSC) => typeTwo match {
      case (IntTypeSC | IntLitSC) => true
      case _ => false
    }
    case (FloatTypeSC | FloatLitSC) => typeTwo match {
      case (FloatTypeSC | FloatLitSC) => true
      case _ => false
    }
    case (BoolLitSC | BoolTypeSC) => typeTwo match {
      case (BoolTypeSC | BoolLitSC) => true
      case _ => false
    }
    case (StrTypeSC | StringLitSC) => typeTwo match {
      case (StrTypeSC | StringLitSC) => true
      case _ => false
    }
  }
  
  def locateType(name:String, c:Context, globalEnv:GlobalEnv):Option[MemType] = {
    val env = c.asInstanceOf[LocalEnv]
    
    _locateType(name, env.curBlk.declList, (a:DeclSC) => a.name, (a:DeclSC) => a.decl) match {
      case None => _locateType(name, if (env.prevBlk == null) List[DeclSC]() else env.prevBlk.declList, (a:DeclSC) => a.name, (a:DeclSC) => a.decl) match {
        case None => _locateType(name, env.localPara.paraList, (a:ParaSC) => a.name, (a:ParaSC) => a.paraType) match {
          case None => None
          case Some(a) => Some(a)
        }
        case Some(a) => Some(a)
      }
      case Some(a) => Some(a)
    }
  }
  
  def _locateType[T](name: String, list:List[T], func:T=>String, func1:T=>MemType):Option[MemType] = list match {
    case List() => None
    case head::tail => if (func(head) == name) Some(func1(head)) else _locateType(name, tail, func, func1)
  }
  
  def locateConstructorOfClass(nameClass:String, g:GlobalEnv):Option[MemType] = {
    val classList = g.classList
    
    val classDecl = _locateClass(nameClass, classList)
    
    if (classDecl == None) None
    else {
      val memType = _locateType(nameClass, classDecl.get.memberList.members, (a:MemberDecl) => a.name, (a:MemberDecl) => a.memType)
      
      if (memType != None) memType
      else {
        if (classDecl.get.parent == "") None
        else locateConstructorOfClass(classDecl.get.parent, g)
      }
    }
  }
  
  def locateTypeInClass(nameDecl:String, nameClass:String, g:GlobalEnv):Option[MemType] = {
    val classList = g.classList
    
    val classDecl = _locateClass(nameClass, classList)
    
    if (classDecl == None) None
    else {
      val memType = _locateType(nameDecl, classDecl.get.memberList.members, (a:MemberDecl) => a.name, (a:MemberDecl) => a.memType)
      
      if (memType != None) memType
      else {
        if (classDecl.get.parent == "") None
        else locateTypeInClass(nameDecl, classDecl.get.parent, g)
      }
    }
  }
  
  def _locateClass(name:String, classList:List[ClassCon]):Option[ClassCon] = classList match {
    case List() => None
    case head::tail => if (head.name == name) Some(head) else _locateClass(name, tail)
  }
  
  // this is check whether an assignment is possible
  // these assignments can be seen in these cases
  // variable assignment
  // constant assignment
  // parameters passing
  def checkCompatible(lhType:MemType, rhType:MemType, globalEnv:GlobalEnv):Boolean = {
    lhType match {
      case (BoolTypeSC | StrTypeSC | IntTypeSC | IntLitSC | BoolLitSC | StringLitSC) => typeCheck(lhType, rhType)
      case (FloatTypeSC) => if (typeCheck(lhType, rhType) || rhType.isInstanceOf[IntTypeSC.type] || rhType.isInstanceOf[IntLitSC.type]) true else false
      case ClassTypeSC(lhName) => rhType match {
                                  case ClassTypeSC(rhName) => if ((lhName == rhName) || isChild(lhName, rhName, globalEnv)) true else false
                                  case NullSC => true
                                  case _ => false
                             }
      case ArrTypeSC(lhArrType, lhDim) => rhType match {
                                  case ArrTypeSC(rhArrType, rhDim) => if (checkCompatible(lhArrType, rhArrType, globalEnv) && (lhDim == rhDim)) true else false
                                  case _ => false
                             }
    }
  }
  
  // for passing arguments to method only
  // paraList: list of parameters which a method expects to receive
  // arguList: list of arguments which you pass to the method
  def checkCompatible(paraList:List[MemType], arguList:List[MemType], globalEnv:GlobalEnv):Boolean = {
    if (paraList.size != arguList.size) false
    else if (paraList.size == 0) true
    else paraList.zip(arguList).forall(x => checkCompatible(x._1, x. _2, globalEnv))
  }
  
  // check whether a class which is childClass is a child of a class which is superClass
  def isChild(superClass:String, childClass:String, globalEnv:GlobalEnv):Boolean = {
    // it should find out the class, if it's not, it's a bug!!
    val classDecl = _locateClass(childClass, globalEnv.classList).get
    
    if (classDecl.parent == "") false
    else {
      if (superClass == classDecl.parent) true
      else isChild(superClass, classDecl.parent, globalEnv)
    }
  }
  
  def isStatic(memberName:String, className:String, globalEnv:GlobalEnv):Boolean = {
    val memKind = findMemberKind(memberName, className, globalEnv)
    if (memKind.get == Static) true else false
  }
  
  def findMemberKind(memberName:String, className:String, globalEnv:GlobalEnv):Option[SIKind] = {
    val classList = globalEnv.classList
    
    val classDecl = _locateClass(className, classList)
    
    if (classDecl == None) None
    else _findMemberKind(memberName, classDecl.get.memberList.members)
  }
  
  def _findMemberKind(memberName:String, listMember:List[MemberDecl]):Option[SIKind] = listMember match {
    case List() => None
    case head::rest => if (memberName == head.name) Option(head.mKind) else _findMemberKind(memberName, rest)
  }
  
  def typeOfTwoStrings(type1:MemType, type2:MemType):Option[MemType] = type1 match {
    case StringLitSC => if (type2.isInstanceOf[StringLitSC.type]) Some(StringLitSC) else Some(StrTypeSC)
    case StrTypeSC => Some(StrTypeSC)
    case _ => None
  }
  
  def typeOfTwoBools(type1:MemType, type2:MemType):Option[MemType] = {
    //println("vao so sanh == duoc ket qua : " + type1 + " va " + type2)
    type1 match {
      case BoolLitSC => if (type2.isInstanceOf[BoolLitSC.type]) Some(BoolLitSC) else Some(BoolTypeSC)
      case BoolTypeSC => Some(BoolTypeSC)
      case _ => None
    }
  }
    
  
  def typeOfTwoInts(type1:MemType, type2:MemType):Option[MemType] = type1 match {
    case IntLitSC => if (type2.isInstanceOf[IntLitSC.type]) Some(IntLitSC) else Some(IntTypeSC)
    case IntTypeSC => Some(IntTypeSC)
    case _ => None
  }
  
  def typeOfEqualOperator(type1:MemType, type2:MemType):Option[MemType] = type1 match {
    case (FloatLitSC | IntLitSC | BoolLitSC | StringLitSC) => {
      //println(type2)
      if (type2.isInstanceOf[LiteralSC]) Some(BoolLitSC) else Some(BoolTypeSC)
    }
    case (FloatTypeSC | StrTypeSC | BoolTypeSC | IntTypeSC) => Some(BoolTypeSC)
    case _ => None
  }
  
  def typeOfRelationOperator(type1:MemType, type2:MemType):Option[MemType] = type1 match {
    case (FloatLitSC | IntLitSC) => type2 match {
      case (FloatLitSC | IntLitSC) => Some(BoolLitSC)
      case (FloatTypeSC | IntTypeSC) => Some(BoolTypeSC)
      case _ => None
    }
    case (FloatTypeSC | IntTypeSC) => Some(BoolTypeSC)
    case _ => None
  }
  
  def typeOfAddOperator(type1:MemType, type2:MemType):Option[MemType] = type1 match {
    case FloatLitSC => type2 match {
      case (FloatLitSC | IntLitSC) => Some(FloatLitSC)
      case (FloatTypeSC | IntTypeSC) => Some(FloatTypeSC)
      case _ => None
    }
    case IntLitSC => type2 match {
      case IntLitSC => Some(IntLitSC)
      case FloatLitSC => Some(FloatLitSC)
      case FloatTypeSC => Some(FloatTypeSC)
      case IntTypeSC => Some(IntTypeSC)
      case _ => None
    }
    case FloatTypeSC => Some(FloatTypeSC)
    case IntTypeSC => type2 match {
      case (IntLitSC | IntTypeSC) => Some(IntTypeSC)
      case (FloatTypeSC | FloatLitSC) => Some(FloatTypeSC)
      case _ => None
    }
    case _ => None
  }
  
  def typeOfFloatDiv(type1:MemType, type2:MemType):Option[MemType] = type1 match {
    case IntLitSC => type2 match {
      case (IntLitSC | FloatLitSC) => Some(FloatLitSC)
      case (IntTypeSC | FloatTypeSC) => Some(FloatTypeSC)
      case _ => None
    }
    case FloatLitSC => type2 match {
      case (IntLitSC | FloatLitSC) => Some(FloatLitSC)
      case (IntTypeSC | FloatTypeSC) => Some(FloatTypeSC)
      case _ => None
    }
    case (IntTypeSC | FloatTypeSC) => type2 match {
      case (IntTypeSC | FloatTypeSC | IntLitSC | FloatLitSC) => Some(FloatTypeSC)
      case _ => None
    }
    case _ => None
  }
}