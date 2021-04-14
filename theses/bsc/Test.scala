class MyClass

trait SomeTrait

case class MyCaseClass() {
  override def equals(other: Any): Boolean = super.equals(other)

  override def hashCode: Int = super.hashCode
}

object Test {
    def main(args: Array[String]) = {
        val c = SubClass(3)
        println(c)
    }
}

class SuperClass(a: Int)

object SuperClass {
  def apply(a: Int): SuperClass = {
    println("!")
    new SuperClass(a)
  }
}

case class SubClass(b: Int) extends SuperClass(b)