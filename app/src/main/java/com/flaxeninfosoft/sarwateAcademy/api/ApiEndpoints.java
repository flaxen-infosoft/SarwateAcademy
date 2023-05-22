package com.flaxeninfosoft.sarwateAcademy.api;

public class ApiEndpoints {
    public static final String BASE_URL = "http://103.118.17.202/~mkeducation/MK_API/";

    //Auth
    public static final String STUDENT_LOGIN = "Auth/login.php";//OK
    public static final String REGISTER_USER = "Auth/registerStudent.php";//OK
    public static final String REGISTER_TEACHER = "Auth/registerTeacher.php";//OK
    public static final String TEACHER_LOGIN = "Auth/login_teacher.php";//OK
    public static final String STUDENT_DETAILS_UPDATE = "User/updateStudentDetails.php";//ok
    public static final String TEACHER_DETAILS_UPDATE = "User/updateTeacherDetails.php";//ok

    //course
    public static final String GET_ALL_COURSE = "User/getAllCourse.php";//ok
    public static final String GET_COURSE_BY_ID = "User/getCourseById.php";
    public static final String GET_ALL_CATEGORY = "User/getAllCategory.php";//ok
    public static final String ADD_COURSE = "User/addCourse.php";//ok
    public static final String GET_ALL_STUDENT = "User/getAllStudents.php";//ok
    public static final String GET_STUDENT_BY_ID = "User/getStudentById.php";//ok
    public static final String GET_ALL_STUDY_MATERIAL = "User/getAllStudyMaterials.php";
    public static final String GET_STUDY_MATERIAL_BY_ID = "User/getStudyMaterialById.php";
    public static final String ADD_VIDEO = "User/addVideo.php";//ok
    public static final String ADD_STUDY_MATERIAL = "User/addStudyMaterial.php";//ok

    public static final String GET_STUDY_MATERIAL_BY_COURSE_ID = "User/getStudyMaterialByCourseId.php";//ok
    public static final String GET_VIDEOS_BY_COURSE_ID = "User/getVideosByCourseId.php";//ok
    public static final String MY_COURSE_STUDENT = "User/myCourseStudent.php";
    public static final String MY_COURSE_TEACHER = "User/myCourseTeacher.php";//ok
    public static final String UPDATE_COURSE = "User/updateCourseByCourseID.php";//OK
    public static final String GET_TEACHER_BY_ID = "User/getTeacherDetailsById.php";//OK
    public static final String UPDATE_VIDEO = "User/updateVideo.php";//OK
    public static final String UPDATE_NOTES = "User/updateStudyMaterial.php";//ok
    public static final String PURCHASE_COURSE_REQUEST = "User/coursePurchaseRequest.php";//ok


    //quiz
    public static final String GET_ALL_QUIZ_CATEGORY = "User/GetAllquizcategory.php"; //ok
    public static final String GET_ALL_QUIZ_DATA = "User/getAllQuiz.php"; //ok
    public static final String GET_ALL_QUIZ_QUESTIONS = "User/getAllQuestionBycatId%26quizId.php";
    public static final String GET_MY_QUIZ = "User/myQuiz.php"; //ok
    public static final String GET_QUIZ_RESULT = "/ser/addQuizResult.php";
    public static final String GET_ALL_QUIZ_BY_CATEGORY_ID = "User/GetAllQuizBycategoryId.php";
}
