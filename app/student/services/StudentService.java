package student.services;

import com.mongodb.client.result.DeleteResult;
 import student.db.MongoOperations;
import student.models.Student;
import org.bson.Document;
import play.Logger;
import student.utils.ApplicationConstants;
import student.utils.Log;
import student.utils.studentUtil;

import javax.inject.Inject;
import java.util.List;

public class StudentService implements ApplicationConstants {
    private static final Logger.ALogger logger = Log.Logger;

    @Inject
    MongoOperations mongoOperations;

    @Inject
    studentUtil studentUtil;

    public List<Document> listStudents(int limit, int offset) {

        Document queryDocument = new Document();
        int skip = (offset - 1)*limit;
        return mongoOperations.find(local, studentsData, queryDocument, limit, skip);
    }

    public int getSize(){
        return mongoOperations.size(local, studentsData);
    }

    public Document addStudents(Student student) {


        student.setStudentID(studentUtil.getRandomId());
        Document queryDocument = new Document("emailId", student.getEmailId());
        List<Document> documents = mongoOperations.find(local, studentsData, queryDocument);
        if(documents.size() == 0){
            Document insertDoc = new Document(queryDocument);
            insertDoc.append("studentId", studentUtil.getRandomId());
            insertDoc.append("name", student.getName());

            insertDoc.append("mobile", student.getMobile());
            insertDoc.append("education", student.getEducation());
            return mongoOperations.insertOne(local, studentsData, insertDoc);
        }

        logger.info("inside add students: {}", student);
        return  null;
    }

    public Boolean deleteStudent(String studentID) {
        Boolean status = true;

        Document queryDocument = new Document("studentId", studentID);
        DeleteResult deleteResult = mongoOperations.deleteOne(local, studentsData, queryDocument);

        if(deleteResult == null || deleteResult.getDeletedCount() == 0) {
            status = false;
        }

        return status;
    }

    public Document updateStudents(Student student) {


        try {
           // for(Student student: studentInputRequest.getStudents()) {
                Document queryDocument = new Document("studentId", student.getStudentId());


              //  Document studentDocument = new ObjectMapper().convertValue(student, Document.class);
               // studentDocument.remove("dateCreated");
              //  studentDocument.remove("lastUpdated");

                Document updateDocument = new Document(queryDocument);
                updateDocument.append("name", student.getName());
//                updateDocument.append("emailId", student.getEmailId());
                updateDocument.append("mobile", student.getMobile());
                updateDocument.append("education", student.getEducation());

              //  updateDocument.append("$currentDate", new Document("lastUpdated", true));

                Document updateResult = mongoOperations.updateOne(local, studentsData, queryDocument, new Document("$set", updateDocument));
                System.out.println(updateResult);
//                if (updateResult == null || updateResult.getModifiedCount() == 0) {
//                    status.get(1).add(student.getEmailId());
//                } else {
//                    status.get(0).add(student.getEmailId());
//                }
          // }
            return updateResult;
        } catch (Exception e) {
            logger.error("Exception occurred in StudentService: " + e);
        }

        return null;
    }
    public List<Document> searchStudents(String query){
        return mongoOperations.searchMany(query, local, studentsData);
    }
}