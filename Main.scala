/**
  * Created by fabiodelia on 17/04/16.
  */
class Main {

}

object Main extends App{
//  val a: BottlesOfBeer = new BottlesOfBeer()
//  a.main(3)

  val a: MazeGenerator = new MazeGenerator()
  // I get a stack overflow exception at about 100
  // TODO: better use of collections
  a.generate(80)

}
