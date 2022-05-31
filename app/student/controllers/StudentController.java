package student.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bson.Document;
import student.models.Student;
import student.models.StudentInputRequest;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import student.services.StudentService;
import student.utils.ApplicationConstants;
import student.utils.Log;
import student.utils.Response;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class StudentController extends Controller implements ApplicationConstants {
    Logger.ALogger logger = Log.Logger;

   @Inject
    StudentService studentService;

    public Result listStudents(int limit, int offset) {
        Response response = new Response();

    List<Document> result= studentService.listStudents(limit, offset);
    int size = studentService.getSize();

            if(result == null) {
                response.setStatus(FAILURE);
                response.setErrorMessage("Error in StudentController listStudents action: null result");
            } else {
                JsonNode jsonNode = new ObjectMapper().convertValue(result, JsonNode.class);
                response.setStatus(SUCCESS);
                response.setData(jsonNode);
                response.setSize(size);
            }
            return ok(Json.toJson(response)).withHeader("Access-Control-Allow-Origin", "*");

    }

    public Result addStudents(Http.Request request) {
        JsonNode payload = request.body().asJson();

        logger.info("Payload received at StudentController.addStudents is: {}", payload);

        Response response = new Response();

        try {
            if(payload == null) {
                logger.info("Error at StudentController.addStudents: Expected JSON data");
                response.setStatus(FAILURE);
                response.setErrorMessage("Expected JSON data");
                return ok(Json.toJson(response)); // converting response class as json
            }

           // StudentInputRequest studentInputRequest = new ObjectMapper().convertValue(payload, StudentInputRequest.class);
            Student studentInputRequest=Json.fromJson(payload, Student.class); // converting pyaload to student


            Document result =  studentService.addStudents(studentInputRequest);

                if(result == null) {
                    response.setStatus(FAILURE);
                    response.setErrorMessage("Error in StudentController addStudents action: null result");
                } else {
//                    JsonNode successEmails = new ObjectMapper().convertValue(result.get(0), JsonNode.class);
//                    JsonNode failureEmails = new ObjectMapper().convertValue(result.get(1), JsonNode.class);
//
//                    ObjectNode resultNode = Json.newObject();
//                    resultNode.put("Success Emails", successEmails);
//                    resultNode.put("Failure Emails", failureEmails);

                    response.setStatus(SUCCESS);
                    response.setData(result);
                }
                return ok(Json.toJson(response));
        } catch (Exception e) {
            logger.error("Exception occurred in StudentController.addStudents: " + e);
        }

        return (ok(Json.toJson(response))).withHeader("Access-Control-Allow-Origin", "*");
    }

    public Result deleteStudent(Http.Request request) {
        JsonNode payload = request.body().asJson();

        logger.info("Payload received at StudentController.deleteStudent is: {}", payload);

        Response response = new Response();

        String studentID = payload.get("studentId").asText();

        boolean result = studentService.deleteStudent(studentID);

            if(!result) {
                response.setStatus(FAILURE);
                response.setErrorMessage("Error in StudentController deleteStudent action: null result");
            } else {
                JsonNode jsonNode;
                if(result) {
                    response.setStatus(SUCCESS);
                   // jsonNode = new ObjectMapper().convertValue("Student Deleted successfully", JsonNode.class);
                    response.setMessage("student deleted successfully");
                } else {
                    response.setStatus(FAILURE);
                   // jsonNode = new ObjectMapper().convertValue("Student does not exist", JsonNode.class);
                    response.setMessage("student not exists");
                }


            }
            return ok(Json.toJson(response)).withHeader("Access-Control-Allow-Origin", "*");

    }

    public Result updateStudents(Http.Request request) {
        JsonNode payload = request.body().asJson();

        logger.info("Payload received at StudentController.updateStudents is: {}", payload);

        Response response = new Response();

        try {
            if (payload == null) {
                logger.error("Error at StudentController.updateStudents: Expected JSON data");
                response.setStatus(FAILURE);
                response.setErrorMessage("Expected JSON data");

                return (ok(Json.toJson(response)));
            }

            // StudentInputRequest studentInputRequest = new ObjectMapper().convertValue(payload, StudentInputRequest.class);
            Student studentInputRequest = Json.fromJson(payload, Student.class);


            Document result = studentService.updateStudents(studentInputRequest);
//                if(error != null) {
//                    logger.error("Error in StudentController updateStudents action: {}", error);
//                    response.setStatus(FAILURE);
//                    response.setErrorMessage("Error in StudentController updateStudents action: " +  error);

            // return ok(Json.toJson(response));
            // }

                if (result == null) {
                    response.setStatus(FAILURE);
                    response.setErrorMessage("Error in StudentController updateStudents action: null result");
                } else {
//                    JsonNode successEmails = new ObjectMapper().convertValue(result.get(0), JsonNode.class);
//                    JsonNode failureEmails = new ObjectMapper().convertValue(result.get(1), JsonNode.class);
//
//                    ObjectNode resultNode = Json.newObject();
//                    resultNode.put("Success Emails", successEmails);
//                    resultNode.put("Failure Emails", failureEmails);

                    response.setStatus(SUCCESS);
                    response.setData(result);
                }
                return ok(Json.toJson(response));

            } catch (Exception e) {
                logger.error("Exception occurred in StudentController.updateStudents: " + e);
            }

            return (ok(Json.toJson(response)));
        }
    public Result searchStudents(String query){
        logger.info("Payload received at StudentController.searchStudents is: {}", query);

        Response response = new Response();

        List<Document> students = studentService.searchStudents(query);

        try{
            if(students == null){
                response.setStatus("failure");
                response.setErrorMessage("error in getting students list");
            }else {
                response.setData(students);
                response.setSize(students.size());
                response.setStatus("success");
            }
            return ok(Json.toJson(response));
        }catch (Exception e){
            logger.error("Exception occurred at StudentController.searchStudents" + e);
        }
        return ok(Json.toJson(response));
    }

        };