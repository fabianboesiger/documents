/*
BUG
- Consider: this.parent == null ==> this.root == this
- partiallyConsumedHeap, ssCache not equal after pure implies from evaluator
- second ssCache empty, first not
*/











/*
- min max anzahl knoten
- benchmarks ohne vereinfachungen (performance change vs runtime), mit ohne flyweight
- plugin konfiguration
*/


// Anzahl Knoten: gemacht, sehr ähnlich wie Tiefe.
// Bemerkung: Nichtdeterminismus wird mit flyweight pattern eingeführt!


// Benchmarks ohne Vereinfachungen: Sehr viele Tests schlagen fehl, benchmark nicht aussagekräftig
// Performance könnte besser geworden sein, mehr tests notwendig


// Fokus auf Codebase vereinfachung: @flyweight -> @rewrite
// Generiere: 
// - subterms (als lazy val)
// - recurse (als def)     
//
// Performance könnte besser werden. (wie genau funktioniert override in Scala?)
// Zeigt, das makros etwas nützen

def subterms(t: Term): Seq[Term] = t match {
    case _: Symbol | _: Literal | _: MagicWandChunkTerm => Nil
    case op: BinaryOp[Term@unchecked] => List(op.p0, op.p1)
    case op: UnaryOp[Term@unchecked] => List(op.p)
    case ite: Ite => List(ite.t0, ite.t1, ite.t2)
    case and: And => and.ts
    case or: Or => or.ts
    case _: PermLiteral => Nil
    case fp: FractionPerm => List(fp.n, fp.d)
    case ivp: IsValidPermVar => List(ivp.v)
    case irp: IsReadPermVar => List(irp.v, irp.ub)
    case app: Application[_] => app.args
    case sr: SeqRanged => List(sr.p0, sr.p1)
    case ss: SeqSingleton => List(ss.p)
    case su: SeqUpdate => List(su.t0, su.t1, su.t2)
    case mu: MapUpdate => List(mu.base, mu.key, mu.value)
    case ss: SingletonSet => List(ss.p)
    case ss: SingletonMultiset => List(ss.p)
    case sw: SortWrapper => List(sw.t)
    case _: Distinct => Seq.empty // d.ts.toList
    case q: Quantification => q.vars ++ List(q.body) ++ q.triggers.flatMap(_.p)
    case l: Let =>
      val (vs, ts) = l.bindings.toSeq.unzip
      vs ++ ts :+ l.body
    case Domain(_, fvf) => fvf :: Nil
    case Lookup(_, fvf, at) => fvf :: at :: Nil
    case PermLookup(_, pm, at) => pm :: at :: Nil
    case PredicateDomain(_, psf) => psf :: Nil
    case PredicateLookup(_, psf, args) => Seq(psf) ++ args
    case PredicatePermLookup(_, pm, args) => Seq(pm) ++ args
    case FieldTrigger(_, fvf, at) => fvf :: at :: Nil
    case PredicateTrigger(_, psf, args) => psf +: args

  }

  /** @see [[viper.silver.ast.utility.Transformer.simplify()]] */
  def transform[T <: Term](term: T,
                           pre: PartialFunction[Term, Term] = PartialFunction.empty)
                          (recursive: Term => Boolean = !pre.isDefinedAt(_),
                           post: PartialFunction[Term, Term] = PartialFunction.empty)
                          : T = {

    def go[D <: Term](term: D): D = transform(term, pre)(recursive, post)

    def goTriggers(trigger: Trigger) = Trigger(trigger.p map go)

    def recurse(term: Term): Term = term match {
      case _: Var | _: Function | _: Literal | _: MagicWandChunkTerm | _: Distinct => term

      case Quantification(quantifier, variables, body, triggers, name, isGlobal) =>
        Quantification(quantifier, variables map go, go(body), triggers map goTriggers, name, isGlobal)

      case Plus(t0, t1) => Plus(go(t0), go(t1))
      case Minus(t0, t1) => Minus(go(t0), go(t1))
      case Times(t0, t1) => Times(go(t0), go(t1))
      case Div(t0, t1) => Div(go(t0), go(t1))
      case Mod(t0, t1) => Mod(go(t0), go(t1))
      case Not(t) => Not(go(t))
      case Or(ts) => Or(ts map go : _*)
      case And(ts) => And(ts map go : _*)
      case Implies(t0, t1) => Implies(go(t0), go(t1))
      case Iff(t0, t1) => Iff(go(t0), go(t1))
      case Ite(t0, t1, t2) => Ite(go(t0), go(t1), go(t2))
      case BuiltinEquals(t0, t1) =>
        val t0New = go(t0)
        val t1New = go(t1)
        /* Rewriting equalities is potentially ambiguous: if the sort of the arguments of a
         * built-in equality changes from a primitive to a non-primitive sort, e.g. from Int
         * to Set[E], then users might or might not expect that the built-in equality is
         * replaced by the sort-specific, custom equality.
         *
         * For now, such potentially ambiguous transformations are rejected by the following
         * assertions.
         */
        assert(t0New.sort == t0.sort, s"Unexpected sort change: from ${t0.sort} to ${t0New.sort}")
        BuiltinEquals(t0New, t1New)
      case CustomEquals(t0, t1) =>
        val t0New = go(t0)
        val t1New = go(t1)
        /* See comments for built-in equality.
         *
         * Difference here: instead of creating a new CustomEquality instance directly, the
         * factory method Equals.apply could be used to create, depending on the new sort of the
         * arguments, either a built-in or a custom equality.
         */
        assert(t0New.sort == t0.sort, s"Unexpected sort change: from ${t0.sort} to ${t0New.sort}")
        CustomEquals(t0New, t1New)
      case Less(t0, t1) => Less(go(t0), go(t1))
      case AtMost(t0, t1) => AtMost(go(t0), go(t1))
      case Greater(t0, t1) => Greater(go(t0), go(t1))
      case AtLeast(t0, t1) => AtLeast(go(t0), go(t1))
      case _: PermLiteral => term
      case FractionPerm(n, d) => FractionPerm(go(n), go(d))
      case IsValidPermVar(v) => IsValidPermVar(go(v))
      case IsReadPermVar(v, ub) => IsReadPermVar(go(v), go(ub))
      case PermTimes(p0, p1) => PermTimes(go(p0), go(p1))
      case IntPermTimes(p0, p1) => IntPermTimes(go(p0), go(p1))
      case PermIntDiv(p0, p1) => PermIntDiv(go(p0), go(p1))
      case PermPlus(p0, p1) => PermPlus(go(p0), go(p1))
      case PermMinus(p0, p1) => PermMinus(go(p0), go(p1))
      case PermLess(p0, p1) => PermLess(go(p0), go(p1))
      case PermAtMost(p0, p1) => PermAtMost(go(p0), go(p1))
      case PermMin(p0, p1) => PermMin(go(p0), go(p1))
      case App(f, ts) => App(f, ts map go)
      case SeqRanged(t0, t1) => SeqRanged(go(t0), go(t1))
      case SeqSingleton(t) => SeqSingleton(go(t))
      case SeqAppend(t0, t1) => SeqAppend(go(t0), go(t1))
      case SeqDrop(t0, t1) => SeqDrop(go(t0), go(t1))
      case SeqTake(t0, t1) => SeqTake(go(t0), go(t1))
      case SeqLength(t) => SeqLength(go(t))
      case SeqAt(t0, t1) => SeqAt(go(t0), go(t1))
      case SeqIn(t0, t1) => SeqIn(go(t0), go(t1))
      case SeqUpdate(t0, t1, t2) => SeqUpdate(go(t0), go(t1), go(t2))
      case SingletonSet(t) => SingletonSet(go(t))
      case SetAdd(t0, t1) => SetAdd(go(t0), go(t1))
      case SetUnion(t0, t1) => SetUnion(go(t0), go(t1))
      case SetIntersection(t0, t1) => SetIntersection(go(t0), go(t1))
      case SetSubset(t0, t1) => SetSubset(go(t0), go(t1))
      case SetDifference(t0, t1) => SetDifference(go(t0), go(t1))
      case SetIn(t0, t1) => SetIn(go(t0), go(t1))
      case SetCardinality(t) => SetCardinality(go(t))
      case SetDisjoint(t0, t1) => SetDisjoint(go(t0), go(t1))
      case SingletonMultiset(t) => SingletonMultiset(go(t))
      case MultisetUnion(t0, t1) => MultisetUnion(go(t0), go(t1))
      case MultisetIntersection(t0, t1) => MultisetIntersection(go(t0), go(t1))
      case MultisetSubset(t0, t1) => MultisetSubset(go(t0), go(t1))
      case MultisetDifference(t0, t1) => MultisetDifference(go(t0), go(t1))
      case MultisetCardinality(t) => MultisetCardinality(go(t))
      case MultisetCount(t0, t1) => MultisetCount(go(t0), go(t1))
      case MultisetAdd(t1, t2) => MultisetAdd(go(t1), go(t2))
      case MapLookup(t0, t1) => MapLookup(go(t0), go(t1))
      case MapCardinality(t) => MapCardinality(go(t))
      case MapUpdate(t0, t1, t2) => MapUpdate(go(t0), go(t1), go(t2))
      case MapDomain(t) => MapDomain(go(t))
      case MapRange(t) => MapRange(go(t))
      case MagicWandSnapshot(lhs, rhs) => MagicWandSnapshot(go(lhs), go(rhs))
      case Combine(t0, t1) => Combine(go(t0), go(t1))
      case First(t) => First(go(t))
      case Second(t) => Second(go(t))
      case SortWrapper(t, s) => SortWrapper(go(t), s)
//      case Distinct(ts) => Distinct(ts map go)
      case Let(bindings, body) => Let(bindings map (p => go(p._1) -> go(p._2)), go(body))
      case Domain(f, fvf) => Domain(f, go(fvf))
      case Lookup(f, fvf, at) => Lookup(f, go(fvf), go(at))
      case PermLookup(field, pm, at) => PermLookup(field, go(pm), go(at))
      case FieldTrigger(f, fvf, at) => FieldTrigger(f, go(fvf), go(at))

      case PredicateDomain(p, psf) => PredicateDomain(p, go(psf))
      case PredicateLookup(p, psf, args) => PredicateLookup(p, go(psf), args map go)
      case PredicatePermLookup(predname, pm, args) => PredicatePermLookup(predname, go(pm), args map go)
      case PredicateTrigger(p, psf, args) => PredicateTrigger(p, go(psf), args map go)

    }

    val beforeRecursion = pre.applyOrElse(term, identity[Term])

    val afterRecursion =
      if (recursive(term)) recurse(beforeRecursion)
      else beforeRecursion

    post.applyOrElse(afterRecursion, identity[Term]).asInstanceOf[T]
  }

























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
