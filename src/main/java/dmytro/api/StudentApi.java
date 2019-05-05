package dmytro.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dmytro.db.StudentDao;
import dmytro.model.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class StudentApi {

    private static StudentDao studentDao = new StudentDao();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response test() {
        Student student = new Student("Ivan", "2000-10-12");
        studentDao.add(student);
        String result = gson.toJson(student);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("get_all_students")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStudents() {
        String result = gson.toJson(studentDao.getAllStudents());
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("get_students_by_id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentById(@PathParam("id") int id) {
        String result = gson.toJson(studentDao.getStudents(id));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("get_students_by_name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentById(@PathParam("name") String name) {
        String result = gson.toJson(studentDao.getByName(name));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("get_students_by_age/{from_age}/{to_age}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByAge(@PathParam("from_age") int fromAge, @PathParam("to_age") int toAge) {
        String result = gson.toJson(studentDao.getStudentsByAge(fromAge, toAge));
        return Response.status(Response.Status.OK).entity(result).build();
    }


    @POST
    @Path("add/{name}/{dataOfBirth}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(@PathParam("name") String name, @PathParam("dataOfBirth") String dataOfBirth ) {
        Student student = new Student(name, dataOfBirth);
        studentDao.add(student);
        String result = gson.toJson(student);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @PUT
    @Path("update")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(String inJson) {
        Student student = gson.fromJson(inJson, Student.class);
        studentDao.updateStudent(student);
        String result = "Updated: " + student;
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @DELETE
    @Path("remove")
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(String inJson) {
        Student student = gson.fromJson(inJson, Student.class);
        studentDao.removeStudent(student);
        String resultText = "Removed: " + student;
        return Response.status(Response.Status.OK).entity(resultText).build();
    }

}
