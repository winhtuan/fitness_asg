package reponsitory;

import java.util.List;
import model.Progress;

public interface IProgressReponsitory {

    List<Progress> readFile();

    void writeFile(List<Progress> progresses);
}
