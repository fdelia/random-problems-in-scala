/**
  * Created by fabiodelia on 17/04/16.
  */
class BottlesOfBeer {
  def main(beers: Int): Unit = {
    if (beers <= 0){
      println("given Argument is too low, no beer to drink")
      return
    }

    for ( b <- beers to 0 by -1){
      println (b + " bottles of beer on the wall")
      if (b>0){
        println (b + " bottles of beer")
        println ("Take one down, pass it around")
        println (" ")
      }
    }
  }
}
