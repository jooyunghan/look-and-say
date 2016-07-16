import scala.collection.immutable.Stream.Empty

/**
  * Created by jooyung.han on 7/16/16.
  */
object StreamMain extends App {
  def ant = Stream.iterate(Stream(1))(next)
  def next(s: Stream[Int]) = group(s) flatMap {g => Stream(g.size, g.head)}
  def group[A](as: Stream[A]): Stream[Seq[A]] = as match {
    case Empty => Empty
    case a #:: _ => val (pre,suf) = as.span(_ == a); pre #:: group(suf)
  }

  println(ant(1000000)(1000000))
}
