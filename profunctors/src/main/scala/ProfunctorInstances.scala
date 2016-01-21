package scato
package profunctors

trait ProfunctorInstances { instances =>
  implicit val function: TC2[Function, Profunctor] = TC2(new Profunctor[Function] {
    override def lmap[A, B, C](ab: A => B)(ca: C => A): C => B = c => ab(ca(c))
    override def rmap[A, B, C](ab: A => B)(bc: B => C): A => C = a => bc(ab(a))
    override def dimap[A, B, C, D](ab: A => B)(ca: C => A)(bd: B => D): C => D = c => bd(ab(ca(c)))
  })
}
