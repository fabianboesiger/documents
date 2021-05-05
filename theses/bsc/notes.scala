/*
- min max anzahl knoten
- benchmarks ohne vereinfachungen (performance change vs runtime), mit ohne flyweight
- plugin konfiguration
*/


/*
AST Tiefe bei equality checks im Durchschnitt 3.
*/


/*
Verifizierung von VerCors, Frontends: Prusti, Vyper, Gobra

Stdev		2.9%
Differenz	2.0% 		(Verschlechterung)

--> Differenz liegt innerhalb der Standardabweichung!
*/


/*
Verifizierung von VerCors

HashMap			-1.3%
WeakHashMap		-0.2%
TrieMap			-0.2%
ListMap			89.5%

--> Schwierig, Aussagen über Performance zu machen, da innerhalb Stdev
--> Aber: ListMap sehr schlecht :)
*/




/*
Multithreading in Silicon

Verifizerung mit 8 Threads ist pro Datei im Durchschnitt 40% langsamer als mit 1 Thread.
ABER: Insgesamt über das ganze VerCors Projekt hebt sich das wieder auf (Siehe Diagramm).

Zusammenhang mit bug in compare_benchmarks.py?

Performance würde wahrscheinlich besser werden, wenn Verifizierung von mehreren Files gleichzeitig
erlaubt wäre ... Eventuell ein Extension Goal?
*/















@flyweight 
class True()

object True



True()

True




/*
- case classes -> case objects
- nur terme, die sich "lohnen" in flyweight zu verwandeln (cache misses?, arraylist?)
- benchmarks einordnen (beschreibung?)
  - für Prusti
    - enableMoreCompleteExhale
    - disableMostStateConsolidations
  - numberOfParallelVerifiers
- plugin tests, konfigurationin
- anzahl knoten der terme bestimmen (equals methode überschreiben?)
*/


/*
Reset pools after each file: -3.6% -> Keine Verbesserung
BuiltinEquals Änderung:            -> Keine Verbesserung

- Plugin!
*/

object Equals extends ((Term, Term) => BooleanTerm) {
  def apply(e0: Term, e1: Term) = {
    assert(e0.sort == e1.sort,
      s"Expected both operands to be of the same sort, but found ${e0.sort} ($e0) and ${e1.sort} ($e1).")

    if (e0 == e1)
      True()
    else
      e0.sort match {
        case sorts.Snap =>
          (e0, e1) match {
            case (sw1: SortWrapper, sw2: SortWrapper) if sw1.t.sort != sw2.t.sort =>
              assert(false, s"Equality '(Snap) $e0 == (Snap) $e1' is not allowed")
            /* The next few cases are nonsensical and might indicate a bug in Silicon.
               However, they can also arise on infeasible paths (and preventing them
               would require potentially expensive prover calls), so treating
               them as errors is unfortunately not an option.
             */
            // case (_: Combine, _: SortWrapper) =>
            //   assert(false, s"Equality '$e0 == (Snap) $e1' is not allowed")
            // case (_: SortWrapper, _: Combine) =>
            //   assert(false, s"Equality '(Snap) $e0 == $e1' is not allowed")
            // case (Unit, _: Combine) | (_: Combine, Unit) =>
            //   assert(false, s"Equality '$e0 == $e1' is not allowed")
            case _ => /* Ok */
          }

          BuiltinEquals(e0, e1)

        case _: sorts.Seq | _: sorts.Set | _: sorts.Multiset | _: sorts.Map => CustomEquals(e0, e1)
        case _ => BuiltinEquals(e0, e1)
      }
  }

  def unapply(e: Equals) = Some((e.p0, e.p1))
}














// Mean (B) und Mean (V) Spalten vertauscht bei compare benchmarks

/*

IdentityHashCode (lazy val):	+3.4%
IdentityHashCode (val): 		-0.3%
Reset Pools after each file: 	-3.6%
BuiltinEquals 					+30.1% <- nicht konsistent!
BuiltinEquals                   -3%    <- nur konsistente files

*/



/*

BuiltinEquals fehlgeschlagene Tests
- issue387/va6.vpr
- issue387/va9.vpr
- all/basic/abstract_funcs_and_preds.vpr
- all/basic/arithmetic.vpr
- all/basic/func2
- all/basic/goto.vpr
...

*/



/*
Eine Idee:

Ersetze Seq[Term] / Seq[Node] durch LinkedHashSet[Term] / LinkedHashSet[Node]
- Insertion order wird beibehalten
- Operationen wie contains() sind schneller
*/







/*

            Live Bytes
Base        77 MB
Flyweight   214 MB


NEUE BENCHMARKS VerCors verification time (positiv ist besser)
HashMap	0.04
Scaffeine	-3.58
TrieMap	0.61 <- minimale verbesserung
TrieMap Case Class	-0.25


- Arbeitsspeicherauslastung 3 mal mehr für +0.6% performance
*/



//- Cache kann nicht funktionieren 

// Pool mit max. 1 Objekt
val a = Term(1)
// Term(1) jetzt im memory pool
Term(2)
// Term(1) nicht mehr im memory pool
val b = Term(1)
// Eine neue Instanz von Term(1) wurde generiert
assert(a == b)
// Assertion hält nicht, da a und b verschiedene Instanzen sind



//- Stattdessen Maps leeren nach jedem Durchlauf?
//- Multithreading












// Scalas hashCode Methode ruft foldgende Methode auf:
scala.runtime.ScalaRunTime._hashCode()
// Definition:
def _hashCode(x: Product): Int = {
  val arr =  x.productArity
  var code = arr
  var i = 0
  while (i < arr) {
    val elem = x.productElement(i)
    code = code * 41 + (if (elem == null) 0 else elem.hashCode()) // <- Rekursion!
    i += 1
  }
  code
}


// Stattdessen definieren wir die HashCode Metthode wie folgt:
override def hashCode(): Int = super.hashCode()
// super.hashCode() ruft die hashCode Methode von java.lang.Object auf
// Implementierung:
/*
[..] This is typically implemented by converting the internal address of the object into an integer,
but this implementation technique is not required by the JavaTM programming language.
*/
// -> Keine Rekursion



// Problem
class Combine(val p0: Term, val p1: Term) extends SnapshotTerm
  with StructuralEqualityBinaryOp[Term] { ... }

case class MagicWandSnapshot(abstractLhs: Term, rhsSnapshot: Term)
  extends Combine(abstractLhs, rhsSnapshot) { ... }



















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
