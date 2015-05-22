import scala.io.Source

/**
 * Created by king on 15-5-20.
 */

// reference:
// http://en.wikipedia.org/wiki/Smith%E2%80%93Waterman_algorithm
// smith waterman algorithm
// and https://github.com/s0897918/SparkSW Cheng Ling


// apply affine gap method (http://en.wikipedia.org/wiki/Gap_penalty) A+(B⋅L). gap = o + e * (k - 1)
// Score Matrix = ?????? score matrix

// s1 = db sequence
// s2 = query sequence
class SmithWater(val s1: String, val s2: String, val score: Array[Array[Int]] = Array.ofDim(26, 26), val o: Int = 0, val e: Int = -1) {

  // the size is added by 1 for -
  val m = s1.length + 1
  val n = s2.length + 1

  val H = Array.ofDim[Int](m, n)

  var k = 0

  // define the match and mismatch
  // TODO: gap calculation affine
  def ismatch(a: Char, b: Char) = {
    val gap = -1
//    score is calculated based on the balsum table, for protein
    val s = score(a.toInt - 65)(b.toInt - 65)
//    or based on 2 | -1 for nucleotide?? normal mode
//    val s = if (a==b) 2 else -1
    if (a == b) k=0 else k = k+1
    (s, gap)
  }


  // return maxScore
  def computeMatrix(): Int = {

    var maxScore = 0

//    for simplicity, we treat the MAX(k>=1) { H(i-k, j) + gap } = H(i-1)(j) + gap,
//    MAX(l>=1) { H(i, j-l) + gap } = H(i)(j-1) + gap

//    MAX(k>=1) { H(i-k, j) + gap } deletion
    val E = Array.ofDim[Int](m, n)
//    MAX(l>=1) { H(i, j-l) + gap } insertion
    val F = Array.ofDim[Int](m, n)



    // need to get the route of calculation?
    for (i <- 1 until m) {
      for (j <- 1 until n) {
        // score and gap are two factors determine the currency of sw
        val (s, gap) = ismatch(s1.charAt(i - 1), s2.charAt(j - 1))
//        why gap = -2 or -12? in protein calculation
        F(i)(j) = Array(F(i - 1)(j) - 2, H(i - 1)(j) - 12).max
        E(i)(j) = Array(E(i)(j - 1) - 2, H(i)(j - 1) - 12).max
        H(i)(j) = Array(0, E(i)(j), F(i)(j), H(i-1)(j-1) + s).max
        if (H(i)(j) > maxScore)
          maxScore = H(i)(j)
      }
    }

    maxScore
  }

  def printMatrix(): Unit = {
    H.foreach( row => {
      row.foreach(c => print(c + "\t"))
      println()
    })
  }



}


object SmithWater {

  def main(args: Array[String]) {
    // import the blosum file
    val blosum = Source.fromFile("blosum").getLines().map(row => {
      row.split("\t").map(_.toInt)
    }).toArray

//    assume query sequences have been splitted as lines in queryseq file.
    val queryseq = Source.fromFile("queryseq").getLines().toArray

    val dbseqs = Source.fromFile("dbseqs").getLines().map(row => {
      val tmp = row.split(",")
      (tmp(0), tmp(1))
    }).toArray

//    demo is right.
//    dbseqs.map(m => (m._1, new SmithWater(m._2, queryseq(0), blosum).computeMatrix())).sortBy(-_._2).take(10).foreach(println)
//    val sw = new SmithWater("AGCACACA", "ACACACTA", blosum)
//    println(sw.computeMatrix())
//    sw.printMatrix()

  }

}
