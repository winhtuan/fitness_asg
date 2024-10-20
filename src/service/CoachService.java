package service;

import java.util.List;
import model.Coach;
import reponsitory.CoachReponsitory;

public class CoachService implements ICoachService {

    private List<Coach> listCoachs;

    public CoachService() {
        listCoachs = new CoachReponsitory().readFile();
    }
    

    @Override
    public void add(Coach e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Coach id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Coach id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void display() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Coach findByID(String id) {
        for (Coach coach : listCoachs) {
            if (coach.getId().equalsIgnoreCase(id)) {
                return coach;
            }
        }
        return null;
    }

}
