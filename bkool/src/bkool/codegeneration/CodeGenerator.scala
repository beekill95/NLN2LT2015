/**
 *	@author Nguyen Hua Phung
 *	@version 1.0
 *	23/10/2015
 * 	This file provides a simple version of code generator
 *
 */

package bkool.codegeneration



import bkool._
import bkool.parser._
import bkool.checker._
import bkool.utils._
import java.io.{PrintWriter, File}
import org.antlr.v4.runtime.ANTLRFileStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree._
import scala.collection.JavaConverters._

case class MethodType(in:List[Type],out:Type) extends Type
object TestCodeGenerator {
  def init() = {
    val mem = List( Member("readInt",Static,Method,MethodType(List(),IntType),None),
                      Member("writeInt",Static,Method,MethodType(List(IntType),VoidType),None),
                      Member("writeIntLn",Static,Method,MethodType(List(IntType),VoidType),None),
                      Member("readFloat",Static,Method,MethodType(List(),FloatType),None),
                      Member("writeFloat",Static,Method,MethodType(List(FloatType),VoidType),None),
                      Member("writeFloatLn",Static,Method,MethodType(List(FloatType),VoidType),None),
                      Member("readBool",Static,Method,MethodType(List(),BoolType),None),
                      Member("writeBool",Static,Method,MethodType(List(BoolType),VoidType),None),
                      Member("writeBoolLn",Static,Method,MethodType(List(BoolType),VoidType),None),
                      Member("readStr",Static,Method,MethodType(List(),StringType),None),
                      Member("writeStr",Static,Method,MethodType(List(StringType),VoidType),None),
                      Member("writeStrLn",Static,Method,MethodType(List(StringType),VoidType),None)
                    )
    ClassData("io","",mem)
  }
	def check(infile:ANTLRFileStream,outfile:PrintWriter,dir:File) = {
    val lexer = new BKOOLLexer(infile);
    val tokens = new CommonTokenStream(lexer);
    val parser = new BKOOLParser(tokens);
    val progtree = parser.program()
    
    val astbuild = new ASTGeneration()
    val ast = astbuild.visit(progtree).asInstanceOf[Program]
    
    // you should be careful here!!!!!!
		val mem = List(Member("main",Static,Method,MethodType(List(),VoidType),None))
    val cls = ClassData("a","",mem)
    
    val cls_test = new GlobalEnvVisitor().visit(ast, null).asInstanceOf[ListClass].value
    println(cls_test)
    println(cls)
    
    val ini = init()
    //val gl = List(cls,ini)
    val gl = cls_test :+ ini
    
		val gc = new CodeGenVisitor(ast,gl,dir)
		gc.visit(ast, null);
	}
}

case class Member(val name:String,val skind:SIKind,val kind:Kind,val mtype:Type,val value:Option[Value])
case class ClassData(val cname:String,val pname:String,val mem:List[Member])
trait Value
case class IntValue(value:Int) extends Value
case class FloatValue(value:Float) extends Value
case class StringValue(value:String) extends Value
case class BoolValue(value:Boolean) extends Value
case class RawValue(value:Expr) extends Value
case object NullValue extends Value
case class ListMember(value:List[Member]) extends Context
case class ListClass(value:List[ClassData]) extends Context


case class SubContext(emit:Emitter,classname:String,decl:List[Decl]) extends Context
case class SubBody(emit:Emitter,classname:String,frame:Frame,sym:List[(String,Type,Val)]) extends Context
case class SymbolTable(sym:List[(String,Type,Val)],classname:String) extends Context
case class Access(emit:Emitter,classname:String,frame:Frame,sym:List[(String,Type,Val)],isLeft:Boolean,isFirst:Boolean) extends Context
  trait Val
  case class Index(value:Int) extends Val
  case class Const(value:Expr) extends Val
class MVisitor extends Visitor {
  def visitProgram(ast: Program, c: Context): Object = c
  def visitVarDecl(ast: VarDecl, c: Context): Object  = c
  def visitConstDecl(ast: ConstDecl, c: Context): Object  = c
  def visitParamDecl(ast: ParamDecl, c: Context): Object  = c
  def visitClassDecl(ast: ClassDecl, c: Context): Object  = c
  def visitMethodDecl(ast: MethodDecl, c: Context): Object = c
  def visitAttributeDecl(ast: AttributeDecl, c: Context): Object = c
  def visitInstance(ast: Instance.type, c: Context): Object = c
  def visitStatic(ast: Static.type, c: Context): Object = c
  
  override def visitIntType(ast:IntType.type,o:Context) = ast
    override def visitFloatType(ast:FloatType.type,o:Context) = ast
    override def visitStringType(ast:StringType.type,o:Context) = ast
    override def visitBoolType(ast:BoolType.type,o:Context) = ast
    override def visitVoidType(ast:VoidType.type,o:Context) = ast
    override def visitArrayType(ast:ArrayType,o:Context) = ast
    override def visitClassType(ast:ClassType,o:Context) = ast
  
  def visitBinaryOp(ast: BinaryOp, c: Context): Object = c
  def visitUnaryOp(ast: UnaryOp, c: Context): Object = c
  def visitNewExpr(ast: NewExpr, c: Context): Object = c
  def visitCallExpr(ast: CallExpr, c: Context): Object = c
  def visitId(ast: Id, c: Context): Object = c
  def visitArrayCell(ast: ArrayCell, c: Context): Object = c
  def visitFieldAccess(ast: FieldAccess, c: Context): Object = c
  def visitBlock(ast: Block, c: Context): Object = c
  def visitAssign(ast: Assign, c: Context): Object = c
  def visitIf(ast: If, c: Context): Object = c
  def visitCall(ast: Call, c: Context): Object = c
  def visitWhile(ast: While, c: Context): Object = c
  def visitBreak(ast: Break.type, c: Context): Object = c
  def visitContinue(ast: Continue.type, c: Context): Object = c
  def visitReturn(ast: Return, c: Context): Object = c
  def visitIntLiteral(ast: IntLiteral, c: Context): Object = c
  def visitFloatLiteral(ast: FloatLiteral, c: Context): Object = c
  def visitStringLiteral(ast: StringLiteral, c: Context): Object = c
  def visitBooleanLiteral(ast: BooleanLiteral, c: Context): Object = c
  def visitNullLiteral(ast: NullLiteral.type, c: Context): Object = c
  def visitSelfLiteral(ast: SelfLiteral.type, c: Context): Object = c
}

class GlobalEnvVisitor extends MVisitor {
  override def visitProgram(ast:Program, o:Context) = ast.decl.foldLeft(ListClass(List()))((x,y) => visit(y,x).asInstanceOf[ListClass])
  
