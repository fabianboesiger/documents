// Verhält sich wie eine "normale" case class
@flyweight
case class Plus {...} 



// Wird umgreschrieben in:
class Plus {..}

// Alle Methoden einer case class werden generiert,
// @flyweight case class Pluss kann also effektiv wie eine echte case class verwendet werden.
object Plus {
  def apply() {...}
  def unapply() {...}
  // TODO:
  def copy() {...} 
  // ?
  def toString() {...}
  
  // Wird nicht mehr benötigt, da wir nun auf reference equality überprüfen
  def equals() {}
  def hashCode() {}
}



// Problem: Return Type von apply ist derzeit nicht unbedingt ein konkreter Typ,
// d.h. interne HashMap muss als Value Type "Term" haben, nicht "Plus".
def apply(e0: Term, e1: Term): Term = (e0, e1) match {
  case (t0, Zero) => t0
  case (Zero, t1) => t1
  case (IntLiteral(n0), IntLiteral(n1)) => IntLiteral(n0 + n1)
  case _ => new Plus(e0, e1)
}



// Lösung: Zweites Makro, für die simplification Funktionalität
// 1. Unbenennen der vorhandenen apply methode (z.b. zu "_apply")
// 2. Generiere eine neue apply Methode
//    * Die neue apply Mehode versucht zuerst zu reduzieren mit der bereits definierten reduce methode
//    * Falls dies gelingt (Some(_)), returne die reduktion
//    * Falls nicht (None), wende _apply an, um eine neue Plus instanz zu erzeugen (Oder eben auch nicht, falls schon eine exisitert)
@reduce
@flyweight
case class Plus {...} 

object Plus {
  def reduce(e0: Term, e1: Term): Term = (e0, e1) match {
    case (t0, Zero) => Some(t0)
    case (Zero, t1) => Some(t1)
    case (IntLiteral(n0), IntLiteral(n1)) => Some(IntLiteral(n0 + n1))
    case _ => None
  }
}


// PROS:
// - Benötigt kein IntelliJ Plugin, da IntelliJ bereits weiss, dass Plus alle Methoden einer case class hat
// - Zwischen der Flyweight Funktionalität und der Simplification Funktionalität wird klar unterschieden, wie es sein sollte.
// - Da das Flyweight makro sehr allegmein ist, kann es separat als Scala library angeboten werden
// - Das reduce makro kann später einfacher erweitert werden (DSL, extension goals)

// CONS:
// - Zwei Makros statt eines (Funktioniert das überhaupt)



// Namen der Makros:
@reduce
@flyweight
case class Plus {...} 

@simplify
@cached
case class Plus {...} 























// Vorher:
class Plus(val p0: Term, val p1: Term) extends ArithmeticTerm
    with BinaryOp[Term] with StructuralEqualityBinaryOp[Term] {

  override val op = "+"
}

object Plus extends ((Term, Term) => Term) {
  import predef.Zero

  def apply(e0: Term, e1: Term) = (e0, e1) match {
    case (t0, Zero) => t0
    case (Zero, t1) => t1
    case (IntLiteral(n0), IntLiteral(n1)) => IntLiteral(n0 + n1)
    case _ => new Plus(e0, e1)
  }

  def unapply(t: Plus) = Some((t.p0, t.p1))
}





// Nachher:
@flyweight
case class Plus(p0: Term, p1: Term) extends ArithmeticTerm
  with BinaryOp[Term] {

  override val op = "+"
}

object Plus {
  import predef.Zero

  // As of Scala 2.12.2+, Scala allows overriding apply and unapply by default. 
  // https://stackoverflow.com/questions/5827510/how-to-override-apply-in-a-case-class-companion
  override def apply(e0: Term, e1: Term): Term = (e0, e1) match {
    case (t0, Zero) => t0
    case (Zero, t1) => t1
    case (IntLiteral(n0), IntLiteral(n1)) => IntLiteral(n0 + n1)
    case _ => new Plus(e0, e1)
  }
}

// Wird zu:
class Plus(val p0: Term, val p1: Term) extends ArithmeticTerm
  with BinaryOp[Term]  {

  override val op = "+"
}

object Plus {
  import scala.collection.mutable.HashMap
  var pool = new HashMap[(Term, Term), Plus]

  import predef.Zero

  // apply wird zu _apply
  // Problem: return type von apply muss nicht unbedingt Typ Plus haben
  def _apply(e0: Term, e1: Term): Term = (e0, e1) match {
    case (t0, Zero) => t0
    case (Zero, t1) => t1
    case (IntLiteral(n0), IntLiteral(n1)) => IntLiteral(n0 + n1)
    case _ => new Plus(e0, e1)
  }

  def apply(e0: Term, e1: Term) = {
    pool.get(e0, e1) match {
      case Some(term) => term
      case None =>
        val term = Plus._apply(e0, e1)
        pool.addOne(((e0, e1), term))
        term
    }
  }

  def unapply() {...}
  def copy() {...} 
  def toString() {...}
}











object Pool {
  import scala.collection.mutable.HashMap

  val pool = new HashMap[Term, Term]

  // Checks if an instance of a node already exists in our pool.
  def check[T <: Term](term: T): T = {
    pool.get(term) match {
      // If an instance exists, return this instance.
      case Some(existingTerm) => existingTerm.asInstanceOf[T]
      // If no such instance exists, return the new instance.
      case None => {
        pool.addOne((term, term))
        term
      }
    }
  }
}

sealed trait Term extends Node {
  // ...

  // Override the equals method to do a reference equality check.
  override def equals(other: Any) =
    this.eq(other.asInstanceOf[AnyRef])
}

object Plus extends ((Term, Term) => Term) {
  import predef.Zero

  def apply(e0: Term, e1: Term) = {
    Pool.check(new Plus(e0, e1))
  }

  // ...
}

class Plus {

}

object Plus {
  def apply() {...}
  def unapply() {...}
  // TODO:
  def copy() {...} 
  // ?
  def toString() {...}
  
  // Wird nicht mehr benötigt, da wir nun auf reference equality überprüfen
  def equals() {}
  def hashCode() {}
}