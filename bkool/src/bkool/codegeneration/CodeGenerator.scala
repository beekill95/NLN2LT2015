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
    val retType = visit(ast.returnType, o).asInstanceOf[Type]
    
    val methodDecl = Member(ast.name.name, ast.kind, Method, MethodType(paraList, retType), None)
    
    ListMember(memberList :+ methodDecl)
  }
  
  override def visitParamDecl(ast:ParamDecl, o:Context) = ast.paramType
  
  override def visitVarDecl(ast:VarDecl, o:Context):(String,Type,Value) = (ast.variable.name, ast.varType, null)
  
  override def visitConstDecl(ast:ConstDecl, o:Context):(String,Type,Value) = (ast.id.name, ast.constType, RawValue(ast.const))
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
    val memDecl = lookup(memName, classDecl.mem, (x:Member) => x.name).get
    
    // return to the caller
    memDecl
  }
  
  // this will check whether type1 should be converted to match type2
  // type1: type is checked
  // type2: type is checked with
  // version: this will only deal with integer and float
  def shouldBeConvert(type1:Type, type2:Type, emit:Emitter, frame:Frame):Option[(String,Type)] = type1 match {
    case IntType => if (type2 == FloatType) {
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
      
      // cai nay truyen vao List[Decl]() la rong
      ast.decl.map(x => visit(x,SubContext(emit,ast.name.name,List())))
      
      // su dung ham generate method de generate ra default constructor (trong truong hop nay)
      // con ham genMethod thay viet se generate ra method chung luon
      // minh phai can than, chi khi nao trong class khong co constructor thi minh moi su dung ham nay
      // nguoc lai thi phai su dung cua nguoi lap trinh viet ra
      genMETHOD(ast.name.name,
            MethodDecl(Instance,ast.name,List(),null,Block(List(),List())),o,List(),new Frame(VoidType),emit)
            
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
    val param = if (isMain) List(ParamDecl(Id("args"), ArrayType(IntLiteral(0),StringType))) else consdecl.param
    val mtype =  MethodType(param.map(_.paramType),returnType)
    
    emit.printout(emit.emitMETHOD(methodName, mtype, consdecl.kind==Static,frame));
    frame.enterScope(true);
    
    // Generate code for parameter declarations
    if (consdecl.kind == Instance) emit.printout(emit.emitVAR(frame.getNewIndex,"this",ClassType(classname),frame.getStartLabel,frame.getEndLabel,frame))
    // cho nay SubBody de lam gi?
    val env = param.foldLeft(List[(String,Type,Val)]())((y,x) => visit(x,SubBody(emit,classname,frame,y)).asInstanceOf[List[(String,Type,Val)]])
    val body = consdecl.body.asInstanceOf[Block]
    
    //Generate code for local declarations
    val newenv = body.decl.foldLeft(List[(String,Type,Val)]())((y,x) => visit(x,SubBody(emit,classname,frame,y)).asInstanceOf[List[(String,Type,Val)]])
    emit.printout(emit.emitLABEL(frame.getStartLabel(),frame))
    
    //Generate code for statements
    if (isInit) {
      emit.printout(emit.emitREADVAR("this",ClassType(classname),0,frame))
      emit.printout(emit.emitINVOKESPECIAL())
    }
    body.stmt.map(x=>visit(x,SubBody(emit,classname,frame,env++newenv)))
    
    emit.printout(emit.emitLABEL(frame.getEndLabel(),frame))
    if (returnType == VoidType) emit.printout(emit.emitRETURN(VoidType,frame));
    emit.printout(emit.emitENDMETHOD(frame));
    frame.exitScope();
    
    
  }
  override def visitMethodDecl(ast:MethodDecl,o:Context) = {
    val subctxt = o.asInstanceOf[SubContext]
    val emit = subctxt.emit
    val frame = new Frame(ast.returnType)
    
    // truong hop nay la generate ra method chung (neu co constructor thi cung se duoc generate ra)
    genMETHOD(subctxt.classname,ast,o,subctxt.decl,frame,emit)
    
    o
  }
  
  override def visitAttributeDecl(ast:AttributeDecl, o:Context) = {
    val subctx = o.asInstanceOf[SubContext]
    val emit = subctx.emit
    
    val decl = visit(ast.decl, null).asInstanceOf[(String,Type,Value)]
    
    // this is just temporary
    emit.printout(emit.emitATTRIBUTE(decl._1, ast.kind, decl._2, if (decl._3 == null) false else true, if (decl._3 == null) "" else decl._3.toString))
    
    // return to the caller
    o
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
    val (str,typ) = visit(ast.parent,Access(emit,ctxt.classname,frame,nenv,false,true)).asInstanceOf[(String,Type)]
    val member = getMember(str, ast.method.name)
    val arguList = member.mtype.asInstanceOf[MethodType]
    val arguArr = arguList.in.toArray
    var index = 0
    
    val in = ast.params.foldLeft(("",List[Type]()))((y,x)=>
      {
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
    emit.printout(emit.emitINVOKESTATIC(str+"/"+ast.method.name,MethodType(in._2,VoidType),frame)) // cai nay co return type la voidtype chi ap dung cho phase 1 ma thoi
                                                                                                   // do ham in ra man hinh khong co gia tri tra ve
             
    
  }
  
  override def visitId(ast:Id,o:Context) = {// ham visit nay chi co chuc nang tra ve ClassData        
      lookup(ast.name,env,(x:ClassData)=>x.cname) match {
        case Some(c) => (ast.name,ClassType(ast.name))
        case None => throw Undeclared(Identifier,ast.name)
      }  
  }
  
  // ham visit nay phai tra ve tuple (String,Type) voi Type la ket qua cua phep toan
  override def visitBinaryOp(ast:BinaryOp, o:Context) = {
    val ctx = o.asInstanceOf[Access]
    val emit = ctx.emit
    val frame = ctx.frame
    
    val lhExpr = visit(ast.left, o).asInstanceOf[(String,Type)]
    val rhExpr = visit(ast.right, o).asInstanceOf[(String,Type)]
    val sameType = lhExpr._2 == rhExpr._2       // check whether these two operands are of the same type
    
    val (convertStr,converType) = if (!sameType) {
      // if this happens, these two types must be int and float
      if (lhExpr._2 == IntType) {
        (lhExpr._1 + emit.emitI2F(frame) + rhExpr._1, rhExpr._2)
      } else {
        (lhExpr._1 + rhExpr._1 + emit.emitI2F(frame), lhExpr._2)
      }
    } else {
      (lhExpr._1 + rhExpr._1, lhExpr._2)
    }
    
    val (exprStr,exprType) = ast.op match {
      case ("+" | "-") => {
        if (sameType) {// if they are of the same type
          (emit.emitADDOP(ast.op, lhExpr._2, frame),lhExpr._2)
        } else {
          (emit.emitADDOP(ast.op, FloatType, frame), FloatType)
        }
      }
      
      case ("*" | "/") => {
        if (sameType) {// if they are of the same type
          if (lhExpr._2 == IntType && ast.op == "/") {
            (emit.emitMULOP(ast.op, lhExpr._2, frame) + emit.emitI2F(frame), FloatType)
          } else
            (emit.emitMULOP(ast.op, lhExpr._2, frame), lhExpr._2)
        } else {
          (emit.emitMULOP(ast.op, FloatType, frame), FloatType)
        }
      }
      
      case "%" => {// somewhere in the code we assume that the two types will only be IntType
        // no need to check of the same type
        (emit.emitMOD(frame), IntType)
      }
      
      case "\\" => {// this only receives two intergers (we assume somewhere else in the code)
        (emit.emitMULOP("\\", IntType, frame), IntType)
      }
      
      case _ => ("", null) // we don't have to process this at this stage
    }
    
    (convertStr + exprStr, exprType)
  }
  
  override def visitUnaryOp(ast:UnaryOp, o:Context) = {
    val ctx = o.asInstanceOf[Access]
    val emit = ctx.emit
    val frame = ctx.frame
    
    val expr = visit(ast.body, o).asInstanceOf[(String,Type)]
    
    val (exprStr, exprType) = ast.op match {
      case "+" => ("", expr._2)
      case "-" => {
        (emit.emitNEGOP(expr._2, frame), expr._2)
      }
      case _ => ("", null) // we don't have to process this operator at this stage yet
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
}