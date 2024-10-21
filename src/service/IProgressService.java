package service;

public interface IProgressService {

    void viewUserProgress(String userId, String courseID);

    void updateUserProgress(String userId, String courseID);
}
