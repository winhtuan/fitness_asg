package reponsitory;

import Model.Users;
import java.io.File;
import java.util.List;

public interface IUserReponsitory extends Reponsitory<Users, List<Users>> {

    final String PATH = new File("src\\data\\user.csv").getAbsolutePath();

}