  override def visitClassDecl(ast:ClassDecl, o:Context) = {
    val classList = o.asInstanceOf[ListClass].value
    
    // visit class's members
    val memberList = ast.decl.foldLeft(ListMember(List()))((x,y) => visit(y,x).asInstanceOf[ListMember])
    
    // return list of classes with this class
    ListClass(classList :+ ClassData(ast.name.name, ast.parent.name, memberList.value))
  }
  
  override def visitAttributeDecl(ast:AttributeDecl, o:Context) = {
    val memberList = o.asInstanceOf[ListMember].value
    
    val decl = visit(ast.decl, o).asInstanceOf[(String,Type,Value)]
    val attDecl = Member(decl._1, ast.kind, Attribute, decl._2, if (decl._3 == null) None else Some(decl._3))
    
    ListMember(memberList :+ attDecl)
  }
  
  override def visitMethodDecl(ast:MethodDecl, o:Context) = {
    val memberList = o.asInstanceOf[ListMember].value
    
    val paraList = ast.param.map(visit(_,o)).asInstanceOf[List[Type]]
    val retType = if (ast.returnType == null) null else visit(ast.returnType,o).asInstanceOf[Type]
    
    val methodDecl = Member(ast.name.name, ast.kind, Method, MethodType(paraList, retType), None)
    
    ListMember(memberList :+ methodDecl)
  }
  
  /*override def visitBinaryOp(ast:BinaryOp, o:Context) = {
    val lExpr = visit(ast.left, o)
    
  }*/
  
  override def visitParamDecl(ast:ParamDecl, o:Context) = ast.paramType
  
  override def visitVarDecl(ast:VarDecl, o:Context):(String,Type,Value) = (ast.variable.name, ast.varType, null)
  
  override def visitConstDecl(ast:ConstDecl, o:Context):(String,Type,Value) = (ast.id.name, ast.constType, RawValue(ast.const))
}

class ConstCalculator extends MVisitor {
  override def visitBinaryOp(ast:BinaryOp, o:Context) = {
    val lExpr = visit(ast.left, o)
    val rExpr = visit(ast.right, o)
    
    val lVal = lExpr match {
      case IntValue(value) => value
      case FloatValue(value) => value
      case BoolValue(value) => value
      case StringValue(value) => value
    }
    
    val rVal = rExpr match {
      case IntValue(value) => value
      case FloatValue(value) => value
      case BoolValue(value) => value
      case StringValue(value) => value
    }
    
    ast.op match {
      case "+" => (lVal,rVal) match {
        case (x:Int,y:Int) => IntValue(x + y)
        case (x:Float,y:Int) => FloatValue(x+y)
        case (x:Int,y:Float) => FloatValue(x+y)
        case (x:Float,y:Float) => FloatValue(x+y)
      }
      case "-" => (lVal,rVal) match {
        case (x:Int,y:Int) => IntValue(x-y)
        case (x:Float,y:Int) => FloatValue(x-y)
        case (x:Int,y:Float) => FloatValue(x-y)
        case (x:Float,y:Float) => FloatValue(x-y)
      }
      case "*" => (lVal,rVal) match {
        case (x:Int,y:Int) => IntValue(x*y)
        case (x:Float,y:Int) => FloatValue(x*y)
        case (x:Int,y:Float) => FloatValue(x*y)
        case (x:Float,y:Float) => FloatValue(x*y)
      }
      case "/" => (lVal,rVal) match {
        case (x:Int,y:Int) => FloatValue(x.asInstanceOf[Float]/y)
        case (x:Float,y:Int) => FloatValue(x/y)
        case (x:Int,y:Float) => FloatValue(x/y)
        case (x:Float,y:Float) => FloatValue(x/y)
      }
      case "\\" => IntValue(lVal.asInstanceOf[Int] / rVal.asInstanceOf[Int])
      case "%" => IntValue(lVal.asInstanceOf[Int] % rVal.asInstanceOf[Int])
      case ">" => (lVal,rVal) match {
        case (x:Int,y:Int) => BoolValue(x>y)
        case (x:Float,y:Int) => BoolValue(x>y)
        case (x:Int,y:Float) => BoolValue(x>y)
        case (x:Float,y:Float) => BoolValue(x>y)
      }
      case "<" => (lVal,rVal) match {
        case (x:Int,y:Int) => BoolValue(x<y)
        case (x:Float,y:Int) => BoolValue(x<y)
        case (x:Int,y:Float) => BoolValue(x<y)
        case (x:Float,y:Float) => BoolValue(x<y)
      }
      case ">=" => (lVal,rVal) match {
        case (x:Int,y:Int) => BoolValue(x>=y)
        case (x:Float,y:Int) => BoolValue(x>=y)
        case (x:Int,y:Float) => BoolValue(x>=y)
        case (x:Float,y:Float) => BoolValue(x>=y)
      }
      case "<=" => (lVal,rVal) match {
        case (x:Int,y:Int) => BoolValue(x<=y)
        case (x:Float,y:Int) => BoolValue(x<=y)
        case (x:Int,y:Float) => BoolValue(x<=y)
        case (x:Float,y:Float) => BoolValue(x<=y)
      }
      case "==" => (lVal,rVal) match {
        case (x:Int,y:Int) => BoolValue(x==y)
        case (x:Boolean,y:Boolean) => BoolValue(x==y)
        case (x:String,y:String) => BoolValue(x==y)
        case (x:Float,y:Float) => BoolValue(x==y)
      }
      case "<>" => (lVal,rVal) match {
        case (x:Int,y:Int) => BoolValue(x!=y)
        case (x:Boolean,y:Boolean) => BoolValue(x!=y)
        case (x:String,y:String) => BoolValue(x!=y)
        case (x:Float,y:Float) => BoolValue(x!=y)
      }
      case "&&" => BoolValue(lVal.asInstanceOf[Boolean] && rVal.asInstanceOf[Boolean])
      case "||" => BoolValue(lVal.asInstanceOf[Boolean] || rVal.asInstanceOf[Boolean])
      case "^" => {
        val lStr = lVal.asInstanceOf[String].substring(0, lVal.asInstanceOf[String].length() - 1)
        val rStr = rVal.asInstanceOf[String].substring(1, rVal.asInstanceOf[String].length())
        
        StringValue(lStr + rStr)
      }
    }
  }
  
  override def visitUnaryOp(ast:UnaryOp, o:Context) = {
    val expr = visit(ast.body, o)
    
    ast.op match {
      case "+" => expr
      case "-" => {
        if (expr.isInstanceOf[IntValue]) IntValue(-expr.asInstanceOf[IntValue].value)
        else FloatValue(-expr.asInstanceOf[FloatValue].value)
      }
      case "!" => BoolValue(!expr.asInstanceOf[BoolValue].value)
    }
  }
  
