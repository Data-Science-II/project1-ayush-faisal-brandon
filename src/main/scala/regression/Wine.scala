package regression 

//ScalaTion imports
import scalation.analytics.{ANCOVA, Regression, QuadRegression, QuadXRegression, 
                            CubicRegression, CubicXRegression, LassoRegression, RidgeRegression}
import scalation.analytics.PredictorMat
import scalation.analytics.Fit
import scalation.columnar_db.Relation 
import scalation.linalgebra.{MatriD, MatrixD, VectorD, VectoD, VectorI}
import scalation.plot.Plot
import scalation.util.banner

//Scala imports 
import scala.Console
import java.io.{File, FileOutputStream}

//other imports 
import helper.Helper

/**
* Regression Analysis for winequality dataset. 
* @author Ayush Kumar
*/
object Wine extends App {

    val red_wine = Relation.apply("data/WineQuality/winequality-red.csv", "red_wine", 
                                domain=null, key = 0, eSep = ";", cPos = null)
    red_wine.show(5)

    val (x,y) = red_wine.toMatriDD(0 to 10, 11)
    val ox = new MatrixD(x.dim1, 1, 1.0) ++^ x // x augmented with vector of ones 
    //Creating all the models
    val MVR = new Regression(ox,y) 
    val Quad = new QuadRegression(x,y)
    val QuadX = new QuadXRegression(x, y) 
    val Cubic = new CubicRegression(x, y) 
    val CubicX = new CubicXRegression(x, y) 
    
    val Ridge = new RidgeRegression(ox, y) 
    val Lasso = new LassoRegression(ox, y)

    banner("Correlation Matrix")
    println(MVR.corrMatrix())

    banner("Simple Linear Model")
    
    var (backRegMVR, _) = MVR.backwardElimAll(cross = false, index_q = Fit.index_rSqBar)
    var (forRegMVR, _) = MVR.forwardSelAll(cross = false, index_q = Fit.index_rSqBar)
    var (stepRegMVR, _) = Helper.stepRegressionAll(MVR, index_q = Fit.index_rSqBar)

    banner("Quadratic Model")

    var (backRegQuad, _) = Quad.backwardElimAll(cross = false, index_q = Fit.index_rSqBar)
    var (forRegQuad, _) = Quad.forwardSelAll(cross = false, index_q = Fit.index_rSqBar)
    var (stepRegQuad, _) = Helper.stepRegressionAll(QuadX, index_q = Fit.index_rSqBar)

    banner("Quadratic Model with Cross-Term")

    var (backRegQuadX, _) = Quad.backwardElimAll(cross = false, index_q = Fit.index_rSqBar)     
    var (forRegQuadX, _) = Quad.forwardSelAll(cross = false, index_q = Fit.index_rSqBar)
    var (stepRegQuadX, _) = Helper.stepRegressionAll(QuadX, index_q = Fit.index_rSqBar)
    
    banner("Cubic Model")

    var (backRegCubic, _) = Cubic.backwardElimAll(cross = false, index_q = Fit.index_rSqBar)
    var (forRegCubic, _) = Cubic.forwardSelAll(cross = false, index_q = Fit.index_rSqBar)
    var (stepRegCubic, _) = Helper.stepRegressionAll(Cubic, index_q = Fit.index_rSqBar)

    banner("Cubic Model with Cross Term")

    var (backRegCubicX, _) = CubicX.backwardElimAll(cross = false, index_q = Fit.index_rSqBar)
    var (forRegCubicX, _) = CubicX.forwardSelAll(cross = false, index_q = Fit.index_rSqBar)
    var (stepRegCubicX, _) = Helper.stepRegressionAll(CubicX, index_q = Fit.index_rSqBar)

} // Wine