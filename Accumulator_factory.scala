/**
  * Created by fabiodelia on 22/04/16.
  */
class Accumulator_factory {
}


def fac[N](i: N)(implicit num: Numeric[N]) ={
  import num._
  (j: N) => {
    i+j
  }
}

// solution without the Numeric thing, only Int works
def add(a: Int) = {b: Int => a + b}