  override def visitIntLiteral(ast:IntLiteral, o:Context) = IntValue(ast.value)
  override def visitFloatLiteral(ast:FloatLiteral, o:Context) = FloatValue(ast.value)
  override def visitBooleanLiteral(ast:BooleanLiteral, o:Context) = BoolValue(ast.value)
  override def visitStringLiteral(ast:StringLiteral, o:Context) = {
    //println(ast.value)
    StringValue(ast.value)
  }
  
  override def visitNullLiteral(ast:NullLiteral.type,o:Context) = NullValue
}

  // TODO: CodeGenVisitor

class CodeGenVisitor(astTree:AST,env:List[ClassData],dir:File) extends MVisitor {
	
  def lookup[T](name:String,lst:List[T],func:T=>String):Option[T] = lst match {
    case List() => None
    case head::tail => if (name == func(head)) Some(head) else lookup(name,tail,func)
  }
  
  def getMember(cls:String, memName:String):Member = {
    // lookup for class
    // somewhere in the code we assume that this will always return a class
    val classDecl = lookup(cls, env, (x:ClassData) => x.cname).get
    
    // lookup for member name in that class
    // somewhere in the code we assume that this will always return a member of that class
    val memDecl = lookup(memName, classDecl.mem, (x:Member) => x.name)
    
    // if there is no member in that class, it should be in it's parent
    if (memDecl == None)
      getMember(classDecl.pname, memName)
    else
      memDecl.get
  }
  
  // this will check whether type1 should be converted to match type2
  // type1: type is checked
  // type2: type is checked with
  // version: this will only deal with integer and float
  def shouldBeConvert(type1:Type, type2:Type, emit:Emitter, frame:Frame):Option[(String,Type)] = type1 match {
    case IntType => if (type2 == FloatType) {
      println("type1 " + type1 + "type2 " + type2)
      Some((emit.emitI2F(frame), FloatType))
    } else None
    
    case _ => None
  }
 
  // o ben ngoai truyen vao thi c la null
  override def visitProgram(ast:Program,c:Context) = ast.decl.map(visit(_,c))

   override def visitClassDecl(ast:ClassDecl,o:Context) = {
      val path = dir.getPath()
      val emit = new Emitter(path+"/"+ast.name.name+".j")
      emit.printout(emit.emitPROLOG(ast.name.name, ast.parent.name))
      
      val clsDecl = lookup(ast.name.name, env, (x:ClassData) => x.cname).get
      val constructor = lookup(ast.name.name, clsDecl.mem, (x:Member) => x.name)
      
      // cai nay truyen vao List[Decl]() la rong
      ast.decl.map(x => visit(x,SubContext(emit,ast.name.name,List())))
      
      // su dung ham generate method de generate ra default constructor (trong truong hop nay)
      // con ham genMethod thay viet se generate ra method chung luon
      // minh phai can than, chi khi nao trong class khong co constructor thi minh moi su dung ham nay
      // nguoc lai thi phai su dung cua nguoi lap trinh viet ra
      // if not found
      if (constructor == None) {
        //val declsToInit = getDeclsToInit(clsDecl.mem)
        genMETHOD(ast.name.name,
            MethodDecl(Instance,ast.name,List(),null,Block(List(),List())),o,List(),new Frame(VoidType),emit)
      }
      
      genCLINIT(ast.name.name, new Frame(VoidType), emit)
      
      emit.emitEPILOG()
      o   
  }
  
    /** generate code for default constructor 
   *  @param classname the name of the enclosing class
   *  @param lst the list of instance attributes (array type or immutable) that need to initialize
   *  @param frame the frame where the initialization happen 
   *  @param v the visitor that visits the sub-node to generate code
   */
  def genMETHOD(classname:String,consdecl:MethodDecl,o:Context,lst:List[Decl],frame:Frame,emit:Emitter) = {
    
    val isInit = consdecl.returnType == null
    val isMain = consdecl.kind == Static && consdecl.name.name == "main" && consdecl.param.length == 0 && consdecl.returnType == VoidType
    val returnType = if (isInit) VoidType else consdecl.returnType
    val methodName = if (isInit) "<init>" else consdecl.name.name
    val clsDecl = lookup(classname, env, (x:ClassData) => x.cname).get
    val parent = clsDecl.pname
    
    val param = if (isMain) List(ParamDecl(Id("args"), ArrayType(IntLiteral(0),StringType))) else consdecl.param
    val mtype =  MethodType(param.map(_.paramType),returnType)
    
    emit.printout(emit.emitMETHOD(methodName, mtype, consdecl.kind==Static,frame));
    frame.enterScope(true);
    
    // Generate code for parameter declarations
    if (consdecl.kind == Instance) emit.printout(emit.emitVAR(frame.getNewIndex,"this",ClassType(classname),frame.getStartLabel,frame.getEndLabel,frame))
    // cho nay SubBody de lam gi?
    val nenv = param.foldLeft(List[(String,Type,Val)]())((y,x) => visit(x,SubBody(emit,classname,frame,y)).asInstanceOf[List[(String,Type,Val)]])
    val body = consdecl.body.asInstanceOf[Block]
    
    //Generate code for local declarations
    val newenv = body.decl.foldLeft(List[(String,Type,Val)]())((y,x) => visit(x,SubBody(emit,classname,frame,y)).asInstanceOf[List[(String,Type,Val)]])
    emit.printout(emit.emitLABEL(frame.getStartLabel(),frame))
    
    // generate code for array and class variable initialization
    val initList = newenv.filter((p:(String,Type,Val)) => (p._2.isInstanceOf[ArrayType] || p._2.isInstanceOf[ClassType]))
    initArrCls(initList,frame,emit)
    
    //Generate code for statements
    if (isInit) {
      emit.printout(emit.emitREADVAR("this",ClassType(classname),0,frame))
      val constructorParam = nenv.foldLeft("")((x,y)=>
        x + emit.emitREADVAR(y._1, y._2, y._3.asInstanceOf[Index].value, frame)   
      )
      println(constructorParam)
      if (parent == "") emit.printout(emit.emitINVOKESPECIAL())
      else {
        emit.printout(constructorParam + emit.emitINVOKESPECIAL(parent + "/<init>", MethodType(mtype.in,VoidType), frame))
      }
      
      // filter final attributes but not static
      // or class and array attributes
      val classDecl = lookup(classname, env, (x:ClassData) => x.cname).get
      val listToInit = classDecl.mem.filter((x:Member) => (x.skind != Static) &&
          (x.value != None || x.mtype.isInstanceOf[ArrayType] || x.mtype.isInstanceOf[ClassType]))
        
      initInstanceAtt(classname, listToInit, frame, emit)  
    }
    body.stmt.map(x=>visit(x,SubBody(emit,classname,frame,nenv++newenv)))
    
    emit.printout(emit.emitLABEL(frame.getEndLabel(),frame))
    if (returnType == VoidType) emit.printout(emit.emitRETURN(VoidType,frame));
    emit.printout(emit.emitENDMETHOD(frame));
    frame.exitScope(); 
  }
  
