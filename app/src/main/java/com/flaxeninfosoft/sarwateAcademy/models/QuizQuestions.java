package com.flaxeninfosoft.sarwateAcademy.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class QuizQuestions {
         @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("question")
        @Expose
        private String question;
        @SerializedName("option1")
        @Expose
        private String option1;
        @SerializedName("option2")
        @Expose
        private String option2;
        @SerializedName("option3")
        @Expose
        private String option3;
        @SerializedName("option4")
        @Expose
        private String option4;
        @SerializedName("answer")
        @Expose
        private String answer;
        @SerializedName("quizeid")
        @Expose
        private String quizeid;
        @SerializedName("quiz_catid")
        @Expose
        private String quizCatid;


        public QuizQuestions() {
        }

        public QuizQuestions(String id, String question, String option1, String option2, String option3, String option4, String answer, String quizeid, String quizCatid) {
            super();
            this.id = id;
            this.question = question;
            this.option1 = option1;
            this.option2 = option2;
            this.option3 = option3;
            this.option4 = option4;
            this.answer = answer;
            this.quizeid = quizeid;
            this.quizCatid = quizCatid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getOption1() {
            return option1;
        }

        public void setOption1(String option1) {
            this.option1 = option1;
        }

        public String getOption2() {
            return option2;
        }

        public void setOption2(String option2) {
            this.option2 = option2;
        }

        public String getOption3() {
            return option3;
        }

        public void setOption3(String option3) {
            this.option3 = option3;
        }

        public String getOption4() {
            return option4;
        }

        public void setOption4(String option4) {
            this.option4 = option4;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getQuizeid() {
            return quizeid;
        }

        public void setQuizeid(String quizeid) {
            this.quizeid = quizeid;
        }

        public String getQuizCatid() {
            return quizCatid;
        }

        public void setQuizCatid(String quizCatid) {
            this.quizCatid = quizCatid;
        }

    @Override
    public String toString() {
        return "QuizQuestions{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", option1='" + option1 + '\'' +
                ", option2='" + option2 + '\'' +
                ", option3='" + option3 + '\'' +
                ", option4='" + option4 + '\'' +
                ", answer='" + answer + '\''+
                ", quizeid=" + quizeid +
                ", quizCatid=" + quizCatid +
                '}';
    }
}
