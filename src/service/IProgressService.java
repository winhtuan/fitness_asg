package service;

import model.Progress;

public interface IProgressService extends Service<Progress>{

    void viewUserProgress(String userId, String courseID);

    void updateUserProgress(String userId, String courseID);
}