  def initArrCls(lst:List[(String,Type,Val)], frame:Frame, emit:Emitter):Unit = lst match {
    case List() => Unit
    case head::tail => {
      head._2 match {
        case ArrayType(_,_) => {
          emit.printout(emit.emitNEWARRAY(head._2.asInstanceOf[ArrayType], frame))
          emit.printout(emit.emitASTORE(head._3.asInstanceOf[Index].value, head._2, frame))
        }
        case ClassType(_) => {
          /*emit.printout(emit.emitALOAD(head._3.asInstanceOf[Index].value, head._2, frame))*/
          emit.printout(emit.emitNULLCONST(frame))
          emit.printout(emit.emitASTORE(head._3.asInstanceOf[Index].value, head._2, frame))
        }
      }
      initArrCls(tail,frame,emit)
    }
  }
  
  def initInstanceAtt(clsName:String,lst:List[(Member)], frame:Frame, emit:Emitter):Unit = lst match {
    case List() => Unit
    case head::tail => {
      // generate code
      emit.printout(emit.emitALOAD(0, ClassType(""), frame))
      if (head.value != None) {// constant case
        // get constant
        val exprConst = new ConstCalculator().visit(head.value.get.asInstanceOf[RawValue].value, null).asInstanceOf[Value]
        emit.printout(emit.emitPUSHCONST(exprConst, frame))
        
      } else {// other cases: array and class attributes
        head.mtype match {
          case ArrayType(dimen, elemType) => emit.printout(emit.emitNEWARRAY(ArrayType(dimen,elemType), frame))
          case ClassType(_) => emit.printout(emit.emitNULLCONST(frame))
        }
      }
    }
    emit.printout(emit.emitPUTFIELD(clsName + "." + head.name, head.mtype, frame))
    
    // generate code for the rest
    initInstanceAtt(clsName, tail, frame, emit)
  }
  
  def genCLINIT(className:String, frame:Frame, emit:Emitter) = {
    // find class declaration
    val classDecl = lookup(className, env, (x:ClassData) => x.cname).get
    val attToInit = classDecl.mem.filter((p:Member) => (p.skind == Static) && (p.mtype.isInstanceOf[ArrayType]))
    
    if (!attToInit.equals(List())) {// if not found any
      val varList = attToInit.map((x:Member) => VarDecl(Id(x.name),x.mtype))
      
      emit.printout(emit.emitCLINIT(className, varList, frame))
    }
  }
  
  override def visitMethodDecl(ast:MethodDecl,o:Context) = {
    val subctxt = o.asInstanceOf[SubContext]
    val emit = subctxt.emit
    val frame = new Frame(ast.returnType)
    
    // truong hop nay la generate ra method chung (neu co constructor thi cung se duoc generate ra)
    if (ast.name.name == subctxt.classname) {// for constructor
      val clsDecl = lookup(ast.name.name, env, (x:ClassData) => x.cname).get
      /*val declsToInit = getDeclsToInit(clsDecl.mem)
      println(declsToInit)*/
      
      val metDecl = ast.body.asInstanceOf[Block].decl
      val metStmt = ast.body.asInstanceOf[Block].stmt
      val metArgu = ast.param
      
      genMETHOD(ast.name.name,
          MethodDecl(Instance,ast.name,metArgu,null,Block(metDecl, metStmt)),o,List(),new Frame(VoidType),emit)
    } else // for normal method
      genMETHOD(subctxt.classname,ast,o,subctxt.decl,frame,emit)
    
    o
  }
  
  override def visitAttributeDecl(ast:AttributeDecl, o:Context) = {
    val subctx = o.asInstanceOf[SubContext]
    val emit = subctx.emit
    
    val decl = visit(ast.decl, null).asInstanceOf[(String,Type,Value)]
    
    val constVal = if (decl._3 != null) new ConstCalculator().visit(decl._3.asInstanceOf[RawValue].value, null) else null
    println(constVal)
    
    // this is just temporary
    println(ast.kind)
    val attStr = if (constVal == null)
      emit.emitATTRIBUTE(decl._1, ast.kind, decl._2, false, "")
    else constVal match {
      case IntValue(value) => if (decl._2 == FloatType) emit.emitATTRIBUTE(decl._1, ast.kind, decl._2, true, (value * 1.0).toString)
                              else emit.emitATTRIBUTE(decl._1, ast.kind, decl._2, true, value.toString)
      case FloatValue(value) => emit.emitATTRIBUTE(decl._1, ast.kind, decl._2, true, value.toString)
      case BoolValue(value) => emit.emitATTRIBUTE(decl._1, ast.kind, decl._2, true, if (value) 1.toString else 0.toString)
      case StringValue(value) => emit.emitATTRIBUTE(decl._1, ast.kind, decl._2, true, value)
    }
    
    emit.printout(attStr)
    // return to the caller
    o
  }
  
  override def visitVarDecl(ast:VarDecl, o:Context) = {
    if (o == null) {// is visited from AttributeDecl
      (ast.variable.name, ast.varType, null)
    } else {// is visited from visitor inside a block
      val ctx = o.asInstanceOf[SubBody]
      val emit = ctx.emit
      val frame = ctx.frame
      val env = ctx.sym
      val idx = frame.getNewIndex()
      
      emit.printout(emit.emitVAR(idx, ast.variable.name, ast.varType, frame.getStartLabel(), frame.getEndLabel(), frame))
      env :+ (ast.variable.name, ast.varType, Index(idx))
    }
  }
  
  override def visitConstDecl(ast:ConstDecl, o:Context) = {
    if (o == null) {// is visited from AttributeDecl
      (ast.id.name, ast.constType, RawValue(ast.const))
    } else {// is visited from visitor inside a block
      val ctx = o.asInstanceOf[SubBody]
      val emit = ctx.emit
      val frame = ctx.frame
      val env = ctx.sym
      
      ast.constType match {
        case (ArrayType(_,_) | ClassType(_)) // see these as normal variable
          => {
            val idx = frame.getNewIndex()
            emit.printout(emit.emitVAR(idx, ast.id.name, ast.constType, frame.getStartLabel(), frame.getEndLabel(), frame))
            env :+ (ast.id.name, ast.constType, Index(idx))
          }
        case _ => env :+ (ast.id.name, ast.constType, Const(ast.const))
      }
    }
  }
  
