package sourceCode.Models;

public class Feedback {

    private String feedbackID;
    private String userID;
    private String ISBN;
    private String comment;
    private String rating;

    public Feedback() {
        feedbackID = "";
        userID = "";
        ISBN = "";
        comment = "";
        rating = "";
    }

    public Feedback(String feedbackID, String userID, String ISBN, String comment, String rating) {
        this.feedbackID = feedbackID;
        this.userID = userID;
        this.ISBN = ISBN;
        this.comment = comment;
        this.rating = rating;
    }

    public String getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(String feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
