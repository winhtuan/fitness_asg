
package service;

import model.Coach;

public interface ICoachService extends service<Coach>{
    
    public void add(Coach e);

    public void update(Coach id);

    public void delete(Coach id);
}