  // visit param decl se tra ve mot tuple (String, paramType, index cua param)
  // cai index duoc se duoc su dung sau nay
  override def visitParamDecl(ast:ParamDecl,o:Context) = {
    val ctxt = o.asInstanceOf[SubBody]
    val emit = ctxt.emit
    val frame = ctxt.frame
    val env = ctxt.sym
    val idx = frame.getNewIndex
    emit.printout(emit.emitVAR(idx,ast.id.name,ast.paramType,frame.getStartLabel(),frame.getEndLabel(),frame))
    env :+ (ast.id.name,ast.paramType,Index(idx))
  }
  
  // ham nay chua tang frame len 1???
  override def visitCall(ast:Call,o:Context) = {
    val ctxt = o.asInstanceOf[SubBody]
    val emit = ctxt.emit
    val frame = ctxt.frame
    val nenv = ctxt.sym
    
    // visit nay la visit toi id (trong truong hop cu the nay thoi)
    // tim ra parent cua expression
    val (str,typ) = visit(ast.parent,Access(emit,ctxt.classname,frame,nenv,false,true)).asInstanceOf[(String,Type)]
    val member = getMember(typ.asInstanceOf[ClassType].classType, ast.method.name)
    val arguList = member.mtype.asInstanceOf[MethodType]
    val arguArr = arguList.in.toArray
    var index = 0
    
    emit.printout(str)
    
    val in = ast.params.foldLeft(("",List[Type]()))((y,x)=>
      {
        // because in phase 1, we only have literals, therefore, this visit will always return a literal
        val (str1,typ1) = visit(x,Access(emit,ctxt.classname,frame,nenv,false,true)).asInstanceOf[(String,Type)]
        // we have to convert between float and integer
        val converted = shouldBeConvert(typ1, arguArr(index), emit, frame)
        index = index + 1;
        if (converted == None)
          (y._1 + str1,y._2 :+ typ1)
        else
          (y._1 + str1 + converted.get._1 , y._2 :+ converted.get._2)
      }
    )
    emit.printout(in._1)
    
    // cai nay la de bo cac params ra khoi frame
    ast.params.map(x=>frame.pop)
    if (member.skind == Static)
      emit.printout(emit.emitINVOKESTATIC(typ.asInstanceOf[ClassType].classType +"/"+ast.method.name,MethodType(in._2,VoidType),frame)) // cai nay co return type la voidtype chi ap dung cho phase 1 ma thoi
    else
      emit.printout(emit.emitINVOKEVIRTUAL(typ.asInstanceOf[ClassType].classType + "/" + ast.method.name, MethodType(in._2, VoidType), frame))
  }
  
  override def visitAssign(ast:Assign, o:Context) = {
    val ctxt = o.asInstanceOf[SubBody]
    val emit = ctxt.emit
    val frame = ctxt.frame
    val nenv = ctxt.sym
    
    val rhs = visit(ast.expr, Access(emit, ctxt.classname, frame, nenv, false, true)).asInstanceOf[(String,Type)]
    val lhs_1 = visit(ast.leftHandSide, Access(emit, ctxt.classname, frame, nenv, true, true)).asInstanceOf[(String,Type)]
    val lhs_2 = visit(ast.leftHandSide, Access(emit, ctxt.classname, frame, nenv, true, false)).asInstanceOf[(String,Type)]
    
    println(frame.currOpStackSize)
    println(lhs_2._2 + "   " + rhs._2)
    
    val converted = shouldBeConvert(rhs._2, lhs_1._2, emit, frame)
    
    if (converted == None) {
      
      emit.printout(lhs_1._1 + rhs._1 + lhs_2._1)
    } else {
      println("Da nhay xuong day!")
      emit.printout(lhs_1._1 + rhs._1 + converted.get._1 + lhs_2._1)
    }
  }
  
  override def visitBlock(ast:Block, o:Context) = {
    val ctxt = o.asInstanceOf[SubBody]
    val emit = ctxt.emit
    val frame = ctxt.frame
    val nenv = ctxt.sym
    
    // enter block
    frame.enterScope(false)
    //Generate code for local declarations
    val newenv = ast.decl.foldLeft(List[(String,Type,Val)]())((y,x) => visit(x,SubBody(emit,ctxt.classname,frame,y)).asInstanceOf[List[(String,Type,Val)]])
    emit.printout(emit.emitLABEL(frame.getStartLabel(),frame))
    
    // generate code for array and class variable initialization
    val initList = newenv.filter((p:(String,Type,Val)) => (p._2.isInstanceOf[ArrayType] || p._2.isInstanceOf[ClassType]))
    initArrCls(initList,frame,emit)
    
    //Generate code for statements
    ast.stmt.map(x=>visit(x,SubBody(emit,ctxt.classname,frame,newenv++nenv)))
    emit.printout(emit.emitLABEL(frame.getEndLabel(), frame))
    frame.exitScope()
    
    o
  }
  
  override def visitWhile(ast:While, o:Context) = {
    val ctxt = o.asInstanceOf[SubBody]
    val emit = ctxt.emit
    val frame = ctxt.frame
    val nenv = ctxt.sym
    
    // enter loop
    frame.enterLoop()
    val expr = visit(ast.expr, Access(emit, ctxt.classname, frame, nenv, false, true)).asInstanceOf[(String,Type)]
    val conLabel = emit.emitLABEL(frame.getContinueLabel(), frame)
    val loopCondition = emit.emitIFFALSE(frame.getBreakLabel(), frame)
    val breakLabel = emit.emitLABEL(frame.getBreakLabel(), frame)    
    
    // print these codes before visit statement inside while
    emit.printout(conLabel + expr._1 + loopCondition)
    
    // visit statement inside this while
    visit(ast.loop, o)
    
    // continue
    emit.printout(emit.emitGOTO(frame.getContinueLabel(), frame) + breakLabel)
    
    // exit loop
    frame.exitLoop()
    
    o
  }
  
  override def visitBreak(ast:Break.type, o:Context) = {
    val ctxt = o.asInstanceOf[SubBody]
    val emit = ctxt.emit
    val frame = ctxt.frame
    
    emit.printout(emit.emitGOTO(frame.getBreakLabel(), frame))
    o
  }
  
