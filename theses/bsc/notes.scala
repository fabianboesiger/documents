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