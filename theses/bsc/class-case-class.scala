class MyClass

case class MyCaseClass(x: Int, y: String)

/*
package <empty> {
  class MyClass extends Object {
    def <init>(): MyClass = {
      MyClass.super.<init>();
      ()
    }
  };
  case class MyCaseClass extends Object with Product with java.io.Serializable {
    def productElementNames(): Iterator = MyCaseClass.super.productElementNames();
    <synthetic> def copy(): MyCaseClass = new MyCaseClass();
    override <synthetic> def productPrefix(): String = "MyCaseClass";
    <synthetic> def productArity(): Int = 0;
    <synthetic> def productElement(x$1: Int): Object = {
      case <synthetic> val x1: Int = x$1;
      case4(){
        matchEnd3(scala.runtime.Statics.ioobe(x$1))
      };
      matchEnd3(x: Object){
        x
      }
    };
    override <synthetic> def productIterator(): Iterator = scala.runtime.ScalaRunTime.typedProductIterator(MyCaseClass.this);
    <synthetic> def canEqual(x$1: Object): Boolean = x$1.$isInstanceOf[MyCaseClass]();
    override <synthetic> def productElementName(x$1: Int): String = {
      case <synthetic> val x1: Int = x$1;
      case4(){
        matchEnd3(scala.runtime.Statics.ioobe(x$1).$asInstanceOf[String]())
      };
      matchEnd3(x: String){
        x
      }
    };
    override <synthetic> def hashCode(): Int = scala.runtime.ScalaRunTime._hashCode(MyCaseClass.this);
    override <synthetic> def toString(): String = scala.runtime.ScalaRunTime._toString(MyCaseClass.this);
    override <synthetic> def equals(x$1: Object): Boolean = {
  case <synthetic> val x1: Object = x$1;
  case5(){
    if (x1.$isInstanceOf[MyCaseClass]())
      matchEnd4(true)
    else
      case6()
  };
  case6(){
    matchEnd4(false)
  };
  matchEnd4(x: Boolean){
    x
  }
}.&&(x$1.$asInstanceOf[MyCaseClass]().canEqual(MyCaseClass.this));
    def <init>(): MyCaseClass = {
      MyCaseClass.super.<init>();
      MyCaseClass.super./*Product*/$init$();
      ()
    }
  };
  <synthetic> object MyCaseClass extends scala.runtime.AbstractFunction0 with java.io.Serializable {
    final override <synthetic> def toString(): String = "MyCaseClass";
    case <synthetic> def apply(): MyCaseClass = new MyCaseClass();
    case <synthetic> def unapply(x$0: MyCaseClass): Boolean = if (x$0.eq(null))
      false
    else
      true;
    <synthetic> private def writeReplace(): Object = new scala.runtime.ModuleSerializationProxy(classOf[MyCaseClass$]);
    case <synthetic> <bridge> <artifact> def apply(): Object = MyCaseClass.this.apply();
    def <init>(): MyCaseClass.type = {
      MyCaseClass.super.<init>();
      ()
    }
  }
}
*/