
package service;

import Model.Admin;

public interface IAdminService extends service<Admin>{
    
    public void add(Admin e);

    public void update(Admin id);

    public void delete(Admin id);
}
