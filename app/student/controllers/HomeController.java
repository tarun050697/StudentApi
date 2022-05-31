package student.controllers;
//
import play.Logger;
import play.mvc.*;
import student.utils.Log;

//
/*
// * This controller contains an action to handle HTTP requests
// * to the application's home page.
// */
public class HomeController extends Controller {
    private static final Logger.ALogger logger = Log.Logger;

    //
//    /**
 //    * An action that renders an HTML page with a welcome message.
//     * The configuration in the <code>routes</code> file means that
//     * this method will be called when the application receives a
//     * <code>GET</code> request with a path of <code>/</code>.
//     */
  public Result index() {
      logger.info("checkkkkkkkkkkkkkkkk");
       return ok("Hiiiii,my first api is here");
   }
//
}
