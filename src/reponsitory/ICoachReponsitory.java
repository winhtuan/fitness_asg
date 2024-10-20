package reponsitory;

import java.io.File;
import java.util.List;
import model.Coach;

public interface ICoachReponsitory extends Reponsitory<Coach, List<Coach>> {

    final String PATH = new File("src\\data\\coach.csv").getAbsolutePath();

}
