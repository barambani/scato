package scato
package clazz

trait MonadClass[M[_]] extends BindClass[M] with ApplicativeClass[M] { self =>
  final val monad: Monad[M] = new Monad[M] {
    def applicative: Applicative[M] = self.applicative
    def bind: Bind[M] = self.bind
  }
}