  override def visitContinue(ast:Continue.type, o:Context) = {
    val ctxt = o.asInstanceOf[SubBody]
    val emit = ctxt.emit
    val frame = ctxt.frame
    
    emit.printout(emit.emitGOTO(frame.getContinueLabel(), frame))
    o
  }
  
  override def visitIf(ast:If, o:Context) = {
    val ctxt = o.asInstanceOf[SubBody]
    val emit = ctxt.emit
    val frame = ctxt.frame
    val nenv = ctxt.sym
    
    val expr = visit(ast.expr, Access(emit, ctxt.classname, frame, nenv, false, true)).asInstanceOf[(String,Type)]
    val elseLabel = frame.getNewLabel()
    val endIfLabel = frame.getNewLabel()
    
    // print codes
    emit.printout(expr._1 + emit.emitIFFALSE(elseLabel, frame))
    visit(ast.thenStmt, o)
    emit.printout(emit.emitGOTO(endIfLabel, frame) + emit.emitLABEL(elseLabel, frame))
    if (ast.elseStmt != None) visit(ast.elseStmt.get, o)
    emit.printout(emit.emitLABEL(endIfLabel, frame))
    
    o
  }
  
  override def visitNewExpr(ast:NewExpr, o:Context) = {
    val access = o.asInstanceOf[Access]
    val emit = access.emit
    val clsName = access.classname
    val frame = access.frame
    val nenv = access.sym
    
    ast.exprs match {
      case List() => {
        val createClsStr = emit.emitNEW(ClassType(ast.name.name), frame) + emit.emitINVOKESPECIAL(ast.name.name +"/<init>", MethodType(List(),null), frame)
        (createClsStr, ClassType(ast.name.name))
      }
      case _ => {
        val constructor = getMember(ast.name.name, ast.name.name)
        val arguList = constructor.mtype.asInstanceOf[MethodType].in
        var index = 0
        val createCls = emit.emitNEW(ClassType(ast.name.name), frame)
        val in = ast.exprs.foldLeft(("",List[Type]()))((y,x)=>
          {
            // because in phase 1, we only have literals, therefore, this visit will always return a literal
            val (str1,typ1) = visit(x,Access(emit,clsName,frame,nenv,false,true)).asInstanceOf[(String,Type)]
            // we have to convert between float and integer
            val converted = shouldBeConvert(typ1, arguList(index), emit, frame)
            index = index + 1;
            if (converted == None)
              (y._1 + str1,y._2 :+ typ1)
            else
              (y._1 + str1 + converted.get._1 , y._2 :+ converted.get._2)
          }
        )
        val initStr = emit.emitINVOKESPECIAL(ast.name.name +"/<init>", constructor.mtype, frame)
    
        // cai nay la de bo cac params ra khoi frame
        ast.exprs.map(x=>frame.pop)
        (createCls + in._1 + initStr, ClassType(ast.name.name))
      }
    }
  }
  
  // only work with io methods
  override def visitCallExpr(ast:CallExpr, o:Context) = {
    // get all attributes of access
    val access = o.asInstanceOf[Access]
    val emit = access.emit
    val clsName = access.classname
    val frame = access.frame
    val nenv = access.sym
    
    val (str,typ) = visit(ast.cName, o).asInstanceOf[(String,Type)]
    val member = getMember(typ.asInstanceOf[ClassType].classType, ast.method.name)
    val retType = member.mtype.asInstanceOf[MethodType].out
    val arguList = member.mtype.asInstanceOf[MethodType].in
    var index = 0
    
    val in = ast.params.foldLeft(("",List[Type]()))((y,x)=>
      {
        // because in phase 1, we only have literals, therefore, this visit will always return a literal
        val (str1,typ1) = visit(x,Access(emit,access.classname,frame,nenv,false,true)).asInstanceOf[(String,Type)]
        // we have to convert between float and integer
        val converted = shouldBeConvert(typ1, arguList(index), emit, frame)
        index = index + 1;
        if (converted == None)
          (y._1 + str1,y._2 :+ typ1)
        else
          (y._1 + str1 + converted.get._1 , y._2 :+ converted.get._2)
      }
    )
    
    // cai nay la de bo cac params ra khoi frame
    ast.params.map(x=>frame.pop)
    
    if (member.skind == Static)
      (in._1 + emit.emitINVOKESTATIC(typ.asInstanceOf[ClassType].classType +"/"+ast.method.name,member.mtype,frame), retType)
    else
      (str + in._1 + emit.emitINVOKEVIRTUAL(typ.asInstanceOf[ClassType].classType + "/" + ast.method.name, member.mtype, frame), retType)
  }
  
  override def visitReturn(ast:Return, o:Context) = {
    // get all attributes of access
    val access = o.asInstanceOf[SubBody]
    val emit = access.emit
    val clsName = access.classname
    val frame = access.frame
    val nenv = access.sym
    
    val (str,typ) = visit(ast.expr, Access(emit, clsName, frame, nenv, false, true)).asInstanceOf[(String,Type)]
    
    val convert = shouldBeConvert(frame.returnType,typ,emit,frame)
    if (convert == None)
      emit.printout(str + emit.emitRETURN(typ, frame))
    else
      emit.printout(str + convert.get._1 + emit.emitRETURN(convert.get._2,frame))
  }
  
  override def visitArrayCell(ast:ArrayCell, o:Context) = {
    // get all attributes of access
    val access = o.asInstanceOf[Access]
    val emit = access.emit
    val clsName = access.classname
    val frame = access.frame
    val nenv = access.sym
    val isLeft = access.isLeft
    val isFirst = access.isFirst
    
    if (!isLeft) {
      val (strArr,typArr) = visit(ast.arr, Access(emit, clsName, frame, nenv, false, true)).asInstanceOf[(String,Type)]
      val (strIdx,typIdx) = visit(ast.idx, Access(emit, clsName, frame, nenv, false, true)).asInstanceOf[(String,Type)]
      val elemType = typArr.asInstanceOf[ArrayType].eleType
      
      (strArr + strIdx + emit.emitALOAD(elemType,frame), elemType)
    } else {
      if (isFirst) {
        val (strArr,typArr) = visit(ast.arr, Access(emit, clsName, frame, nenv, false, true)).asInstanceOf[(String,Type)]
        val (strIdx,typIdx) = visit(ast.idx, Access(emit, clsName, frame, nenv, false, true)).asInstanceOf[(String,Type)]
        
        (strArr + strIdx, typArr.asInstanceOf[ArrayType].eleType)
      } else {
        val (strArr,typArr) = visit(ast.arr, Access(emit, clsName, frame, nenv, false, true)).asInstanceOf[(String,Type)]
        
        println("trong array cell : " + typArr.asInstanceOf[ArrayType].eleType)
        (emit.emitASTORE(typArr.asInstanceOf[ArrayType].eleType, frame), typArr.asInstanceOf[ArrayType].eleType)
      }
    }
  }
  
