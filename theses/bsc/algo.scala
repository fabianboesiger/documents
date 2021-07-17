private def findJoinPoint(queueInit: Iterable[SilverBlock],
                    visitedInit: Iterable[SilverBlock],
                    // We never enqueue loopHeads which we already have seen.
                    // This would lead to non-termination.
                    loopHeadsSeen: Iterable[SilverBlock],
                    getNext: SilverBlock => Iterable[SilverBlock])
                    : (Option[SilverBlock], mutable.Map[SilverBlock, SilverBlock]) = {

    var queue = mutable.Queue.from(queueInit)
    var visited: Vector[SilverBlock] = Vector.from(visitedInit)
    val map = mutable.Map[SilverBlock, SilverBlock]()
    var loopHeads: Vector[SilverBlock] = Vector.from(loopHeadsSeen)

    while (queue.nonEmpty) {
      val curr = queue.dequeue()
      val visitNext = curr match {
        case curr =>
          if (!visited.contains(curr)) {
            visited :+= curr
            if (curr.isInstanceOf[SilverLoopHeadBlock]) {
              loopHeads :+= curr
            }
            getNext(curr) match {
              case out @ Seq() => out
              case out @ Seq(_) => out
              case out @ Seq(_, _) =>
                val (joinPoint, innerMap) =
                  findJoinPoint(out.filter(!loopHeads.contains(_)), Seq(curr), loopHeads, getNext)
                map ++= innerMap
                joinPoint match {
                  case Some(joinPoint) =>
                    map += curr -> joinPoint
                  case None => ()
                }
                joinPoint
              case _ => sys.error("At most two outgoing edges expected.")
            }
          } else {
            return (Some(curr), map)
          }
      }

      queue = queue.enqueueAll(visitNext.iterator.filter(!loopHeads.contains(_)))
    }
    (None, map)
}

lazy val joinPoints: mutable.Map[SilverBlock, SilverBlock] = {
    val (jp, map) = findJoinPoint(Seq(entry), Seq.empty, Seq.empty, successors)
    assert(jp.isEmpty)
    map
}