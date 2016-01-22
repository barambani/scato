package scato
package free
package monad

import clazz.{Apply, Applicative, Bind, Functor, Monad}

trait FreeInstances {
  implicit def freeBind[F[_]](implicit F: TC[F, Functor]): TC[Free[F, ?], Bind] =
    TC[Free[F, ?], Bind](new Bind[Free[F, ?]] {
      override val apply = new Apply[Free[F, ?]] {
        override val functor = new Functor[Free[F, ?]] {
          override def map[A, B](fa: Free[F, A])(f: A => B): Free[F, B] = fa.map(f)
        }
        override def ap[A, B](fa: Free[F, A])(fab: Free[F, A => B]): Free[F, B] = fa.ap(fab)
      }

      override def flatMap[A, B](fa: Free[F, A])(fb: A => Free[F, B]): Free[F, B] = fa.flatMap(fb)
    })

  implicit def freeMonad[F[_]](implicit F: TC[F, Functor]): TC[Free[F, ?], Monad] =
    TC[Free[F, ?], Monad](new Monad[Free[F, ?]] {
      override val bind = freeBind[F].instance
      override val applicative = new Applicative[Free[F, ?]] {
        override val apply = freeBind[F].instance.apply
        override def pure[A](a: A): Free[F, A] = Free.pure[F, A](a)
      }
    })
}