  override def visitFieldAccess(ast:FieldAccess, o:Context) = {
    println("visit field")
    // get all attributes of access
    val access = o.asInstanceOf[Access]
    val emit = access.emit
    val clsName = access.classname
    val frame = access.frame
    val nenv = access.sym
    val isLeft = access.isLeft
    val isFirst = access.isFirst
    
    if (!isLeft) {// this expression is on the right side of an assignment/ or it's an parameter
        // find a class
        val (str,typ) = visit(ast.name, Access(emit, clsName, frame, nenv, false, true)).asInstanceOf[(String,Type)]
    
        // find a member in that class
        val member = getMember(typ.asInstanceOf[ClassType].classType, ast.field.name)
        
       member.value match {
         case Some(value) => {
           // this is a constancy
           val constExpr = value.asInstanceOf[RawValue].value
           
           // visit this expression and return a tuple to the caller
           val constResult = new ConstCalculator().visit(constExpr, null).asInstanceOf[Value]
            constResult match {
              case IntValue(_) => (emit.emitPUSHCONST(constResult, frame), IntType)
              case BoolValue(_) => (emit.emitPUSHCONST(constResult, frame), BoolType)
              case FloatValue(_) => (emit.emitPUSHCONST(constResult, frame), FloatType)
              case StringValue(_) => (emit.emitPUSHCONST(constResult, frame), StringType)
              case _ => throw NeverHappen
            }
         }
         case None => {
           // this is a variable
           println("memtype " + member.mtype)
           if (member.skind == Static) (emit.emitGETSTATIC(typ.asInstanceOf[ClassType].classType + "." + ast.field.name, member.mtype, frame), member.mtype)
           else (str + emit.emitGETFIELD(typ.asInstanceOf[ClassType].classType + "." + ast.field.name, member.mtype, frame), member.mtype)
         }
       }
    } else {// this expression is on the left side of an assignment
      if (isFirst) {
        val (str,typ) = visit(ast.name, Access(emit, clsName, frame, nenv, false, true)).asInstanceOf[(String,Type)]
        val member = getMember(typ.asInstanceOf[ClassType].classType, ast.field.name)
        
         member.value match {
          /*case Some(_) => throw NeverHappen*/
          case (None | Some(_)) => {
            if (member.skind == Static) ("",member.mtype)
            else (str,member.mtype)
          }
        }
      } else {
        // only need class name
        val (str,typ) = visit(ast.name, Access(emit, clsName, frame, nenv, false, true)).asInstanceOf[(String,Type)]
        println("vao first")
        //frame.pop()
        println("vao second")
        
        val member = getMember(typ.asInstanceOf[ClassType].classType, ast.field.name) 
        member.value match {
          /*case Some(_) => throw NeverHappen*/
          case (None | Some(_)) => {
            if (member.skind == Static) (emit.emitPUTSTATIC(typ.asInstanceOf[ClassType].classType + "." + ast.field.name, member.mtype, frame), member.mtype)
            else
              (emit.emitPUTFIELD(typ.asInstanceOf[ClassType].classType + "." + ast.field.name, member.mtype, frame), member.mtype)
            }
          }
        }
      }
  }
  
  override def visitId(ast:Id,o:Context) = {// ham visit nay chi co chuc nang tra ve ClassData
    // get all attributes of access
    val access = o.asInstanceOf[Access]
    val emit = access.emit
    val clsName = access.classname
    val frame = access.frame
    val nenv = access.sym
    val isLeft = access.isLeft
    val isFirst = access.isFirst
    
      /*lookup(ast.name,env,(x:ClassData)=>x.cname) match {
        case Some(c) => (ast.name,ClassType(ast.name))
        case None => throw Undeclared(Identifier,ast.name)
      }*/
    if (lookup(ast.name, env, (x:ClassData) => x.cname) == None) {
      // this will always found something
      val variable = lookup(ast.name, nenv, (v:(String,Type,Val)) => v._1).get
      
      if (!isLeft) {
        variable._3 match {
          case Index(i) => (emit.emitALOAD(i, variable._2, frame), variable._2)
          case Const(expr) => {
            println("nhay vao visit constResult")
            val constResult = new ConstCalculator().visit(expr, null).asInstanceOf[Value]
            constResult match {
              case IntValue(_) => (emit.emitPUSHCONST(constResult, frame), IntType)
              case BoolValue(_) => (emit.emitPUSHCONST(constResult, frame), BoolType)
              case FloatValue(_) => (emit.emitPUSHCONST(constResult, frame), FloatType)
              case StringValue(_) => (emit.emitPUSHCONST(constResult, frame), StringType)
              case _ => throw NeverHappen
            }
          }
        }
      } else {
        if (!isFirst) {
          variable._3 match {
            case Const(_) => throw NeverHappen
            case Index(i) => (emit.emitASTORE(i, variable._2, frame), variable._2)
          }
        } else ("",variable._2)
      }
    } else ("", ClassType(ast.name))
  }
  
