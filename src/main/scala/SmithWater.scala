/**
 * Created by king on 15-5-20.
 */

// http://en.wikipedia.org/wiki/Smith%E2%80%93Waterman_algorithm
// smith waterman algorithm
// apply affine gap method (http://en.wikipedia.org/wiki/Gap_penalty) A+(B⋅L). gap = o + e * (k - 1)
// Score Matrix = ??????


class SmithWater(val s1: String, val s2: String, val score: Array[Array[Int]] = Array.ofDim(26, 15), val o: Int = 0, val e: Int = -1) {

  // the size is added by 1 for -
  val m = s1.length + 1
  val n = s2.length + 1

  val H = Array.ofDim[Int](m, n)

  var k = 0

  // define the match and mismatch
  def ismatch(a: Char, b: Char) = {
    val gap = -1
    if (a == b) {
      k = 0
      (2, gap)
    } else {
      k = k + 1
      (-1, gap)
    }
  }


  // return maxScore
  def computeMatrix(): Int = {

    var maxScore = 0

//    for simplicity, we treat the MAX(k>=1) { H(i-k, j) + gap } = H(i-1)(j) + gap,
//    MAX(l>=1) { H(i, j-l) + gap } = H(i)(j-1) + gap

//     MAX(k>=1) { H(i-k, j) + gap } deletion
//    val E = Array.ofDim[Int](m, n)
//     MAX(l>=1) { H(i, j-l) + gap } insertion
//    val F = Array.ofDim[Int](m, n)


    for (i <- 1 until m) {
      for (j <- 1 until n) {

        // score and gap are two factors determine the currency of sw
        //
        val (s, gap) = ismatch(s1.charAt(i - 1), s2.charAt(j - 1))
        H(i)(j) = Array(0, H(i-1)(j) + gap, H(i)(j-1) + gap, H(i-1)(j-1) + s).max
        if (H(i)(j) > maxScore)
          maxScore = H(i)(j)
      }
    }

    maxScore
  }

  def printMatrix(): Unit = {
    H.foreach( row => {
      row.foreach(c => print(c + " "))
      println()
    })
  }



}


object SmithWater {

  def main(args: Array[String]) {
    val sw = new SmithWater("AGCACACA", "ACACACTA")
    println(sw.computeMatrix())
    sw.printMatrix()



  }

}