  // ham visit nay phai tra ve tuple (String,Type) voi Type la ket qua cua phep toan
  override def visitBinaryOp(ast:BinaryOp, o:Context) = {
    val ctx = o.asInstanceOf[Access]
    val emit = ctx.emit
    val frame = ctx.frame
    
    ast.op match {
      case "^" => {
        val createStrBuilder = emit.emitNEW(ClassType("java/lang/StringBuilder"), frame) +
                                    emit.emitINVOKESPECIAL("java/lang/StringBuilder/<init>",MethodType(List(),VoidType), frame)
        frame.pop()
        
        val lhs = visit(ast.left, o).asInstanceOf[(String,Type)]
        val app1 = emit.emitINVOKEVIRTUAL("java/lang/StringBuilder/append", MethodType(List(StringType),ClassType("java/lang/StringBuilder")), frame)
        frame.pop()
        
        val rhs = visit(ast.right, o).asInstanceOf[(String,Type)]
        val app2 = emit.emitINVOKEVIRTUAL("java/lang/StringBuilder/append", MethodType(List(StringType),ClassType("java/lang/StringBuilder")), frame)
        frame.pop()
        
        val toStr = emit.emitINVOKEVIRTUAL("java/lang/StringBuilder/toString", MethodType(List(), StringType), frame)
        
        // return to caller
        (createStrBuilder + lhs._1 + app1 + rhs._1 + app2 + toStr, StringType)
      }
      case ("+"|"-"|"*"|"/"|"\\"|"%") => {
        val lhs = visit(ast.left, o).asInstanceOf[(String,Type)]
        val rhs = visit(ast.right, o).asInstanceOf[(String,Type)]
        
        ast.op match {
          case ("+"|"-"|"*") => {
            val sameType = lhs._2 == rhs._2
            if (sameType) {
              if (ast.op == "*")
                (lhs._1 + rhs._1 + emit.emitMULOP(ast.op, lhs._2, frame), lhs._2)
              else
                (lhs._1 + rhs._1 + emit.emitADDOP(ast.op, lhs._2, frame), lhs._2)
            } else {
              if (lhs._2 == IntType)
                (lhs._1 + emit.emitI2F(frame) + rhs._1
                    + (if (ast.op == "*") emit.emitMULOP(ast.op, rhs._2, frame) else emit.emitADDOP(ast.op, rhs._2, frame)), rhs._2)
              else
                (lhs._1 + rhs._1 + emit.emitI2F(frame)
                    + (if (ast.op == "*") emit.emitMULOP(ast.op, lhs._2, frame) else emit.emitADDOP(ast.op, lhs._2, frame)), lhs._2)
            }
          }
          case "\\" => (lhs._1 + rhs._1 + emit.emitDIV(frame), lhs._2)
          case "%" => (lhs._1 + rhs._1 + emit.emitMOD(frame), lhs._2)
          case "/" => (lhs._2,rhs._2) match {
            case (IntType,IntType) => (lhs._1 + emit.emitI2F(frame) + rhs._1 + emit.emitI2F(frame) + emit.emitMULOP(ast.op, FloatType, frame),FloatType)
            case (IntType,FloatType) => (lhs._1 + emit.emitI2F(frame) + rhs._1 + emit.emitMULOP(ast.op,FloatType,frame),FloatType)
            case (FloatType,IntType) => (lhs._1 + rhs._1 + emit.emitI2F(frame) + emit.emitMULOP(ast.op, FloatType, frame),FloatType)
            case _ => (lhs._1 + rhs._1 + emit.emitMULOP(ast.op,lhs._2,frame),lhs._2)
          }
        }
      }
      case ("||"|"&&") => {
        val shCircuitLabel = frame.getNewLabel()
        val endLabel = frame.getNewLabel()
        
        val lhs = visit(ast.left, o).asInstanceOf[(String,Type)]
        val shortCircuitStr = if (ast.op == "&&") emit.emitIFFALSE(shCircuitLabel, frame) else emit.emitIFTRUE(shCircuitLabel, frame)
        val pushConst = if (ast.op == "&&") emit.emitPUSHICONST("true", frame) else emit.emitPUSHICONST("false",frame)
        val rhs = visit(ast.right, o).asInstanceOf[(String,Type)]
        val cmd = if (ast.op == "&&") emit.emitANDOP(frame) else emit.emitOROP(frame)
        val jumpToEndLabel = emit.emitGOTO(endLabel, frame)
        val shortCircuitLabel = emit.emitLABEL(shCircuitLabel,frame) + (if (ast.op == "&&") emit.emitPUSHICONST(0, frame) else emit.emitPUSHICONST(1, frame))
        
        (lhs._1 + shortCircuitStr + pushConst + rhs._1 + cmd + jumpToEndLabel + shortCircuitLabel + emit.emitLABEL(endLabel, frame), BoolType)
      }
      case ("==" | "<>" | ">" | ">=" | "<=" | "<") => {
        val lhs = visit(ast.left,o).asInstanceOf[(String,Type)]
        val rhs = visit(ast.right,o).asInstanceOf[(String,Type)]
        val sameType = lhs._2.isInstanceOf[ClassType] || lhs._2.isInstanceOf[ArrayType] || lhs._2 == rhs._2
        if (sameType) (emit.emitREOP(ast.op, lhs._2, frame),BoolType)
        else {
          if (lhs._2 == IntType) (lhs._1 + emit.emitI2F(frame) + rhs._1 + emit.emitREOP(ast.op,rhs._2,frame),BoolType)
          else (lhs._1 + rhs._1 + emit.emitI2F(frame) + emit.emitREOP(ast.op, lhs._2, frame), BoolType)
        }
      } 
    }
  }
  
  override def visitUnaryOp(ast:UnaryOp, o:Context) = {
    val ctx = o.asInstanceOf[Access]
    val emit = ctx.emit
    val frame = ctx.frame
    
    val expr = visit(ast.body, o).asInstanceOf[(String,Type)]
    
    val (exprStr, exprType) = ast.op match {
      case "+" => ("", expr._2)
      case "-" => (emit.emitNEGOP(expr._2, frame), expr._2)
      case "!" => (emit.emitNOT(expr._2, frame), expr._2)
    }
    
    (expr._1 + exprStr, expr._2)
  }
 
  override def visitIntLiteral(ast:IntLiteral,o:Context) = {
    val ctxt = o.asInstanceOf[Access] // cai nay duoc truyen vao la access
    val emit = ctxt.emit
    val frame = ctxt.frame
    
    // ham visit nay co chuc nang tao ra string de load int constancy
    (emit.emitPUSHICONST(ast.value, frame),IntType)
  }
  
  override def visitFloatLiteral(ast:FloatLiteral,o:Context) = {
    val ctx = o.asInstanceOf[Access]
    val emit = ctx.emit
    val frame = ctx.frame
    
    (emit.emitPUSHFCONST(ast.value.toString, frame), FloatType)
  }
  
  override def visitBooleanLiteral(ast:BooleanLiteral, o:Context) = {
    val ctx = o.asInstanceOf[Access]
    val emit = ctx.emit
    val frame = ctx.frame
    
    (emit.emitPUSHICONST(ast.value.toString, frame), BoolType)
  }
  
  override def visitStringLiteral(ast:StringLiteral, o:Context) = {
    val ctx = o.asInstanceOf[Access]
    val emit = ctx.emit
    val frame = ctx.frame
    
    println("StringLiteral " + ast.value)
    
    (emit.emitPUSHCONST(ast.value, StringType, frame), StringType)
  }
  
  override def visitSelfLiteral(ast:SelfLiteral.type, o:Context) = {
    val ctx = o.asInstanceOf[Access]
    val emit = ctx.emit
    val frame = ctx.frame
    
    (emit.emitALOAD(0, ClassType(ctx.classname), frame), ClassType(ctx.classname))
  }
  
  override def visitNullLiteral(ast:NullLiteral.type, o:Context) = {
    val ctx = o.asInstanceOf[Access]
    val emit = ctx.emit
    val frame = ctx.frame
    
    (emit.emitNULLCONST(frame),ClassType(""))
  }
}